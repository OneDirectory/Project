<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


<!-- Bootstrap Core CSS -->
<link href="Resources/css/bootstrap1.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="Resources/css/simple-sidebar.css" rel="stylesheet">
<link href="Resources/css/ProjectStyleSheet.css" rel="stylesheet">
</head>


<body class="adminPage">

<%
	String user = null;
	if(session.getAttribute("user")==null){
		response.sendRedirect("index.jsp");
	}else user = (String) session.getAttribute("user");
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
	for(Cookie cookie : cookies){
    if(cookie.getName().equals("user")) userName = cookie.getValue();
    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
	}
}
%>
	<div class="page-header">
		<br>
		<h2>
			Group One - One Directory <small>Project</small>
		</h2>
		<h2>Network Management Engineer Page</h2>
	</div>

	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Menu </a></li>

				<li><a href="#" onclick="toggle('imsiCount');">Affected IMSI with Count</a></li>
				<li><a href="#" onclick="toggle('modelCount');">Call Failure by Model</a></li>

				<li><a href="http://localhost:8080/project/LogoutServlet">Log out</a></li>
			</ul>
			<br>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="imsiCount">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1>Total number of failures per IMSI</h1>

						<label class="control-label col-sm-2" for="ID">FROM:</label><br>

						<div>
							<input type="datetime-local" id='from' class="form-control"
								name="from" placeholder="dd-mm-yyyy hh:mm" autofocus>
						</div>

						<label class="control-label col-sm-2" for="ID">TO:</label>

						<div>
							<input type="datetime-local" id='to' class="form-control"
								name="to" placeholder="dd-mm-yyyy hh:mm" autofocus>
						</div>



						<div class="col-sm-offset-4 col-sm-10">
							<br>
							<button id="submit" type="submit" class="btn btn-primary">Search</button>
						</div>


					</div>
				</div>
				<table class="table" id='table' name='table'></table>
			</div>
			
			
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->


	<div id="modelCount">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<h1>Call Failures by Model</h1>
						
						<label class="control-label col-sm-2" for="modelInput">Model: </label>

						<div>
							<select class="col-sm-5" id="modelInput">
							</select>
						</div>
						
						<div class="col-sm-offset-4 col-sm-10">
							<button id="modelSubmit" type="submit" class="btn btn-primary">Search</button>
						</div>
					
				</div>
			</div>
		</div>
		
		</div>
				<table class="table" id='tablePeter' name='table'></table>
			</div>
	</div>

	<!-- jQuery -->
	<script src="Resources/js/jquery-1.6.1.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="Resources/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<script>
var divs = ["imsiCount","modelCount"];
var visibleDiv = null;
var $modelTable = $('#tablePeter')

$(function(){
	
	

	document.getElementById("imsiCount").style.display='none';


	document.getElementById("modelCount").style.display='none';
	

});

function toggle(divId){
	removeData();
 	removeModelData();
	if(visibleDiv === divId) {
		  visibleDiv = null;
	} else {
		    visibleDiv = divId;
		}

	hideOtherDivs();
}

