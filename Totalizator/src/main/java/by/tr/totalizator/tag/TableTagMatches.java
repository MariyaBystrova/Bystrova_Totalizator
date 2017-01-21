package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.tag.bean.JSPListBean;

public class TableTagMatches extends TagSupport {
	private static final long serialVersionUID = 1L;

	private JSPListBean list;

	private String matchName;
	private String teamOne;
	private String teamTwo;
	private String startDate;
	private String endDate;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());
		int fullSize = 15;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			pageContext.getOut()
					.write("<table class=\"mytable table table-striped table-bordered table-hover table-condensed\">");
			pageContext.getOut().write("<tr><th>");
			pageContext.getOut().write("#" + "</th>");

			pageContext.getOut().write("<th>" + matchName + "</th>");
			pageContext.getOut().write("<th>" + teamOne + "</th>");
			pageContext.getOut().write("<th>" + teamTwo + "</th>");
			pageContext.getOut().write("<th>" + startDate + "</th>");
			pageContext.getOut().write("<th>" + endDate);
			pageContext.getOut().write("</th></tr>");

			for (int i = 0; i < size; i++) {
				Match match = list.getMatchElement();
				pageContext.getOut().write("<tr>");
				pageContext.getOut().write("<form action=\"Controller\" method=\"post\">");
				pageContext.getOut().write("<input type=\"hidden\" name=\"command\" value=\"edit-match\" /> ");
				pageContext.getOut().write("<input type='hidden' name='page' value='admin-form-matches' /> ");
				pageContext.getOut().write("<input type=\"hidden\" name=\"coupon-id\" value=\""
						+ new Integer(match.getCouponId()).toString() + "\" /> ");
				pageContext.getOut().write("<input type=\"hidden\" name=\"match-id\" value=\""
						+ new Integer(match.getId()).toString() + "\" /> ");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write(".</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<textarea name=\"match-name\" rows=\"2\" cols=\"27\" required=\"required\">"
						+ match.getName() + "</textarea>");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<textarea name=\"team-one\" rows=\"2\" cols=\"27\" required=\"required\">"
						+ match.getTeamOne() + "</textarea>");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<textarea name=\"team-two\" rows=\"2\" cols=\"27\" required=\"required\">"
						+ match.getTeamTwo() + "</textarea>");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut()
						.write("<input type=\"datetime-local\" name=\"match-start-date\" placeholder=\"yyyy-mm-dd hh:mm\" value=\""
								+sdf.format(match.getStartDate()) + "\"required=\"required\" />");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut()
						.write("<input type=\"datetime-local\" name=\"match-end-date\" placeholder=\"yyyy-mm-dd hh:mm\" value=\""
								+ sdf.format(match.getEndDate()) + "\"required=\"required\" />");
				pageContext.getOut().write("</td>");
				pageContext.getOut().write("<td>");

				pageContext.getOut().write("<input type=\"submit\" value=\"Edit\" class=\"btn btn-default\" />");

				pageContext.getOut().write("</td>");
				pageContext.getOut().write("</form>");
				pageContext.getOut().write("</tr>");

			}
			for (int i = size; i < fullSize; i++) {

				pageContext.getOut().write("<tr>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write(".</td>");
				pageContext.getOut().write("<form action=\"Controller\" method=\"post\">");
				pageContext.getOut().write("<input type=\"hidden\" name=\"command\" value=\"register-match\" /> ");
				pageContext.getOut().write("<input type='hidden' name='page' value='admin-form-matches' /> ");
				pageContext.getOut().write("<input type=\"hidden\" name=\"coupon-id\" value=\""
						+ pageContext.getRequest().getParameter("coupon-id") + "\" /> ");
				pageContext.getOut().write(
						"<td><textarea name=\"match-name\" rows=\"2\" cols=\"27\" required=\"required\"></textarea></td>");
				pageContext.getOut().write(
						"<td><textarea name=\"team-one\" rows=\"2\" cols=\"27\" required=\"required\"></textarea></td>");
				pageContext.getOut().write(
						"<td><textarea name=\"team-two\" rows=\"2\" cols=\"27\" required=\"required\"></textarea></td>");
				pageContext.getOut().write(
						"<td><input type=\"datetime-local\" name=\"match-start-date\" placeholder=\"yyyy-mm-dd hh:mm\" value=\"\"required=\"required\" /></td>");
				pageContext.getOut().write(
						"<td><input type=\"datetime-local\" name=\"match-end-date\" placeholder=\"yyyy-mm-dd hh:mm\" value=\"\"required=\"required\" /></td>");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<input type=\"submit\" value=\"Add\" class=\"btn btn-default\" />");
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

	public JSPListBean getList() {
		return list;
	}

	public void setList(JSPListBean list) {
		this.list = list;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(String teamOne) {
		this.teamOne = teamOne;
	}

	public String getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(String teamTwo) {
		this.teamTwo = teamTwo;
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

}
