
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
<script src="Resources/js/selectize.js"></script>
<script src="Resources/js/jquery-1.6.1.min.js"></script>
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
			Group one - One Directory <small>Project</small>
		</h2>
		<h2>Support Engineer</h2>
	</div>

	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Menu </a></li>
				<li><a href="#" onclick="toggle('briansQuery');">Total number of Failures per Model</a></li>
				<li><a href="#" onclick="toggle('johnsQuery');">All IMSIs with Failed Call Data</a></li>
				<li><a href="#" onclick="toggle('imsisForFailreClassDiv');">All IMSIs for a FailureClass</a></li>
				<li class="sidebar-brand"><a href="CSRPage.jsp">Customer Service Rep Enq</a></li>
				<li><a href="http://localhost:8080/project/LogoutServlet">Log out</a></li>
			</ul>
			<br>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="briansQuery">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" id='container'>
						<div class="transbox">
						<br>
						<h1>Number of Call Failures by Phone Model</h1>
                    <div class="form-horizontal">
                        
						<div class="form-group" id="myDiv">
								<label class="control-label col-sm-2" for="ID">Model</label>
                            <div class="col-sm-5">
                            <select class="form-control" id="ID" placeholder="Select a model.."
									autofocus>
								</select><br>
							</div>
						</div>
                        
                        
                        
						<div class="form-group" >
                            <label class="control-label col-sm-2" for="ID">From: </label>
                        <div class="col-sm-5">
							<input type="datetime-local" id='from' class="form-control"
								name="from" placeholder="yyyy-dd-mm hh:mm" autofocus>
                        </div>
						</div>
                        
                        <div class = "form-group">
						      <label class="control-label col-sm-2" for="ID">To: </label>
                        <div class="col-sm-5">
							<input type="datetime-local" id='to' class="form-control"
								name="to" placeholder="yyyy-dd-mm hh:mm" autofocus>
						</div>
                        </div>
                            
                        <div class="form-group">
						<div class="col-sm-offset-4 col-sm-10">
							<br>
							<button id="submitBrian" type="submit" class="btn btn-primary">Search</button>
                            </div>
						</div>
					</div>
				</div>
                </div>

			</div>
		</div>
	</div>
	<table class="tableSE" width = '500px' id='tableBrian' name='table'>


	</table>
	
	<!-- /#page-content-wrapper -->

	<div id="johnsQuery">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
                    <div class="transbox">
						<br>
					<h1>All IMSIs with Call Failures between:</h1>
                    <div class ="form-horizontal">
                    
                        <div class ="form-group">
					       <label class="control-label col-sm-2" for="ID">From:</label>
                            <div class ="col-sm-5">
						<input type="datetime-local" id='IMSIFrom' class="form-control"
							name="from" placeholder="dd-mm-yyyy hh:mm" autofocus>
					       </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="ID">To:</label>
                            <div class="col-sm-5">
						<input type="datetime-local" id='IMSITo' class="form-control"
							name="to" placeholder="dd-mm-yyyy hh:mm" autofocus>
					       </div>
                        </div>

					<div class="col-sm-offset-4 col-sm-10">
						<br>
						<button id="submitJohn" type="submit" class="btn btn-primary">Search</button>
					</div>

				</div>
                    </div>
                    </div>
			</div>
		</div>
		<div id='tableJohn' >
		
		</div>
	</div>
        <div id="imsisForFailreClassDiv">
            <div class = 'container-fluid'>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="transbox">
						<br>
					<h1>All IMSIs for Failure Class</h1>
                    <div class ="form-horizontal">
		              <div id="failureClassDiv" class="form-group">
                          <label class="control-label col-sm-2" for="ID">Failure Class:</label>
                      <div class="col-sm-5">
			               <select class="form-control" id="failureClassSelectDiv"></select>
                      </div>
                      </div>
                      
                <div class="col-sm-offset-4 col -sm-10">
			         <button id="failureClassSelectButton" class="btn btn-primary">Search</button>
			    </div>
                </div>
                      
			<div id="imsisTableForFailreClassDiv">
			</div>
               </div>       
		</div>
		</div>
	</div>
        </div>
        

		<!-- /#page-content-wrapper -->

	
	<!-- /#wrapper -->


	
                           
        	
                     
						
	</div>
    

		<!-- /#wrapper -->

    
    
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

    function createCauseCodeButton(){

    	var butDiv=document.createElement('div');
 		butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
 		var button=document.createElement(button);
 		button.setAttribute('id', 'causeCodeTableButton');
 		button.setAttribute('class','btn btn-primary');
 		button.innerHTML='Search Again';
 		button.addEventListener('click', removeCauseCodeData);
 		butDiv.appendChild(button);
 		$viewCauseCode.append(butDiv);

    }

    function removeCauseCodeData(){
        var removeHead=document.getElementById('thead');
        var removeButton=document.getElementById('causeCodeTableButton');
        $table.empty();

    }

