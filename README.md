# 회의실 예약 어플리케이션
> 회의실과 날짜, 사용 시간을 입력하여 회의실을 예약합니다

## Build & Run
* meeting-room-mgmt-master.zip 압축을 풀면 아래와 같은 파일 구조입니다.
* frontend 정상 작동을 위해서는 반드시 backend가 구동상태여야 합니다.

```	
├── meeting-room-mgmt-master
│   └── meeting-room-mgmt-backend 
│   └── meeting-room-mgmt-frontend
│   └── README.md
```
### Maven (on Terminal)
* meeting-room-mgmt-backend 
  - 8090 포트에서 실행됩니다 (application.properties에서 설정)
  - **meeting-room-mgmt-backend 디렉토리**에서 아래 명령어를 수행하여 구동합니다. 

```bash
$ mvn clean package	
$ java -jar target/meeting-room-mgmt-backend-0.0.1-SNAPSHOT.war
```
  
* meeting-room-mgmt-frontend 
  - 8080 포트에서 실행됩니다
  - **meeting-room-mgmt-frontend 디렉토리**에서 아래 명령어를 수행하여 구동합니다. 

```bash
$ mvn clean package	
$ java -jar target/meeting-room-mgmt-frontend-0.0.1-SNAPSHOT.war
```

### STS (IDE 환경)
 * meeting-room-mgmt-backend, meeting-room-mgmt-frontend 프로젝트를 각각 Import 
 * Spring Boot App 으로 Run 

----
 
## 문제 해결 전략
* 독립적 운영을 위해 frontend/backend 프로젝트를 분리하였습니다. 
* frontend는 backend 에 RestTemplate으로 API를 호출합니다.
* 기준 정보 (회의실, 예약 시각, 예약 분) 확장성을 위해 각각의 테이블로 관리합니다.
* parameter 변조에 의한 부정 예약을 막기 위해 validation check 를 frontend/backend 모두 수행합니다.  
 
* 반복 예약을 위해 예약은 
    ``ReservationMaster`` : ``Reservation``  = 1 : n 으로 생성합니다.   
    (단건일 경우 n=1)  
    
     ``ReservationMaster.reservationId`` = ``Reservation.reservationId`` 매핑됩니다.
     
 -  ``ReservationMaster`` 테이블 
      
| Column Name    | description                                         |
|----------------|-----------------------------------------------------|
| reservation_id | 예약 ID<br> RESERVATION 테이블의 reservation_id와 매핑   |
| room_id        | 회의실 ID MEETINGROOM INFO 테이블의 room_id 와 매핑       |
| reg_date       | 등록일시                                              |
       

-  ``Reservation `` 테이블 
    
| Column Name      | description                                                                        |
|------------------|------------------------------------------------------------------------------------|
| id               | 개별 예약 ID                                                                       |
| reservation_id   | 예약 ID<br> RESERVATION 테이블의 reservation_id와 매핑                                 |
| room_id          | 회의실 ID <br> MEETINGROOM_INFO 테이블의 room_id 와 매핑                                |
| reservation_date | 회의실 예약 일시 <br> 반복예약의 경우 동일 reservation_id에 여러 reservation date 생성 |
| start_time       | 예약 시작 시간 (HH:mm)                                                             |
| end_time         | 예약 종료 시간 (HH:mm)                                                             |
| repeat_count     | 반복 예약 횟수 (단건 예약인 경우 1)                                                     |
| turn_count       | 반복 예약 횟수 중 순번 (단건 예약인 경우 1)                                             |
| name             | 예약자 이름                                                                        |
| reg_date         | 등록 일시                                                                          |
             
             
             
* 아래와 같은 경우 중 하나라도 해당 되면, 중첩 예약으로 판단합니다.
1. 예약 시작 시각이 기존 시작 시간들보다 작거나 같고, 예약 종료 시각이 기존 시작 시간보다 큰 경우
2. 예약 시작 시각이 기존 시작 시각들보다 작고, 예약 종료 시각이 기존 종료 시각보다 크거나 같은 경우
3. 예약 시작 시각이 기존 시작 시각들보다 크고, 예약 시작 시각이 기존 종료시각보다 작은 경우   

