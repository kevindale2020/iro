<?php
require_once 'db\db_connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
	$flag = false;
	$fname = mysqli_real_escape_string($connection, $_POST['fname']);
	$lname = mysqli_real_escape_string($connection, $_POST['lname']);
	$nname = mysqli_real_escape_string($connection, $_POST['nname']);
	$occupation = mysqli_real_escape_string($connection, $_POST['occupation']);
	$address = mysqli_real_escape_string($connection, $_POST['address']);
	$email = mysqli_real_escape_string($connection, $_POST['email']);
	$contact = mysqli_real_escape_string($connection, $_POST['contact']);
	$username = mysqli_real_escape_string($connection, $_POST['username']);
	$password = mysqli_real_escape_string($connection, $_POST['password']);

	//generate Vkey
	$vkey = md5(time().$username);

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
		$sql = mysqli_query($connection, "INSERT INTO `users` (`user_id`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`, `vkey`) VALUES (NULL, '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', '$password', '$vkey')");
		$userid = mysqli_insert_id($connection);
		$sql2 = mysqli_query($connection, "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`, `verified`) VALUES (NULL, '$userid', '1', '$created_date', '1', '0')");
	}
	
	if(!$sql || !$sql2) {
		$result["success"] = "0";
		$result["message"] = "error";
	}
	else {
		//send data to android in json format
		$result["success"] = "1";
		$result["message"] = "success";
		$flag = true;
	}

	echo json_encode($result);

	if($flag==true) {
		//send email
	require 'phpmailer/PHPMailerAutoload.php';
		
		$mail = new PHPmailer(true);
		try {
			$mail->SMTPDebug = 1; //*
			$mail->isSMTP(); //*
			$mail->Host='smtp.gmail.com'; //*
			$mail->SMTPAuth=true; //*
			$mail->Username='island.rescue.orgranization@gmail.com'; //email username
			$mail->Password='iro123123'; //email password
			$mail->SMTPSecure='tls'; //*
			$mail->Port=587; //*

			$mail->setFrom('island.rescue.orgranization@gmail.com','Iro System'); //email nimo, title sa email 
			$mail->addAddress($email); //kung kinsay sendan nimo

			$body = '
              
              <p>Thanks for signing up!</p>
              <p>Your account has been created, you can login with the following credentials after you have activated your account by pressing the url below.</p>
              <p>------------------------</p>
              <p>Username: '.$username.'</p>
              <p>Password: '.$password.'</p>
              <p>------------------------</p>
              
              <p>Please click this link to activate your account:</p>
              <p>http://192.168.137.1:8080/iro/verify_account.php?email='.$email.'&hash='.$vkey.'</p>
               
              '; //email body kung unsay e message nimo
					

			$mail->isHTML(true); //*
			$mail->Subject='Account Verification'; //Subject sa email
			$mail->Body=$body; 
			$mail->AltBody=strip_tags($body);

			$mail->send();
			echo "Message sent!";
		} catch (Exception $e){
			echo "Message could not send!";
		}
	}
	
	mysqli_close($connection);
}

?>