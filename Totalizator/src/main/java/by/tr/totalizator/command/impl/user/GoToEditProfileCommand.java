package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;

public class GoToEditProfileCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=go-to-edit-profile&operation=";
	private final static String USER = "user";
	private final static String OPERATION = "operation";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL+request.getParameter(OPERATION));
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
