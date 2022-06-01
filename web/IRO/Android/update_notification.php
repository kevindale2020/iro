<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$userid = $_POST['userid'];
	$typeid = $_POST['typeid'];
	
	$sql = mysqli_query($connection, "UPDATE `notifications` SET `is_seen` = '1' WHERE user_id = $userid and notification_type_id = $typeid");

	if(!$sql) {
		$result["success"] = "0";
		$result["message"] = "error";
	} else {
		$result["success"] = "1";
		$result["message"] = "success";
	}

	echo json_encode($result);
	mysqli_close($connection);
}
?>