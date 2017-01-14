package by.tr.totalizator.bean;

import java.io.Serializable;

public class MatchBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String couponId;
	private String teamOne;
	private String teamTwo;
	private String startDate;
	private String endDate;
	private String status;
	private String result;

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

	
	
	@Override
	public String toString() {
		return "MatchBean [id=" + id + ", name=" + name + ", couponId=" + couponId + ", teamOne=" + teamOne
				+ ", teamTwo=" + teamTwo + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status
				+ ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((teamOne == null) ? 0 : teamOne.hashCode());
		result = prime * result + ((teamTwo == null) ? 0 : teamTwo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchBean other = (MatchBean) obj;
		if (couponId == null) {
			if (other.couponId != null)
				return false;
		} else if (!couponId.equals(other.couponId))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (teamOne == null) {
			if (other.teamOne != null)
				return false;
		} else if (!teamOne.equals(other.teamOne))
			return false;
		if (teamTwo == null) {
			if (other.teamTwo != null)
				return false;
		} else if (!teamTwo.equals(other.teamTwo))
			return false;
		return true;
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
