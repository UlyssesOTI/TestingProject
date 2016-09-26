<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="secur"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- host link --%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="shopName"
	value="${fn:substring(req.contextPath, 1, fn:length(req.contextPath))}" />
<c:set var="hostURL"
	value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), shopName )}" />
<%-- ${hostURL} --%>

<!-- container -->
<div class="container">
	<div class="row">
		<div class="col-md-12">

			<h2>Результат тесту</h2>

			<!-- breadcrumbs 
			<ul class="breadcrumb">
				<li><a href="${hostURL}/view_tests">Test</a> <span class="divider"></span></li>
				<li><a href="${hostURL}/view_tests_course2">JavaCore</a> <span class="divider"></span></li>
				<li class="active">Масиви</li>
			</ul>
			 breadcrumbs -->

			<div class="alert alert-success">
				<div id="inputHere">
					<div id="commentDiv">
						<c:choose>
							<c:when test="${empty comment}">
								<h4>Відгук:</h4>
								<c:if test="${editComment == 'true'}">
									<textarea id="comment" disabled
										style="display: none; border: none; background-color: #DFF0D8; width: 100%;"
										rows="0"></textarea>
									<button id="buttonAddComment" class="btn btn-info"
										onclick="addComment(${resultId})">Додати відгук</button>
								</c:if>
							</c:when>
							<c:otherwise>
								<h4>Відгук:</h4>
								<textarea id="comment" disabled
									style="border: none; background-color: #dff0d8; width: 100%;"
									rows="${rows}"><c:out value="${comment}" /></textarea>
								<c:if test="${editComment == 'true'}">
									<button id="buttonEditComment" class="btn btn-info"
										onclick="editComment(${resultId})">Редагувати відгук</button>
								</c:if>
							</c:otherwise>
						</c:choose>

					</div>
				</div>
			</div>

			<!-- кнопки -->
			<div class="test-group">
				<c:forEach var="i" begin="0" end="${userResults.size()-1}">
					<span><a class="btn btn-success item-ok" id="button${i}"
						onclick="showAnswer(${i})">${i+1}</a></span>
				</c:forEach>
				<span><a class="btn btn-success item-ok"  style="background-color: #1FB33D;"
						onclick="showAnswer('All')">All</a></span>
			</div>
			<!-- кнопки -->

			<!--  початок питань питань -->
			<c:forEach var="result" items="${userResults}">
				<!-- test content  -->
				<div class="bg-success test-text-style no-select test-content resultDiv">
					<pre class="bg-success test-text-style no-select" style="background-color: #fdfdfd" >${result.questText}</pre>
					<c:if test="${role != 3}">
						<div>				
							<h4>Зараховано ${result.questPoints} балів (Рівень ${result.questGrade})</h4>
						</div>
					</c:if>
					<%-- <p class="test-md">${result.questText}</p> --%>
					<!-- test table  -->
					<table class="table table-striped  table-hover table-bordered ">
						<tbody>
							<c:forEach var="answerList" items="${result.answersList}">
								<%-- <c:forEach var="answer" items="${answerList}"> --%>
								<c:choose>
									<c:when test="${answerList.get(3) == 'true'}">
										<tr class="test-field-bad">
									</c:when>
									<c:otherwise>
										<c:if test="${answerList.get(4) == 'true'}">
											<tr class="test-field-ok">
										</c:if>
										<c:if test="${answerList.get(4) != 'true'}">
											<tr>
										</c:if>
									</c:otherwise>
								</c:choose>
								<td class="text-center th-min-width"><c:choose>
										<c:when test="${answerList.get(4) == 'true'}">
											<input disabled="disabled" type="${result.inputType}" checked>
										</c:when>
										<c:otherwise>
											<input disabled="disabled" type="${result.inputType}">
										</c:otherwise>
									</c:choose></td>
								<c:if test="${answerList.get(1) == 'true'}">
									<td class="test-field-cur">${answerList.get(0)}</td>
								</c:if>
								<c:if test="${answerList.get(1) != 'true'}">
									<td>${answerList.get(0)}</td>
								</c:if>

								<c:if test="${role != 3}">
									<td width="5%">${answerList.get(2)}</td>
								</c:if>
								
								
								
							</c:forEach>


						</tbody>
					</table>
					<!-- table  -->
				</div>
				<!-- test content  -->

			</c:forEach>
			<!--  кінець питань -->
		</div>

	</div>

</div>



