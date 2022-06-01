<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select users.user_id, users.user_image, users.user_fname, users.user_lname, users.user_nname, users.user_address, users.user_email, users.user_contact, users.user_occupation, users.user_username from users inner join user_roles on users.user_id = user_roles.user_id where users.user_id = $id");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['user_id'] = $row['user_id'];
			$theArray['user_image'] = $row['user_image'];
			$theArray['user_fname'] = $row['user_fname'];
			$theArray['user_lname'] = $row['user_lname'];
			$theArray['user_nname'] = $row['user_nname'];
			$theArray['user_address'] = $row['user_address'];
			$theArray['user_email'] = $row['user_email'];
			$theArray['user_contact'] = $row['user_contact'];
			$theArray['user_occupation'] = $row['user_occupation'];
			$theArray['user_username'] = $row['user_username'];
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