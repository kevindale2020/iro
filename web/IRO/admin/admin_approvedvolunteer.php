<?php
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['doc_id']))
{
	$doc_id = $_GET['doc_id'];
	$approved_date = date("Y-m-d");
	$success = 'Volunteer has been successfully approved';

	$details = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $doc_id");

	while ($row = mysqli_fetch_array($details))
	{
		$doc_form_id = $row['doc_form_id'];

		//get the adoption request details
		$volunteer_details = mysqli_query($database, "SELECT * FROM volunteer INNER JOIN documents ON volunteer.volunteer_id = documents.doc_form_id WHERE volunteer.volunteer_id = $doc_form_id AND documents.doc_type_id = 2");

		while ($data = mysqli_fetch_array($volunteer_details))
		{
			$volunteer_id = $data['volunteer_id'];
			$user_id = $data['user_id'];
			$userid = $_SESSION['user_id'];
			$date_today = date('Y-m-d');
			$doc_id = $data['doc_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");

			//this code will change the status of the document to not approved
			$approvedvolunteer = mysqli_query($database, "UPDATE documents SET doc_status_id = 2 WHERE doc_id = $doc_id");

			$approvedvolunteers = mysqli_query($database, "UPDATE volunteer SET is_active = 1 WHERE volunteer_id = $volunteer_id");

			//this code will allow you to add the request details to approve_document table
			$insertapproved = "INSERT approved_documents (doc_id, approved_by, approved_date) VALUES ('$doc_id', '$userid', '$approved_date')";

			$activate = mysqli_query($database, "UPDATE user_roles SET role_id = 3 WHERE user_id = $user_id");
 
			//this code will allow you to send notification to requestor
			$send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '3', 'Volunteer', 'Your application for volunteer has been approved. You can now login your account to our web.', '$date_today', '0', '0')");

			if (mysqli_query($database, $insertapproved))
			{
				echo "<script>window.location.href='admin_volunteerrequest.php?success=$success';</script>";
			}

		}

	}
}

if (isset($_GET['reactivate']))
{
	$doc_id = $_GET['reactivate'];
	$approved_date = date("Y-m-d");
	$success = 'Volunteer has been reactivated successfully';

	$details = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $doc_id");

	while ($row = mysqli_fetch_array($details))
	{
		$doc_form_id = $row['doc_form_id'];

		//get the adoption request details
		$volunteer_details = mysqli_query($database, "SELECT * FROM volunteer INNER JOIN documents ON volunteer.volunteer_id = documents.doc_form_id WHERE volunteer.volunteer_id = $doc_form_id AND documents.doc_type_id = 2");

		while ($data = mysqli_fetch_array($volunteer_details))
		{
			$volunteer_id = $data['volunteer_id'];
			$user_id = $data['user_id'];
			$userid = $_SESSION['user_id'];
			$date_today = date('Y-m-d');
			$doc_id = $data['doc_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");

			$approvedvolunteers = mysqli_query($database, "UPDATE volunteer SET is_active = 1 WHERE volunteer_id = $volunteer_id");

			
			$activate = "UPDATE user_roles SET role_id = 3 WHERE user_id = $user_id";
 
			//this code will allow you to send notification to requestor
			$send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '3', 'Volunteer', 'Your volunteer account has been reactivated.', '$date_today', '0', '0')");

			if (mysqli_query($database, $activate))
			{
				echo "<script>window.location.href='admin_viewuser.php?success=$success';</script>";
			}

		}

	}
}
 ?>
