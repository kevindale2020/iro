<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	//$reason = $_POST['reason'];
	//$date = date("Ymd");

	$sql = mysqli_query($connection, "Update documents set doc_status_id = 1 where doc_id = $id and doc_type_id = 2");
	//$sql2 = mysqli_query($connection, "INSERT INTO `cancelled_documents` (`id`, `doc_id`, `cancelled_reason`, `cancelled_date`) VALUES (NULL, '$id', '$reason', '$date')");

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