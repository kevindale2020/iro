<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['inform'])) 
{
	$doc_id = $_GET['inform'];
	date_default_timezone_set("Asia/Singapore");
	$date_today = date("F j, Y, g:i a");
	$success = 'User has been successfully informed!';

	$details = mysqli_query($database, "SELECT * FROM documents INNER JOIN adoptions ON documents.doc_form_id = adoptions.adoption_id WHERE documents.doc_id = $doc_id");

	$row = mysqli_fetch_array($details);

	$userid = $row['user_id'];
	$doc_form_id = $row['adoption_id'];

	$delete_request = mysqli_query($database, "DELETE FROM adoptions WHERE adoption_id = $doc_form_id");

	$delete_doc = mysqli_query($database, "DELETE FROM documents WHERE doc_id = $doc_id");

	//this code will allow you to send notification to requestor
	$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$userid', '1', 'Adoption', 'Sorry to inform you that this dog is already adopted by someone else.', '$date_today', '0', '0')";

	if (mysqli_query($database, $send_notification))
	{
		echo "<script>window.location.href='admin_adoptionrequest.php?success=$success';</script>";
	}
}
 ?>