package by.tr.totalizator.command.impl.user;

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

public class ShowCurrentCouponCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowCurrentCouponCommand.class.getName());

	private final static String CURRENT_URL = "currentUrl";
	private final static String URL = "Controller?command=show-current-coupon";
	private final static String LIST = "list";
	private final static String USER = "user";
	private final static String COUPON = "coupon";
	private final static String MIN_BET_AMOUNT = "minBetAmount";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return PageName.INDEX_PAGE;
		}
		
		request.getSession(false).setAttribute(CURRENT_URL, URL);

		String page = null;

		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {

			ServiceFactory factory = ServiceFactory.getInstance();
			TotalizatorService totoService = factory.getTotaliztorService();
			try {
				List<Match> list = totoService.getCurrentCupon();
				if (list != null && !list.isEmpty()) {
					JSPListBean jsp = new JSPListBean(list);
					request.setAttribute(LIST, jsp);
					request.setAttribute(COUPON, list.get(0).getCouponId());
					
					int minBetAmount = totoService.getMinBetAmount(list.get(0).getCouponId());
					request.setAttribute(MIN_BET_AMOUNT, minBetAmount);
				} 
				
				page = PageName.USER_PAGE_TOTO;
			} catch (ServiceException e) {
				logger.error(e);
				page = PageName.ERROR_PAGE;
			}
		} else {
			page = PageName.INDEX_PAGE;
		}
		return page;

	}

}
