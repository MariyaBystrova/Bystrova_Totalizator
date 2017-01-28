package by.tr.totalizator.dao.factory;

import by.tr.totalizator.dao.SourceInitDAO;
import by.tr.totalizator.dao.BetOperationDAO;
import by.tr.totalizator.dao.CouponOperationDAO;
import by.tr.totalizator.dao.MatchOperationDAO;
import by.tr.totalizator.dao.UserOperationDAO;
import by.tr.totalizator.dao.impl.SQLBetOperationDAO;
import by.tr.totalizator.dao.impl.SQLCouponOperationDAO;
import by.tr.totalizator.dao.impl.SQLInit;
import by.tr.totalizator.dao.impl.SQLMatchOperationDAO;
import by.tr.totalizator.dao.impl.SQLUserOperationDAO;

/**
 * Represents DAO layer factory designed by Factory pattern, based on Singleton,
 * which provides an abstraction.
 * 
 * @author mariya
 *
 */
public class DAOFactory {
	private static final DAOFactory INSTANCE = new DAOFactory();

	private MatchOperationDAO matchOperationDAO = new SQLMatchOperationDAO();
	private CouponOperationDAO couponOperationDAO = new SQLCouponOperationDAO();
	private BetOperationDAO betOperationDAO = new SQLBetOperationDAO();
	private UserOperationDAO userOperationDAO = new SQLUserOperationDAO();
	private SourceInitDAO sourceInitDAO = new SQLInit();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public MatchOperationDAO getMatchOperationDAO() {
		return matchOperationDAO;
	}

	public CouponOperationDAO getCouponOperationDAO() {
		return couponOperationDAO;
	}

	public BetOperationDAO getBetOperationDAO() {
		return betOperationDAO;
	}

	public UserOperationDAO getUserOperationDAO() {
		return userOperationDAO;
	}

	public SourceInitDAO getSourceInitDAO() {
		return sourceInitDAO;
	}

}
