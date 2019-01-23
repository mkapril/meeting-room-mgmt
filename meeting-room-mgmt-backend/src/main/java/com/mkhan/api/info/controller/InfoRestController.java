package com.mkhan.api.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkhan.api.info.dto.*;;

@RestController
@RequestMapping(path="api/info")
public class InfoRestController {
	@Autowired HoursRepository hRepository;
	@Autowired MinutesRepository mRepository;
	@Autowired MeetingRoomRepository meetingRoomRepository;
	
	@RequestMapping(path="/hoursList")
	public @ResponseBody Iterable<Hours> getEventList() {
		System.out.println("Hour List API START=====");
		return hRepository.findAll();
	}
	
	@RequestMapping(path="/minutesList")
	public @ResponseBody Iterable<Minutes> getMinutesList() {
		System.out.println("Minutes List API START=====");
		return mRepository.findAll();
	}
	
	@RequestMapping(path="/meetingRoomList")
	public @ResponseBody Iterable<MeetingRoom> getMeetingRoomList() {
		System.out.println("MeetingRoom List API START=====");
		return meetingRoomRepository.findAll();
	}
	
}
