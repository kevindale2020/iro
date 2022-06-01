<?php
require_once 'db\db_connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];

	$sql = mysqli_query($connection, "Delete from notifications where notification_id = $id");

	if($sql) {
		$result['success'] = "1";
		$result['message'] = "success";
	}

	echo json_encode($result);
	mysqli_close($connection);
}

?>