</script>		
<script>
$(function(){
	
	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/failureclasses',
		contentType:'application/json',
		success: function(data){
			var x = document.getElementById("failureClassSelectDiv");
			$.each(data, function(i, fd){
				var option = document.createElement('option');
				option.text = "ID: "+fd.failureId+" - "+"Description: "+ fd.description;
				x.appendChild(option);
				});
			}
		});

$('#failureClassSelectButton').click(function(e){
	var x = document.getElementById("failureClassSelectDiv");
	var selectedOption = x.options[x.selectedIndex].text;
	var failureID = selectedOption.split('-')[0];
	var idInt = failureID.split(':')[1].trim();

	$('#imsisTableForFailreClassDiv').empty();
	createImsiFailureClassTable();

	
	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsibyfailureclass/'+idInt,
		contentType:'application/json',
		success: function(data){
			for(var i=0; i<data.length; i++){
				$('#viewImsisFailureClass').find('tbody').append('<tr><td>'+data[i]+'</td></tr>');
			}
			$('#viewImsisFailureClass').dataTable();
			}	
});	
});
});

function createImsiFailureClassTable(){
	var div = document.getElementById('imsisTableForFailreClassDiv');
	var divContainer = document.createElement('div');
	divContainer.setAttribute('class', 'table-responsive');
	divContainer.setAttribute('id', 'divContainer');
	var table=document.createElement('table');
	table.setAttribute('class', 'tableSE');
	table.setAttribute('id', 'viewImsisFailureClass');
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
	div.appendChild(divContainer);
	
}

</script>		

