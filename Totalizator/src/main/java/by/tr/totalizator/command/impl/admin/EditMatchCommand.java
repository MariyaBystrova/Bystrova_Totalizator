package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.service.MatchService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Implements {@link by.tr.totalizator.command.Command} to edit match for the
 * chosen coupon. Available for admin.
 * 
 * @author Mariya Bystrova
 */
public class EditMatchCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditMatchCommand.class.getName());

	private final static String MATCH_NAME = "match-name";
	private final static String TEAM_ONE = "team-one";
	private final static String TEAM_TWO = "team-two";
	private final static String START_DATE = "match-start-date";
	private final static String END_DATE = "match-end-date";
	private final static String COUPON_ID = "coupon-id";
	private final static String ADMIN_GO_TO_FORM_MATCHES_URL = "Controller?command=show-coupon-matches&coupon-id=";
	private final static String AMP_PAGE_EQ = "&page=";
	private final static String PAGE = "page";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String MATCH_ID = "match-id";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT = "resultEdit";

	/**
	 * Provides the service for editing match details for the chosen coupon that
	 * is available for admin. Checks the session and user's privileges to do
	 * this operation.
	 * <p>
	 * Forms the request object with the result of editing match details
	 * operation. Sets <code>resultEdit</code> session variable as
	 * <code>true</code> in case of the correct ending of this operation and as
	 * <code>false</code> in case of failing this operation.
	 * </p>
	 * 
	 * @return an URL of command to go to the page with chosen coupon to fulfill
	 *         or edit, if the role of authorized person is "admin", or an
	 *         index.jsp page, if the session time has expired or the authorized
	 *         user's role is not "admin".
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
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory sf = ServiceFactory.getInstance();
			MatchService matchService = sf.getMatchService();

			try {
				MatchDTO match = new MatchDTO(request.getParameter(MATCH_NAME), request.getParameter(TEAM_ONE),
						request.getParameter(TEAM_TWO), request.getParameter(START_DATE),
						request.getParameter(END_DATE));
				match.setId(request.getParameter(MATCH_ID));

				boolean result = matchService.editMatch(match);
				request.getSession(false).setAttribute(RESULT, result);
			} catch (ServiceException | ServiceDataException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}
			page = ADMIN_GO_TO_FORM_MATCHES_URL + request.getParameter(COUPON_ID) + AMP_PAGE_EQ
					+ request.getParameter(PAGE);
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
