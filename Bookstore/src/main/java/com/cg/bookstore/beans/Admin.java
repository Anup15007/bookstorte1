package com.cg.bookstore.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
public class Admin{
	@Id
	String adminEmailId;
	String adminFullName;
	String password;
	public Admin() {
		super();
	}
	public Admin(String adminEmailId, String password) {
		super();
		this.adminEmailId = adminEmailId;
		this.password = password;
	}
	public Admin(String adminEmailId, String adminFullName, String password) {
		super();
		this.adminEmailId = adminEmailId;
		this.adminFullName = adminFullName;
		this.password = password;
	}
	public String getAdminEmailId() {
		return adminEmailId;
	}
	public void setAdminEmailId(String adminEmailId) {
		this.adminEmailId = adminEmailId;
	}
	public String getAdminFullName() {
		return adminFullName;
	}
	public void setAdminFullName(String fullName) {
		this.adminFullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminEmailId == null) ? 0 : adminEmailId.hashCode());
		result = prime * result + ((adminFullName == null) ? 0 : adminFullName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Admin other = (Admin) obj;
		if (adminEmailId == null) {
			if (other.adminEmailId != null)
				return false;
		} else if (!adminEmailId.equals(other.adminEmailId))
			return false;
		if (adminFullName == null) {
			if (other.adminFullName != null)
				return false;
		} else if (!adminFullName.equals(other.adminFullName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Admin [adminEmailId=" + adminEmailId + ", adminFullName=" + adminFullName + ", password=" + password + "]";
	}
}
