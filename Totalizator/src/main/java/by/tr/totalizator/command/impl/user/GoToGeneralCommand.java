package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;

public class GoToGeneralCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=go-to-general";
	private final static String USER = "user";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}

		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {
			page = PageName.USER_PAGE_GENERAL;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}

}
