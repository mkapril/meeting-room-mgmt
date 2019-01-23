package com.mkhan.frontend.dto;

public class MeetingRoom {
	
	private Integer roomid;
	
	private String roomname;

	private Integer capacity;
	
	public void setRoomId(Integer roomid) {
		this.roomid = roomid;
	}
	
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
