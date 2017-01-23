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

/**
 * Implements {@link by.tr.totalizator.command.Command} to close the chosen
 * coupon. Available for admin.
 * 
 * @author Mariya Bystrova
 */
public class CloseCouponCommand implements Command {
	private final static Logger logger = LogManager.getLogger(CloseCouponCommand.class.getName());
	private final static String COUPON_ID = "coupon-id";
	private final static String URL = "Controller?command=admin-go-to-edit-current-coupon&coupon-id=";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT_CLOSE_COUPON = "resultCloseCoupon";
	private final static String RESILT_NUMBET_FINISHED_MATCHES = "resultFinishedMatches";

	/**
	 * Provides the closing coupon service that is available for admin. Checks
	 * the session and user's privileges to do this operation.
	 * <p>
	 * Forms the request object with the result of closing coupon operation.
	 * Sets <code>resultCloseCoupon</code> session variable as <code>true</code>
	 * in case of the correct ending of this operation and as <code>false</code>
	 * in case of failing this operation.
	 * </p>
	 * <p>
	 * Sets <code>resultFinishedMatches</code> session variable as
	 * <code>false</code> if not all matches in this coupon has the results.
	 * </p>
	 * 
	 * @return an URL of command to go to the page for editing coupons with
	 *         status "open" or "free", if the role of authorized person is
	 *         "admin", or an index.jsp page, if the session time has expired or
	 *         the authorized user's role is not "admin".
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
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
