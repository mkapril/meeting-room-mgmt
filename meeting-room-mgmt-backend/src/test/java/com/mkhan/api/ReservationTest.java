package com.mkhan.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mkhan.api.info.dto.*;
import com.mkhan.api.reservation.dto.Reservation;
import com.mkhan.api.reservation.dto.ReservationDTO;
import com.mkhan.api.reservation.dto.ReservationRepository;
import com.mkhan.api.reservation.service.ReservationService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes=ReservationService.class,webEnvironment = WebEnvironment.RANDOM_PORT)

@EnableAutoConfiguration
@ContextConfiguration(classes = ReservationService.class)
@SpringBootTest(classes=ReservationService.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@DataJpaTest
public class ReservationTest {
	
	/*
	 * application을 구동하기 위한 필수 정보 데이터셋이 정상인지 테스트 
	 * 														*/
	
	@Autowired
	 private ReservationService service;
	 
	
	 @Autowired
	 private ReservationDTO single;
	
	 @Autowired
	 private ReservationDTO multi;
	
	
	
		
		@Before
		public void setUp() {
			service = new ReservationService(); 
			
			single = ReservationDTO.builder()
					  .name("TEST USER")
					  .reservationDate("20190123")
					  .roomId(3)
					  .startTime("1000")
					  .endTime("1200")
					  .firstreservationDate("20190123")
					  .repeatCount(1)
					  .build();
			
					
			multi = ReservationDTO.builder()
					  .name("TEST USER")
					  .reservationDate("20190123")
					  .roomId(1)
					  .startTime("1000")
					  .endTime("1200")
					  .firstreservationDate("20190123")
					  .repeatCount(4)
					  .build();
			
			
		}
		
		 @Test
		 public void multiReservation() {

			 // multi (4주 예약) 예약 확인 
			 List<Reservation> multiList = service.createReservation(multi);
			 
			 assertThat(multiList).hasSize(4)
			 					  ;
			 					  
		 }
		 
		 @Test
		 public void singleReservation() {
			 // 단건 예약 확
		 	List<Reservation> singleList = service.createReservation(single);
			 
			 assertThat(singleList).hasSize(1)
			 					  ;
			
		 }
	
	

}
