package by.tr.totalizator.command.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.bean.UserBean;
import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.UserService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class EditProfileCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegisterUserCommand.class.getName());

	private final static String FIRST_NAME = "first-name";
	private final static String LAST_NAME = "last-name";
	private final static String SEX = "sex";
	private final static String EMAIL = "email";
	private final static String COUNTRY = "country";
	private final static String CITY = "city";
	private final static String ADDRESS = "address";

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
				UserBean userBean = new UserBean(request.getParameter(FIRST_NAME), request.getParameter(LAST_NAME),
						request.getParameter(SEX), request.getParameter(EMAIL), request.getParameter(COUNTRY),
						request.getParameter(CITY), request.getParameter(ADDRESS));
				userBean.setId(Integer.toString(user.getId()));

				User userUpdated = userService.editUserPersonalInfo(userBean);

				if (userUpdated != null) {
					user.setFirstName(userUpdated.getFirstName());
					user.setLastName(userUpdated.getLastName());
					user.setSex(userUpdated.getSex());
					user.setEmail(userUpdated.getEmail());
					user.setCountry(userUpdated.getCountry());
					user.setCity(userUpdated.getCity());
					user.setAddress(userUpdated.getAddress());

					request.getSession(false).setAttribute(USER, user);
					request.getSession(false).setAttribute(RESULT, true);
				} else {
					request.getSession(false).setAttribute(RESULT, false);
				}

			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}

			page = GO_TO_EDIT_PROFILE_URL+request.getParameter(OPERATION);
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}

}
