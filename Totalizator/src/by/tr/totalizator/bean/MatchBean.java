package by.tr.totalizator.bean;

public class MatchBean {
	private String id;
	private String name;
	private String couponId;
	private String teamOne;
	private String teamTwo;
	private String startDate;
	private String endDate;

	public MatchBean() {

	}

	public MatchBean(String name, String teamOne, String teamTwo, String startDate, String endDate) {
		this.name = name;
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public MatchBean(String name, String couponId, String teamOne, String teamTwo, String startDate, String endDate) {
		this(name, teamOne, teamTwo, startDate, endDate);
		this.couponId = couponId;
	}
	
	public MatchBean(String id, String name, String couponId, String teamOne, String teamTwo, String startDate, String endDate){
		this(name, couponId, teamOne, teamTwo, startDate, endDate);
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
