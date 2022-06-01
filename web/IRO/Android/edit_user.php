<?php
require_once 'db\db_connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$fname = mysqli_real_escape_string($connection, $_POST['fname']);
	$lname = mysqli_real_escape_string($connection, $_POST['lname']);
	$nname = mysqli_real_escape_string($connection, $_POST['nname']);
	$occupation = mysqli_real_escape_string($connection, $_POST['occupation']);
	$address = mysqli_real_escape_string($connection, $_POST['address']);
	$email = mysqli_real_escape_string($connection, $_POST['email']);
	$contact = mysqli_real_escape_string($connection, $_POST['contact']);
	$username = mysqli_real_escape_string($connection, $_POST['username']);
	$photo = $_POST['photo'];
	$filename = "$id.jpg";
	$path = "images/users/".$filename;

	if($photo!='0') {
		$sql = mysqli_query($connection, "UPDATE `users` SET `user_image` = '$filename', `user_fname` = '$fname', `user_lname` = '$lname', `user_nname` = '$nname', `user_address` = '$address', `user_email` = '$email', `user_contact` = '$contact', `user_occupation` = '$occupation', `user_username` = '$username' WHERE `users`.`user_id` = $id");
			if(file_put_contents($path, base64_decode($photo))) {
				$result['success'] = "1";
				$result['message'] = "success";
				echo json_encode($result);
			}
	} else {
		$sql = mysqli_query($connection, "UPDATE `users` SET `user_fname` = '$fname', `user_lname` = '$lname', `user_nname` = '$nname', `user_address` = '$address', `user_email` = '$email', `user_contact` = '$contact', `user_occupation` = '$occupation', `user_username` = '$username' WHERE `users`.`user_id` = $id");
			$result['success'] = "1";
			$result['message'] = "success";
			echo json_encode($result);
	}
	mysqli_close($connection);
}

?>