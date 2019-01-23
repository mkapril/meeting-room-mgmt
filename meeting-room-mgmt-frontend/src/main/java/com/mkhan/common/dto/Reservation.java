package com.mkhan.common.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Table(name="RESERVATION")
public class Reservation {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	private Integer roomId;
	
	private String reservationDate;
	
	private String startTime;
	
	private String endTime;
	
	private Integer repeatCount;
	
	private Integer turnCount;
	
	private String name;
	
	private LocalDateTime regDate;
	
	private Integer reservationId;
	
	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(Integer turnCount) {
		this.turnCount = turnCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}
 
	
	
}
