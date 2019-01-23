package com.mkhan.api.reservation.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	
	@Query(value = "select count(1) from RESERVATION " + 
			"where reservation_date = :reservationDate\n" + 
			"and room_Id = :roomId \n" + 
			"and (    (start_time >= :startTime and start_time < :endTime) \n" + 
			"     OR  (end_time > :startTime and end_time <= :endTime )\n" + 
			"     OR  (start_time < :startTime and :startTime < end_time )\n" + 
			"     )", nativeQuery=true)
	int countOverlap(@Param("reservationDate") String reservationDate, @Param("roomId")Integer roomId, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	List<Reservation> findAllByReservationDate (String reservationDate);
}
