<?php 
session_start();
include '../config.php'; //this code will allow you to connect to your database
$userid = $_SESSION['user_id'];

if (isset($_GET['acknowledge'])) 
{
	$acknowledge = $_GET['acknowledge'];
	$doc_id = $_GET['acknowledge'];

	$notify = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $acknowledge");

	while ($row = mysqli_fetch_array($notify)) 
	{
		$doc_form_id = $row['doc_form_id'];

		$notify_details = mysqli_query($database, "SELECT * FROM reports WHERE report_id = $doc_form_id");

		while ($data = mysqli_fetch_array($notify_details)) 
		{
			$report_type_id = $data['report_type_id'];
			$user_id = $data['user_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");
			$acknowledged_date = date('Y-m-d');

			if ($report_type_id == 3) 
			{
				$success = 'Report has been acknowledge successfully';

				$acknowledge = mysqli_query($database, "UPDATE documents SET doc_status_id = 5 WHERE doc_id = $acknowledge");

				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '5', 'Homeless Pet', 'Your report for homeless pet has been acknowledged', '$date_today', '0', '0')";

				$acknowledged = mysqli_query($database, "INSERT INTO `iro_db`.`acknowledged_documents` (`id`, `doc_id`, `acknowledge_by`, `acknowledged_date`) VALUES (NULL, '$doc_id', '$userid', '$acknowledged_date')");

				if (mysqli_query($database, $send_notification))
				{
					echo "<script>window.location.href='admin_homelesspet.php?success=$success';</script>";
				}
			}
			else if($report_type_id == 4)
			{
				$success = 'Report has been acknowledge successfully';

				$acknowledge = mysqli_query($database, "UPDATE documents SET doc_status_id = 5 WHERE doc_id = $acknowledge");

				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '6', 'Animal Cruelty', 'Your report for animal cruelty has been acknowledged', '$date_today', '0', '0')";

				$acknowledged = mysqli_query($database, "INSERT INTO `iro_db`.`acknowledged_documents` (`id`, `doc_id`, `acknowledge_by`, `acknowledged_date`) VALUES (NULL, '$doc_id', '$userid', '$acknowledged_date')");

				if (mysqli_query($database, $send_notification))
				{
					echo "<script>window.location.href='admin_animalcruelty.php?success=$success';</script>";
				}
			}
			else
			{
				$success = 'Report has been acknowledge successfully';

				$acknowledge = mysqli_query($database, "UPDATE documents SET doc_status_id = 5 WHERE doc_id = $acknowledge");

				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '4', 'Lost and Found', 'Your report for lost and found has been acknowledged', '$date_today', '0', '0')";

				$acknowledged = mysqli_query($database, "INSERT INTO `iro_db`.`acknowledged_documents` (`id`, `doc_id`, `acknowledge_by`, `acknowledged_date`) VALUES (NULL, '$doc_id', '$userid', '$acknowledged_date')");

				if (mysqli_query($database, $send_notification))
				{
					echo "<script>window.location.href='admin_lostandfound.php?success=$success';</script>";
				}
			}
			

		}
	}
}


if (isset($_GET['closed'])) 
{
	$closed = $_GET['closed'];
	$doc_id = $_GET['closed'];

	$notify = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $closed");

	while ($row = mysqli_fetch_array($notify)) 
	{
		$doc_form_id = $row['doc_form_id'];

		$notify_details = mysqli_query($database, "SELECT * FROM reports WHERE report_id = $doc_form_id");

		while ($data = mysqli_fetch_array($notify_details)) 
		{
			$report_type_id = $data['report_type_id'];
			$user_id = $data['user_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");
			$closed_date = date('Y-m-d');

			if ($report_type_id == 3) 
			{
				$success = 'Report has been closed successfully';

				$closed = mysqli_query($database, "UPDATE documents SET doc_status_id = 6  WHERE doc_id = $closed");

				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '5', 'Homeless Pet', 'Your report for homeless pet has been closed', '$date_today', '0', '0')";

				$closed_doc = mysqli_query($database, "INSERT INTO `closed_documents` (`id`, `doc_id`, `closed_date`) VALUES (NULL, '$doc_id','$closed_date')");

				if (mysqli_query($database, $send_notification))
				{
					echo "<script>window.location.href='admin_homelesspet.php?success=$success';</script>";
				}
			}
			else
			{
				$success = 'Report has been closed successfully';

				$closed = mysqli_query($database, "UPDATE documents SET doc_status_id = 6 WHERE doc_id = $closed");

				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '6', 'Animal Cruelty', 'Your report for animal cruelty has been closed', '$date_today', '0', '0')";

				$closed_doc = mysqli_query($database, "INSERT INTO `closed_documents` (`id`, `doc_id`, `closed_date`) VALUES (NULL, '$doc_id','$closed_date')");

				if (mysqli_query($database, $send_notification))
				{
					echo "<script>window.location.href='admin_animalcruelty.php?success=$success';</script>";
				}
			}
			

		}
	}
}

if (isset($_GET['remove'])) 
{
	$doc_id = $_GET['remove'];

	$details = mysqli_query($database, "SELECT * FROM documents WHERE doc_id = $doc_id");

	while ($ress = mysqli_fetch_array($details)) 
	{
		$doc_id = $ress['doc_id'];
		$doc_form_id = $ress['doc_form_id'];

		$notify_details = mysqli_query($database, "SELECT * FROM reports WHERE report_id = $doc_form_id");

		while ($data = mysqli_fetch_array($notify_details)) 
		{
			$report_type_id = $data['report_type_id'];
			$user_id = $data['user_id'];
			date_default_timezone_set("Asia/Singapore");
			$date_today = date("F j, Y, g:i a");
			$closed_date = date('Y-m-d');

			
			$success = 'Report has been removed successfully';

			$delete_document = mysqli_query($database, "DELETE FROM documents WHERE doc_id = $doc_id");

			$delete_report = mysqli_query($database, "DELETE FROM reports WHERE report_id = $doc_form_id");

			$delete_attachment = mysqli_query($database, "DELETE FROM files_attachment WHERE doc_id = $doc_id");

			if ($report_type_id == 2) 
			{
				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '4', 'Lost and Found', 'Your report has been removed by admin due to incomplete details.', '$date_today', '0', '0')";
			}
			else if($report_type_id == 3)
			{
				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '5', 'Homeless Pet', 'Your report has been removed by admin due to incomplete details.', '$date_today', '0', '0')";
			}
			else
			{
				$send_notification = "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '6', 'Animal Cruelty', 'Your report has been removed by admin due to incomplete details.', '$date_today', '0', '0')";
			}

			

			if (mysqli_query($database, $send_notification))
			{
				if ($report_type_id == 2) 
				{
					echo "<script>window.location.href='admin_lostandfound.php?success=$success';</script>";
				}
				else if($report_type_id == 3)
				{
					echo "<script>window.location.href='admin_homelesspet.php?success=$success';</script>";
				}
				else
				{
					echo "<script>window.location.href='admin_animalcruelty.php?success=$success';</script>";
				}
				
			}
			
		}
	}


}
 ?>