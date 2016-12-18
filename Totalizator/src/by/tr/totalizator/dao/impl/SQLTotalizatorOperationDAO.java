package by.tr.totalizator.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.tr.totalizator.dao.TotalizatorOperationDAO;
import by.tr.totalizator.dao.connectionpool.ConnectionPool;
import by.tr.totalizator.dao.connectionpool.exception.ConnectionPoolException;
import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;

public class SQLTotalizatorOperationDAO implements TotalizatorOperationDAO {
	private final static String SELECT_MATCHES_WHERE_CUPONID = "SELECT m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`, m.`start_date`, m.`end_date` FROM `match` AS m WHERE m.`cupon_id`=?;";
	private final static String SELECT_CURRENT_COUPON_MATCHES = "SELECT m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`, m.`start_date`, m.`end_date` FROM `match` AS m JOIN `cupon` AS c ON c.`cupon_id`=m.`cupon_id`WHERE c.`start_date`<= NOW() AND  c.`status_id` = 1 ORDER BY m.`match_id`;";

	// private final static String SELECT_CURRENT_COUPON_MATCHES = "SELECT
	// m.`match_id`, m.`match_name`, m.`cupon_id`, m.`team_one`, m.`team_two`,
	// m.`start_date`, m.`end_date` FROM `match` AS m JOIN `cupon` AS c ON
	// c.`cupon_id`=m.`cupon_id`WHERE c.`start_date`<= NOW() AND c.`end_date`>
	// NOW() AND c.`status_id` = 1;";

	private final static String SELECT_MIN_BET_AMOUNT_BY_COUPONID = "SELECT `min_bet_amount` FROM `cupon` WHERE `cupon_id`=?;";
	private final static String SELECT_FREE_VALID_COUPONS = "SELECT c.`cupon_id`,c.`start_date`,c.`end_date`, c.`min_bet_amount`, c.`cupon_pull`, c.`jackpot`, s.`status_name` FROM `cupon` AS c JOIN `status` AS s ON c.`status_id`=s.`status_id` WHERE c.`start_date`> NOW() AND c.`status_id` = 6;";
	private final static String INSERT_INTO_COUPON = "INSERT INTO `cupon`(`start_date`,`end_date`,`min_bet_amount`,`cupon_pull`,`jackpot`,`status_id`)VALUES(?,?,?,0,0,6);";
	private final static String INSERT_INTO_MATCH = "INSERT INTO `match`(`match_name`,`cupon_id`,`team_one`,`team_two`,`start_date`,`end_date`,`real_result`,`status_id`) SELECT * FROM (SELECT ? as match_name, ? as cupon_id, ? as team_one, ? as team_two, ? as start_date, ? as end_date, NULL, 2) AS tmp WHERE EXISTS (SELECT c.cupon_id FROM `cupon` AS c WHERE DATE_ADD(c.end_date, INTERVAL 2 DAY) > tmp.start_date AND c.end_date < tmp.start_date AND c.status_id=6 ) LIMIT 1;";
	private final static String UPDATE_MATCH = "UPDATE `totalizator`.`match` as m JOIN (SELECT tmp.match_id, tmp.match_name, tmp.team_one, tmp.team_two, tmp.start_date, tmp.end_date FROM (SELECT ? as match_name, ? as match_id, ? as team_one, ? as team_two, ? as start_date, ? as end_date) AS tmp JOIN `match` AS ma ON ma.match_id=tmp.match_id WHERE EXISTS (SELECT c.cupon_id FROM `cupon` AS c WHERE c.cupon_id=ma.cupon_id AND c.status_id=6 AND  DATE_ADD(c.end_date, INTERVAL 2 DAY) > tmp.start_date AND c.end_date < tmp.start_date) LIMIT 1) AS q ON q.match_id=m.match_id SET m.`match_name` = q.match_name, m.`team_one` = q.team_one, m.`team_two` = q.team_two, m.`start_date` = q.start_date, m.`end_date` = q.end_date;";
	private final static String INSERT_INTO_BET = "INSERT INTO `bet` (`user_id`,`cupon_id`,`bet_amount`,`transaction_date`,`creditcard_number`,`win_match_count`,`win_bet_amount`) VALUES(?,?,?,NOW(),?,NULL,NULL);";
	private final static String LAST_INSERTED_ID = "SELECT LAST_INSERT_ID();";
	private final static String INSERT_INTO_USER_BET_DETAIL = "INSERT INTO `user_bet_detail`(`bet_id`,`match_id`,`result`,`win_flag`) VALUES (?,?,?,NULL);";

