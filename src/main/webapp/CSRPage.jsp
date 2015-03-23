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
			Group one - One Directory <small>Project</small>
		</h2>
		<h2>Customer Service Representative Page</h2>
	</div>

	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Menu </a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Search by IMSI</a></li>
				<li><a href="http://localhost:8080/project/LogoutServlet">Log out</a></li>
			</ul>
			<br>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12" id='container'>
						<h1>Search EventId, CauseCode by IMSI</h1>

						<div class="form-group" id="myDiv">
							<label class="control-label col-sm-2" for="ID">IMSI:</label> <select
								class="col-sm-5" id="ID">

							</select>


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
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->


	<table class="table" id='table' name='table'>

		<div id="butDiv"></div>
	</table>




	<script src="Resources/js/jquery-1.6.1.min.js"></script>
	<script src="Resources/js/bootstrap.min.js"></script>

	

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
</script>


</body>

</html>
