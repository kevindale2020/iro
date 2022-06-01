<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$name = mysqli_real_escape_string($connection, $_POST['name']);
	$number = mysqli_real_escape_string($connection, $_POST['number']);
	$amount = mysqli_real_escape_string($connection, $_POST['amount']);
	$date = date("Ymd");
	$type = $_POST['type'];
	$photo = $_POST['photo'];
	$filename = $_POST['filename'];
	$path = "images/donations/".$filename;

	if($photo!='0') {
		$sql = mysqli_query($connection, "UPDATE `donations` SET `donation_type_id` = '$type', `account_name` = '$name', `account_number` = '$number', `amount` = '$amount', `image` = '$filename', `date_modified` = '$date' WHERE `donations`.`donation_id` = $id");
			if(file_put_contents($path, base64_decode($photo))) {
				$result['success'] = "1";
				$result['message'] = "success";
				echo json_encode($result);
			}
	} else {
		$sql = mysqli_query($connection, "UPDATE `donations` SET `donation_type_id` = '$type', `account_name` = '$name', `account_number` = '$number', `amount` = '$amount', `date_modified` = '$date' WHERE `donations`.`donation_id` = $id");
			$result['success'] = "1";
			$result['message'] = "success";
			echo json_encode($result);
	}
	mysqli_close($connection);
}
?>