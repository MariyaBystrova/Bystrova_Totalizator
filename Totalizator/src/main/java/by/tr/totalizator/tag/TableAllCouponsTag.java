package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.tag.bean.JSPListBean;

/**
 * Represents a tag without body which is draws a table with all existing
 * coupons in the system.
 * <p>
 * Draws all coupons and a button to edit them.
 * </p>
 * 
 * <p>
 * Columns:
 * </p>
 * <p>
 * 1. Numeric sequence number (1, 2, 3, ... , 15). The number of matches in the
 * coupon must be equals to 15.
 * </p>
 * <p>
 * 2. Start date and time of the particular coupon.
 * </p>
 * <p>
 * 3. End date and time of the particular coupon.
 * </p>
 * <p>
 * 4. A value of minimal bet amount for the particular coupon.
 * </p>
 * <p>
 * 5. A value of coupon's jackPot.
 * </p>
 * <p>
 * 6. A value of coupon's pool. The pool is a money amount of all bets for the
 * coupon.
 * </p>
 * <p>
 * 7. A gaming status of the particular coupon. Available values: 1 - open -
 * process of gathering bets; 3 - closed - all games results are set and people
 * has there results; 4 - cancelled - 5 or more matches cancelled; 6 - free - in
 * a forming process.
 * </p>
 * 
 * @author Mariya Bystrova
 *
 */
public class TableAllCouponsTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * A variable referencing UseBean component representing a list.
	 */
	private JSPListBean list;
	/**
	 * Start date and time of the particular coupon.
	 */
	private String startDate;
	/**
	 * End date and time of the particular coupon.
	 */
	private String endDate;
	/**
	 * A value of minimal bet amount for the particular coupon.
	 */
	private String minBetAmount;
	/**
	 * A value of coupon's jackPot.
	 */
	private String jackpot;
	/**
	 * A value of coupon's pool. The pool is a money amount of all bets for the
	 * coupon.
	 */
	private String pool;
	/**
	 * A gaming status of the particular coupon.
	 * <p>
	 * Available values: 1 - open - process of gathering bets; 3 - closed - all
	 * games results are set and people has there results; 4 - cancelled - 5 or
	 * more matches cancelled; 6 - free - in a forming process.
	 * </p>
	 */
	private String status;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

		try {
			pageContext.getOut()
					.write("<table class=\"mytable table table-striped table-bordered table-hover table-condensed\">");
			pageContext.getOut().write("<tr><th>");
			pageContext.getOut().write("#" + "</th>");

			pageContext.getOut().write("<th class='min-width'>" + startDate + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + endDate + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + minBetAmount + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + jackpot + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + pool + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + status);
			pageContext.getOut().write("</th></tr>");

			for (int i = 0; i < size; i++) {
				Coupon coupon = list.getCouponElement();
				pageContext.getOut().write("<tr id='line" + i + "'>");
				pageContext.getOut().write("<form action=\"Controller\" method=\"get\">");
				pageContext.getOut()
						.write("<input type=\"hidden\" name=\"command\" value=\"admin-go-to-edit-coupon-info\" /> ");
				pageContext.getOut()
						.write("<input type=\"hidden\" name=\"coupon-id\" value='" + coupon.getId() + "' /> ");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write(".</td>");
				pageContext.getOut().write(
						"<td id='startDate" + i + "'>" + sdf.format(coupon.getStartDate()).replace('T', ' ') + "</td>");
				pageContext.getOut().write(
						"<td id='endDate" + i + "'>" + sdf.format(coupon.getEndDate()).replace('T', ' ') + "</td>");
				pageContext.getOut().write("<td id='minBetAmount" + i + "'>" + coupon.getMinBetAmount() + "</td>");
				pageContext.getOut().write("<td id='jackpot" + i + "'>" + coupon.getJackpot() + "</td>");
				pageContext.getOut().write("<td id='jackpot" + i + "'>" + coupon.getPull() + "</td>");
				pageContext.getOut().write("<td id='status" + i + "'>" + statusNameById(coupon.getStatus()) + "</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<input type=\"submit\" value=\"Edit\" class=\"btn btn-default\"/>");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("</form>");
				pageContext.getOut().write("</tr>");

			}
			pageContext.getOut().write("</table>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

	private String statusNameById(int status) {
		if (status == 1) {
			return "open";
		}
		if (status == 3) {
			return "closed";
		}
		if (status == 4) {
			return "cancelled";
		}
		if (status == 6) {
			return "free";
		}
		return "unknown";
	}

	public JSPListBean getList() {
		return list;
	}

	public void setList(JSPListBean list) {
		this.list = list;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMinBetAmount() {
		return minBetAmount;
	}

	public void setMinBetAmount(String minBetAmount) {
		this.minBetAmount = minBetAmount;
	}

	public String getJackpot() {
		return jackpot;
	}

	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

}
