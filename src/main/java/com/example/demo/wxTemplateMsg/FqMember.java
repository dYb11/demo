/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.example.demo.wxTemplateMsg;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息Entity
 */
public class FqMember {
	
	private static final long serialVersionUID = 1L;

	private String mobile;		// mobile
	private String idNo;		// id_no
	private String name;		// name
	private String type;		// type
	private String idAuth;		// id_auth
	private String lastLoginIp; //最新一次登录IP
	private BigDecimal checkCredits; //签到授信额度
	private Date lastCheck; // 上一次签到
	private String age;//年龄

	private String openId;//年龄
	private String wxNickName;//年龄

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIdAuth() {
		return idAuth;
	}

	public void setIdAuth(String idAuth) {
		this.idAuth = idAuth;
	}

	public BigDecimal getCheckCredits() {
		return checkCredits;
	}

	public void setCheckCredits(BigDecimal checkCredits) {
		this.checkCredits = checkCredits;
	}



	public String getSex(){
        return "-";
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}
}