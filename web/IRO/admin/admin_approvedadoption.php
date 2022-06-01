<?php
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['doc_id']))
{
	$doc_id = $_GET['doc_id'];
	$approved_date = date("Y-m-d");
	$success = 'Adoption has been successfully approved';

	//get the document details
	$details = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $doc_id");

	while ($row = mysqli_fetch_array($details))
	{
		$doc_form_id = $row['doc_form_id'];

		//get the adoption request details
		$adoption_details = mysqli_query($database, "SELECT * FROM adoptions WHERE adoption_id = $doc_form_id");

		while ($data = mysqli_fetch_array($adoption_details))
		{
			$pet_id = $data['pet_id'];
			$userid = $_SESSION['user_id'];
			$user_id = $data['user_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");

			//this code will change the status of the pet to not available
			$adopt = mysqli_query($database, "UPDATE pets SET pet_status_id = 2 WHERE pet_id = $pet_id");

			//this code will change the status of the document to not approved
			$approvedadoption = mysqli_query($database, "UPDATE documents SET doc_status_id = 2 WHERE doc_id = $doc_id");

			//this code will allow you to add the request details to approve_document table
			$insertapproved = "INSERT approved_documents (doc_id, approved_by, approved_date) VALUES ('$doc_id', '$userid', '$approved_date')";

			//this code will allow you to send notification to requestor
			$send_notification = mysqli_query($database, "INSERT INTO notifications
				(`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES
				(NULL, '$user_id', '1', 'Adoption', 'Your application form has been approved. kindly visit our booth at parkmall on 2nd or 4th saturday of the month for final screening of your adoption.', '$date_today', '0', '0')");

			if (mysqli_query($database, $insertapproved))
			{
				echo "<script>window.location.href='admin_adoptionrequest.php?success=$success';</script>";
			}
		}
	}
}
 ?>
