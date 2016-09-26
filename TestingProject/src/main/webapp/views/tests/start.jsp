<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<%-- ${hostURL} --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>








<div class="col-xs-12">

	<h2>Проходження тесту</h2>

	<!-- breadcrumbs 
	<ol class="breadcrumb">
		<li><a href="view_group_">Назва курсу</a></li>
		<li><a href="view_user_">Мій кабінет?</a></li>
		<li class="active">Назва тесту</li>
	</ol>
	<!-- breadcrumbs -->
	
	<div class="clearfix"></div>
	<!-- Таймер -->
	<div class="input-group timer-right">
		<span class="input-group-addon">
			<span class="glyphicon glyphicon-time"></span>
		</span><input type="text" class="form-control " id="testTime"
			value=""> 
	</div>
	<!-- Таймер -->
	
	<!-- Завершити -->
	<div class="input-group pull-right">
		<button class="btn btn-success btn-block" onClick="finishTest()">завершити</button>
	</div>
	<div class="clearfix"></div>
	<!-- Завершити -->
	
	
	<!-- кнопки -->
	<div class="test-group">
	<span><a class="btn btn-success item-cur"
				id="${testQuestions.get(0).getQuestion().getId()}"
				onclick="nextQuestion(0)">${1}</a></span>
		<c:forEach var="i" begin="1" end="${testQuestions.size()-1}">
			<span><a class="btn btn-success item-ok"
				id="${testQuestions.get(i).getQuestion().getId()}"
				onclick="nextQuestion(${i})">${i+1}</a></span>
		</c:forEach>
		
	</div>
	<!-- кнопки -->

	<!-- типу тако має воно бути, хз вапше -->
	
	
	<form name="testForm" action="save_result" method="post">
		
		<div id="testdiv">		
		
			<pre class="bg-success test-text-style no-select" >

			</pre>
			
			<table class="table table-striped  table-hover table-bordered ">
			<tbody>
			<tr>
			<td class="text-center th-min-width"><input type="checkbox"	value=""></td>
			<td></td>
			</tr>
			</tbody>
			</table>
		</div>

	</form>
	
	

		<!-- туди сюди -->
		<ul class="pager">
			<li class="previous"><a href="#">Prev.</a></li>
			<li class="next"><a href="#">Next</a></li>
		</ul>
		<!-- туди сюди -->
		
		<!-- Або тут кнопка завершити, хз типу 
		<button class="btn btn-success btn-block" onClick="finishTest()">завершити</button>
		<!-- Або тут кнопка завершити, хз типу -->		
	</div>
	<!-- типу тако має воно бути, хз вапше -->



<!-- тако робити не гарно, зроби нормально, ну -->
<!-- тако робити не гарно, зроби нормально, ну -->




<script>

document.body.onload=startTest;

var data=null;

function startTest(){
	nextQuestion(0);
	startTime("${testTime}:00");
}

function startTime(time) {
	var my_timer = document.getElementById("testTime");
// 	var time = my_timer.value;	
	var arr = time.split(":");	
	var m = arr[0];
	var s = arr[1];
	if (s == 0) {
		if (m == 0) {			
			alert("Свята дуже харило тому напис поміняли");
			finishTest();
			//window.location.reload();
			return;
		}		
		m--;
		if (m< 10) m = "0" + m;
		s = 59;
	} else s--;
	if (s < 10) s = "0" + s;
	//document.getElementById("testTime").innerHTML =  m + ":" + s;
	time=m + ":" + s;
	my_timer.value = time;
	setTimeout(startTime, 1000,time);
}

