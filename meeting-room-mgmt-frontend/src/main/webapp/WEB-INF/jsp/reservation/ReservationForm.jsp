<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Reserve the Meeting Room !</title>
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.1/css/bootstrap.min.css">
	<link href="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css" rel="stylesheet" />
	
	<script src="/webjars/jquery/3.3.0/jquery.min.js"></script>
	<script src="/webjars/momentjs/2.22.2/min/moment-with-locales.min.js"></script>
	<script src="/webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script src="/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.js"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
	  <li role="presentation" class="active"><a href="/reservation/reservationForm">예약하기</a></li>
	  <li role="presentation"><a href="/reservation/dashboard">예약현황</a></li>
	</ul>
	<br><br>
	<div class="bd-example">
	    <form class="form-horizontal" id="reservationForm" name="reservationForm">
			<div class="form-group">
			    <label for="roomId" class="col-sm-2 control-label">회의실명 </label>
			    <div class="col-sm-2">
			      <select class="form-control" id="roomId" name="roomId">
				  	 <c:forEach items="${meetingRoomList}" var="meetingRoom">
					  	<option value="${meetingRoom.roomId}"> ${meetingRoom.roomName}</option>
					  </c:forEach>
				</select>
			    </div>
		   </div>
		  
		  <div class="form-group">
		    <label for="name" class="col-sm-2 control-label">예약자명 </label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="name" name="name" placeholder="예약자명" maxlength="200">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="repeatCount" class="col-sm-2 control-label">반복예약 </label>
		    <div class="col-sm-2">
		      <input type="number" class="form-control" id="repeatCount" name="repeatCount" placeholder="반복횟수" maxlength="20" aria-describedby="helpBlock">
		      <label class="sr-only" for="inputHelpBlock">Input with help text</label>
			  <span id="helpBlock" class="help-block">
			  	매주 반복 예약 됩니다. 
			  </span>
		    </div>
		  </div>
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
				
				        	$("#reservationDatePicker").datetimepicker({
					  		  locale : 'ko', 
					  		  format : 'YYYY-MM-DD',
					  		  defaultDate : new Date() 
					  		});
				        });
				        
				        
				        $("#reservationDatePicker").on("dp.change", function (e) {
				        });
				        </script>
				    </div>
				  </div>
			  </div>
		  	<div class="form-group">
			    <label for="startTimeHour" class="col-sm-2 control-label">예약 시작 시각 </label>
			    <div class="col-sm-2">
			      <select class="form-control" id="startTimeHour" name="startTimeHour">
			      	<c:forEach items="${hoursList}" var="hour">
						<option value="${hour.hour}"> ${hour.hour}</option>
					</c:forEach>
				</select>
			    </div>
			    <div class="col-sm-2">
			      <select class="form-control" id="startTimeMinutes" name="startTimeMinutes">
					  <c:forEach items="${minutesList}" var="minute">
					  	<option value="${minute.minutes}"> ${minute.minutes}</option>
					  </c:forEach>
					</select>
			    </div>
	
		   </div>
		   <div class="form-group">
		    <label for="endTimeHour" class="col-sm-2 control-label">예약 종료 시각 </label>
			    <div class="col-sm-2">
			      <select class="form-control" id="endTimeHour" name="endTimeHour">
			      	<c:forEach items="${hoursList}" var="hour">
						<option value="${hour.hour}"> ${hour.hour}</option>
					</c:forEach>
					
				</select>
			    </div>
			    <div class="col-sm-2">
			      <select class="form-control" id="endTimeMinutes" name="endTimeMinutes">
				  	<c:forEach items="${minutesList}" var="minute">
						<option value="${minute.minutes}"> ${minute.minutes}</option>
					</c:forEach>
				</select>
			    </div>
		   </div>
		  <div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button class="btn btn-lg btn-primary" onClick="onSubmit();return false;">예약하기 </button>
		    </div>
		  </div>
		  <script>
		  
		  function onSubmit() {
			  
			  $("#startTime").val($("#startTimeHour").val()+$("#startTimeMinutes").val());
			  $("#endTime").val($("#endTimeHour").val()+$("#endTimeMinutes").val());
			  $("#reservationDate").val($("#reservationDatePicker").data("date").replace(/-/g,""));
			  
			  var jsonStr = JSON.stringify($("#reservationForm").serializeObject());
			  console.log("jsonStr : " + jsonStr);
			  if(checkValidation()){
				  $.ajax({
						url : '/reservation/addReservation'
				        , method : "post"
						, dataType : 'json'
						, data : jsonStr
						, processData : false /*querySTring make false*/
						, contentType : "application/json; charset=UTF-8"
						, success : function(data, stat, xhr) {
							console.log("success");
							console.log(data.message);
							console.log(data.resultCode);
							alert (data.message);
						}
					    , error : function(xhr, stat, err) {
					    	console.log(xhr.message);
					    	console.log(err);
					    	alert (xhr.message);
					    }
					});
			  }
		  	}
		  
		  function checkValidation(){
			  if ($("#name").val() ==''){
				  alert("이름을 넣어주세요 ");
				  return false;
			  }
			  
			  return true;
		  }
		  
		  $.fn.serializeObject = function() {
			  "use strict"
			  var result = {}
			  var extend = function(i, element) {
			    var node = result[element.name]
			    if ("undefined" !== typeof node && node !== null) {
			      if ($.isArray(node)) {
			        node.push(element.value)
			      } else {
			        result[element.name] = [node, element.value]
			      }
			    } else {
			      result[element.name] = element.value
			    }
			  }
	
			  $.each(this.serializeArray(), extend)
			  return result
			}
		  
		 	 $(function() {
			    console.log( "ready~~" );
			    $("#endTimeHour option:eq(1)").attr("selected", "selected");
			});
			
	
		  </script>
		  <input type="hidden" id="startTime" name="startTime">
		  <input type="hidden" id="endTime" name="endTime">
		  <input type="hidden" id="reservationDate" name="reservationDate">
		</form>
	</div>
</body>
</html>