<script>

        $(function(){

        var $select = $('#ID');

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
 
 <!-- Add all IMSIs to list? -->
<script>

$(function(){
	var $select = $('#imsiSelect');
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
	var $causeCodeTable = $('#causeCodeTable');
		$(function(){

	$( "#id/causecodesubmit" ).click(function(e) {
	
	removeData();
    removeDataJohn();
    removeCauseCodeData()	
	var x=document.getElementById("imsiSelect");
	var selected=x.options[x.selectedIndex].text;

	createCauseCodeTable();
	createCauseCodeButton();

	$.ajax({

		type:'GET',
		url:'http://localhost:8080/project/rest/failedcalldata/imsi/'+selected,
		dataType: 'json',
		contentType: "application/json",

		success:function(data){
			
			$.each(data, function(key, value){
				
				$causeCodeTable.append('<tr><td>'+value[0]+'</td><td>'+value[1]+'</td><td>'+value[2]+'</td><td>'+selected+'</td></tr>');
			});
		}
	  });
   });
});

function createCauseCodeTable(){
	
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

	$causeCodeTable.append(row);
	
}

function createCauseCodeButton(){

	var butDiv=document.createElement('div');
	butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
	var button=document.createElement(button);
	button.setAttribute('id', 'tableButton');
	button.setAttribute('class','btn btn-primary');
	button.setAttribute('position', 'absolute');
	button.setAttribute('top', '50%');
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
$(function(){

 	$( "#submitBrian" ).click(function(e) {	

 	removeData();
 	removeDataJohn();
    removeCauseCodeData();
	var fromDate=$('#from').val();
	var toDate=$('#to').val();
	var myurl;
	var x=document.getElementById("ID");
    var selected=x.options[x.selectedIndex].text;
	var isValid=false;
	
	if(validateEntry(fromDate, toDate)){	
		myurl='http://localhost:8080/project/rest/failedcalldata/getFailedCallDataByModel/'+selected+'£'+fromDate+'£'+toDate;
		isValid=true;
		}

	else{
		myurl= 'http://localhost:8080/project/SEPage.jsp';
		isValid=false;
		}

        $.ajax({

            type:'GET',
            url: myurl,
            dataType: 'json',
            contentType: "application/json",

            success:function(data){
              if(isValid && data.length>0){
 	 			createTable();
 	 			createButton();
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
        
   
    function createTable(){
		var x=document.getElementById("ID");
        var selected=x.options[x.selectedIndex].text;
        var row=document.createElement('tr');
        row.setAttribute('id', 'head');
        var colOne=document.createElement('th');
     
        colOne.innerHTML='Count of failed calls for '+selected;     
        row.appendChild(colOne);     
        $table.append(row);

    }

    function createButton(){

        var butDiv=document.createElement('div');
 		butDiv.setAttribute('class', "col-sm-offset-5 col-sm-10");
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

<script>
	$(function(){		
		$( "#submitJohn" ).click(function(e) {
			
			removeData();
		 	removeDataJohn();
            removeCauseCodeData()
			var fromDate=$('#IMSIFrom').val();
			var toDate=$('#IMSITo').val();
			var myurl;
			var isValid=false;
			
			if(validateEntry(fromDate, toDate)){	
				myurl='http://localhost:8080/project/rest/failedcalldata/dateIMSI/'+fromDate+'£'+toDate;
				isValid=true;
				}
			
			else{
				myurl= 'http://localhost:8080/project/supportPage3.html';
				isValid=false;
				}
			
			$.ajax({
		 		type: 'GET',
		 		url: myurl,
		 		success: function(data){
		 			
		 	 		if(isValid && data.length>0){
		 	 			createTableJohn();
		 	 			createButtonJohn();
		 	 			$.each(data, function(key, value){
		 	 				$('#viewImsis').find('tbody').append('<tr><td>'+value+'</td></tr>');
		 	 	 			});
			 	 			isValid=false;
			 	 			$('#viewImsis').dataTable();	
		 	 	 		}
	 	 	 		
		 	 		else if(isValid && data.length===0){
						alert('No available data for selected dates');
		 	 	 		}
		 			}

		 		});
			});
		});
	
	
	
 	function createTableJohn(){
 	 	var tableDiv = document.getElementById('tableJohn')
 		var divContainer = document.createElement('div');
 		divContainer.setAttribute('class', 'table-responsive');
 		divContainer.setAttribute('id', 'divContainer');
 		var table=document.createElement('table');
 		table.setAttribute('class', 'table table-striped');
 		table.setAttribute('id', 'viewImsis');
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
 	
 	function createButtonJohn(){
 		var butDiv=document.createElement('div');
 		butDiv.setAttribute('class', "col-sm-offset-12 col-sm-10");
 		var button=document.createElement(button);
 		button.setAttribute('id', 'tableButton');
 		button.setAttribute('class','btn btn-primary');
 		button.innerHTML='Search Again';
 		button.addEventListener('click', removeDataJohn);
 		butDiv.appendChild(button);
 		$tableJohn.append(butDiv);
 		
 	}

 	function removeDataJohn(){
 		var removeHead=document.getElementById('head');
 		var removeButton=document.getElementById('tableButton');
 		$tableJohn.empty();
 		
 	}

 	var divs = ["johnsQuery","briansQuery","imsisForFailreClassDiv"];
	var visibleDiv = null;
	 var $tableJohn = $('#tableJohn');
	 var $table = $('#tableBrian');

	$(function(){

		document.getElementById("johnsQuery").style.display='none';
		document.getElementById("briansQuery").style.display='none';
		document.getElementById("imsisForFailreClassDiv").style.display='none';

	});

	function toggle(divId){
		removeData();
	 	removeDataJohn();
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
 	

    </script>
</body>

</html>