```
             기존 시작 시각         기존 종료 시각 
              |--------------------|
     1)   |--------|
     2)   |------------------|
     3)              |------------------|     
```
  
* 다수의 사용자의 동시 중첩 예약을 막기 위해 예약 가능 여부 & 예약 insert 를 synchronized 로 처리하였습니다. 

```java
synchronized (this) {
	reservationList.forEach(r -> {
		if ( checkVacancy(r) ) {
			r.setReservationMaster(rm);
			reservationRepository.save(r);
			} 
		});
			}
```

## 환경 구성 및 Dependencies 
> Based on Strping Boot Project 

### Frontend
 * JSP (with jQuery, JSTL, Bootstrap, moment)
 * Lombok
 * Maven
  
### Backend 
 * H2 Database
 * JPA (Java Persist API)
 * Lombok 
 * Swagger
 * Maven 

## 단위 테스트 및 통합 테스트 
> Junit Test 로 수행합니다.

### 단위 테스트 
> /meeting-room-mgmt-backend/src/test/java/com/mkhan/api 하위

* InfoTest.java 
	> 기준 정보 가져오기 
- hoursTest()
- minutesTest()
- meetingRoomTest()
	
* ReservationTest.java
	> 예약 정보 생성  테스트 
 - multiReservation() - 반복 예약 생성 
 - singleReservation() - 단건 예약 생성 

### 통합 테스트
* MeetingRoomMgmtApplicationTests
	> /meeting-room-mgmt-frontend/src/test/java/com/mkhan/ 하위  
	> Backend의 각 API를 RestTemplate 으로 호출 하고 정합성 체크합니다. 

	
 - testA() - 시간 정보 가져오기
 - testB() - 단건 예약 API 호출 후 예약 생성 정상 결과코드 확인 
 - testC() - 반복 예약(4회) API 호출 후 예약 생성 정상 결과코드 확인
 - testD() - testB, testC에서 생성한 예약 조회 확인
 - testE() - B,C와 중첩 시간대 예약 시 실패 결과 코드 확인
 - testF() - 00분,30분 단위가 아닌 10분 단위 예약 호출 시 실패 결과 코드 확인
 - testG() - 필수값 누락하고 예약 API 호출 시 실패 결과 코드 확인
 - testH() - 회의실 기준 정보에 없는 회의실 예약 API 호출 시 실패 결과 코드 확인
 - testI() - 전체 예약 정보 조회 확인
 - testJ() - 전체 예약 건 삭제 후 확인 



## API 명세 
* Backend 구동 후 아래 swagger url 에서도 확인 및 테스트할 수 있습니다. 
 - [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)

### 시각 기준 정보 조회
#### Request
```
GET /api/info/hoursList
```
#### Response
```json
[
  {
    "hour": "09"
  },
  {
    "hour": "10"
  },
 ... 생략 
]
```

### 분 기준 정보 조회
#### Request
```
GET /api/info/minutesList
```
#### Response
```json
[
  {
    "minutes": "00"
  },
  {
    "minutes": "30"
  }
]
```

