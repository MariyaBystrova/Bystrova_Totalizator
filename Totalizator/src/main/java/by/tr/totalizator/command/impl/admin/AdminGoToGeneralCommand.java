package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.bean.User;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to the
 * general admin's page.
 * 
 * @author Mariya Bystrova
 */
public class AdminGoToGeneralCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=admin-go-to-general";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	/**
	 * Provides the service of forming the page to go to. Checks the session and
	 * user's privileges to go to this page.
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName#ADMIN_PAGE_GENERAL},
	 *         if the role of authorized person is "admin" or
	 *         {@link by.tr.totalizator.controller.PageName#INDEX_PAGE}, if
	 *         either the session time has expired or an authorized user's role
	 *         is not "admin".
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		String page;
		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);

		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {
			page = PageName.ADMIN_PAGE_GENERAL;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}

}
