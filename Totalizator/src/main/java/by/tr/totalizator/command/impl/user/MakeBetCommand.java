package by.tr.totalizator.command.impl.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.bean.RegisterBetBean;
import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.User;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

public class MakeBetCommand implements Command {
	private final static Logger logger = LogManager.getLogger(MakeBetCommand.class.getName());
	private final static String USER = "user";
	private final static String URL = "Controller?command=show-current-coupon";
	private final static String GO_TO_INDEX = "index.jsp";
	private final static String RESULT = "result";
	private final static String AMOUNT = "amount";
	private final static String COUPON_ID = "couponId";
	private final static String CREDIT_CARD = "credit-card-number";
	private final static String DASH = "-";
	private final static String EMPTY = "";
	private final static String RESULT_ADD = "resultAdd";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return GO_TO_INDEX;
		}

		String page;

		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(USER)) {

			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();

			try {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= 15; i++) {
					map.put(RESULT + new Integer(i).toString(),
							request.getParameter(RESULT + new Integer(i).toString()));
				}

				int amount = 0;
				amount = Integer.parseInt(request.getParameter(AMOUNT));

				String creditCard = request.getParameter(CREDIT_CARD).replace(DASH, EMPTY);
				RegisterBetBean bean = new RegisterBetBean(map, amount, creditCard, user.getId(), request.getParameter(COUPON_ID));
				
				boolean result = totoService.registerBet(bean);

			} catch (ServiceException e) {
				logger.error(e);
			}
			page = URL;
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
