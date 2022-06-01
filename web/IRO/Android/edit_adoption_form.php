<?php
$files = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$userid = $_POST['userid'];
	$docid = $_POST['docid'];
	$adoptionid = $_POST['adoptionid'];
	$age = $_POST['age'];
	$reason = $_POST['reason'];
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
	$file1 = $_POST['file1'];
	$file2 = $_POST['file2'];
	$file3 = $_POST['file3'];
	$file4 = $_POST['file4'];
	$file5 = $_POST['file5'];

	//file array
	$file['item1'] = $file1;
	$file['item2'] = $file2;
	$file['item3'] = $file3;
	$file['item4'] = $file4;
	$file['item5'] = $file5;
	$files[] = $file;

	//jsonarray
	$received_Array = $_POST['array'];
	$new_array=json_decode($received_Array, true);

	if($received_Array!='0') {
		if($have_vet==0) {
			$vet_name = "";
			$vet_address = "";
		}
		if($is_agreed==0) {
			$home_visit_time = "";
		} else {
			$reason_disagree = "";
		}
		$sql = mysqli_query($connection, "UPDATE `adoptions` SET `reason` = '$reason', `pet_live_type_id` = '$typeid', `current_pet` = '$current', `past_pet` = '$past_pet', `past_pet_details` = '$past_pet_details', `have_yard` = '$have_yard', `is_fenced` = '$is_fenced', `have_vet` = '$have_vet', `vet_name` = '$vet_name', `vet_address` = '$vet_address', `circumstances` = '$circumstances', `is_agreed` = '$is_agreed', `reason_disagree` = '$reason_disagree', `home_visit_time` = '$home_visit_time', `comments` = '$comments' WHERE `adoptions`.`adoption_id` = $adoptionid");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date_formatted' WHERE `documents`.`doc_id` = $docid");
		//delete current file
		foreach($files as $row) {
			$item1 = $row['item1'];
			$item2 = $row['item2'];
			$item3 = $row['item3'];
			$item4 = $row['item4'];
			$item5 = $row['item5'];
			$sql3 = mysqli_query($connection, "Delete from file_attachments where file_name = '$item1' or file_name = '$item2' or file_name = '$item3' or file_name = '$item4'  or file_name = '$item5'");
		}
		//replace with new ones
		foreach ($new_array as $row) {
			$photo = $row['photo'];
			$filename = $row['filename'];
			$path = "images/attachments/".$filename;
			$sql4 = mysqli_query($connection, "INSERT INTO `file_attachments` (`file_attachment_id`, `doc_id`, `file_name`, `file_path`, `created_by`, `created_date`, `is_active`) VALUES (NULL, '$docid', '$filename', '$path', '$userid', '$date_formatted', '1')");
			if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
			}
		}
		//delete null photo
		$sql5 = mysqli_query($connection, "Select * from file_attachments where doc_id = $doc_id");
		while($row = mysqli_fetch_array($sql5)) {
			if($row['file_name']=="") {
				$fileid = $row['file_attachment_id'];
				$sql6 = mysqli_query($connection, "Delete from file_attachments where file_attachment_id = $fileid");
			}
		}
		/*
		foreach ($new_array as $row) {
			$photo = $row['photo'];
			$filename = $row['filename'];
			$path = "images/attachments/".$filename;
			$sql3 = mysqli_query($connection, "UPDATE `file_attachments` SET `file_name` = '$filename', `file_path` = '$path', `updated_by` = '$userid', `updated_date` = '$date_formatted' WHERE `file_attachments`.`doc_id` = $docid");
			if(file_put_contents($path, base64_decode($photo))) {
				$result["success"] = "1";
				$result["message"] = "success";
		
			}
		}
		*/
	} else {
		if($have_vet==0) {
			$vet_name = "";
			$vet_address = "";
		}
		if($is_agreed==0) {
			$home_visit_time = "";
		} else {
			$reason_disagree = "";
		}
		$sql = mysqli_query($connection, "UPDATE `adoptions` SET `reason` = '$reason', `pet_live_type_id` = '$typeid', `current_pet` = '$current', `past_pet` = '$past_pet', `past_pet_details` = '$past_pet_details', `have_yard` = '$have_yard', `is_fenced` = '$is_fenced', `have_vet` = '$have_vet', `vet_name` = '$vet_name', `vet_address` = '$vet_address', `circumstances` = '$circumstances', `is_agreed` = '$is_agreed', `reason_disagree` = '$reason_disagree', `home_visit_time` = '$home_visit_time', `comments` = '$comments' WHERE `adoptions`.`adoption_id` = $adoptionid");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date_formatted' WHERE `documents`.`doc_id` = $docid");
		$result["success"] = "1";
			$result["message"] = "success";
		//delete current file
		foreach($files as $row) {
			$item1 = $row['item1'];
			$item2 = $row['item2'];
			$item3 = $row['item3'];
			$item4 = $row['item4'];
			$item5 = $row['item5'];
			$sql3 = mysqli_query($connection, "Delete from file_attachments where file_name = '$item1' or file_name = '$item2' or file_name = '$item3' or file_name = '$item4'  or file_name = '$item5'");
		}
			$result["success"] = "1";
			$result["message"] = "success";
	}
	echo json_encode($result);
	mysqli_close($connection);
}

?>