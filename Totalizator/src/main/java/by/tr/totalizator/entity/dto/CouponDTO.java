package by.tr.totalizator.entity.dto;

import java.io.Serializable;

/**
 * Represents Data Transfer Object (DTO) for coupon.
 * 
 * @author Mariya Bystrova
 *
 */
public class CouponDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String startDate;
	private String endDate;
	private String minBetAmount;
	private String jackpot;
	private String pool;
	private String status;

	public CouponDTO() {

	}

	public CouponDTO(String id, String startDate, String endDate, String minBetAmount, String jackpot, String pool,
			String status) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minBetAmount = minBetAmount;
		this.jackpot = jackpot;
		this.pool = pool;
		this.status = status;
	}

	@Override
	public String toString() {
		return "CouponBean [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", minBetAmount="
				+ minBetAmount + ", jackpot=" + jackpot + ", pool=" + pool + ", status=" + status + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jackpot == null) ? 0 : jackpot.hashCode());
		result = prime * result + ((minBetAmount == null) ? 0 : minBetAmount.hashCode());
		result = prime * result + ((pool == null) ? 0 : pool.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CouponDTO other = (CouponDTO) obj;
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
		if (jackpot == null) {
			if (other.jackpot != null)
				return false;
		} else if (!jackpot.equals(other.jackpot))
			return false;
		if (minBetAmount == null) {
			if (other.minBetAmount != null)
				return false;
		} else if (!minBetAmount.equals(other.minBetAmount))
			return false;
		if (pool == null) {
			if (other.pool != null)
				return false;
		} else if (!pool.equals(other.pool))
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
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMinBetAmount() {
		return minBetAmount;
	}

	public void setMinBetAmount(String minBetAmount) {
		this.minBetAmount = minBetAmount;
	}

	public String getJackpot() {
		return jackpot;
	}

	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}

	public String getPool() {
		return pool;
	}

	public void setPull(String pool) {
		this.pool = pool;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
