package com.xhu.xuyi.entity;
// Generated 2016-3-16 21:42:57 by Hibernate Tools 3.5.0.Final

/**
 * Oxygentime generated by hbm2java
 */
@SuppressWarnings("serial")
public class OxygenTime implements java.io.Serializable {

	private String oxygenTime;
	private String oxygenValue;

	public OxygenTime() {
	}

	public OxygenTime(String oxygenTime) {
		this.oxygenTime = oxygenTime;
	}

	public OxygenTime(String oxygenTime, String oxygenValue) {
		this.oxygenTime = oxygenTime;
		this.oxygenValue = oxygenValue;
	}

	public String getOxygenTime() {
		return this.oxygenTime;
	}

	public void setOxygenTime(String oxygenTime) {
		this.oxygenTime = oxygenTime;
	}

	public String getOxygenValue() {
		return this.oxygenValue;
	}

	public void setOxygenValue(String oxygenValue) {
		this.oxygenValue = oxygenValue;
	}

}
