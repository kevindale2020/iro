<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$type = $_POST['type'];
	$title = mysqli_real_escape_string($connection, $_POST['title']);
	$desc = mysqli_real_escape_string($connection, $_POST['desc']);
	$location = $_POST['location'];
	$photo = $_POST['photo'];
	$filename = $_POST['filename'];
	$path = "images/attachments/".$filename;
	$date_reported = date("F j, Y, g:i a");
	$date_submitted = date("Ymd");

	$sql = mysqli_query($connection, "INSERT INTO `reports` (`report_id`, `user_id`, `report_type_id`, `title`, `description`, `reporter_location`, `date_reported`) VALUES (NULL, '$id', '$type', '$title', '$desc', '$location', '$date_reported')");
	$reportid = mysqli_insert_id($connection);
	$sql2 = mysqli_query($connection, "INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`) VALUES (NULL, '$reportid', '3', '1', '$date_submitted')");
	$docid = mysqli_insert_id($connection);
	$sql3 = mysqli_query($connection, "INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES (NULL, '$docid', '$filename', '$path', '$id', '$date_submitted', '1')");
	if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
	}
	if($type==2) {
		$sql4 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$id', '4', 'Lost & Found', 'A new lost & found report has been submitted', '$date_reported', '0')");
	} else if($type==3) {
		$sql4 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$id', '5', 'Homeless Pet', 'A new homeless pet report has been submitted', '$date_reported', '0')");
	} else {
		$sql4 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$id', '6', 'Animal Cruelty', 'A new animal cruelty report has been submitted', '$date_reported', '0')");
	}


	echo json_encode($result);
	mysqli_close($connection);

}
?>