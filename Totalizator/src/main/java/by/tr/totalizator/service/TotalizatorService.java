package by.tr.totalizator.service;

import java.util.List;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;

public interface TotalizatorService {
	List<Match> getCuponMatches(int cuponId) throws ServiceException, ServiceDataException;
	List<Match> getCurrentCupon() throws ServiceException;
	int getMinBetAmount(int couponId) throws ServiceException, ServiceDataException;
	List<Coupon> getEmptyValidCoupons() throws ServiceException;
	boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException, ServiceDataException;
	boolean registerBet(RegisterBetDTO bean) throws ServiceException, ServiceDataException;
	boolean registerMatch(MatchDTO match) throws ServiceException, ServiceDataException;
	boolean editMatch(MatchDTO match) throws ServiceException, ServiceDataException;
	List<Coupon> getCurrentCoupons() throws ServiceException;
	boolean editMatchResStatus(MatchDTO match) throws ServiceException, ServiceDataException;
	boolean closeCoupon(String couponId) throws NotAllFinishedMatchesServiceException, ServiceException, ServiceDataException;
	List<Coupon> getAllCoupons() throws ServiceException;
	Coupon getCouponById(String id) throws ServiceException, ServiceDataException;
	boolean editCouponInfo(CouponDTO coupon) throws ServiceException, ServiceDataException;
}
