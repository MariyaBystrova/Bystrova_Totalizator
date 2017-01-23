package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.dto.UserDTO;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Implements {@link by.tr.totalizator.command.Command} to register a new user.
 * 
 * @author Mariya Bystrova
 */
public class RegisterUserCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegisterUserCommand.class.getName());

	private final static String FIRST_NAME = "first-name";
	private final static String LAST_NAME = "last-name";
	private final static String SEX = "sex";
	private final static String EMAIL = "email";
	private final static String COUNTRY = "country";
	private final static String CITY = "city";
	private final static String ADDRESS = "address";
	private final static String USER = "user";
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String PASSWORD_AGAIN = "password-again";
	private final static String GO_TO_REGISTRATION_URL = "Controller?command=go-to-registration";
	private final static String RESULT = "resultAdd";

	/**
	 * Provides the service for registration of a new user. Checks the session
	 * and user's privileges to do this operation.
	 * <p>
	 * Forms the request object with the result of editing user's account
	 * details operation. Sets <code>resultAdd</code> session variable as
	 * <code>true</code> in case of the correct ending of this operation and as
	 * <code>false</code> in case of failing this operation.
	 * </p>
	 * 
	 * @return an URL of command to go to the registration page.
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String page;

		ServiceFactory sf = ServiceFactory.getInstance();
		UserService userService = sf.getUserService();
		try {
			byte[] password = request.getParameter(PASSWORD).getBytes();
			byte[] rpassword = request.getParameter(PASSWORD_AGAIN).getBytes();

			UserDTO user = new UserDTO(request.getParameter(FIRST_NAME), request.getParameter(LAST_NAME),
					request.getParameter(SEX), request.getParameter(EMAIL), request.getParameter(COUNTRY),
					request.getParameter(CITY), request.getParameter(ADDRESS), USER, request.getParameter(LOGIN),
					password, rpassword);
			boolean result = userService.registerUser(user);

			request.getSession(false).setAttribute(RESULT, result);
		} catch (ServiceException e) {
			logger.error(e);
			request.getSession(false).setAttribute(RESULT, false);
		}
		page = GO_TO_REGISTRATION_URL;

		return page;

	}

}
