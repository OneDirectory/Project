
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

<body>
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
		<h2>Admin Page</h2>
	</div>

	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Menu </a></li>
				<li><a href="#" onclick="toggle('createUser');">Add a user</a></li>
				<li><a href="#" onclick="toggle('import');">Import data</a></li>
				<li><a href="http://localhost:8080/project/LogoutServlet">Log out</a></li>
				
	
				
			</ul>
			<br>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="createUser">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<div class="createUserTransbox">
						<h1>Create New User</h1>
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-2" for="ID">ID:</label>
								<div class="col-sm-5">
									<input type="number" class="form-control" name="ID"
										placeholder="ID" id="id" autofocus>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="pwd">Password:</label>
								<div class="col-sm-5">
									<input type="password" id="password" class="form-control"
										name="password" placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="pwd">First
									Name:</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="fName"
										name="firstname" placeholder="First Name">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="pwd">Last
									Name:</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="lName"
										name="lastname" placeholder="Last Name">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-1 col-sm-10">
									<div class="col-sm-10" id="types">
										<br>
										<label class="radio-inline">
											<input type="radio" name="role" checked="checked"
											value="Network Management Engineer">
											Network Management Engineer
										</label>
										<br>
										<label class="radio-inline">
											<input type="radio" name="role" value="Support Engineer">
											Support Engineer
										</label>
										<br>
										<label class="radio-inline">
											<input type="radio" name="role" value="Customer Service Rep">
											Customer Service Rep
										</label>
									</div>
								</div>
							</div>

								<div class="form-group">
									<div class="col-sm-offset-4 col-sm-10">
										<br>
										<button id='regButton' type="submit" class="btn btn-primary">Create
											User</button>
									</div>
								</div>
						</div>
						</div>
						<br>
						<div class="transboxTable">
							<table class="table" id='table' name='table'>
								<tr>
									<th>UserID</th>
									<th>Name</th>
									<th>UserType</th>
				
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /#page-content-wrapper -->

		<div id="import">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<div class="createUserTransbox">
							<h1>Import Failed Call Data</h1>
							<div class="form-horizontal">
								<div class="form-group">
									<label class="control-label col-sm-2" for="pwd">
									Please select file: 
									</label>
									<div class="col-sm-5">
										<form action="rest/failedcalldata/upload" method="post"
										enctype="multipart/form-data">
											<br>
											<input type="file" name="selectedFile" />
											<br>
											<input type="submit" value="Upload" />
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="Resources/js/jquery-1.6.1.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="Resources/js/bootstrap.min.js"></script>

	<!-- Menu Toggle Script -->
	<script>
var divs = ["createUser","import"];
var visibleDiv = null;
var ids = [];


$(function(){

	document.getElementById("createUser").style.display='none';
	document.getElementById("import").style.display='none';

	$('#regButton').click(function(e){

		var id = document.getElementById('id').value;
		var pass = document.getElementById('password').value;
		var fName = document.getElementById('fName').value;
		var lName = document.getElementById('lName').value;
		var type = $("input[type='radio'][name='role']:checked").val();;


		var correct = check_form();

		if(correct==true){

			newUser = {
					userID: id,
					userType: type,
					userFName: fName,
					userSName: lName,
					userPassword: pass
					}

			$.ajax({
				type: 'POST',
				url:'http://localhost:8080/project/rest/user/add',
			    dataType: 'json',
				data:JSON.stringify(newUser),
				contentType: "application/json",
				success: function(data){
					alert('User successfully added');
				}	
			});
		}
	});	
});

function toggle(divId){
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




function check_form(){
				
var fieldID = document.getElementById("id").value;		
var fieldPassword = document.getElementById("password").value;
var fieldFName = document.getElementById("fName").value;
var fieldLName = document.getElementById("lName").value;
var idTaken = false;


	


	if(fieldID.match(/^[0-9]+$/)){
		for (var i = 0; i < ids.length; i++) {
	    	if(fieldID==ids[i]){
	        	alert("ID is already Taken")
	        	return false;
	        	}
		}
					
	}else{
		alert('Please enter numeric ID');
		return false;
		}
	if(fieldPassword.length<4 || fieldPassword.length>6){
		alert('Please enter a password of 4-6 charachters');
		return false;
	}
				
	if(fieldFName.length===0){
		alert('Enter a first name');
		return false;
	}
	if(fieldLName.length===0){
		alert('Enter a last name');
		return false;
	}
			
	return true;

}


	$(function(){

	var idArray = [];	
    var $users = $('#table');
    

	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/user',
		success: function(users){
			$.each(users, function(i, user){
			$users.append('<tr><td>'+user.userID+'</td><td>'+user.userFName+' '+user.userSName+'</td><td>'+user.userType+'</td></tr>');
				ids[i] = user.userID;
			});
			}

		});

	});

</script>
</body>

</html>
