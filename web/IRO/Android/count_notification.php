<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from notifications where user_id = $id and is_seen2 = 0");
	$counter = mysqli_num_rows($sql);
	if($counter>0) {
		$response["success"] = "1";
		$response["counter"] = $counter;
	}
	else{
		$response["success"] = "0";
		//$response["data"] = $result;
	}
	echo json_encode($response);
}
?>