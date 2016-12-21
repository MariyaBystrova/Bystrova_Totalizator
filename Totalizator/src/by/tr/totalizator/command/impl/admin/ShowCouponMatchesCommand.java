package by.tr.totalizator.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.controller.PageName;
import by.tr.totalizator.entity.Match;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;
import by.tr.totalizator.tag.bean.JSPListBean;

public class ShowCouponMatchesCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowCouponMatchesCommand.class.getName());

	private final static String CURRENT_URL = "currentUrl";
	private final static String URL = "http://localhost:8080/Totalizator/Controller?command=show-coupon-matches&coupon-id=";
	private final static String AMP = "&";
	private final static String EQUALS = "=";
	private final static String LIST = "list";
	private final static String USER = "user";
	private final static String COUPON_ID = "coupon-id";
	private final static String COUPON = "coupon";
	private final static String ADMIN = "admin";
	private final static String PAGE = "page";
	private final static String PAGE_FORM_MATCHES = "admin-form-matches";
	private final static String PAGE_EDIT_CURRENT_COUPON = "admin-edit-current-coupon";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}
		
		request.getSession(false).setAttribute(CURRENT_URL, URL + request.getParameter(COUPON_ID) 
												+ AMP + PAGE + EQUALS +  request.getParameter(PAGE) 
												+ AMP + COUPON + EQUALS + request.getParameter(COUPON_ID));

		int couponId = 0;
		String page = null;
		User user = (User) request.getSession(false).getAttribute(USER);

		if (user != null && user.getRole().equals(ADMIN)) {
			if (request.getParameter(COUPON_ID) != null) {
				couponId = Integer.parseInt(request.getParameter(COUPON_ID));
			}
			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();

			try {
				List<Match> list = totoService.getCuponMatches(couponId);
				if (list != null) {
					JSPListBean jsp = new JSPListBean(list);
					request.setAttribute(LIST, jsp);
					
					request.setAttribute(COUPON, couponId);
				}
			} catch (ServiceException e) {
				logger.error(e);
				page = PageName.ERROR_PAGE;
			}
			
			switch(request.getParameter(PAGE)){
			case PAGE_FORM_MATCHES: {page = PageName.ADMIN_FORM_MATCHES_MENU_PAGE; break;}
			case PAGE_EDIT_CURRENT_COUPON: {page = PageName.ADMIN_EDIT_CURRENT_COUPON; break;}
			}
			
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}

}
