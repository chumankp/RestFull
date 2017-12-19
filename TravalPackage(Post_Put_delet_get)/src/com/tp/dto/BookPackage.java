package com.tp.dto;

import java.io.Serializable;

public class BookPackage implements Serializable {
	private static final long serialVersionUID = -7907224846991596207L;
	private String packageName;
	private String name;
	private String mobile;
	private String email;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
