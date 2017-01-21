package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.MatchDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class CreateMatchCommand implements Command {
	private final static Logger logger = LogManager.getLogger(CreateMatchCommand.class.getName());

	private final static String MATCH_NAME = "match-name";
	private final static String TEAM_ONE = "team-one";
	private final static String TEAM_TWO = "team-two";
	private final static String START_DATE = "match-start-date";
	private final static String END_DATE = "match-end-date";
	private final static String COUPON_ID = "coupon-id";
	private final static String ADMIN_GO_TO_FORM_MATCHES_URL = "Controller?command=show-coupon-matches&coupon-id=";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT = "resultAdd";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return GO_TO_INDEX;
		}
		
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();

			try {
				MatchDTO match = new MatchDTO(request.getParameter(MATCH_NAME), request.getParameter(COUPON_ID),
						request.getParameter(TEAM_ONE), request.getParameter(TEAM_TWO),
						request.getParameter(START_DATE), request.getParameter(END_DATE));

				boolean result = totoService.registerMatch(match);
				request.getSession(false).setAttribute(RESULT, result);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}
			page = ADMIN_GO_TO_FORM_MATCHES_URL + request.getParameter(COUPON_ID) + "&page=" + request.getParameter("page");
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}

}
