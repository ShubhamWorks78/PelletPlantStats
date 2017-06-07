<?php
	define("hostname","localhost");
	define("username","id912463_coalstorage");
	define("password","CoalStorage");
	$con = mysqli_connect(hostname,username,password) or die("Connection not successfull");
	if(mysqli_connect_errno()){
		printf("Connect failed: %s\n",mysqli_connect_error());
		exit();
	}
	mysqli_select_db($con,username);
	if(isset($_POST['Day']) && isset($_POST['Month']) && isset($_POST['Year']) && isset($_POST['Coal']) && isset($_POST['Limestone']) && isset($_POST['Haematite']) && isset($_POST['Manganese'])){
		$day = $_POST['Day'];
		$month = $_POST['Month'];
		$year = $_POST['Year'];
		$coal = $_POST['Coal'];
		$limestone = $_POST['Limestone'];
		$haematite = $_POST['Haematite'];
		$manganese = $_POST['Manganese'];
		
		if( $day == '' || $month == '' || $year == '' || $coal == '' || $limestone == '' || $haematite == '' || $manganese == ''){
			echo "Filling all the fields is mandatory";
		}
		else{
			$qry = "INSERT INTO PelletPlant(Day,Month,Year,Coal,Limestone,Haematite,Manganese) VALUES('$day','$month','$year','$coal','$limestone','$haematite','$manganese')";
			$res = mysqli_query($con,$qry);
			
			if(!$res){
				echo "Could not execute query";
				exit;
			}
			else{
				echo "Data Successfully saved";
			}
		}
	}
?>