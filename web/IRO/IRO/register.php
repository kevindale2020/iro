<?php
require_once 'db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$fname = $_POST['fname'];
	$lname = $_POST['lname'];
	$nname = $_POST['nname'];
	$occupation = $_POST['occupation'];
	$address = $_POST['address'];
	$email = $_POST['email'];
	$contact = $_POST['contact'];
	$username = $_POST['username'];
	$password = $_POST['password'];
	$created_date = date('Ymd');
	$check_username = mysqli_query($connection, "Select * from users where user_username = '$username' ");
	$check_email = mysqli_query($connection, "Select * from users where user_email = '$email'");
	$username_counter = mysqli_num_rows($check_username);
	$email_counter = mysqli_num_rows($check_email);

	if($username_counter>0) {
		$result["success"] = "2";
		$result["message"] = "Username already exists.";
		echo json_encode($result);
	}

	if($email_counter>0) {
		$result["success"] = "3";
		$result["message"] = "This email is already in use.";
		echo json_encode($result);
	}

	if(!$username_counter>0 && !$email_counter>0) {
		$sql = mysqli_query($connection, "INSERT INTO `users` (`user_id`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`) VALUES (NULL, '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', '$password')");
		$userid = mysqli_insert_id($connection);
		$sql2 = mysqli_query($connection, "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`) VALUES (NULL, '$userid', '3', '$created_date', '1')");
	}
	
	if(!$sql || !$sql2) {
		$result["success"] = "0";
		$result["message"] = "error";
	}
	else {
		$result["success"] = "1";
		$result["message"] = "success";
	}

	echo json_encode($result);
	mysqli_close($connection);
}

?>