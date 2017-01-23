package by.tr.totalizator.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;
import by.tr.totalizator.tag.bean.JSPListBean;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to
 * edit coupons with status "open" or "free". Forms the request object with the
 * list of possible coupons.
 * 
 * @author Mariya Bystrova
 */
public class GoToEditCurrentCouponCommand implements Command {
	private final static Logger logger = LogManager.getLogger(GoToEditCurrentCouponCommand.class.getName());

	private final static String CURRENT_URL = "currentUrl";
	private final static String URL = "Controller?command=admin-go-to-edit-current-coupon";
	private final static String COUPONS = "coupons";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	/**
	 * Provides the service of forming the page to go to. Checks the session and
	 * user's privileges to go to this page. Forms the request object with the
	 * specific coupons list (coupons with status "open"(1) or "free"(6)).
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName.ADMIN_EDIT_CURRENT_COUPON},
	 *         if the role of authorized person is "admin" and the correct
	 *         ending of getting the coupons list or
	 *         {@link by.tr.totalizator.controller.PageName.INDEX_PAGE}, if
	 *         either the session time has expired or an authorized user's role
	 *         is not "admin".
	 *         <p>
	 * 		Might return
	 *         {@link by.tr.totalizator.controller.PageName.ERROR_PAGE} in case
	 *         of {@link by.tr.totalizator.service.exception.ServiceException}.
	 *         </p>
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		request.getSession(false).setAttribute(CURRENT_URL, URL);
		String page = null;

		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();

			try {
				List<Coupon> list = totoService.getCurrentCoupons();
				JSPListBean jsp = new JSPListBean(list);
				request.setAttribute(COUPONS, jsp);

				page = PageName.ADMIN_EDIT_CURRENT_COUPON;
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
