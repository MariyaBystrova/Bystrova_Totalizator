package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Coupon;
import by.tr.totalizator.tag.bean.JSPListBean;

/**
 * Represents a tag without body which draws a dropDown with coupon's dates from
 * the UseBean list.
 * 
 * 
 * @author Mariya Bystrova
 *
 */
public class CouponDropdownTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * A variable referencing UseBean component representing a list for coupons.
	 */
	private JSPListBean list;
	/**
	 * A value of coupon id, if it is selected.
	 */
	private String activeCouponId;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());
		try {
			if (size != 0) {
				DateFormat med = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.UK);

				pageContext.getOut().write(
						"<select name=\"coupon-id\" id=\"coupon\" class=\"form-control\"  onchange=\"return visibility()\">");
				Coupon coupon = list.getCouponElement();
				if (!activeCouponId.isEmpty() && Integer.parseInt(activeCouponId) == coupon.getId()) {
					pageContext.getOut()
							.write("<option value=\"" + coupon.getId() + "\" selected>"
									+ med.format(coupon.getStartDate()).toString() + " - "
									+ med.format(coupon.getEndDate()).toString() + "</option>");
				} else {
					pageContext.getOut()
							.write("<option value=\"" + coupon.getId() + "\" >"
									+ med.format(coupon.getStartDate()).toString() + " - "
									+ med.format(coupon.getEndDate()).toString() + "</option>");
				}
				for (int i = 1; i < size; i++) {
					coupon = list.getCouponElement();
					if (!activeCouponId.isEmpty() && Integer.parseInt(activeCouponId) == coupon.getId()) {
						pageContext.getOut()
								.write("<option value=\"" + coupon.getId() + "\" selected>"
										+ med.format(coupon.getStartDate()).toString() + " - "
										+ med.format(coupon.getEndDate()).toString() + "</option>");
					} else {
						pageContext.getOut()
								.write("<option value=\"" + coupon.getId() + "\">"
										+ med.format(coupon.getStartDate()).toString() + " - "
										+ med.format(coupon.getEndDate()).toString() + "</option>");
					}
				}
				pageContext.getOut().write("</select>");
			} else {
				pageContext.getOut().write("<select name=\"coupon-id\" id=\"coupon\" class=\"form-control\">");
				pageContext.getOut().write("<option value=\"\" selected disabled>No valid coupons found</option>");
				pageContext.getOut().write("</select>");
			}
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}

	public JSPListBean getList() {
		return list;
	}

	public void setList(JSPListBean list) {
		this.list = list;
	}

	public String getActiveCouponId() {
		return activeCouponId;
	}

	public void setActiveCouponId(String activeCouponId) {
		this.activeCouponId = activeCouponId;
	}

}
