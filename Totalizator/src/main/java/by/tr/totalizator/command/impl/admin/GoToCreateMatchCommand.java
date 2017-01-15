package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;

public class GoToCreateMatchCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "Controller?command=admin-go-to-create-match";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}
		
		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) { 
			page = PageName.ADMIN_MAKE_MATCH_MENU_PAGE;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}
}
