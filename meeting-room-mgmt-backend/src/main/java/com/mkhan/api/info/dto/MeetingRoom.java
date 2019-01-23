package com.mkhan.api.info.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="MEETINGROOM_INFO")
public class MeetingRoom {
	
	@Id
	private Integer roomid;
	
	private String roomname;

	private Integer capacity;
	
	public Integer getRoomId() {
		return roomid;
	}

	public String getRoomName() {
		return roomname;
	}

	public void setRoomName(String roomname) {
		this.roomname = roomname;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	
}
