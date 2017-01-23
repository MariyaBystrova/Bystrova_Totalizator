package by.tr.totalizator.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.tr.totalizator.command.Command;
import by.tr.totalizator.entity.bean.User;
import by.tr.totalizator.entity.dto.CouponDTO;
import by.tr.totalizator.service.TotalizatorService;
import by.tr.totalizator.service.exception.ServiceDataException;
import by.tr.totalizator.service.exception.ServiceException;
import by.tr.totalizator.service.factory.ServiceFactory;

/**
 * Implements {@link by.tr.totalizator.command.Command} to edit the chosen
 * coupon's details. Available for admin.
 * 
 * @author Mariya Bystrova
 */
public class EditCouponInfoCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditCouponInfoCommand.class.getName());
	private final static String COUPON_ID = "coupon-id";
	private final static String COUPON_START_DATE = "coupon-start-date";
	private final static String COUPON_END_DATE = "coupon-end-date";
	private final static String COUPON_MIN_BET_AMOUNT = "coupon-min-bet-amount";
	private final static String JACKPOT = "coupon-jackpot";
	private final static String STATUS = "coupon-status";
	private final static String PULL = "coupon-pull";
	private final static String URL = "Controller?command=admin-go-to-edit-coupon-info&coupon-id=";
	private final static String INDEX_URL = "index.jsp";
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String RESULT = "resultEdit";

	/**
	 * Provides the service for editing the chosen coupon details. This service
	 * is available for admin. Checks the session and user's privileges to do
	 * this operation.
	 * <p>
	 * Forms the request object with the result of editing coupon information
	 * details operation. Sets <code>resultEdit</code> session variable as
	 * <code>true</code> in case of the correct ending of this operation and as
	 * <code>false</code> in case of failing this operation.
	 * </p>
	 * 
	 * @return an URL of command to go to the page with chosen coupon, if the
	 *         role of authorized person is "admin", or an index.jsp page, if
	 *         the session time has expired or the authorized user's role is not
	 *         "admin".
	 * 
	 * @see by.tr.totalizator.command.Command
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession(false) == null) {
			return INDEX_URL;
		}

		String page;
		User user = (User) request.getSession(false).getAttribute(USER);
		if (user != null && user.getRole().equals(ADMIN)) {
			ServiceFactory sf = ServiceFactory.getInstance();
			TotalizatorService totoService = sf.getTotaliztorService();
			try {
				CouponDTO coupon = new CouponDTO(request.getParameter(COUPON_ID),
						request.getParameter(COUPON_START_DATE), request.getParameter(COUPON_END_DATE),
						request.getParameter(COUPON_MIN_BET_AMOUNT), request.getParameter(JACKPOT),
						request.getParameter(PULL), request.getParameter(STATUS));
				boolean result = totoService.editCouponInfo(coupon);

				request.getSession(false).setAttribute(RESULT, result);
			} catch (ServiceException | ServiceDataException e) {
				logger.error(e);
				request.getSession(false).setAttribute(RESULT, false);
			}
			page = URL + request.getParameter(COUPON_ID);
		} else {
			page = INDEX_URL;
		}
		return page;
	}

}
