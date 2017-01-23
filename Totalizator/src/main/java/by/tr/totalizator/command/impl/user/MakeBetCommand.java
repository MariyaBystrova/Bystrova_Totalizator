package by.tr.totalizator.command.impl.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.RegisterBetDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Implements {@link by.tr.totalizator.command.Command} to register a bet.
 * Available for "user".
 * 
 * @author Mariya Bystrova
 */
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

	/**
	 * Provides the service for making a bet that is available for "user".
	 * Checks the session and user's privileges to do this operation.
	 * <p>
	 * Forms the request object with the result of register bet operation. Sets
	 * <code>resultAdd</code> session variable as <code>true</code> in case of
	 * the correct ending of this operation and as <code>false</code> in case of
	 * failing this operation.
	 * </p>
	 * 
	 * @return an URL of command to go to the page with available current
	 *         coupon, if the role of authorized person is "user", or an
	 *         index.jsp page, if the session time has expired or the authorized
	 *         user's role is not "user".
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
				RegisterBetDTO bean = new RegisterBetDTO(map, amount, creditCard, user.getId(),
						request.getParameter(COUPON_ID));
				boolean result = totoService.registerBet(bean);

				request.getSession(false).setAttribute(RESULT_ADD, result);
			} catch (ServiceException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT_ADD, false);
			}
			page = URL;
		} else {
			page = GO_TO_INDEX;
		}
		return page;
	}
}
