package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.tag.bean.JSPListBean;

/**
 * Represents a tag without body which draws a table for receiving bets done
 * with coupon matches and there possible results ("1", "x", "2").
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
 * 4-6. Possible results: 4 - "1", 5 - "x", 6 - "2".
 * </p>
 * 
 * @author Mariya Bystrova
 *
 */
public class TableTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * A variable referencing UseBean component representing a list.
	 */
	private JSPListBean list;

	/**
	 * Start date of the particular match.
	 */
	private String date;
	/**
	 * Teams (written in the way: team one - team two).
	 */
	private String teams;
	/**
	 * Possible result "1" - first team win.
	 */
	private String result1;
	/**
	 * Possible result "x" - draw.
	 */
	private String result2;
	/**
	 * Possible result "2" - second team win.
	 */
	private String result3;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());

		DateFormat med = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.UK);
		try {
			pageContext.getOut()
					.write("<table class=\"table table-striped table-bordered table-hover table-condensed\">");
			pageContext.getOut().write("<tr><th>");
			pageContext.getOut().write("#" + "</th>");

			pageContext.getOut().write("<th>" + date + "</th>");
			pageContext.getOut().write("<th>" + teams + "</th>");
			pageContext.getOut().write("<th>" + result1 + "</th>");
			pageContext.getOut().write("<th>" + result2 + "</th>");
			pageContext.getOut().write("<th>" + result3);
			pageContext.getOut().write("</th></tr>");

			for (int i = 0; i < size; i++) {
				Match match = list.getMatchElement();
				pageContext.getOut().write("<tr>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write(".</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(med.format(match.getStartDate()).toString());
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(match.getTeamOne() + " - " + match.getTeamTwo());
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(
						"<input type=\"checkbox\" name=\"result" + new Integer(i + 1).toString() + "\" value=\"1\">");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(
						"<input type=\"checkbox\" name=\"result" + new Integer(i + 1).toString() + "\" value=\"X\">");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(
						"<input type=\"checkbox\" name=\"result" + new Integer(i + 1).toString() + "\" value=\"2\">");
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String getResult1() {
		return result1;
	}

	public void setResult1(String result1) {
		this.result1 = result1;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}

	public String getResult3() {
		return result3;
	}

	public void setResult3(String result3) {
		this.result3 = result3;
	}
}
