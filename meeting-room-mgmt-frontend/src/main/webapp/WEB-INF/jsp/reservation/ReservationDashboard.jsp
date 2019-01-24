<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Dashboard !</title>
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.1/css/bootstrap.min.css">
	<link href="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet" />
	
	<script src="/webjars/jquery/3.3.0/jquery.min.js"></script>
	<script src="/webjars/momentjs/2.22.2/min/moment-with-locales.min.js"></script>
	<script src="/webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script src="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.js"></script>
</head>
<body>
	<ul class="nav nav-tabs">
	 <li role="presentation"><a href="/reservation/reservationForm">예약하기</a></li>
	  <li role="presentation" class="active"><a href="/reservation/dashboard">예약현황</a></li>
	</ul>
	
	<br><br>
	<div class="form-group">
			  	  <label for="reservationDatePicker" class="col-sm-2 control-label">예약일자 </label>
				  <div class="container">
				    <div class="row">
				        <div class="col-sm-3">
				                <div class="input-group date" id="reservationDatePicker">
				                    <input type="text" class="form-control" />
				                    <span class="input-group-addon">
				                        <span class="glyphicon glyphicon-calendar"></span>
				                    </span>
				                </div>
				        </div>
				        <script type="text/javascript">
				        $(function () {
				        	var today= new Date();
							var year = ''+today.getFullYear();
							var month = ''+ today.getMonth()+1;
							var day = ''+today.getDate();
							
							if (month.length < 2) month = '0' + month; 
							
							var defaultDate = year + '-' + month + '-' + day;
							if ( $("#reservationDate").val() != '' ){
								defaultDate = $("#reservationDate").val().substring(0,4) +'-'
												+ $("#reservationDate").val().substring(4,6) +'-'
												+ $("#reservationDate").val().substring(6,8) ;
							}
							
				        	$("#reservationDatePicker").datetimepicker({
					  		  locale : 'ko', // 화면에 출력될 언어를 한국어로 설정한다.
					  		  format : 'YYYY-MM-DD',
					  		  defaultDate : defaultDate // 기본값으로 오늘 날짜를 입력한다. 기본값을 해제하려면 defaultDate 옵션을 생략한다.
					  		});
				        });
				        
				        
				        $("#reservationDatePicker").on("dp.change", function (e) {
				             if ( $("#reservationDatePicker").data('date').length == 10 ){
							  console.log("change!"+$("#reservationDatePicker").data('date'));
							  $("#reservationDate").val($("#reservationDatePicker").data("date").replace(/-/g,""));
							  $("#frm").submit();
				             }
				        });
				        </script>
				    </div>
				  </div>
			  </div>
	<table class="table" id="meetingRoomTable">
		 <thead> 
		 	<tr> 
		 		<th>시간</th> 
		 		 <c:forEach items="${meetingRoomList}" var="meetingRoom">
					<th> ${meetingRoom.roomName}</th>
				 </c:forEach>
			</tr> 
		</thead> 
		<tbody> 
		
			 <c:forEach items="${hoursList}" var="hour">
				<tr> 
					<th scope="row">${hour.hour}</th> 
						<c:forEach items="${meetingRoomList}" var="meetingRoom">
							<td id="td${meetingRoom.roomId}${hour.hour}"></td>
				 		</c:forEach>
				</tr> 
				 </c:forEach>
	
	 	</tbody> 
 	</table>
 	
 	<c:forEach items="${reservationListByDate}" var="reservation">
	  
	    <c:set var="startHour" value="${fn:substring(reservation.startTime,0,2)}" />
		<c:set var="startMin"  value="${fn:substring(reservation.startTime,2,4)}" />
		
		<c:set var="endHour" value="${fn:substring(reservation.endTime,0,2)}" />
		<c:set var="endMin"  value="${fn:substring(reservation.endTime,2,4)}" />
		
		<c:if test="${ reservation.repeatCount > 1}">
			<c:set var="repeatText" value="(반복 ${reservation.turnCount}/${reservation.repeatCount}) <br>" />
		</c:if>
		<script>
			 $("#td${reservation.roomId}${startHour}").html('${repeatText}'+'${reservation.name}'
			 +'<br><br>${startHour}:${startMin}<br>${endHour}:${endMin}');
		 	$("#td${reservation.roomId}${startHour}").addClass("success");
		 	$("#td${reservation.roomId}${endHour-1}").addClass("success");
		 </script>
	 </c:forEach>

<form name="frm" id="frm" action="/reservation/dashboard" method="get">
	<input type="hidden" id="reservationDate" name="reservationDate" value="${reservationDate}">
</form>
</body>
</html>