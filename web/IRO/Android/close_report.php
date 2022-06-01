<?php
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {

	$id = $_POST['id'];
	$id2 = $_POST['id2'];
	$userid = $_POST['userid'];
	$date = date("Ymd");
	$date_notified = date("F j, Y, g:i a");

	$sql = mysqli_query($connection, "Update documents set doc_status_id = 6 where doc_id = $id");
	$sql2 = mysqli_query($connection, "INSERT INTO `closed_documents` (`id`, `doc_id`, `closed_date`) VALUES (NULL, '$id', '$date')");
	//notify admin
	$sql3 = mysqli_query($connection, "Select * from users inner join user_roles on users.user_id = user_roles.user_id INNER JOIN reports ON reports.user_id = users.user_id WHERE reports.user_id = $userid");
	while($row = mysqli_fetch_array($sql3)) {
		$adminid = $row['user_id'];
		$report_type = $row['report_type_id'];
		if($report_type==2) {
			$sql3 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '4', 'Report', 'Report has been closed', '$date_notified', '0')");
		} else if($report_type==3) {
			$sql3 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '5', 'Report', 'Report has been closed', '$date_notified', '0')");
		} else {
			$sql3 = mysqli_query($connection, "INSERT INTO `notifications_admin` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`) VALUES (NULL, '$userid', '6', 'Report', 'Report has been closed', '$date_notified', '0')");
		}
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