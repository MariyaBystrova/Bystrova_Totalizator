package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class EditCouponInfoCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditCouponInfoCommand.class.getName());

	private final static String COUPON_ID = "coupon-id";
	private final static String COUPON_START_DATE = "coupon-start-date";
	private final static String COUPON_END_DATE = "coupon-end-date";
	private final static String COUPON_MIN_BET_AMOUNT = "coupon-min-bet-amount";
	private final static String JACKPOT = "coupon-jackpot";
	private final static String STATUS = "coupon-status";
	private final static String PULL = "coupon-pull";
	private final static String URL = "Controller?command=admin-go-to-edit-coupon-info&coupon-id=";
	private final static String INDEX_URL = "index.jsp";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT = "resultEdit";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return INDEX_URL;
		}
		
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {
			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();
			try {
				CouponDTO coupon = new CouponDTO(request.getParameter(COUPON_ID),
												request.getParameter(COUPON_START_DATE), 
												request.getParameter(COUPON_END_DATE), 
												request.getParameter(COUPON_MIN_BET_AMOUNT),
												request.getParameter(JACKPOT),
												request.getParameter(PULL),
												request.getParameter(STATUS)
												);
				boolean result = totoService.editCouponInfo(coupon);
				
				request.getSession(false).setAttribute(RESULT, result);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}
			page = URL+request.getParameter(COUPON_ID);
		} else {
			page = INDEX_URL;
		}
		return page;
	}

}
