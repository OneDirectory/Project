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
			Group one - One Directory <small>Project</small>
		</h2>
		<h2>Customer Service Representative Page</h2>
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
							<label class="control-label col-sm-2" for="ID">IMSI:</label> 
                             <div class="col-sm-5">
                            <select class="form-control" id="ID"></select>
                            </div>
                         </div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-10">
									<br>
									<button id='submit' name='submit' class="btn btn-primary">Search</button>
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
							<label class="control-label col-sm-2" for="ID">IMSI:</label> 
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
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
                    <div class="transbox">
						<br>
					<h1>Count of Call Failures by IMSI</h1>	
                    <div class="form-horizontal">
                        
                        <div class="from-group">
						<label class="control-label col-sm-2" for="imsiInput">IMSI: </label>
                        <div class="col-sm-5">
							<select class="form-control" id="imsiInput"></select>
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
		      </div>	
		<!--  </div>
				<div id='tableForCountQuery' ></div>
			</div> -->
            </div>
        </div>
	</div>
		
		
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<table class="table" id='table' name='table'>
		<div id="butDiv"></div>
	</table>
	<table class="table" id="countTable" name="countTable">
		<div id="butDiv2"></div>
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
		});
	});
    
    
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
              if(isValid && data.length>0){
 	 			createCauseCodeTable();
 	 			createCauseCodeButton();
 	 			$.each(data, function(key, value){
 	 				$table.append('<tr><td>'+data+'</td></tr>');
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
        
   
    function createCauseCodeTable(){
		var x=document.getElementById("causeCodeImsi");
        var selected=x.options[x.selectedIndex].text;
        var row=document.createElement('tr');
        row.setAttribute('id', 'head');
        var colOne=document.createElement('th');
     
        colOne.innerHTML='Cause Codes for IMSI: '+selected;     
        row.appendChild(colOne);     
        $table.append(row);

    }

    function createCauseCodeButton(){

        var butDiv=document.createElement('div');
 		butDiv.setAttribute('class', "col-sm-offset-5 col-sm-10");
 		var button=document.createElement(button);
 		button.setAttribute('id', 'tableButton');
 		button.setAttribute('class','btn btn-primary');
 		button.innerHTML='Search Again';
 		button.addEventListener('click', removeCauseCodeData);
 		butDiv.appendChild(button);
 		$table.append(butDiv);

    }

    function removeCauseCodeData(){
        var removeHead=document.getElementById('head');
        var removeButton=document.getElementById('tableButton');
        $table.empty();

    }

</script>		

<script>

var $table = $('#table');

	$(function(){

    var $select = $('#ID');

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


<!-- Add all IMSIs to list? -->
<script>
var $countTable = $('#countTable');

$(function(){
	var $select = $('#ID');
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

$(function(){

	$( "#submit" ).click(function(e) {
	
	removeData();	
	var x=document.getElementById("ID");
	var selected=x.options[x.selectedIndex].text;

	createTable();
	createButton();

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


function createTable(){
	
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

function createButton(){

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
	var removeHead=document.getElementById('head');
	var removeButton=document.getElementById('tableButton');
	$table.empty();
	
}

//table for the count

$(function(){

	$( "#countSubmit" ).click(function(e) {
	
	removeCountData();	
	var x=document.getElementById("ID");
	var selected=x.options[x.selectedIndex].text;

	createCountTable();
	createCountButton();

	$.ajax({

		type:'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/getCountFailedCallsInTimePeriodByImsi/'+fromDate+'£'+toDate+'£0£5'+selected,
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

function createCountTable(){
	
	var row=document.createElement('tr');
	row.setAttribute('id', 'counthead');
	var colOne=document.createElement('th');
	var colTwo=document.createElement('th');
	var colThree=document.createElement('th');
	var colFour=document.createElement('th');

	colOne.innerHTML='ID';
	colTwo.innerHTML='IMSI';
	colThree.innerHTML='Description';
	colFour.innerHTML ='Count'

	row.appendChild(colOne);
	row.appendChild(colTwo);
	row.appendChild(colThree);
	row.appendChild(colFour);

	$table.append(row);
	
}

function createCountButton(){

	var butDiv2=document.createElement('div');
	butDiv2.setAttribute('class', "col-sm-offset-12 col-sm-10");
	var countButton=document.createElement(button);
	countButton.setAttribute('id', 'countTableButton');
	countButton.setAttribute('class','btn btn-primary');
	countButton.setAttribute('position', 'absolute');
	countButton.setAttribute('top', '50%');
	countButton.innerHTML='Search Again';
	countButton.addEventListener('click', removeData);
	butDiv2.appendChild(countButton);
	$table.append(butDiv2);
	
}


function removeCountData(){
	var removeHead=document.getElementById('countHead');
	var removeButton=document.getElementById('countTableButton');
	$table.empty();
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
		//removeData();
	 	//removeDataJohn();
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
