package by.tr.totalizator.entity.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Match implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int couponId;
	private String teamOne;
	private String teamTwo;
	private Timestamp startDate;
	private Timestamp endDate;
	private int status;
	private String result;

	public Match() {
	}
	
	public Match(String name, String teamOne, String teamTwo, Timestamp startDate, Timestamp endDate) {
		this.name = name;
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Match(String name, int couponId, String teamOne, String teamTwo, Timestamp startDate, Timestamp endDate) {
		this(name, teamOne, teamTwo, startDate, endDate);
		this.couponId = couponId;
	}

	public Match(int id, String name, int couponId, String teamOne, String teamTwo, Timestamp startDate, Timestamp endDate) {
		this(name, couponId, teamOne, teamTwo, startDate, endDate);
		this.id = id;
	}
	
	

	@Override
	public String toString() {
		return "Match [id=" + id + ", name=" + name + ", couponId=" + couponId + ", teamOne=" + teamOne + ", teamTwo="
				+ teamTwo + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int res = 1;
		res = prime * res + couponId;
		res = prime * res + ((endDate == null) ? 0 : endDate.hashCode());
		res = prime * res + id;
		res = prime * res + ((name == null) ? 0 : name.hashCode());
		res = prime * res + ((startDate == null) ? 0 : startDate.hashCode());
		res = prime * res + ((teamOne == null) ? 0 : teamOne.hashCode());
		res = prime * res + ((teamTwo == null) ? 0 : teamTwo.hashCode());
		res = prime * res + status;
		res = prime * res + ((result == null) ? 0 : result.hashCode());
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Match other = (Match) obj;
		if (couponId != other.couponId) {
			return false;
		}
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (teamOne == null) {
			if (other.teamOne != null) {
				return false;
			}
		} else if (!teamOne.equals(other.teamOne)) {
			return false;
		}
		if (teamTwo == null) {
			if (other.teamTwo != null) {
				return false;
			}
		} else if (!teamTwo.equals(other.teamTwo)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		if (result == null) {
			if (other.result != null) {
				return false;
			}
		} else if (!result.equals(other.result)) {
			return false;
		}
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
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

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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
