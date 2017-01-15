package by.tr.totalizator.command.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class LoginCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(LoginCommand.class.getName());

	private final static String LOGIN = "signin-login";
	private final static String PASSWORD = "signin-password";
	private final static String ADMIN = "admin";
	private final static String USER = "user";
	
	private final static String ADMIN_GO_TO_GENERAL = "Controller?command=admin-go-to-general";
	private final static String USER_GO_TO_GENERAL = "Controller?command=go-to-general";
	private final static String GO_TO_INDEX = "index.jsp";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return GO_TO_INDEX;
		}
		String url = null;

		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();

		String login = request.getParameter(LOGIN);
		byte[] password = request.getParameter(PASSWORD).getBytes();

		try {
			User user = userService.authentication(login, password);
			if (user != null) {
				request.getSession(true).setAttribute(USER, user);
				Cookie[] cookieArray = request.getCookies();
				if(cookieArray!=null){
					for(Cookie cookie: cookieArray){
						request.getSession(false).setAttribute(cookie.getName(), cookie.getValue());
					}
				}
				
				if (user.getRole().equals(ADMIN)) {
					url = ADMIN_GO_TO_GENERAL;
				}
				if (user.getRole().equals(USER)) {
					url = USER_GO_TO_GENERAL;
				}
			} else {
				url = GO_TO_INDEX;
			}
		} catch (ServiceException e) {
			logger.error(e);
			url = GO_TO_INDEX;
		}

		return url;
	}

}