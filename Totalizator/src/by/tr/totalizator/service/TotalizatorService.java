package by.tr.totalizator.service;

import java.util.List;
import java.util.Map;

import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.Match;
import by.tr.totalizator.service.exception.ServiceException;

public interface TotalizatorService {
	List<Match> getCuponMatches(int cuponId) throws ServiceException;
	List<Match> getCurrentCupon() throws ServiceException;
	List<Coupon> getEmptyValidCoupons() throws ServiceException;
	boolean registerCoupon(String startDate, String endDate, int minBetAmount) throws ServiceException;
	boolean registerBet(Map<String, String[]> params) throws ServiceException;
	boolean registerMatch(MatchBean match) throws ServiceException;
	boolean editMatch(MatchBean match) throws ServiceException;
//	boolean editProfileUser(User user) throws ServiceException;
}
