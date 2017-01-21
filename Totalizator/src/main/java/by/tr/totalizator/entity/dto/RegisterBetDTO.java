package by.tr.totalizator.entity.dto;

import java.io.Serializable;
import java.util.Map;

public class RegisterBetDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String, String> map;
	private int amount;
	private String creditCardNumber;
	private int userId;
	private String couponId;

	public RegisterBetDTO() {
	}

	public RegisterBetDTO(Map<String, String> map, int amount, String creditCardNumber, int userId, String couponId) {
		this.map = map;
		this.amount = amount;
		this.creditCardNumber = creditCardNumber;
		this.userId = userId;
		this.couponId = couponId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((couponId == null) ? 0 : couponId.hashCode());
		result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + userId;
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
		RegisterBetDTO other = (RegisterBetDTO) obj;
		if (amount != other.amount) {
			return false;
		}
		if (couponId == null) {
			if (other.couponId != null) {
				return false;
			}
		} else if (!couponId.equals(other.couponId)) {
			return false;
		}
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null) {
				return false;
			}
		} else if (!creditCardNumber.equals(other.creditCardNumber)) {
			return false;
		}
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		if (userId != other.userId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegisterBetBean [map=" + map + ", amount=" + amount + ", creditCardNumber=" + creditCardNumber
				+ ", userId=" + userId + ", couponId=" + couponId + "]";
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}