	@Override
	public List<Match> getCuponMatches(int cuponId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Match> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(SELECT_MATCHES_WHERE_CUPONID);
			ps.setInt(1, cuponId);

			rs = ps.executeQuery();
			while (rs.next()) {
				Match match = new Match(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getTimestamp(6), rs.getTimestamp(7));
				list.add(match);
			}

		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<Match> getCurrentCoupon() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		Statement s = null;
		ResultSet rs = null;

		List<Match> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			s = con.createStatement();
			rs = s.executeQuery(SELECT_CURRENT_COUPON_MATCHES);

			while (rs.next()) {
				Match match = new Match(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getTimestamp(6), rs.getTimestamp(7));
				list.add(match);
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, s, rs);
		}
		return list;
	}

	@Override
	public int getMinBetAmount(int couponId) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(SELECT_MIN_BET_AMOUNT_BY_COUPONID);
			ps.setInt(1, couponId);

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps, rs);
		}
	}

	@Override
	public List<Coupon> getEmptyValidCoupons() throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		Statement s = null;
		ResultSet rs = null;
		List<Coupon> list = new ArrayList<>();
		try {
			con = connectionPool.takeConnection();
			s = con.createStatement();
			rs = s.executeQuery(SELECT_FREE_VALID_COUPONS);

			while (rs.next()) {
				Coupon coupon = new Coupon(rs.getInt(1), rs.getTimestamp(2), rs.getTimestamp(3), rs.getInt(4),
						rs.getInt(5), rs.getInt(6), rs.getString(7));
				list.add(coupon);
			}

		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, s, rs);
		}
		return list;
	}

	@Override
	public boolean registerCoupon(Timestamp startDate, Timestamp endDate, int minBetAmount) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(INSERT_INTO_COUPON);
			ps.setTimestamp(1, startDate);
			ps.setTimestamp(2, endDate);
			ps.setInt(3, minBetAmount);
			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}

		return false;
	}

	@Override
	public boolean registerMatch(Match match) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(INSERT_INTO_MATCH);
			ps.setString(1, match.getName());
			ps.setInt(2, match.getCouponId());
			ps.setString(3, match.getTeamOne());
			ps.setString(4, match.getTeamTwo());
			ps.setTimestamp(5, match.getStartDate());
			ps.setTimestamp(6, match.getEndDate());
			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}
		return false;
	}

	@Override
	public boolean editMatch(Match match) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;

		try {
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(UPDATE_MATCH);
			ps.setString(1, match.getName());
			ps.setInt(2, match.getId());
			ps.setString(3, match.getTeamOne());
			ps.setString(4, match.getTeamTwo());
			ps.setTimestamp(5, match.getStartDate());
			ps.setTimestamp(6, match.getEndDate());
			if (ps.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {
			connectionPool.closeConnection(con, ps);
		}
		return false;
	}

	@Override
	public boolean registerBet(Map<String, String> res, int amount, String creditCardNumber, int userId, int couponId)
			throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		java.sql.Connection con = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;

		try {
			con = connectionPool.takeConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(INSERT_INTO_BET);
			ps.setInt(1, userId);
			ps.setInt(2, couponId);
			ps.setInt(3, amount);
			ps.setString(4, creditCardNumber);
			ps.executeUpdate();
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			

			
			s = con.createStatement();
			rs = s.executeQuery(LAST_INSERTED_ID);
			rs.next();
			int betId = rs.getInt(1);
			try {
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			

			s = con.createStatement();
			rs = s.executeQuery(SELECT_CURRENT_COUPON_MATCHES);
			List<Integer> matchId = new ArrayList<Integer>();
			while (rs.next()) {
				matchId.add(rs.getInt(1));
			}
			try {
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			

			for (int i = 0; i < matchId.size(); i++) {
				ps = con.prepareStatement(INSERT_INTO_USER_BET_DETAIL);
				ps.setInt(1, betId);
				ps.setInt(2, matchId.get(i));
				ps.setString(3, res.get("result" + new Integer(i + 1).toString()));
				ps.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new DAOException("Database access error.", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("Connection pool error.", e);
		} finally {

			connectionPool.closeConnection(con, ps);
		}
		return false;
	}

}
