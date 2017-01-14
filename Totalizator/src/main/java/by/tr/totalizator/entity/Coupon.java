package by.tr.totalizator.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Timestamp startDate;
	private Timestamp endDate;
	private int minBetAmount;
	private int pull;
	private int jackpot;
	private int status;

	public Coupon() {
	}

	public Coupon(Timestamp startDate, Timestamp endDate, int minBetAmount, int pull, int jackpot) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.minBetAmount = minBetAmount;
		this.pull = pull;
		this.jackpot = jackpot;
	}

	public Coupon(int id, Timestamp startDate, Timestamp endDate, int minBetAmount, int pull, int jackpot,
			int status) {
		this(startDate, endDate, minBetAmount, pull, jackpot);
		this.id = id;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", minBetAmount="
				+ minBetAmount + ", pull=" + pull + ", jackpot=" + jackpot + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result + jackpot;
		result = prime * result + minBetAmount;
		result = prime * result + pull;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + status;
		return result;
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
		Coupon other = (Coupon) obj;
		if (endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!endDate.equals(other.endDate)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (jackpot != other.jackpot) {
			return false;
		}
		if (minBetAmount != other.minBetAmount) {
			return false;
		}
		if (pull != other.pull) {
			return false;
		}
		if (startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!startDate.equals(other.startDate)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

	public String getStartDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdf.format(startDate);
	}
	
	public String getEndDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdf.format(endDate);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getMinBetAmount() {
		return minBetAmount;
	}

	public void setMinBetAmount(int minBetAmount) {
		this.minBetAmount = minBetAmount;
	}

	public int getPull() {
		return pull;
	}

	public void setPull(int pull) {
		this.pull = pull;
	}

	public int getJackpot() {
		return jackpot;
	}

	public void setJackpot(int jackpot) {
		this.jackpot = jackpot;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
