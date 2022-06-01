<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select user_roles.is_active from users inner join user_roles on users.user_id = user_roles.user_id where users.user_id = $id");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['is_active'] = $row['is_active'];
			$result[]=$theArray;
		}
		$response["success"] = "1";
		$response["data"] = $result;
	}
	else{
		$response["success"] = "0";
		//$response["data"] = $result;
	}
	echo json_encode($response);
}
?>