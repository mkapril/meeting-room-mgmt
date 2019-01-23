package com.mkhan.frontend.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		// 기본 data load
		
		model.addAttribute("hoursList",reservationService.getHoursList());
		model.addAttribute("minutesList",reservationService.getMinutesList());
		model.addAttribute("meetingRoomList", reservationService.getMeetingRoomList());
		
		return "/reservation/ReservationForm";
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, String reservationDate) {
		
		System.out.println("FORNT!"+reservationDate);
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
		//model.addAttribute("meetingRoomList",meetingRoomRepository.findAll());
//		reservationDate="20190122";
//		model.addAttribute("reservationListByDate", reservationService.findReservationByDate(reservationDate));
		return reservationService.findReservationByDate(reservationDate);
	}
	
	@RequestMapping(path="/addReservation")
	public @ResponseBody CustomMessage addReservation(@RequestBody ReservationDTO reservationDTO) {
		System.out.println("addReservation!");
		System.out.println(reservationDTO.toString());
		CustomMessage s = reservationService.addReservation(reservationDTO);
		
		System.out.println("message"+s.getMessage());
		System.out.println("message"+s.getResultCode());
		return s;
//		return "Hello";
	}
	
	
}
