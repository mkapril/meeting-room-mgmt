package com.mkhan.api.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkhan.common.CustomMessage;
import com.mkhan.api.reservation.dto.Reservation;
import com.mkhan.api.reservation.dto.ReservationDTO;
import com.mkhan.api.reservation.service.ReservationService;


@RestController
@RequestMapping(path="/api/reservation")
public class ReservationRestController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	public ReservationRestController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	 
	@RequestMapping(path="/add" , method=RequestMethod.POST) // Map ONLY GET Requests
	public @ResponseBody CustomMessage addReservation (@RequestBody ReservationDTO reservationDto) {
		return reservationService.addReservation(reservationDto);
	}
	
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Reservation> getAllReservations() {
		// This returns a JSON or XML with the users
		return reservationService.findAllReservation();
	}
	
	@GetMapping(path="/date/{reservationDate}")
	public @ResponseBody Iterable<Reservation> getAllReservationByDate(@PathVariable String reservationDate){
		System.out.println("reservationDate!" + reservationDate);
		return reservationService.findAllReservationByDate(reservationDate);
	}
	
	@GetMapping(path="/delete")
	public void deleteAllReservation() {
		reservationService.deleteAllReservation();
	}
	

}
