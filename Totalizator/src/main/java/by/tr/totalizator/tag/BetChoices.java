package by.tr.totalizator.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.tag.bean.JSPListBean;
import by.tr.totalizator.tag.bean.JspMapBean;

/**
 * Represents a tag without body which draws a table of bet's chosen results in
 * the page of confirmation.
 * 
 * <p>
 * Columns:
 * </p>
 * <p>
 * 1. Numeric sequence number (1, 2, 3, ... , 15). The number of matches in the
 * coupon must be equals to 15.
 * </p>
 * <p>
 * 2. Start date and time of the particular match.
 * </p>
 * <p>
 * 3. Teams (written in the way: team one - team two).
 * </p>
 * <p>
 * 4. The chosen result.
 * </p>
 * 
 * @author Mariya Bystrova
 *
 */
public class BetChoices extends TagSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * A variable referencing UseBean component representing a map.
	 */
	private JspMapBean map;
	/**
	 * A variable referencing UseBean component representing a list.
	 */
	private JSPListBean list;
	/**
	 * Teams (written in the way: team one - team two).
	 */
	private String teams;
	/**
	 * The chosen result.
	 */
	private String result;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());

		try {
			pageContext.getOut()
					.write("<table class=\"table table-striped table-bordered table-hover table-condensed\">");
			pageContext.getOut().write("<tr><th>");
			pageContext.getOut().write("#</th>");
			pageContext.getOut().write("<th>" + teams + "</th>");
			pageContext.getOut().write("<th>" + result);
			pageContext.getOut().write("</th></tr>");
			for (int i = 0; i < size; i++) {
				Match match = list.getMatchElement();
				if (i == 0) {
					pageContext.getOut()
							.write("<input type=\"hidden\" name=\"couponId\" value=\"" + match.getCouponId() + "\"");
				}
				pageContext.getOut().write("<tr>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(match.getTeamOne() + " - " + match.getTeamTwo());
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				if (map.getElement("result" + new Integer(i + 1).toString()) != null) {
					pageContext.getOut().write(map.getElement("result" + new Integer(i + 1).toString()));
				} else {
					pageContext.getOut().write("");
				}
				pageContext.getOut().write("<input type=\"hidden\" name=\"result" + new Integer(i + 1).toString()
						+ "\" value=\"" + map.getElement("result" + new Integer(i + 1).toString()) + "\"");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("</tr>");
			}
			pageContext.getOut().write("</table>");
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

	public JspMapBean getMap() {
		return map;
	}

	public void setMap(JspMapBean map) {
		this.map = map;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
