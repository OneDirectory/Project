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
<script src="Resources/js/jquery-1.6.1.min.js"></script>
<script src="Resources/js/bootstrap.min.js"></script>
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
		<h2>Customer Service Representative</h2>
	</div>

	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Menu </a></li>
				<li><a href="#" onclick="toggle('event/cause');">EventID/CauseCode per IMSI</a></li>
                <li><a href="#" onclick="toggle('failCount');">Count of Call Failures per IMSI</a></li>
                <li><a href="#" onclick="toggle('causeCodes');">Cause Codes per IMSI</a>
				<li><a href="http://localhost:8080/project/LogoutServlet">Log out</a></li>
			</ul>
			<br>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="event/cause">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" id='container'>
                        <div class="transbox">
						<br>
						<h1>Search EventId, CauseCode by IMSI</h1>
                    <div class="form-horizontal">
						<div class="form-group" id="myDiv">
							<label class="control-label col-sm-2" for="eventImsi">IMSI:</label> 
                             <div class="col-sm-5">
                            <select class="form-control" id="eventImsi"></select>
                            </div>
                         </div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-10">
									<br>
									<button id='eventSubmit' name='submit' class="btn btn-primary">Search</button>
								</div>
							</div>
						</div>
                        </div>
                        </div>
					</div>
				</div>
			</div>
        
        <div id="causeCodes">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" id='container'>
                        <div class="transbox">
						<br>
						<h1>All Cause Codes for IMSI</h1>
                        <div class="form-horizontal">
						<div class="form-group" id="myDiv">
							<label class="control-label col-sm-2" for="causeCodeImsi">IMSI:</label> 
                            <div class="col-sm-5">
                            <select class="form-control" id="causeCodeImsi"></select>
                                </div>
                            </div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-10">
									<br>
									<button id='causeCodeImsiSubmit' name='submit' class="btn btn-primary">Search</button>
								</div>
							</div>
						</div>
                        </div>
					</div>
				</div>
			</div>
            <div id="causeCodeTable"></div>
		</div>
		
		<div id="failCount">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" id='container'>
						<div class="transbox">
						<br>
						<h1>Count of Call Failures by IMSI</h1>
                    <div class="form-horizontal">
                        
						<div class="form-group" id="myDiv">
								<label class="control-label col-sm-2" for="imsiInput">IMSI:</label>
                            <div class="col-sm-5">
                            <select class="form-control" id="imsiInput" placeholder="Select an IMSI.."
									autofocus>
								</select><br>
							</div>
						</div>
                        
                        
                        
						<div class="form-group" >
                            <label class="control-label col-sm-2" for="ID">From: </label>
                        <div class="col-sm-5">
							<input type="datetime-local" id='failCountFrom' class="form-control"
								name="failCountFrom" placeholder="yyyy-dd-mm hh:mm" autofocus>
                        </div>
						</div>
                        
                        <div class = "form-group">
						      <label class="control-label col-sm-2" for="ID">To: </label>
                        <div class="col-sm-5">
							<input type="datetime-local" id='failCountTo' class="form-control"
								name="failCountTo" placeholder="yyyy-dd-mm hh:mm" autofocus>
						</div>
                        </div>
                            
                        <div class="form-group">
						<div class="col-sm-offset-4 col-sm-10">
							<br>
							<button id="countSubmit" type="submit" class="btn btn-primary">Search</button>
                            </div>
						</div>
					</div>
					</div>
					<div id='countImsiTablePeter'></div>
                    </div>

			</div>
		</div>
	</div>
		
		
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<table class="table" id='table' name='table'>
		<div id="butDiv"></div>
	</table>
	
    
<script>
/* adding imsi to select for causecodes*/
var $table = $('#causeCodeTable');
	$(function(){
    var $select = $('#causeCodeImsi');
	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsi',
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
		})
	});
    </script>
    
<script>    
/*getting cause code by imsi*/
    $('#causeCodeImsiSubmit').click(function(e){
	var x = document.getElementById("causeCodeImsi");
	var selectedOption = x.options[x.selectedIndex].text;
	  $.ajax({
            type:'GET',
             url:'http://localhost:8080/project/rest/failedcalldata/uniqueCauseCodes/'+selectedOption,
            dataType: 'json',
            contentType: "application/json",
            success:function(data){
 	 			createCauseCodeTable();
//  	 			createCauseCodeButton();
 	 			$.each(data, function(key, value){
 	 				//alert(value);
 	 				$('#viewCauseCode').find('tbody').append('<tr><td>'+value+'</td></tr>');
 	 	 			});
 	 			$('#viewCauseCode').dataTable();
            }
          });
       });
        
   
    function createCauseCodeTable(){
    	var tableDiv = document.getElementById('causeCodeTable')
 		var divContainer = document.createElement('div');
 		divContainer.setAttribute('class', 'table-responsive');
 		divContainer.setAttribute('id', 'divContainer');
 		var table=document.createElement('table');
 		table.setAttribute('class', 'table table-striped');
 		table.setAttribute('id', 'viewCauseCode');
 		var header = document.createElement('thead');
 		var body = document.createElement('tbody');
 		var row = document.createElement('tr');
 		var colOne=document.createElement('td');
 		colOne.innerHTML = 'IMSI';
 		row.appendChild(colOne);
 		header.appendChild(row);
 		table.appendChild(header);
		table.appendChild(body);
		divContainer.appendChild(table);
		tableDiv.appendChild(divContainer);
    }

   
