package by.tr.totalizator.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import by.tr.totalizator.dao.exception.DAOException;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;

public interface TotalizatorOperationDAO {
	List<Match> getCuponMatches(int cuponId) throws DAOException;
	List<Match> getCurrentCoupon() throws DAOException;
	int getMinBetAmount(int couponId) throws DAOException;
	List<Coupon> getEmptyValidCoupons() throws DAOException;
	boolean registerCoupon(Timestamp startDate, Timestamp endDate, int minBetAmount) throws DAOException;
	boolean registerMatch(Match match) throws DAOException;
	boolean editMatch(Match match) throws DAOException;
	boolean registerBet(Map<String, String> res, int amount, String creditCardNumber, int userId, int couponId) throws DAOException;
	List<Coupon> getCurrentCoupons() throws DAOException;
	boolean editMatchResult(Match match) throws DAOException;
	boolean closeCoupon(int couponId) throws DAOException;
}
