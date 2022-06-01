<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$userid = $_POST['userid'];
	
	$sql = mysqli_query($connection, "UPDATE `notifications` SET `is_seen2` = '1' WHERE user_id = $userid");

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