package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class EditMatchCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditMatchCommand.class.getName());

	private final static String MATCH_NAME = "match-name";
	private final static String TEAM_ONE = "team-one";
	private final static String TEAM_TWO = "team-two";
	private final static String START_DATE = "match-start-date";
	private final static String END_DATE = "match-end-date";
	private final static String COUPON_ID = "coupon-id";
	private final static String ADMIN_GO_TO_FORM_MATCHES_URL = "http://localhost:8080/Totalizator/Controller?command=show-coupon-matches&coupon-id=";
	private final static String MATCH_ID = "match-id";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT = "resultEdit";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {

			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();

			try {
				MatchBean match = new MatchBean(request.getParameter(MATCH_NAME),
						request.getParameter(TEAM_ONE), request.getParameter(TEAM_TWO),
						request.getParameter(START_DATE), request.getParameter(END_DATE));
				match.setId(request.getParameter(MATCH_ID));
				
				boolean result = totoService.editMatch(match);
				request.getSession(false).setAttribute(RESULT, result);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}
			page = ADMIN_GO_TO_FORM_MATCHES_URL + request.getParameter(COUPON_ID);
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;
	}
}
