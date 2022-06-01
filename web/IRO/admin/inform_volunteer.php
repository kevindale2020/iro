<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['inform'])) 
{
	$doc_id = $_GET['inform'];
	date_default_timezone_set("Asia/Singapore");
	$date_today = date("F j, Y, g:i a");
	$success = 'Requestor has been successfully informed!';

	$details = mysqli_query($database, "SELECT * FROM documents INNER JOIN volunteer ON documents.doc_form_id = volunteer.volunteer_id WHERE documents.doc_id = $doc_id");

	$row = mysqli_fetch_array($details);

	$userid = $row['user_id'];
	$doc_form_id = $row['volunteer_id'];

	//this code will allow you to send notification to requestor
	$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$userid', '3', 'Volunteer', 'Kindly visit our booth in parkmall on 2nd or 4th saturday of the month for volunteer interview.', '$date_today', '0', '0')";

	if (mysqli_query($database, $send_notification))
	{
		echo "<script>window.location.href='admin_volunteerrequest.php?success=$success';</script>";
	}
}
 ?>