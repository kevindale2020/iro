<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$userid = $_POST['userid'];
	$docid = $_POST['docid'];
	$is_adoption = $_POST['is_adoption'];
	$is_transportation = $_POST['is_transportation'];
	$is_shelter_clean_up = $_POST['is_shelter_clean_up'];
	$is_educational_campaign = $_POST['is_educational_campaign'];
	$is_animal_welfare = $_POST['is_animal_welfare'];
	$is_fostering = $_POST['is_fostering'];
	$is_food_donation_drive = $_POST['is_food_donation_drive'];
	$is_events = $_POST['is_events'];
	$date_submitted = date("Ymd");

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
		$sql = mysqli_query($connection, "UPDATE `volunteer` SET `is_adoption` = '$is_adoption', `is_transportation` = '$is_transportation', `is_shelter_clean_up` = '$is_shelter_clean_up', `is_educational_campaign` = '$is_educational_campaign', `is_animal_welfare` = '$is_animal_welfare', `is_fostering` = '$is_fostering', `is_food_donation_drive` = '$is_food_donation_drive', `is_events` = '$is_events' WHERE `volunteer`.`user_id` = $userid");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date_submitted' WHERE `documents`.`doc_id` = $docid");

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

	} else {
		$sql = mysqli_query($connection, "UPDATE `volunteer` SET `is_adoption` = '$is_adoption', `is_transportation` = '$is_transportation', `is_shelter_clean_up` = '$is_shelter_clean_up', `is_educational_campaign` = '$is_educational_campaign', `is_animal_welfare` = '$is_animal_welfare', `is_fostering` = '$is_fostering', `is_food_donation_drive` = '$is_food_donation_drive', `is_events` = '$is_events' WHERE `volunteer`.`user_id` = $userid");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date_submitted' WHERE `documents`.`doc_id` = $docid");
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


	// if(!$sql) {
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