package by.tr.totalizator.dao;

import java.sql.Timestamp;
import java.util.List;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;

public interface TotalizatorOperationDAO {
	List<Match> getCuponMatches(int cuponId) throws DAOException;
	List<Match> getCurrentCoupon() throws DAOException;
	List<Coupon> getEmptyValidCoupons() throws DAOException;
	boolean registerCoupon(Timestamp startDate, Timestamp endDate, int minBetAmount) throws DAOException;
	boolean registerMatch(Match match) throws DAOException;
	boolean editMatch(Match match) throws DAOException;
}
