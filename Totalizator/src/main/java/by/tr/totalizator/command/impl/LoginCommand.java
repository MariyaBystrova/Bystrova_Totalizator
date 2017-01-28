package by.tr.totalizator.command.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Implements {@link by.tr.totalizator.command.Command} to log in the system
 * under the specified role.
 * 
 * @author Mariya Bystrova
 */
public class LoginCommand implements Command {
	private final static Logger logger = LogManager.getLogger(LoginCommand.class.getName());
	private final static String LOGIN = "signin-login";
	private final static String PASSWORD = "signin-password";
	private final static String ADMIN = "admin";
	private final static String USER = "user";
	private final static String ADMIN_GO_TO_GENERAL = "Controller?command=admin-go-to-general";
	private final static String USER_GO_TO_GENERAL = "Controller?command=go-to-general";
	private final static String GO_TO_INDEX = "index.jsp";

	/**
	 * Provides the log in service that is available for every user. Creates a
	 * session with user's/admin's information if authorization successfully
	 * passed.
	 * 
	 * @return an URL of command to go to general admin's (if the role of
	 *         authorized person is "admin") or user's (if the role of
	 *         authorized person is "user") page. Might return an index.jsp
	 *         page, if the authorization has failed.
	 *         
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String url = null;

		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();

		String login = request.getParameter(LOGIN);
		byte[] password = request.getParameter(PASSWORD).getBytes();

		try {
			User user = userService.authorization(login, password);
			if (user != null) {
				request.getSession(true).setAttribute(USER, user);
				Cookie[] cookieArray = request.getCookies();
				if (cookieArray != null) {
					for (Cookie cookie : cookieArray) {
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
		} catch (ServiceException | ServiceDataException e) {
			logger.error(e);
			url = GO_TO_INDEX;
		}

		return url;
	}

}