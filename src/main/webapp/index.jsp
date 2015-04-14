<!DOCTYPE HTML>

<html>

<head>
<!-- Bootstrap Core CSS -->
<link href="Resources/css/bootstrap1.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="Resources/css/simple-sidebar.css" rel="stylesheet">
<link href="Resources/css/ProjectStyleSheet.css" rel="stylesheet">
</head>

<body class="index">


	<div class="page-header">
		<br>
		<h2>
			Group one - One Directory <small>Project</small>
		</h2>
		<h2>Login Page</h2>
	</div>

	<!-- creating Sign in form, source (incl css): http://getbootstrap.com/examples/signin/ -->
	<!--<div class="container" "col-md-4 col-md-offset-4">-->
	<form action="http://localhost:8080/project/LoginServlet" id='form' method="post" onsubmit="return check_form();">
		<div class="col-md-4 col-md-offset-2">
			<div class="transboxindex">
							<br>
			<h2>Please sign in</h2>
			<br> <input id="id" name="id" type="number" placeholder="UserID"
				class="form-control" required autofocus> <br> <input
				name="pass" id="pass" type="password" placeholder="Password"
				class="form-control" required> <br>

			<button class="btn btn-lg btn-primary btn-block" name="submit"
				id="submit" value="Login">Log In</button>
			<br>
			</div>
			</div>
	</form>

	<footer>
	<div data-role=footer>
		<p>Authors: Calvin McGowan, Darren Kane, Peter Farrell, John
			Fleming, Brian Cowzer</p>
	</div>
</footer>
	
	

	<script src="Resources/js/jquery-1.6.1.min.js"></script>
<!-- 	<script src="https://code.jquery.com/jquery.js"></script> -->
	<script src="Resources/js/bootstrap.min.js"></script>
	<!-- jQuery -->

	<script>

	$(function(){

		var result;
		
	});
	
function check_form(){	
	$.when(ajaxMy()).done(function(a1){
		alert(result);
		return result;
	});
	

}

function ajaxMy(){
	var pass= $("#pass").val();
	
	
	$.ajax({
		type: 'GET',
		url:'http://localhost:8080/project/rest/user/'+$("#id").val(),
		dataType: 'json',
		contentType: "application/json",
		async: 'false',
		success: function(retData){
			
			if(retData===null){
				alert('User ID does not exist');
				result = false;
			}else if(retData.userPassword!=pass){
				alert('Incorrect Password');
				result = false;
			}else
				result = true;

		}
	});
}

	</script>
</body>


</html>

