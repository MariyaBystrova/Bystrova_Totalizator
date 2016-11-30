package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;

public class AdminGoToGeneralCommand implements Command {
	private final static String CURRENT_URL_ATTR = "currentUrl";
	private final static String CURRENT_URL = "http://localhost:8080/Totalizator/Controller?command=admin-go-to-general";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response){
		request.getSession(false).setAttribute(CURRENT_URL_ATTR, CURRENT_URL);
		
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) { 
			page = PageName.ADMIN_PAGE_GENERAL;
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}

}
