package com.mkhan.api.reservation.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name="RESERVATION_MASTER")
public class ReservationMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer reservationId;
	
	private Integer roomId;
	

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	
	public Integer getReservationId() {
		return reservationId;
	}

}
