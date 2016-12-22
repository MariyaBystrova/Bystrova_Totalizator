package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.bean.MatchBean;
import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class EditMatchResultCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditMatchResultCommand.class.getName());

	private final static String START_DATE = "match-start-date";
	private final static String END_DATE = "match-end-date";
	private final static String COUPON_ID = "coupon-id";
	private final static String RESULT = "result";
	private final static String STATUS = "status";
	private final static String ADMIN_GO_TO_FORM_MATCHES_URL = "http://localhost:8080/Totalizator/Controller?command=show-coupon-matches&coupon-id=";
	private final static String AMP = "&";
	private final static String EQ = "=";
	private final static String GO_TO_INDEX = "http://localhost:8080/Totalizator/index.jsp";
	private final static String MATCH_ID = "match-id";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT_EDIT = "resultEdit";
	
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
				MatchBean match = new MatchBean();
				match.setId(request.getParameter(MATCH_ID));
				match.setStartDate(request.getParameter(START_DATE));
				match.setEndDate(request.getParameter(END_DATE));
				match.setResult(request.getParameter(RESULT));
				match.setStatus(request.getParameter(STATUS));
				
				boolean result = totoService.editMatchResStatus(match);
				request.getSession(false).setAttribute(RESULT_EDIT, result);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT_EDIT, false);
			}
			page = ADMIN_GO_TO_FORM_MATCHES_URL + request.getParameter(COUPON_ID) + AMP + "page" + EQ + request.getParameter("page");
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
