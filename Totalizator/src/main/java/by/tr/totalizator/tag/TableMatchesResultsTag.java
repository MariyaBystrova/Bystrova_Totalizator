package by.tr.totalizator.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import by.tr.totalizator.entity.bean.Match;
import by.tr.totalizator.tag.bean.JSPListBean;

public class TableMatchesResultsTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private JSPListBean list;

	private String matchName;
	private String teamOne;
	private String teamTwo;
	private String startDate;
	private String endDate;
	private String status;
	private String result;

	public int doStartTag() throws JspTagException {
		int size = new Integer(list.getSize());
		int fullSize = 15;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			pageContext.getOut()
					.write("<table class=\"mytable table table-striped table-bordered table-hover table-condensed\">");
			pageContext.getOut().write("<tr><th>");
			pageContext.getOut().write("#" + "</th>");

			pageContext.getOut().write("<th class='min-width'>" + matchName + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + teamOne + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + teamTwo + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + startDate + "</th>");
			pageContext.getOut().write("<th class='min-width'>" + endDate + "</th>");
			pageContext.getOut().write("<th>" + status + "</th>");
			pageContext.getOut().write("<th>" + result);
			pageContext.getOut().write("</th></tr>");

			for (int i = 0; i < size; i++) {
				Match match = list.getMatchElement();
				pageContext.getOut().write("<tr>");
				pageContext.getOut().write("<form action=\"Controller\" method=\"post\">");
				pageContext.getOut().write("<input type=\"hidden\" name=\"command\" value=\"edit-match-result\" /> ");
				pageContext.getOut().write("<input type='hidden' name='page' value='admin-edit-current-coupon' />");
				pageContext.getOut().write("<input type=\"hidden\" name=\"coupon-id\" value=\""
						+ new Integer(match.getCouponId()).toString() + "\" /> ");
				pageContext.getOut().write("<input type=\"hidden\" name=\"match-id\" value=\""
						+ new Integer(match.getId()).toString() + "\" /> ");
				pageContext.getOut().write("<td>");
				pageContext.getOut().write(new Integer(i + 1).toString());
				pageContext.getOut().write(".</td>");
				pageContext.getOut().write("<td>" + match.getName() + "</td>");
				pageContext.getOut().write("<td>" + match.getTeamOne() + "</td>");
				pageContext.getOut().write("<td>" + match.getTeamTwo() + "</td>");
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
				pageContext.getOut().write("<select name='status'>");
				if(match.getStatus() == 2){
					pageContext.getOut().write("<option value=\"2\" selected>in progress</option>");
					pageContext.getOut().write("<option value=\"5\">finished</option>");
					pageContext.getOut().write("<option value=\"4\">cancelled</option>");
				}else if(match.getStatus() == 5){
					pageContext.getOut().write("<option value=\"2\">in progress</option>");
					pageContext.getOut().write("<option value=\"5\" selected>finished</option>");
					pageContext.getOut().write("<option value=\"4\">cancelled</option>");
				}else if(match.getStatus() == 4){
					pageContext.getOut().write("<option value=\"2\">in progress</option>");
					pageContext.getOut().write("<option value=\"5\">finished</option>");
					pageContext.getOut().write("<option value=\"4\" selected>cancelled</option>");
				}else {
					pageContext.getOut().write("<option value=\"2\">in progress</option>");
					pageContext.getOut().write("<option value=\"5\">finished</option>");
					pageContext.getOut().write("<option value=\"4\">cancelled</option>");
				}
				pageContext.getOut().write("<select>");
				pageContext.getOut().write("</td>");
				
				pageContext.getOut().write("<td>");
				pageContext.getOut().write("<select name='result'>");
				if(match.getResult() == null){
					pageContext.getOut().write("<option value=\"NULL\" selected>-</option>");
					pageContext.getOut().write("<option value=\"1\">1</option>");
					pageContext.getOut().write("<option value=\"x\">x</option>");
					pageContext.getOut().write("<option value=\"2\">2</option>");
				}else if(match.getResult().equals("1")){
					pageContext.getOut().write("<option value=\"NULL\">-</option>");
					pageContext.getOut().write("<option value=\"1\" selected>1</option>");
					pageContext.getOut().write("<option value=\"x\">x</option>");
					pageContext.getOut().write("<option value=\"2\">2</option>");
				}else if(match.getResult().equals("x")){
					pageContext.getOut().write("<option value=\"NULL\">-</option>");
					pageContext.getOut().write("<option value=\"1\">1</option>");
					pageContext.getOut().write("<option value=\"x\" selected>x</option>");
					pageContext.getOut().write("<option value=\"2\">2</option>");
				}else  if(match.getResult().equals("2")){
					pageContext.getOut().write("<option value=\"NULL\">-</option>");
					pageContext.getOut().write("<option value=\"1\">1</option>");
					pageContext.getOut().write("<option value=\"x\">x</option>");
					pageContext.getOut().write("<option value=\"2\" selected>2</option>");
				}
				pageContext.getOut().write("<select>");
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
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
				pageContext.getOut().write("<td></td>");
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