### 회의실 기준 정보 조회
#### Request
```
GET /api/info/minutesList
```
#### Response
```json
[
  {
    "capacity": 20,
    "roomId": 1,
    "roomName": "회의실 A"
  },
  {
    "capacity": 20,
    "roomId": 2,
    "roomName": "회의실 B"
  }
  ... 생략 
]
```
### 예약 정보 생성
#### Request
```
POST /api/reservation/add
```
```json
{
  
  "name": "예약자명",
  "repeatCount": 반복횟수,
  "reservationDate": "예약일자 (YYYYMMDD)",
  "roomId": 회의실ID,
  "startTime": "시작 시각 (HHmm)",
  "endTime": "종료 시각 (HHmm)"
}
```
| 필드명          | 설명     | Type(size)  | 필수여부 | Sample Data |
|-----------------|----------|-------------|----------|-------------|
| reservationDate | 예약일자 | String(8)   | Y        | '20190125'  |
| roomId          | 회의실ID | Integer     | Y        | 3           |
| startTime       | 시작시각 | String(4)   | Y        | 1330        |
| endTime         | 종료시각 | String(4)   | Y        | 1500        |
| name            | 예약자명 | String(200) | Y        | 이름        |
| repeatCount     | 반복횟수 | Integer     | N        |             |


#### Response
* 성공  

```json
{
    "message": "예약에 성공하였습니다",
    "resultCode": "10"
}
```
* 실패 시 result Code 에는 10 아닌 에러 코드 

```json
{
    "message": "이미 존재하는 예약이 있습니다. ",
    "resultCode": "500"
}
```

### 예약 정보 날짜별 조회 
#### Request
```
GET /api/reservation/date/{reservationDate}
```
#### Response
```json
[
  {
    "id": 1,
    "roomId": 4,
    "reservationDate": "20190121",
    "startTime": "1500",
    "endTime": "1630",
    "repeatCount": 5,
    "turnCount": 1,
    "name": "MingiHan",
    "regDate": "2019-01-24T22:44:33",
    "reservationMaster": {
      "reservationId": 1,
      "roomId": 4
    }
  }
]
```
### 예약 정보 회의실별 조회 
#### Request
```
GET /api/reservation/roomId/{roomId}
```
#### Response
```json
[
  {
    "id": 1,
    "roomId": 4,
    "reservationDate": "20190121",
    "startTime": "1500",
    "endTime": "1630",
    "repeatCount": 5,
    "turnCount": 1,
    "name": "MingiHan",
    "regDate": "2019-01-24T22:44:33",
    "reservationMaster": {
      "reservationId": 1,
      "roomId": 4
    }
  },
  {
    "id": 2,
    "roomId": 4,
    "reservationDate": "20190128",
    "startTime": "1500",
    "endTime": "1630",
    "repeatCount": 5,
    "turnCount": 2,
    "name": "MingiHan",
    "regDate": "2019-01-24T22:44:33",
    "reservationMaster": {
      "reservationId": 1,
      "roomId": 4
    }
  },
  ... 생략 
  }
]
```
### 예약 정보 전체 조회
#### Request
```
GET /api/reservation/all
```
#### Response
```json
[
  {
    "id": 1,
    "roomId": 4,
    "reservationDate": "20190121",
    "startTime": "1500",
    "endTime": "1630",
    "repeatCount": 5,
    "turnCount": 1,
    "name": "MingiHan",
    "regDate": "2019-01-24T22:44:33",
    "reservationMaster": {
      "reservationId": 1,
      "roomId": 4
    }
  },
  {
    "id": 2,
    "roomId": 4,
    "reservationDate": "20190128",
    "startTime": "1500",
    "endTime": "1630",
    "repeatCount": 5,
    "turnCount": 2,
    "name": "MingiHan",
    "regDate": "2019-01-24T22:44:33",
    "reservationMaster": {
      "reservationId": 1,
      "roomId": 4
    }
  },
  ... 생략 
  }
]
```
### 예약 정보 전체 삭제
> 테스트 목적

#### Request
```
DELETE /api/reservation/all
```
#### Response
```
HTTP/1.1 200
```


## Screenshots
### 예약하기
![예약하기][form]


### 예약현황
![예약현황][dashboard]

[form]:http://drive.google.com/uc?export=view&id=1cVBhHUEZitVzDAN_60iZmlcT2iSd-Ho5
[dashboard]:http://drive.google.com/uc?export=view&id=13Qs6fixY6IOFJVExy5ZNn18KywG3DaYY
 
