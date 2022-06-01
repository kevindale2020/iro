<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from donations inner join donation_type on donations.donation_type_id = donation_type.donation_type_id where donations.donation_id = $id");
	if(mysqli_num_rows($sql)!=0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['donation_id'] = $row['donation_id'];
			$theArray['donation_type_id'] = $row['donation_type_id'];
			$theArray['date_verified'] = $row['date_verified'];
			$theArray['account_name'] = $row['account_name'];
			$theArray['account_number'] = $row['account_number'];
			$theArray['amount'] = $row['amount'];
			$theArray['image'] = $row['image'];
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