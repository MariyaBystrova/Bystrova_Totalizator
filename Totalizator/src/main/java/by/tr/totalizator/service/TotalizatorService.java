package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.bean.CouponBean;
import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.bean.RegisterBetBean;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;
import by.tr.totalizator.service.exception.ServiceException;

public interface TotalizatorService {
	List<Match> getCuponMatches(int cuponId) throws ServiceException;
	List<Match> getCurrentCupon() throws ServiceException;
	int getMinBetAmount(int couponId) throws ServiceException;
	List<Coupon> getEmptyValidCoupons() throws ServiceException;
	boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException;
	boolean registerBet(RegisterBetBean bean) throws ServiceException;
	boolean registerMatch(MatchBean match) throws ServiceException;
	boolean editMatch(MatchBean match) throws ServiceException;
	List<Coupon> getCurrentCoupons() throws ServiceException;
	boolean editMatchResStatus(MatchBean match) throws ServiceException;
	boolean closeCoupon(String couponId) throws ServiceException;
	List<Coupon> getAllCoupons() throws ServiceException;
	Coupon getCouponById(String id) throws ServiceException;
	boolean editCouponInfo(CouponBean coupon) throws ServiceException;
}
