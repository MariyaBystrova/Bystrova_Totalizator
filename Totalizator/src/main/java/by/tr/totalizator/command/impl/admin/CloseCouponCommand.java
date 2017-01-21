package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.NotAllFinishedMatchesServiceException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class CloseCouponCommand implements Command {
	private final static Logger logger = LogManager.getLogger(CloseCouponCommand.class.getName());

	private final static String COUPON_ID = "coupon-id";

	private final static String URL = "Controller?command=admin-go-to-edit-current-coupon&coupon-id=";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT_CLOSE_COUPON = "resultCloseCoupon";
	private final static String RESILT_NUMBET_FINISHED_MATCHES = "resultFinishedMatches";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return GO_TO_INDEX;
		}

		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();

			boolean result;
			try {
				result = totoService.closeCoupon(request.getParameter(COUPON_ID));
				request.getSession(false).setAttribute(RESULT_CLOSE_COUPON, result);
			} catch (NotAllFinishedMatchesServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESILT_NUMBET_FINISHED_MATCHES, false);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT_CLOSE_COUPON, false);
			}

			page = URL + request.getParameter(COUPON_ID);
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
