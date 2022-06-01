<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from notifications where user_id = $id order by notification_id desc");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['notification_id'] = $row['notification_id'];
			$theArray['notification_type_id'] = $row['notification_type_id'];
			$theArray['subject'] = $row['subject'];
			$theArray['content'] = $row['content'];
			$theArray['date'] = $row['date'];
		
			$result[]=$theArray;
		}
		$response["success"] = "1";
		$response["data"] = $result;
	} else {
		$response["success"] = "0";
		//$response["data"] = $result;
	}
	echo json_encode($response);
}
?>