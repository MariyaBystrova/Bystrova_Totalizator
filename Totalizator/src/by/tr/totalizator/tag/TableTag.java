package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.Match;
import by.tr.totalizator.tag.bean.JSPListBean;

public class TableTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private JSPListBean list;

	private String date;
	private String teams;
	private String result1;
	private String result2;
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
