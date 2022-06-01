<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from donations inner join donation_type on donations.donation_type_id = donation_type.donation_type_id where donations.user_id = $id and donations.donation_type_id <> 1");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['donation_id'] = $row['donation_id'];
			$theArray['donation_type_id'] = $row['donation_type_id'];
			$theArray['donation_type_desc'] = $row['donation_type_desc'];
			$theArray['image'] = $row['image'];
			$theArray['amount'] = $row['amount'];
			$theArray['date'] = $row['date'];
			$theArray['is_verified'] = $row['is_verified'];
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