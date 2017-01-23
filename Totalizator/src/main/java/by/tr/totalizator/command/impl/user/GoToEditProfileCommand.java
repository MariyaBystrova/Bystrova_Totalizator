package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.bean.User;

/**
 * Implements {@link by.tr.totalizator.command.Command} for a command to go to
 * the page for editing user's profile details. Available for "user".
 * 
 * @author Mariya Bystrova
 */
public class GoToEditProfileCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=go-to-edit-profile&operation=";
	private final static String USER = "user";
	private final static String OPERATION = "operation";

	/**
	 * Provides the service of forming the page to go to. Checks the session and
	 * user's privileges to go to this page.
	 * 
	 * @return {@link by.tr.totalizator.controller.PageName#USER_PAGE_EDIT_PROFILE},
	 *         if the role of authorized person is "user" or
	 *         {@link by.tr.totalizator.controller.PageName#INDEX_PAGE}, if
	 *         either the session time has expired or an authorized user's role
	 *         is not "user".
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL + request.getParameter(OPERATION));

		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {
			request.setAttribute(OPERATION, request.getParameter(OPERATION));
			page = PageName.USER_PAGE_EDIT_PROFILE;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}

}
