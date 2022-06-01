<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$docid = $_POST['docid'];
	$title = mysqli_real_escape_string($connection, $_POST['title']);
	$desc = mysqli_real_escape_string($connection, $_POST['desc']);
	$type = $_POST['type'];
	$photo = $_POST['photo'];
	$date = date("Ymd");
	$filename = $_POST['filename'];
	$path = "images/attachments/".$filename;

	if($photo!='0') {
		$sql = mysqli_query($connection, "UPDATE `reports` SET `report_type_id` = '$type', `title` = '$title', `description` = '$desc' WHERE `reports`.`report_id` = $id");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date' WHERE `documents`.`doc_id` = $docid");
		$sql3 = mysqli_query($connection, "UPDATE `file_attachments` SET `file_name` = '$filename', `file_path` = '$path' WHERE `file_attachments`.`doc_id` = $docid");
			if(file_put_contents($path, base64_decode($photo))) {
				$result['success'] = "1";
				$result['message'] = "success";
				echo json_encode($result);
			}
	} else {
		$sql = mysqli_query($connection, "UPDATE `reports` SET `report_type_id` = '$type', `title` = '$title', `description` = '$desc' WHERE `reports`.`report_id` = $id");
		$sql2 = mysqli_query($connection, "UPDATE `documents` SET `date_modified` = '$date' WHERE `documents`.`doc_id` = $docid");
			$result['success'] = "1";
			$result['message'] = "success";
			echo json_encode($result);
	}
	mysqli_close($connection);
}
?>