function display(data){
	//current button
	var previousButton=document.getElementsByClassName("item-cur")[0];
	previousButton.setAttribute("class","btn btn-success item-ok");
	var button=document.getElementById(data.question.id);
	var buttonClass=button.getAttribute("style");
	button.setAttribute("class","btn btn-success item-cur");
	
	// update form
	var form = document.forms.testForm;
	var div = document.getElementById("testdiv");	
	form.removeChild(div);
	div = document.createElement('div');
	div.id="testdiv";
	form.appendChild(div);
	
	
	
	//var textNode = document.createTextNode(data.question.text+" " +data.question.multipleAnswers);
	
// 	$(".test-text-style").html(data.question.text);
	
	
	var pre=document.createElement("pre");
	pre.setAttribute("class","bg-success test-text-style no-select");
	var txt=document.createTextNode(data.question.text);
	pre.style.fontFamily = "Arial";
	pre.style.fontSize = "11pt";
	pre.appendChild(txt);
	div.appendChild(pre);
	
	//div.appendChild(textNode);
	var br = document.createElement('br');
	div.appendChild(br);
	
	var table = document.createElement("table");
	//class="table table-striped  table-hover table-bordered "
	table.setAttribute("class","table table-striped  table-hover table-bordered ");
	var tbody = document.createElement("tbody");
	for ( var i = 0 ; i<data.question.answers.length;i++) {		
		
		var answer=data.question.answers[i];
		
		var tr = document.createElement("tr");
		var td1 = document.createElement("td");
		td1.setAttribute("class", "text-center th-min-width");
		var input = document.createElement('input');
		input.setAttribute('name','checkedAnswers');
// 		console.log(data.question.status);
		input.setAttribute('type',data.question.multipleAnswers?'checkbox':'radio');
		input.setAttribute('value',answer.id);
		input.setAttribute('id',"answerId"+answer.id);
		if(data.checkedAnswersId[i]!=0)input.checked=true;
		else input.checked=false;
		
		td1.appendChild(input);
		
		var td2 = document.createElement("td");
		
		var textNode = document.createTextNode(answer.text);
		td2.appendChild(textNode)
		
		tr.appendChild(td1);
		tr.appendChild(td2);
		tbody.appendChild(tr);		
	}	
	table.appendChild(tbody);
	div.appendChild(table);
	
}



function nextQuestion(nextQuestionNumber){
	
	var formData = new FormData();	
	var checkedAnswers=[];
	var thisQuestionNumber;
	console.log("data=="+data);
	if(data==null) {

		thisQuestionNumber=0;
// 		console.log("thisQuestionNumber="+thisQuestionNumber);
// 		console.log("nextQuestionNumber "+nextQuestionNumber);
	}
	else{
		thisQuestionNumber=data.numberInList;
// 		console.log(" thisQuestionNumber="+thisQuestionNumber);
// 		console.log("nextQuestionNumber "+nextQuestionNumber);
		var id;
		var answerCheckBoxes=document.getElementsByName("checkedAnswers");
		for ( var i = 0 ; i<data.question.answers.length;i++) {
			id = data.question.answers[i].id;
			if(answerCheckBoxes[i].checked)checkedAnswers.push(id);
			else checkedAnswers.push(0);
		}
	}	
	formData.append("checkedAnswers", checkedAnswers);	

	formData.append("nextQuestionNumber", nextQuestionNumber);
	formData.append("thisQuestionNumber", thisQuestionNumber);


	var xhr = new XMLHttpRequest();
	xhr.open("POST", "test/nextQuestion");
	xhr.send(formData);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
					data=JSON.parse(xhr.responseText);
		        	console.log("status==200")
		        	console.log(data);

		        	display(data);
		    }
		}
	}
}

function finishTest(){
	var formData = new FormData();	
	var checkedAnswers=[];
	var thisQuestionNumber;
	var nextQuestionNumber=0;
	if(data==null) {
		thisQuestionNumber=0;
	}
	else{
		thisQuestionNumber=data.numberInList;
		var id;
		var answerCheckBoxes=document.getElementsByName("checkedAnswers");
		for ( var i = 0 ; i<data.question.answers.length;i++) {
			id = data.question.answers[i].id;
			if(answerCheckBoxes[i].checked)checkedAnswers.push(id);
			else checkedAnswers.push(0);
		}
	}	
	formData.append("checkedAnswers", checkedAnswers);	

	formData.append("nextQuestionNumber", nextQuestionNumber);
	formData.append("thisQuestionNumber", thisQuestionNumber);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", "test/nextQuestion");
	xhr.send(formData);
// 	xhr.onreadystatechange = function() {
// 		if (xhr.readyState == 4) {
// 			if (xhr.status == 200) {
// 		    }
// 		}
// 	}	
	document.testForm.submit();
}

</script>


