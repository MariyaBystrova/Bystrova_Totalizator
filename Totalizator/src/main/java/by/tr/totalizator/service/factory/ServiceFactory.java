package by.tr.totalizator.service.factory;

import by.tr.totalizator.service.SourceInitService;
import by.tr.totalizator.service.BetService;
import by.tr.totalizator.service.CouponService;
import by.tr.totalizator.service.MatchService;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.impl.EditBet;
import by.tr.totalizator.service.impl.EditCoupon;
import by.tr.totalizator.service.impl.EditMatch;
import by.tr.totalizator.service.impl.EditUser;
import by.tr.totalizator.service.impl.SourceInit;

/**
 * Represents service layer factory designed by Factory pattern, based on
 * Singleton, which provides an abstraction.
 * 
 * @author mariya
 *
 */
public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private UserService userService = new EditUser();
	private CouponService couponService = new EditCoupon();
	private MatchService matchService = new EditMatch();
	private BetService betService = new EditBet();
	private SourceInitService sourceInitService = new SourceInit();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public CouponService getCouponService() {
		return couponService;
	}

	public MatchService getMatchService() {
		return matchService;
	}

	public BetService getBetService() {
		return betService;
	}

	public SourceInitService getSourceInitService() {
		return sourceInitService;
	}
}
