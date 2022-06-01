<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$type = $_POST['type'];
	$title = $_POST['title'];
	$name = mysqli_real_escape_string($connection, $_POST['name']);
	$number = mysqli_real_escape_string($connection, $_POST['number']);
	$amount = mysqli_real_escape_string($connection,$_POST['amount']);
	$photo = $_POST['photo'];
	$filename = $_POST['filename'];
	$path = "images/donations/".$filename;
	$date_submitted = date("F j, Y, g:i a");
	$date_submitted2 = date("Ymd");

	$sql = mysqli_query($connection, "INSERT INTO `donations` (`donation_id`, `user_id`, `donation_type_id`, `account_name`, `account_number`, `amount`, `date`, `image`) VALUES (NULL, '$id', '$type', '$name', '$number', '$amount', '$date_submitted2', '$filename')");

	$sql2 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$id', '7', 'Donation', 'An amount $amount was donated', '$date_submitted', '0')");
	if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
	}
	
	echo json_encode($result);
	mysqli_close($connection);

}
?>