<c:if test="${editComment == 'true'}">

	<script type="text/javascript">
		var oldComment = "";
		
		$( "#comment" ).keyup(function() {
			textAreaComment = document.getElementById("comment");	
			textAreaComment.rows = textAreaComment.value.split(/\r\n|\r|\n/).length;
		});
		
		function addComment(resultId){
			commentDiv = document.getElementById("commentDiv");
			buttonAddComment = document.getElementById("buttonAddComment");
			commentDiv.removeChild(buttonAddComment);			
			oldComment = "";
			textAreaComment = document.getElementById("comment");
			textAreaComment.style.display = 'block'; 
			textAreaComment.disabled = false; 
			rows =  textAreaComment.value.split(/\r\n|\r|\n/).length;
			textAreaComment.rows = rows > 3 ? rows : 3;
			textAreaComment.style.backgroundColor = "#FFFFFF"; 
			textAreaComment.style.color = "#000000";
			textAreaComment.style.fontStyle = "italic";
			textAreaComment.style.width = "100%";
			//commentDiv.appendChild(textAreaComment);	
			appenSaveCancel(commentDiv,resultId);
		}
		
		function editComment(resultId){
			var textAreaComment = document.getElementById("comment");
			textAreaComment.style.backgroundColor = "#FFFFFF"; 
			textAreaComment.style.display = 'block'; 
			textAreaComment.style.color = "#000000";
			textAreaComment.style.fontStyle = "italic";
			oldComment = textAreaComment.value;
			textAreaComment.disabled = false; 
			commentDiv = document.getElementById("commentDiv");		
			editButton = document.getElementById("buttonEditComment");				
			commentDiv.removeChild(editButton);
			appenSaveCancel(commentDiv,resultId);
		}
		
		function appenSaveCancel(commentDiv,resultId){
			buttonSaveComment = document.createElement('button');
			buttonSaveComment.addEventListener("click", function(event) {
				  saveComment(resultId);
				  event.preventDefault();
				});
			buttonSaveComment.innerHTML = "Зберегти";
			buttonSaveComment.className ="btn btn-info";
			buttonSaveComment.id = "buttonSaveComment";
			commentDiv.appendChild(buttonSaveComment);			
			buttonCnacel = document.createElement('button');
			buttonCnacel.addEventListener("click", function(event) {
				  cancel(resultId);
				  event.preventDefault();
				});
			buttonCnacel.innerHTML = "Відмінити";
			buttonCnacel.id = "buttonCnacel";
			buttonCnacel.className ="btn btn-warning";
			commentDiv.appendChild(buttonCnacel);			
		}
	
		function saveComment(resultId){			
			textAreaComment = document.getElementById("comment");
			commentDiv = document.getElementById("commentDiv");
			if(textAreaComment.value != oldComment){
				sendComment(textAreaComment.value,resultId);
			}
			
			if((textAreaComment.value.length === 0 || !textAreaComment.value.trim())){
				textAreaComment.style.display = 'none'; 
			}else{
				textAreaComment.disabled = true; 
				textAreaComment.style.display = 'block'; 
				textAreaComment.style.backgroundColor = "#DFF0D8"; 
				textAreaComment.style.color = "#000000";
				textAreaComment.style.fontStyle = "normal";
				textAreaComment.rows = textAreaComment.value.split(/\r\n|\r|\n/).length;
			}

			appendAddEditButton(textAreaComment.value,resultId,commentDiv);
		}
		
		function cancel(resultId){
			commentDiv = document.getElementById("commentDiv");
			textAreaComment = document.getElementById("comment");
			if(!oldComment || 0 === oldComment.length){				
				textAreaComment.rows = "0"; 
				textAreaComment.style.display = 'none'; 
			}else{				
				textAreaComment.value = oldComment;
				textAreaComment.style.display = 'block'; 
				textAreaComment.style.backgroundColor = "#DFF0D8"; 
				textAreaComment.style.color = "#000000";
				textAreaComment.style.fontStyle = "normal";
				textAreaComment.disabled = true; 
				textAreaComment.rows = textAreaComment.value.split(/\r\n|\r|\n/).length;
			}								
			appendAddEditButton(oldComment,resultId,commentDiv);							
		}
		
		function appendAddEditButton(Comment,resultId,commentDiv){
			saveButton = document.getElementById("buttonSaveComment");
			commentDiv.removeChild(saveButton);
			saveButton = document.getElementById("buttonCnacel");
			commentDiv.removeChild(saveButton);				
			if(!Comment || 0 === Comment.length){
				buttonAddComment = document.createElement('button');
				buttonAddComment.addEventListener("click", function(event) {
					  addComment(resultId);
					  event.preventDefault();
					});
				buttonAddComment.innerHTML = "Додати відгук";
				buttonAddComment.id = "buttonAddComment";
				buttonAddComment.className ="btn btn-info";
				commentDiv.appendChild(buttonAddComment);
			}else{
				buttonEditComment = document.createElement('button');
				buttonEditComment.addEventListener("click", function(event) {
					  editComment(resultId);
					  event.preventDefault();
					});
				buttonEditComment.innerHTML = "Редагувати відгук";
				buttonEditComment.id = "buttonEditComment";
				buttonEditComment.className ="btn btn-info";
				commentDiv.appendChild(buttonEditComment);
			}
		}
	
	
		function sendComment(comment,resultId) {
			console.log(comment);
			console.log(resultId);
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "${hostURL}/result/saveResultComment",
				data : JSON.stringify({
					resultId : resultId,
					comment : comment
				}),
				dataType : 'html',
				success : function(data) {
					
				},
				error : function(e) {
					alert("ERROR: ", e);
				},
				done : function(e) {
					alert(e);
				}
			});
		}
		
	</script>

</c:if>

<script type="text/javascript">
		function showAnswer(k){	
			console.log(k);
			var elements = document.getElementsByClassName("resultDiv");
			var buttons  = document.getElementsByClassName("btn btn-success");
			console.log(elements.length);
			for (var index = 0; index < elements.length; index++) {				
				if(index == k){
					elements[index].style.display = 'block';
					buttons[index].style.backgroundColor = "#1FB33D";
					buttons[elements.length].style.backgroundColor = "";					
				}else if( k == "All"){
					elements[index].style.display = 'block';
					buttons[elements.length].style.backgroundColor = "#1FB33D";
					buttons[index].style.backgroundColor = "";
				}else{
					elements[index].style.display = 'none';
					buttons[index].style.backgroundColor = "";
				}	
			}
			
			
			$("body").append("<div id='update'></div>")
			$("#update").remove();
		}	
	</script>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/salvattore.min.js"></script>
