<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

	if (isset($_POST['reject'])) 
	{
		$rejected_reason = mysqli_real_escape_string($database, $_POST['rejected_reason']);
		$doc_id = $_POST['doc_id'];
		$rejected_date = date("Y-m-d");
		$rejected_by = $user_id;
		$rejectmsg = 'Adoption has been successfully rejected';

		$details = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $doc_id");

		while ($row = mysqli_fetch_array($details)) 
		{
			$doc_form_id = $row['doc_form_id'];

			//get the adoption request details
			$adoption_details = mysqli_query($database, "SELECT * FROM adoptions WHERE adoption_id = $doc_form_id");

			while ($data = mysqli_fetch_array($adoption_details)) 
			{
				$user_id = $data['user_id'];
				date_default_timezone_set("Asia/Singapore");
				$date_today = date("F j, Y, g:i a");

				$reject_document = mysqli_query($database, "UPDATE documents SET doc_status_id = 3 WHERE doc_id = $doc_id");

				$send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '1', 'Adoption', 'Your Request has been rejected', '$date_today', '0', '0')");
				
				$reject_request = "INSERT INTO `iro_db`.`rejected_documents` (`id`, `doc_id`, `rejected_reason`, `rejected_by`, `rejected_date`) VALUES (NULL, '$doc_id', '$rejected_reason', '$rejected_by', '$rejected_date')";

				if (mysqli_query($database, $reject_request)) 
				{
					echo "<script>window.location.href='admin_adoptionrequest.php?rejectmsg=$rejectmsg';</script>";
				}
			}
		}
	}
?>