package by.tr.totalizator.command.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;
import by.tr.totalizator.tag.bean.JSPListBean;
import by.tr.totalizator.tag.bean.JspMapBean;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to
 * the page for making a bet. Available for "user".
 * 
 * @author Mariya Bystrova
 */
public class GoToMakeBetCommand implements Command {
	private final static Logger logger = LogManager.getLogger(GoToMakeBetCommand.class.getName());
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=go-to-make-bet";
	private final static String USER = "user";
	private final static String RESULT = "result";
	private final static String AMOUNT = "amount";
	private final static String MAP = "map";
	private final static String LIST = "list";
	private final static String COUPON = "coupon";
	private final static String AMP = "&";
	private final static String EQ = "=";

	/**
	 * Provides the service of forming the page to go to. Checks the session and
	 * user's privileges to go to this page.
	 * <p>
	 * Forms the request object with the list of matches for the current coupon,
	 * the Map of chosen results and entered money amount.
	 * </p>
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName#USER_PAGE_MAKE_BET},
	 *         if the role of authorized person is "user" or
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

		String url = CURRENT_URL;
		for (int i = 1; i <= 15; i++) {
			url = url.concat(AMP + RESULT + new Integer(i).toString() + EQ
					+ request.getParameter(RESULT + new Integer(i).toString()));
		}
		url = url.concat(AMP + AMOUNT + EQ + request.getParameter(AMOUNT));
		request.getSession(false).setAttribute(CURRENT_URL_ATTR, url);

		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {

			Map<String, String> map = new HashMap<String, String>();
			for (int i = 1; i <= 15; i++) {
				map.put(RESULT + new Integer(i).toString(), request.getParameter(RESULT + new Integer(i).toString()));
			}

			JspMapBean mapBean = new JspMapBean();
			mapBean.setMap(map);
			request.setAttribute(MAP, mapBean);
			request.setAttribute(AMOUNT, Integer.parseInt(request.getParameter(AMOUNT)));

			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();
			try {
				List<Match> list = totoService.getCurrentCupon();
				if (list != null && !list.isEmpty()) {
					JSPListBean jsp = new JSPListBean(list);
					request.setAttribute(LIST, jsp);
					request.setAttribute(COUPON, list.get(0).getCouponId());
				}
			} catch (ServiceException e) {
				logger.error(e);
				page = PageName.ERROR_PAGE;
			}

			page = PageName.USER_PAGE_MAKE_BET;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}
}