</script>		

<script>

// add imsi to event/id dropdown

var $table = $('#table');
	$(function(){


    var $select = $('#eventImsi');

	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsi',
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


<!-- Add all IMSIs to list for peters -->
<script>
$(function(){
	var $select = $('#imsiInput');
	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsi',
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

<script>

// ajax for event/id

$(function(){


	$( "#eventSubmit" ).click(function(e) {
	
	removeData();	
	var x=document.getElementById("eventImsi");
	var selected=x.options[x.selectedIndex].text;

	createEventTable();
	createEventButton();

	$.ajax({
		type:'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsi/'+selected,
		dataType: 'json',
		contentType: "application/json",
		success:function(data){
			
			$.each(data, function(key, value){
				
				$table.append('<tr><td>'+value[0]+'</td><td>'+value[1]+'</td><td>'+value[2]+'</td><td>'+selected+'</td></tr>');
			});
		}
	  });
   });
});



function createEventTable(){
	var row=document.createElement('tr');
	row.setAttribute('id', 'head');
	var colOne=document.createElement('th');
	var colTwo=document.createElement('th');
	var colThree=document.createElement('th');
	var colFour=document.createElement('th');
	colOne.innerHTML='Cause Code';
	colTwo.innerHTML='Event ID';
	colThree.innerHTML='Description';
	colFour.innerHTML ='IMSI'
	row.appendChild(colOne);
	row.appendChild(colTwo);
	row.appendChild(colThree);
	row.appendChild(colFour);
	$table.append(row);
	
}


function createEventButton(){
	var butDiv=document.createElement('div');
	butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
	var button=document.createElement(button);
	button.setAttribute('id', 'tableButton');
	button.setAttribute('class','btn btn-primary');
	button.setAttribute('position', 'absolute');
	button.setAttribute('top', '50%');
	button.innerHTML='Search Again';
	button.addEventListener('click', removeData);
	butDiv.appendChild(button);
	$table.append(butDiv);
	
}
function removeData(){
	
	$table.empty();
	$('#countImsiTablePeter').empty();
	$('#causeCodeTable').empty();
	
	
}
	


function removeEventData(){
	var removeHead=document.getElementById('head');
	var removeButton=document.getElementById('tableButton');
	$table.empty();
	$('#countImsiTablePeter').empty();
	$('#causeCodeTable').empty();
	
	
}
//table for the count for peter's
$(function(){
	$( "#countSubmit" ).click(function(e) {
	
		
	removeData();

	var fromDate=$('#failCountFrom').val();
	var toDate=$('#failCountTo').val();
	var x=document.getElementById("imsiInput");
	var selected=x.options[x.selectedIndex].text;
	createCountTable();
	
	$.ajax({
        type:'GET',
        url: 'http://localhost:8080/project/rest/failedcalldata/getCountFailedCallsInTimePeriodByImsi/'+selected+'£'+fromDate+'£'+toDate,
        dataType: 'json',
        contentType: "application/json",
        success:function(data){
        	$('#countTable').find('tbody').append('<tr><td>'+data+'</td></tr>'); 	 		
        }
      });
	
   });
});
function createCountTable(){
	var tableDiv = document.getElementById('countImsiTablePeter')
		var divContainer = document.createElement('div');
		divContainer.setAttribute('class', 'table-responsive');
		divContainer.setAttribute('id', 'divContainercountImsiTablePeter');
		var table=document.createElement('table');
		table.setAttribute('class', 'table table-striped');
		table.setAttribute('id', 'countTable');
		var header = document.createElement('thead');
		var body = document.createElement('tbody');
		var row = document.createElement('tr');
		var colOne=document.createElement('td');
		colOne.innerHTML = 'COUNT';
		row.appendChild(colOne);
		header.appendChild(row);
		table.appendChild(header);
		table.appendChild(body);
		divContainer.appendChild(table);
		tableDiv.appendChild(divContainer);
	
	
}


    
var divs = ["event/cause","failCount","causeCodes"];
	var visibleDiv = null;
	// var $tableJohn = $('#tableJohn');
	// var $table = $('#tableBrian');
	$(function(){
		document.getElementById("event/cause").style.display='none';
		document.getElementById("failCount").style.display='none';
        document.getElementById("causeCodes").style.display='none';
	});
	function toggle(divId){
		removeData();
	 
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
