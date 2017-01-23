package by.tr.totalizator.command.impl.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;
import by.tr.totalizator.tag.bean.JSPListBean;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to
 * the page with current coupon where users might select the results and make a
 * bet. Available for "user".
 * 
 * @author Mariya Bystrova
 */
public class ShowCurrentCouponCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowCurrentCouponCommand.class.getName());

	private final static String CURRENT_URL = "currentUrl";
	private final static String URL = "Controller?command=show-current-coupon";
	private final static String LIST = "list";
	private final static String USER = "user";
	private final static String COUPON = "coupon";
	private final static String MIN_BET_AMOUNT = "minBetAmount";

	/**
	 * Provides the service of forming the page to go to. Checks the session and
	 * user's privileges to go to this page.
	 * <p>
	 * Forms the request object with the list of current coupon's matches,
	 * coupon's unique identifier and minimal bet money amount.
	 * </p>
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName#USER_PAGE_TOTO}, if
	 *         the role of authorized person is "user" or
	 *         {@link by.tr.totalizator.controller.PageName#INDEX_PAGE}, if
	 *         either the session time has expired or an authorized user's role
	 *         is not "user".
	 *         <p>
	 *         Might return
	 *         {@link by.tr.totalizator.controller.PageName#ERROR_PAGE} in case
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
		if (user != null && user.getRole().equals(USER)) {

			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();
			try {
				List<Match> list = totoService.getCurrentCupon();
				if (list != null && !list.isEmpty()) {
					JSPListBean jsp = new JSPListBean(list);
					request.setAttribute(LIST, jsp);
					request.setAttribute(COUPON, list.get(0).getCouponId());

					int minBetAmount = totoService.getMinBetAmount(list.get(0).getCouponId());
					request.setAttribute(MIN_BET_AMOUNT, minBetAmount);
				}
				page = PageName.USER_PAGE_TOTO;
			} catch (ServiceException | ServiceDataException e) {
				logger.error(e);
				page = PageName.ERROR_PAGE;
			}
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}

}
