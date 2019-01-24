package com.mkhan.api.reservation.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mkhan.common.BaseException;
import com.mkhan.common.CustomMessage;
import com.mkhan.api.reservation.dto.Reservation;
import com.mkhan.api.reservation.dto.ReservationDTO;
import com.mkhan.api.reservation.dto.ReservationMaster;
import com.mkhan.api.reservation.dto.ReservationMasterRepository;
import com.mkhan.api.reservation.dto.ReservationRepository;
import com.mkhan.api.info.dto.MeetingRoom;
import com.mkhan.api.info.dto.MeetingRoomRepository;

@Service("reservationService")
public class ReservationService {
	
    private static final Logger logger = Logger.getLogger(ReservationService.class.getCanonicalName());
    
	@Autowired private ReservationRepository reservationRepository;
	@Autowired private ReservationMasterRepository rmRepository;
	@Autowired private MeetingRoomRepository meetingRoomRepo;
	
	@Autowired 
	@Qualifier("customMessage")
	private CustomMessage message;
	
	@Transactional
	public CustomMessage addReservation(ReservationDTO reservationDTO) {
		
		String returnMesage = "예약에 실패하였습니다 " ;
		
		try {
			
			//parameter 체크 
			preCheckParams(reservationDTO);
			
			//master 먼저 생성
			ReservationMaster rm = new ReservationMaster();
			rm.setRoomId(reservationDTO.getRoomId());
			synchronized (this) {
				rmRepository.save(rm);
			}
			
			
			//Reservation 정보 생성 
			List<Reservation> reservationList = createReservation(reservationDTO);
			
			synchronized (this) {
				//Reservation 저장 
				reservationList.forEach(r -> {
					if ( checkVacancy(r) ) {
						r.setReservationMaster(rm);
						
						reservationRepository.save(r);
						} 
					});
			}
			returnMesage = "예약에 성공하였습니다";
			message.setMessage(returnMesage);
			message.setResultCode("10");
			
		}catch(BaseException e) {
			message.setMessage(e.getMessage());
			message.setResultCode(e.getErrCode());
		}catch(Exception e) {
			logger.log(Level.WARNING, "정의되지 않은 에러가 발생하였습니다. ");
			
			e.printStackTrace();
			e.getMessage();
			returnMesage = e.getMessage();
			message.setMessage(returnMesage);
		}
			
		
		return message;
	}
	
	public Iterable<Reservation> findAllReservation(){
		return reservationRepository.findAll();
	}
	
	public Iterable<Reservation> findAllReservationByDate(String reservationDate){
		
		return reservationRepository.findAllByReservationDate(reservationDate);
	}
	
	public void deleteAllReservation() {
		 reservationRepository.deleteAll();
	}
	
	public List<Reservation> createReservation(ReservationDTO rd){
		List<Reservation> list = new ArrayList<Reservation>();
		rd.setFirstreservationDate(rd.getreservationDate());
		int repeatCount = Optional.ofNullable(rd.getRepeatCount()).orElse(1);
		rd.setRepeatCount(repeatCount);
		for (int i=1 ; i < repeatCount+1; i++ ) {
			rd.setTurnCount(i);
			rd.setreservationDate(rd.getNextreservationDate(i-1));
			
			list.add(rd.createReservation());
		}
		
		return list;
	}
	
	public boolean checkVacancy(Reservation r) {
		int count = reservationRepository.countOverlap(r.getreservationDate(), r.getRoomId(), r.getStartTime(), r.getEndTime());
		boolean chk = false;
		if (count==0 ) {
			chk=true;
		}else {
			throw new BaseException("이미 존재하는 예약이 있습니다. ");
		}
		return chk;
	}
	
	 public void preCheckParams(ReservationDTO rd) {
			int len = Optional.ofNullable(rd.getName()).map(String::length).orElseThrow( ()-> new BaseException("300","이름은 필수값입니다. "));
			if (len == 0 ) throw new BaseException("300","이름은 필수값입니다. ");
			
			if (len > 200) throw new BaseException("303","이름은 200자 이하여야 합니다 ");
			
			Optional<MeetingRoom> r = meetingRoomRepo.findById(rd.getRoomId());
			r.orElseThrow(()-> new BaseException("301","유효하지 않은 회의실입니다."));
			
			
			if ( (!("00".equals(rd.getStartTime().substring(2, 4))) && !("30".equals(rd.getStartTime().substring(2,4))))
					|| ( !("00".equals(rd.getEndTime().substring(2, 4))) && !("30".equals(rd.getEndTime().substring(2,4)))) )
				throw new BaseException("302","00분, 30분 단위만 예약 가능합니다.");
			
			SimpleDateFormat fmt = new SimpleDateFormat("HHmm");
			
			try {
				if ( fmt.parse(rd.getStartTime()).getTime() >= fmt.parse(rd.getEndTime()).getTime() ) throw new BaseException("종료 시각은 시작 시간보다 커야 합니다 ");
			} catch (ParseException e) {
				 e.printStackTrace();
				 throw new BaseException("303","유효하지 않은 시간 타입입니다. ");
			}
			
		 }
	
	
}
