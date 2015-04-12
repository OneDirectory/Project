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
<!-- jQuery -->
<script src="Resources/js/jquery-1.6.1.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="Resources/js/bootstrap.min.js"></script>

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">   
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
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
				<li><a href="#" onclick="toggle('imsiCount');">Duration/FailureCount per IMSI</a></li>
				<li><a href="#" onclick="toggle('modelCount');">EventId/CauseCode per Model</a></li>
				<li class="sidebar-brand"><a href="/project/SEPage.jsp">Software Engineer</a></li>
				<li class="sidebar-brand"><a href="/project/CSRPage.jsp">Customer Service Rep.</a></li>
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
				<div id='tableForImsiCountDiv' ></div>
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
				<div id='tableForModelQuery' ></div>
			</div>
	</div>

	

<!-- Menu Toggle Script -->
<script>

</script>
	<script>

 	$(function(){

 		$( "#submit" ).click(function(e) {

 	removeData();
 	removeModelData();
	fromDate=$('#from').val();
	toDate=$('#to').val();
	var myurl;
	var isValid=false;
	
	if(validateEntry(fromDate, toDate)){	
		myurl='http://localhost:8080/project/rest/failedcalldata/count/'+fromDate+'�'+toDate+'�0�5';
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
 	 				$('#viewImsisWithCount').find('tbody').append('<tr><td>'+value[0]+'</td><td>'+value[1]+'</td><td>'+value[2]+'</td></tr>');
 	 	 			});
	 	 			isValid=false;
	 	 			$('#viewImsisWithCount').dataTable();
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

 		var tableDiv = document.getElementById('tableForImsiCountDiv')
 		var divContainer = document.createElement('div');
 		divContainer.setAttribute('class', 'table-responsive');
 		divContainer.setAttribute('id', 'divContainer');
 		var table=document.createElement('table');
 		table.setAttribute('class', 'table table-striped');
 		table.setAttribute('id', 'viewImsisWithCount');
 		var header = document.createElement('thead');
 		var body = document.createElement('tbody');
 		var row = document.createElement('tr');
 		var colOne=document.createElement('td');
 		var colTwo=document.createElement('td');
 		var colThree=document.createElement('td');

 		colOne.innerHTML='IMSI';
 		colTwo.innerHTML='Number Of Failures';
 		colThree.innerHTML='Duration';

 		row.appendChild(colOne);
 		row.appendChild(colTwo);
 		row.appendChild(colThree);

 		header.appendChild(row);
 		table.appendChild(header);
		table.appendChild(body);
		divContainer.appendChild(table);
		tableDiv.appendChild(divContainer);
 		
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
 		$('#viewImsisWithCount').append(butDiv);
	
 	}

 	function removeData(){
 		var removeHead=document.getElementById('head');
 		var removeButton=document.getElementById('tableButton');
 		$('#tableForImsiCountDiv').empty();	
 			
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
						$('#viewModelData').append('<tr><td>' + value[0] + '</td><td>' + value[1] + '</td><td>'+ value[2]+'</td></tr>')
					});
					$('#viewModelData').dataTable();	
				}
			});
		});
	});
	
	function createModelTable() {
		
		var tableDiv = document.getElementById('tableForModelQuery')
 		var divContainer = document.createElement('div');
 		divContainer.setAttribute('class', 'table-responsive');
 		divContainer.setAttribute('id', 'divContainer');
 		var table=document.createElement('table');
 		table.setAttribute('class', 'table table-striped');
 		table.setAttribute('id', 'viewModelData');
 		var header = document.createElement('thead');
 		var body = document.createElement('tbody');
 		var row = document.createElement('tr');
 		var colOne=document.createElement('td');
 		var colTwo=document.createElement('td');
 		var colThree=document.createElement('td');
		
		colOne.innerHTML='Cause Code';
		colTwo.innerHTML='Event ID';
		colThree.innerHTML='Description';
		
		row.appendChild(colOne);
		row.appendChild(colTwo);
		row.appendChild(colThree);

 		header.appendChild(row);
 		table.appendChild(header);
		table.appendChild(body);
		divContainer.appendChild(table);
		tableDiv.appendChild(divContainer);
		
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
		$('#tableForModelQuery').empty();
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


		var divs = ["imsiCount","modelCount"];
		var visibleDiv = null;
		var $modelTable = $('#tablePeter')
		var indexFrom=0;
		var indexTo=5
		var fromDate;
		var toDate;

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
	
</body>

</html>
