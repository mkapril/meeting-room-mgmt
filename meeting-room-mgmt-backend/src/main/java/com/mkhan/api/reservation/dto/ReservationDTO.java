package com.mkhan.api.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;

@Builder
public class ReservationDTO {

    private Integer id;
    
    private Integer reservationId;
	
	private Integer roomId;
	
	private String reservationDate;
	
	private String startTime;
	
	private String endTime;
	
	private Integer repeatCount;
	
	private Integer turnCount;
	
	private String name;
	
	private String firstreservationDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Autowired
	private LocalDateTime regDate;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getreservationDate() {
		return reservationDate;
	}

	public void setreservationDate(String reservationDate) {
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
		repeatCount = repeatCount == null ? 1 : repeatCount;
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

	public void setRegDate() {
		this.regDate = getDateTime();
	}
	
	public String getNextreservationDate(int turn) {
		
		// 예약 시작일 
		LocalDate thisreservationDate = LocalDate.parse(firstreservationDate,DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate nexDate = thisreservationDate.plus(turn, ChronoUnit.WEEKS);
		
		String nextreservationDate = nexDate.format(DateTimeFormatter.BASIC_ISO_DATE);

		return nextreservationDate;
	}

	public void setNextreservationDate(String firstreservationDate, int turn) {
		

	}
	
	public String getFirstreservationDate() {
		return firstreservationDate;
	}
	
	public void setFirstreservationDate (String firstreservationDate) {
		this.firstreservationDate = firstreservationDate;
	}
	
	public Integer getReservationId() {
		return reservationId;
	}
	
	public void setReservationId (Integer reservationId) {
		this.reservationId = reservationId;
	}
	
	
	 private LocalDateTime getDateTime() {
		 LocalDateTime now = LocalDateTime.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 String nowString = now.format(formatter);
		 return LocalDateTime.parse(nowString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); 

	  }
	 
	 
	 
	 public Reservation createReservation() {
		 
		 return Reservation.builder()
				 .roomId(roomId)
				 .regDate(getDateTime())
				 .reservationDate(reservationDate)
				 .startTime(startTime)
				 .endTime(endTime)
				 .repeatCount(repeatCount)
				 .turnCount(turnCount)
				 .name(name)
				 .build();
	 }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
