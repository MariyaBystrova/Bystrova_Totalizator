package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceException;

public interface TotalizatorService {
	List<Match> getCuponMatches(int cuponId) throws ServiceException;
	List<Match> getCurrentCupon() throws ServiceException;
	int getMinBetAmount(int couponId) throws ServiceException;
	List<Coupon> getEmptyValidCoupons() throws ServiceException;
	boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException;
	boolean registerBet(RegisterBetDTO bean) throws ServiceException;
	boolean registerMatch(MatchDTO match) throws ServiceException;
	boolean editMatch(MatchDTO match) throws ServiceException;
	List<Coupon> getCurrentCoupons() throws ServiceException;
	boolean editMatchResStatus(MatchDTO match) throws ServiceException;
	boolean closeCoupon(String couponId) throws NotAllFinishedMatchesServiceException, ServiceException;
	List<Coupon> getAllCoupons() throws ServiceException;
	Coupon getCouponById(String id) throws ServiceException;
	boolean editCouponInfo(CouponDTO coupon) throws ServiceException;
}
