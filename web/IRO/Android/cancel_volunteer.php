<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$reason = mysqli_real_escape_string($connection, $_POST['reason']);
	$date = date("Ymd");

	$sql = mysqli_query($connection, "Update documents set doc_status_id = 4 where doc_id = $id");
	$sql2 = mysqli_query($connection, "INSERT INTO `cancelled_documents` (`id`, `doc_id`, `cancelled_reason`, `cancelled_date`) VALUES (NULL, '$id', '$reason', '$date')");
	//notify admin
	$sql3 = mysqli_query($connection, "Select * from users inner join user_roles on users.user_id = user_roles.user_id where user_roles.role_id = 2");
	while($row = mysqli_fetch_array($sql3)) {
		$adminid = $row['user_id'];
		$sql3 = mysqli_query($connection, "INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen_admin`) VALUES (NULL, '$adminid', '3', 'Volunteer', 'Volunteer Application Form has been cancelled', '$date', '1', '0')");
	}

	if(!$sql || !$sql2) {
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