<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$userid = $_POST['userid'];
	$is_adoption = $_POST['is_adoption'];
	$is_transportation = $_POST['is_transportation'];
	$is_shelter_clean_up = $_POST['is_shelter_clean_up'];
	$is_educational_campaign = $_POST['is_educational_campaign'];
	$is_animal_welfare = $_POST['is_animal_welfare'];
	$is_fostering = $_POST['is_fostering'];
	$is_food_donation_drive = $_POST['is_food_donation_drive'];
	$is_events = $_POST['is_events'];
	$date_submitted = date("Ymd");
	$date_notified = date("F j, Y, g:i a");

	//jsonarray
	$received_Array = $_POST['array'];
	$new_array=json_decode($received_Array, true);

	$check = mysqli_query($connection, "Select * from volunteer where user_id = $userid");
	if(mysqli_num_rows($check)>0) {
		$result["success"] = "0";
		$result["message"] = "You have already submitted a volunteer application form";
		echo json_encode($result);
	} else {
		$sql = mysqli_query($connection, "INSERT INTO `volunteer` (`volunteer_id`, `user_id`, `is_adoption`, `is_transportation`, `is_shelter_clean_up`, `is_educational_campaign`, `is_animal_welfare`, `is_fostering`, `is_food_donation_drive`, `is_events`, `is_active`) VALUES (NULL, '$userid', '$is_adoption', '$is_transportation', '$is_shelter_clean_up', '$is_educational_campaign', '$is_animal_welfare', '$is_fostering', '$is_food_donation_drive', '$is_events', '0')");

		$volunteer_id = mysqli_insert_id($connection);

		$sql2 = mysqli_query($connection, "INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`) VALUES (NULL, '$volunteer_id', '2', '1', '$date_submitted')");

		$doc_id = mysqli_insert_id($connection);

		foreach ($new_array as $row) {
			$photo = $row['photo'];
			$filename = $row['filename'];
			$path = "images/attachments/".$filename;
			$sql3 = mysqli_query($connection, "INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES (NULL, '$doc_id', '$filename', '$path', '$userid', '$date_submitted', '1')");
			if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
			}
		}

		$sql4 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '3', 'Volunteer', 'A new volunteer request has been submitted', '$date_notified', '0')");
	}

	// if(!$sql || !$sql2) {
	// 	$result["success"] = "0";
	// 	$result["message"] = "error";
	// } else {
	// 	$result["success"] = "1";
	// 	$result["message"] = "success";
	// }

	echo json_encode($result);
	mysqli_close($connection);
}
?>