function hideOtherDivs(){
	var i, divId, div;

	for(i = 0; i <divs.length; i++){
		divId = divs[i];
		div = document.getElementById(divId);

		if(visibleDiv == divId){
			div.style.display = 'block';
		}else{
			div.style.display = 'none';
		}
	}
}
</script>
	<script>
	var $table = $('#table');

 	$(function(){

 		$( "#submit" ).click(function(e) {

 	removeData();
 	removeModelData();
	var fromDate=$('#from').val();
	var toDate=$('#to').val();
	var myurl;
	var isValid=false;
	
	if(validateEntry(fromDate, toDate)){	
		myurl='http://localhost:8080/project/rest/failedcalldata/count/'+fromDate+'£'+toDate;
		isValid=true;
		}

	else{
		myurl= 'http://localhost:8080/project/NMEPage.html';
		isValid=false;
		}

 	$.ajax({
 		type: 'GET',
 		url: myurl,
 		success: function(data){
 	 	
 	 		if(isValid && data.length>0){
 	 			createTable();
 	 			createButton();
 	 			$.each(data, function(key, value){
 	 				$table.append('<tr><td>'+value[0]+'</td><td>'+value[1]+'</td><td>'+value[2]+'</td></tr>');
 	 	 			});
	 	 			isValid=false;
 	 	 		}
 	 		else if(isValid && data.length===0){
				alert('No available data for selected dates');
 	 	 		}
 			}

 		});
 	});
 		});

	function validateEntry(from, to){
		if(from===''){
			alert('Invalid FROM DateTime');
			return false;
		}
		else if(to===''){
			alert('Invalid TO DateTime');
			return false;
		}
		else if(isFromGtrTo(from, to)){
			alert('FROM date id greater than TO date');
			return false;
		}
		
		else
			return true;
	}

	function isFromGtrTo(from, to){
			
			var date1 = new Date(from);
			var date2 = new Date(to);
			
			if(date2.getTime()<=date1.getTime())
				return true;
			else
				return false;
	}

	
 	function createTable(){
 		
 		var row=document.createElement('tr');
 		row.setAttribute('id', 'head');
 		var colOne=document.createElement('th');
 		var colTwo=document.createElement('th');
 		var colThree=document.createElement('th');

 		colOne.innerHTML='IMSI';
 		colTwo.innerHTML='Number Of Failures';
 		colThree.innerHTML='Duration';

 		row.appendChild(colOne);
 		row.appendChild(colTwo);
 		row.appendChild(colThree);

 		$table.append(row);
 		
 	}

 	function createButton(){
 		var butDiv=document.createElement('div');
 		butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
 		var button=document.createElement(button);
 		button.setAttribute('id', 'tableButton');
 		button.setAttribute('class','btn btn-primary');
 		button.innerHTML='Search Again';
 		button.addEventListener('click', removeData);
 		butDiv.appendChild(button);
 		$table.append(butDiv);
 		
 	}

 	function removeData(){
 		var removeHead=document.getElementById('head');
 		var removeButton=document.getElementById('tableButton');
 		$table.empty();
 		
 	}
 	 	
	</script>
	
	<!-- US 10 -->
	<!-- Function to show models -->
	<script>
	$(function() {
		$("#modelSubmit").click(function(e) {
			
			removeModelData();
			removeData();
			var x = document.getElementById("modelInput");
			var selected = x.options[x.selectedIndex].text;
			
			createModelTable();
			createModelButton();
			
			$.ajax({
				type:'GET',
				url:'http://localhost:8080/project/rest/failedcalldata/model/'+selected,
				dataType:'json',
				contentType:"application/json",
				
				success:function(data) {
					$.each(data, function(key, value) {
						$modelTable.append('<tr><td>' + value[0] + '</td><td>' + value[1] + '</td><td>'+ value[2]+'</td></tr>')
					});
				}
			});
		});
	});
	
	function createModelTable() {
		
		var row=document.createElement('tr');
		row.setAttribute('id', 'head');
		var colOne=document.createElement('th');
		var colTwo=document.createElement('th');
		var colThree=document.createElement('th');
		

		colOne.innerHTML='Cause Code';
		colTwo.innerHTML='Event ID';
		colThree.innerHTML='Description';
		

		row.appendChild(colOne);
		row.appendChild(colTwo);
		row.appendChild(colThree);
		

		$modelTable.append(row);
	}
	
	function createModelButton() {
		
		var butDiv=document.createElement('div');
		butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
		var button=document.createElement(button);
		button.setAttribute('id', 'modelTableButton');
		button.setAttribute('class','btn btn-primary');
		button.setAttribute('position', 'absolute');
		button.setAttribute('top', '50%');
		button.innerHTML='Search Again';
		button.addEventListener('click', removeModelData);
		butDiv.appendChild(button);
		$modelTable.append(butDiv);
	}
	
	function removeModelData() {
		
		var removeHead=document.getElementById('head');
		var removeButton=document.getElementById('modelTableButton');
		$modelTable.empty();
	}
	</script>
	
	<!-- Function to add phone models to dropdown menu -->
	<script>
		$(function(){

	    var $select = $('#modelInput');

	    $.ajax({
	        type: 'GET',
	        url:'http://localhost:8080/project/rest/userequipment/getAllModelsFromUserEquipment',
	        success: function(mydata){
	            var data=mydata;
	            var length=data.length;

	            for(var i=0; i<length; i++){
	                var x=data[i];
	                var option=document.createElement('option');	
	                option.text=x;	
	                $select.append(option);			
	                }					
	            }
	        });
	    });
	</script>
	
</body>

</html>
