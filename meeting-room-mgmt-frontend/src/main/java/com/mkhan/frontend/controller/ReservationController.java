package com.mkhan.frontend.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mkhan.common.dto.Reservation;
import com.mkhan.common.dto.ReservationDTO;
import com.mkhan.common.CustomMessage;

@Controller
@RequestMapping(path="reservation")
public class ReservationController {
	
	@Autowired ReservationService reservationService;

	@RequestMapping(path="/reservationForm")
	public String reservationForm(Model model) {
		
		String view = "/reservation/ReservationForm";
		// 기본 data load
		try {
			model.addAttribute("hoursList",reservationService.getHoursList());
			model.addAttribute("minutesList",reservationService.getMinutesList());
			model.addAttribute("meetingRoomList", reservationService.getMeetingRoomList());
		} catch (Exception e ) {
			e.printStackTrace();
			view = "error/500"; // backend 응답이 없을 경우 
		}
		
		return view;
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, String reservationDate) {
		
		String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE).toString();
		
		if (reservationDate == null) {
			reservationDate = today;
		}
		model.addAttribute("reservationDate",reservationDate);
		model.addAttribute("hoursList",reservationService.getHoursList());
		model.addAttribute("minutesList",reservationService.getMinutesList());
		model.addAttribute("meetingRoomList", reservationService.getMeetingRoomList());
		model.addAttribute("reservationListByDate", reservationService.findReservationByDate(reservationDate));
		return "/reservation/ReservationDashboard";
	}
	
	@GetMapping("/findReservationByDate")
	public @ResponseBody List<Reservation> findReservationByDate(@RequestParam String reservationDate) {
		return reservationService.findReservationByDate(reservationDate);
	}
	
	@RequestMapping(path="/addReservation")
	public @ResponseBody CustomMessage addReservation(@RequestBody ReservationDTO reservationDTO) {
		CustomMessage s = reservationService.addReservation(reservationDTO);
		
		return s;
	}
	
	
}
