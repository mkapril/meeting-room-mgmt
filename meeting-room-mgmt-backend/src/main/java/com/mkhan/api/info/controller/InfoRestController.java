package com.mkhan.api.info.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mkhan.api.info.dto.*;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(path="api/info")
public class InfoRestController {
	
	private static final Logger logger = Logger.getLogger(InfoRestController.class.getCanonicalName());
	
	@Autowired HoursRepository hRepository;
	@Autowired MinutesRepository mRepository;
	@Autowired MeetingRoomRepository meetingRoomRepository;
	
	@GetMapping(path="/hoursList")
	public @ResponseBody Iterable<Hours> getEventList() {
		logger.info("Hour List API START=====");
		return hRepository.findAll();
	}
	
	@GetMapping(path="/minutesList")
	public @ResponseBody Iterable<Minutes> getMinutesList() {
		logger.info("Minutes List API START=====");
		return mRepository.findAll();
	}
	
	@GetMapping(path="/meetingRoomList")
	public @ResponseBody Iterable<MeetingRoom> getMeetingRoomList() {
		logger.info("MeetingRoom List API START=====");
		return meetingRoomRepository.findAll();
	}
	
}
