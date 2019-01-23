package com.mkhan;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Logger;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mkhan.common.CustomMessage;
import com.mkhan.common.dto.Reservation;
import com.mkhan.common.dto.ReservationDTO;
import com.mkhan.frontend.dto.Hours;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingRoomMgmtApplicationTests {
	
	private static final Logger logger = Logger.getLogger(MeetingRoomMgmtApplicationTests.class.getCanonicalName());

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Value("${api.services.url}")
	private String serviceUrl;
	
	private ReservationDTO reservationDTO;
	private ReservationDTO reservationDTO2;
	private ReservationDTO reservationDTO3;
	private ReservationDTO reservationDTOSingle;
	private ReservationDTO reservationDTO4;
	
	@Before 
	public void setUp() {
		
		reservationDTOSingle = ReservationDTO.builder()
				  .name("TEST USER")
				  .reservationDate("20190125")
				  .roomId(4)
				  .startTime("1000")
				  .endTime("1200")
				  .firstreservationDate("20190125")
				  .repeatCount(1)
				  .build();
		
		reservationDTO = ReservationDTO.builder()
						  .name("MULTI TEST USER")
						  .reservationDate("20190125")
						  .roomId(3)
						  .startTime("1000")
						  .endTime("1200")
						  .firstreservationDate("20190125")
						  .repeatCount(4)
						  .build();
		
		reservationDTO2 = ReservationDTO.builder()
				  .name("TEST USER")
				  .reservationDate("20190123")
				  .roomId(3)
				  .startTime("1010")
				  .endTime("1230")
				  .firstreservationDate("20190125")
				  .repeatCount(4)
				  .build();
		
		/* 필수값 (이름) 누락 케이스 */
		reservationDTO3 = ReservationDTO.builder()
				  .name("")
				  .reservationDate("20190125")
				  .roomId(3)
				  .startTime("1010")
				  .endTime("1230")
				  .firstreservationDate("20190125")
				  .repeatCount(4)
				  .build();
		
		/* 유효하지 않은 회의실  */
		reservationDTO4 = ReservationDTO.builder()
				  .name("TEST H")
				  .reservationDate("20190121")
				  .roomId(20)
				  .startTime("1000")
				  .endTime("1230")
				  .firstreservationDate("20190125")
				  .repeatCount(4)
				  .build();
					  
					  
						
	}
	
	
	
	@Test
    public void testA() {
      
		logger.info("getHours Test====");
		ResponseEntity<Hours[]> response = restTemplate.getForEntity(
        		String.format("%s/api/info/hoursList", serviceUrl), Hours[].class);
    
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        
    }
	
	@Test
	public void testB() {
		logger.info("addReservation Test====");
		
		ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTOSingle,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 //예약 성공 코드 return 받는 지 테스트 
		 assertThat(response.getBody().getResultCode()).isEqualTo("10");
	}
	
	@Test
	public void testC() {
		
		logger.info("addMultiReservation Test====");
		
		ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 //예약 성공 코드 return 받는 지 테스트 
		 assertThat(response.getBody().getResultCode()).isEqualTo("10");
	}
	
	@Test
    public void testD() {
		logger.info("getReservation Test====");
		
        ResponseEntity<Reservation[]> response = restTemplate.getForEntity(
				String.format("%s/api/reservation/date/%s", serviceUrl,"20190125"),
				Reservation[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
	
	@Test
	public void testE() {
		
		 logger.info("addReservationFail Test====");
		
		
		 ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 //중복 예약  코드 return 받는 지 테스트 
		 assertThat(response.getBody().getResultCode()).isEqualTo("500");
		 assertThat(response.getBody().getMessage()).isEqualTo("이미 존재하는 예약이 있습니다. ");
	}
	
	@Test
	public void testF() {
		
		 logger.info("addReservationWrongType Test====");
		
		
		 ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO2,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 // 분 단위가 틀릴 때 결과 체크 
		 assertThat(response.getBody().getResultCode()).isEqualTo("302");
		 assertThat(response.getBody().getMessage()).isEqualTo("00분, 30분 단위만 예약 가능합니다.");
	}
	
	@Test
	public void testG() {
		
		logger.info("addReservatinMissingParam Test====");
		
		
		ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO3,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 // 필수값 (이름) 누락 체크 
		 assertThat(response.getBody().getResultCode()).isEqualTo("300");
		 assertThat(response.getBody().getMessage()).isEqualTo("이름은 필수값입니다. ");
	}
	
	@Test
	public void testH() {
		
		logger.info("addReservation No such MeetingRoom Test====");
		
		
		ResponseEntity<CustomMessage> response = restTemplate.postForEntity(String.format("%s/api/reservation/add", serviceUrl),
				reservationDTO4,CustomMessage.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isNotNull();
		 
		 // db에 존재하는 유효한 회의실인지 체크 

		 assertThat(response.getBody().getResultCode()).isEqualTo("301");
		 assertThat(response.getBody().getMessage()).isEqualTo("유효하지 않은 회의실입니다.");
	}
	
	@Test
	public void testI() {
		logger.info("delete All test data====");
		
		ResponseEntity<Void> response = restTemplate.getForEntity(String.format("%s/api/reservation/delete", serviceUrl),
				Void.class);
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
	
}

