package com.mkhan.api.info.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="WORKING_MINUTES")
public class Minutes {
	
	@Id
	private Integer id;
	
	private String minutes;

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
}
