package com.mkhan.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkhan.api.info.dto.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InfoTest {
	
	/*
	 * application을 구동하기 위한 필수 정보 데이터셋이 정상인지 테스트 
	 * 														*/
	
	 @Autowired
	 private HoursRepository hours;
	 
	 @Autowired
	 private MinutesRepository minutes;
	 
	 @Autowired 
	 private MeetingRoomRepository meetingRoom;
	
	 @Test
	 public void hoursTest() {
		 
		List<Hours> hourList = hours.findAll();
		
		// 초기 데이터 로드 11개 확인 
		assertThat(hourList).isNotEmpty()
							.hasSize(11);
	 }
	 
	 @Test
	 public void minutesTest() {
		 
		List<Minutes> minutesList = minutes.findAll();
		
		// 초기 데이터 로드 2개 확인 
		assertThat(minutesList).isNotEmpty()
							.hasSize(2);
	 }
	 
	 @Test
	 public void meetingRoomTest() {
		 
		List<MeetingRoom> meetingRoomList = meetingRoom.findAll();
		
		// 초기 데이터 로드 10개 확인 
		assertThat(meetingRoomList).isNotEmpty()
							.hasSize(10);
	 }
	
	

}
