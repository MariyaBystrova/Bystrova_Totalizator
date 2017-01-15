package by.tr.totalizator.command.impl.admin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.Coupon;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class GoToEditCouponInfoCommand implements Command {
	private final static Logger logger = LogManager.getLogger(GoToEditCouponInfoCommand.class.getName());

	private final static String CURRENT_URL = "currentUrl";
	private final static String URL = "Controller?command=admin-go-to-edit-coupon-info&coupon-id=";
	private final static String COUPON_ID = "coupon-id";
	private final static String COUPON = "coupon";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		request.getSession(false).setAttribute(CURRENT_URL, URL+request.getParameter(COUPON_ID));
		String page = null;

		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();

			try {
				Coupon coupon = totoService.getCouponById(request.getParameter(COUPON_ID));
				request.setAttribute("couponId", coupon.getId());
				request.setAttribute(COUPON, coupon);
				
				page = PageName.ADMIN_EDIT_COUPON_INFO;
			} catch (ServiceException e) {
				logger.error(e);
				page = PageName.ERROR_PAGE;
			}
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}
}
