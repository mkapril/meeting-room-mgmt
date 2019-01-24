package com.mkhan.frontend.controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mkhan.common.CustomMessage;
import com.mkhan.frontend.dto.Hours;
import com.mkhan.frontend.dto.MeetingRoom;
import com.mkhan.frontend.dto.Minutes;
import com.mkhan.common.dto.Reservation;
import com.mkhan.common.dto.ReservationDTO;

@Service("reservationService")
public class ReservationService {
	
	@Value("${api.services.url}")
	private String serviceUrl;
	
	private RestTemplate restTemplate;

	@Autowired
	public ReservationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public List<Reservation> findAllReservation() {
		return Arrays.asList(restTemplate.getForObject(
				String.format("%s/api/event/eventList", serviceUrl),
				Reservation[].class));
	}
	
	public CustomMessage addReservation(ReservationDTO reservationDTO) {
		return  restTemplate.postForObject(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO,CustomMessage.class);
	}
	
	public List<Hours> getHoursList() {
		return Arrays.asList(restTemplate.getForObject(
				String.format("%s/api/info/hoursList", serviceUrl),
				Hours[].class));
	}
	
	public List<Minutes> getMinutesList() {
		return Arrays.asList(restTemplate.getForObject(
				String.format("%s/api/info/minutesList", serviceUrl),
				Minutes[].class));
	}
	
	public List<MeetingRoom> getMeetingRoomList() {
		return Arrays.asList(restTemplate.getForObject(
				String.format("%s/api/info/meetingRoomList", serviceUrl),
				MeetingRoom[].class));
	}
	
	public List<Reservation> findReservationByDate(String reservationDate) {
		return Arrays.asList(restTemplate.getForObject(
				String.format("%s/api/reservation/date/%s", serviceUrl,reservationDate),
				Reservation[].class));
	}
	
	
	
}
