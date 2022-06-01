<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	
	$userid = $_POST['userid'];
	$petid = $_POST['petid'];
	$age = $_POST['age'];
	$reason = mysqli_real_escape_string($connection, $_POST['reason']);
	$typeid = $_POST['typeid'];
	$current = mysqli_real_escape_string($connection, $_POST['current']);
	$past_pet = mysqli_real_escape_string($connection, $_POST['past_pet']);
	$past_pet_details = mysqli_real_escape_string($connection, $_POST['past_pet_details']);
	$have_yard = $_POST['have_yard'];
	$is_fenced = $_POST['is_fenced'];
	$have_vet = $_POST['have_vet'];
	$vet_name = mysqli_real_escape_string($connection, $_POST['vet_name']);
	$vet_address = mysqli_real_escape_string($connection, $_POST['vet_address']);
	$circumstances = mysqli_real_escape_string($connection, $_POST['circumstances']);
	$is_agreed = $_POST['is_agreed'];
	$reason_disagree = mysqli_real_escape_string($connection, $_POST['reason_disagree']);
	$home_visit_time = mysqli_real_escape_string($connection, $_POST['home_visit_time']);
	$comments = mysqli_real_escape_string($connection, $_POST['comments']);
	$date_submitted = $_POST['date_submitted'];
	$time = strtotime($date_submitted);
	$date_formatted = date('Y-m-d', $time);
	$date_notified = date("F j, Y, g:i a");

	//jsonarray
	$received_Array = $_POST['array'];
	$new_array=json_decode($received_Array, true);

	$check = mysqli_query($connection, "Select * from adoptions inner join documents on adoptions.adoption_id = documents.doc_form_id where adoptions.pet_id = $petid and user_id = $userid and documents.doc_status_id <> 3 and documents.doc_status_id <> 4 ");
	if(mysqli_num_rows($check)>0) {
		$result["success"] = "2";
		$result["message"] = "You have already added this pet to your adoption list.";
		echo json_encode($result);
	} else {
		$sql = mysqli_query($connection, "INSERT INTO `adoptions` (`adoption_id`, `user_id`, `pet_id`, `children_age`, `reason`, `pet_live_type_id`, `current_pet`, `past_pet`, `past_pet_details`, `have_yard`, `is_fenced`, `have_vet`, `vet_name`, `vet_address`, `circumstances`, `is_agreed`, `reason_disagree`, `home_visit_time`, `comments`) VALUES (NULL, '$userid', '$petid', '$age', '$reason', '$typeid', '$current', '$past_pet', '$past_pet_details', '$have_yard', '$is_fenced', '$have_vet', '$vet_name', '$vet_address', '$circumstances', '$is_agreed', '$reason_disagree', '$home_visit_time', '$comments')");
		$doc_form_id = mysqli_insert_id($connection);
		$sql2 = mysqli_query($connection, "INSERT INTO `documents` (`doc_id`, `doc_form_id`, `doc_type_id`, `doc_status_id`, `date_submitted`) VALUES (NULL, '$doc_form_id', '1', '1', '$date_formatted')");
		$doc_id = mysqli_insert_id($connection);
		/*
		$sql3 = mysqli_query($connection, "INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES (NULL, '$doc_id', '$filename', '$path', '$userid', '$date_formatted', '1')");*/
		foreach ($new_array as $row) {
			$photo = $row['photo'];
			$filename = $row['filename'];
			$path = "images/attachments/".$filename;
			$sql3 = mysqli_query($connection, "INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES (NULL, '$doc_id', '$filename', '$path', '$userid', '$date_formatted', '1')");
			if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
			}
		}
		//notify admin
		/*
		$sql4 = mysqli_query($connection, "Select * from users inner join user_roles on users.user_id = user_roles.user_id where user_roles.role_id = 2");
		while($row = mysqli_fetch_array($sql4)) {
			$adminid = $row['user_id'];
			$sql5 = mysqli_query($connection, "INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen_admin`) VALUES (NULL, '$adminid', '1', 'Adoption', 'New request has been submitted', '	$date_formatted', '1', '0')");
		}
		*/

		$sql3 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '1', 'Adoption', 'A new adoption request has been submitted', '$date_notified', '0')");

		//delete null photo
		$sql6 = mysqli_query($connection, "Select * from file_attachments where doc_id = $doc_id");
		while($row = mysqli_fetch_array($sql6)) {
			if($row['file_name']=="") {
				$fileid = $row['file_attachment_id'];
				$sql7 = mysqli_query($connection, "Delete from file_attachments where file_attachment_id = $fileid");
			}
		}

	}
	/*
	if(!$sql || !$sql2 || !$sql3) {
		$result["success"] = "0";
		$result["message"] = "error";
	}
	else {
		$result["success"] = "1";
		$result["message"] = "success";
	}
	*/
	echo json_encode($result);
	mysqli_close($connection);
}

?>