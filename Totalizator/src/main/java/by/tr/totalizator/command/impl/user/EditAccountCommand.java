package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class EditAccountCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegisterUserCommand.class.getName());

	private final static String PASSWORD = "password";
	private final static String PASSWORD_AGAIN = "password-again";

	private final static String USER = "user";
	private final static String GO_TO_EDIT_PROFILE_URL = "http://localhost:8080/Totalizator/Controller?command=go-to-edit-profile&operation=";
	private final static String GO_TO_INDEX = "http://localhost:8080/Totalizator/index.jsp";
	private final static String RESULT = "resultAdd";
	private final static String OPERATION = "operation";

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

			} catch (ServiceException e) {
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
