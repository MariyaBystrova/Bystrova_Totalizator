package by.tr.totalizator.entity.dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents Data Transfer Object (DTO) for user.
 * 
 * @author Mariya Bystrova
 *
 */
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String firstName;
	private String lastName;
	private String sex;
	private String email;
	private String country;
	private String city;
	private String address;
	private String role;
	private String login;
	private byte[] password;
	private byte[] repPassword;

	public UserDTO() {
	}
	
	public UserDTO(String fName, String lName, String sex, String email, String country, String city, String address) {
		this.firstName = fName;
		this.lastName = lName;
		this.sex = sex;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
	}

	public UserDTO(String fName, String lName, String sex, String email, String country, String city, String address,
			String role) {
		this(fName, lName, sex, email, country, city, address);
		this.role = role;
	}

	public UserDTO(String fName, String lName, String sex, String email, String country, String city, String address,
			String role, String login, byte[] password, byte[] repPassword) {
		this(fName, lName, sex, email, country, city, address, role);
		this.password = password;
		this.repPassword = repPassword;
		this.login = login;
	}

	public UserDTO(String id, String fName, String lName, String sex, String email, String country, String city,
			String address, String role) {
		this(fName, lName, sex, email, country, city, address, role);
		this.id = id;
	}
	
	

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", sex=" + sex
				+ ", email=" + email + ", country=" + country + ", city=" + city + ", address=" + address + ", role="
				+ role + ", login=" + login + ", password=" + Arrays.toString(password) + ", repPassword="
				+ Arrays.toString(repPassword) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + Arrays.hashCode(repPassword);
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		if (!Arrays.equals(password, other.password)) {
			return false;
		}
		if (!Arrays.equals(repPassword, other.repPassword)) {
			return false;
		}
		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}
		if (sex == null) {
			if (other.sex != null) {
				return false;
			}
		} else if (!sex.equals(other.sex)) {
			return false;
		}
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public void setPassword(byte b) {
		for(int i=0; i<password.length; i++){
			password[i]=b;
		}
	}

	public byte[] getRepPassword() {
		return repPassword;
	}

	public void setRepPassword(byte[] repPassword) {
		this.repPassword = repPassword;
	}
	
	public void setRepPassword(byte b) {
		for(int i=0; i<repPassword.length; i++){
			repPassword[i]=b;
		}
	}

}
