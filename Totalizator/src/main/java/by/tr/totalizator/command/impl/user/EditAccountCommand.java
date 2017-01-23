package by.tr.totalizator.command.impl.user;

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
 * Implements {@link by.tr.totalizator.command.Command} to edit user's account
 * details (password). Available for "user".
 * 
 * @author Mariya Bystrova
 */
public class EditAccountCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegisterUserCommand.class.getName());

	private final static String PASSWORD = "password";
	private final static String PASSWORD_AGAIN = "password-again";
	private final static String USER = "user";
	private final static String GO_TO_EDIT_PROFILE_URL = "Controller?command=go-to-edit-profile&operation=";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String RESULT = "resultEdit";
	private final static String OPERATION = "operation";

	/**
	 * Provides the service for editing user's information details that is
	 * available for "user". Checks the session and user's privileges to do this
	 * operation.
	 * <p>
	 * Forms the request object with the result of editing user's account
	 * details operation. Sets <code>resultEdit</code> session variable as
	 * <code>true</code> in case of the correct ending of this operation and as
	 * <code>false</code> in case of failing this operation.
	 * </p>
	 * 
	 * @return an URL of command to go to the page editing user's profile, if
	 *         the role of authorized person is "user", or an index.jsp page, if
	 *         the session time has expired or the authorized user's role is not
	 *         "user".
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return GO_TO_INDEX;
		}

		String page;
		User user = (User) request.getSession(false).getAttribute(USER);

		if (user != null && user.getRole().equals(USER)) {
			ServiceFactory sf = ServiceFactory.getInstance();
			UserService userService = sf.getUserService();

			try {
				byte[] password = request.getParameter(PASSWORD).getBytes();
				byte[] rpassword = request.getParameter(PASSWORD_AGAIN).getBytes();

				boolean result = userService.editUserAccountInfo(password, rpassword, user.getId());

				request.getSession(false).setAttribute(RESULT, result);

			} catch (ServiceException | ServiceDataException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}

			page = GO_TO_EDIT_PROFILE_URL + request.getParameter(OPERATION);
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
