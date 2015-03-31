
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
<script src="js/selectize.js"></script>
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
				<li><a href="#" onclick="toggle('briansQuery');">failed
						data by model</a></li>
				<li><a href="#" onclick="toggle('johnsQuery');">List Call
						Failures by IMSI</a></li>
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
						<h1>Search failed data by model</h1>

						<div class="form-group" id="myDiv">
							<div class="col-sm-5">
								<label class="control-label col-sm-2" for="ID">Model:</label> <select
									class="form-control" id="ID" placeholder="Select a model.."
									autofocus>

								</select>
							</div>
						</div>

						<div >
							<input type="datetime-local" id='from' class="form-control"
								name="from" placeholder="yyyy-dd-mm hh:mm" autofocus>
						</div>

						<label class="control-label col-sm-2" for="ID">TO:</label>

						<div>
							<input type="datetime-local" id='to' class="form-control"
								name="to" placeholder="yyyy-dd-mm hh:mm" autofocus>
						</div>



						<div class="col-sm-offset-4 col-sm-10">
							<br>
							<button id="submitBrian" type="submit" class="btn btn-primary">Search</button>
						</div>
					</div>
				</div>


			</div>
		</div>
	</div>
	<table class="table" id='tableBrian' name='table'>


	</table>
	</div>
	<!-- /#page-content-wrapper -->

	<div id="johnsQuery">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<h1>Call Failures</h1>

					<label class="control-label col-sm-2" for="ID">FROM:</label><br>

					<div>
						<input type="datetime-local" id='IMSIFrom' class="form-control"
							name="from" placeholder="dd-mm-yyyy hh:mm" autofocus>
					</div>

					<label class="control-label col-sm-2" for="ID">TO:</label>

					<div>
						<input type="datetime-local" id='IMSITo' class="form-control"
							name="to" placeholder="dd-mm-yyyy hh:mm" autofocus>
					</div>

					<div class="col-sm-offset-4 col-sm-10">
						<br>
						<button id="submitJohn" type="submit" class="btn btn-primary">Search</button>
					</div>

				</div>


			</div>
			<div style="height:250px; width:800px; overflow:auto;">
			
			<table class="table" id='tableJohn' name='table'>


			</table>
</div>

		</div>
		<!-- /#wrapper -->







		<script src="Resources/js/jquery-1.6.1.min.js"></script>
		<script src="Resources/js/bootstrap.min.js"></script>



		<script>

		var divs = ["johnsQuery","briansQuery"];
		var visibleDiv = null;
		 var $tableJohn = $('#tableJohn');
		 var $table = $('#tableBrian');

		$(function(){

			document.getElementById("johnsQuery").style.display='none';

			document.getElementById("briansQuery").style.display='none';
			

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



		<script>
$(function(){

 		$( "#submitBrian" ).click(function(e) {	

 	removeData();
 	removeDataJohn();
	var fromDate=$('#from').val();
	var toDate=$('#to').val();
	var myurl;
	var x=document.getElementById("ID");
    var selected=x.options[x.selectedIndex].text;
	var isValid=false;
	
	if(validateEntry(fromDate, toDate)){	
		myurl='http://localhost:8080/project/rest/failedcalldata/getFailedCallDataByModel/'+selected+'�'+fromDate+'�'+toDate;
		isValid=true;
		}

	else{
		myurl= 'http://localhost:8080/project/supportPage3.html';
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
        var button=document.createElement(button);
        button.setAttribute('class', "col-sm-offset-12 col-sm-10");
        button.setAttribute('position', 'absolute');
    	button.setAttribute('top', '50%');
        button.setAttribute('id', 'tableButton');
        button.setAttribute('class','buttonMy');
        button.innerHTML='Search Again';
        button.addEventListener('click', removeData);
        $table.append(button);

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
			var fromDate=$('#IMSIFrom').val();
			var toDate=$('#IMSITo').val();
			var myurl;
			var isValid=false;
			
			if(validateEntry(fromDate, toDate)){	
				myurl='http://localhost:8080/project/rest/failedcalldata/dateIMSI/'+fromDate+'�'+toDate;
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
		 	 				$tableJohn.append('<tr><td>'+value+'</td></tr>');
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
	
 	function createTableJohn(){
 		
 		var row = document.createElement('tr');
 		
 		row.setAttribute('id', 'head');
 		row.setAttribute('display', 'block');
 		var colOne=document.createElement('th');

 		colOne.innerHTML='IMSI';

 		row.appendChild(colOne);

 		$tableJohn.append(row);
 		
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
 	

    </script>
</body>

</html>