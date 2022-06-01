<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$username = mysqli_real_escape_string($connection, $_POST['username']);
	$password = mysqli_real_escape_string($connection, $_POST['password']);

	$sql = mysqli_query($connection, "Select * from users inner join user_roles on users.user_id = user_roles.user_id where users.user_username = '$username' and users.user_password = '$password' and user_roles.role_id <> 2");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql)) {
			if($row['verified']==0) {
				$response["success"] = "2";
				$response["message"] = "This account is not yet verified";
			} else if($row['is_active']==0) {
				$response["success"] = "3";
				$response["message"] = "Your account has been temporary blocked by Admin";
			} else {

				$theArray["user_id"] = $row['user_id'];
				$theArray["user_image"] = $row['user_image'];
				$theArray["user_fname"] = $row['user_fname'];
				$theArray["user_lname"] = $row['user_lname'];
				$theArray["user_nname"] = $row['user_nname'];
				$theArray["user_occupation"] = $row['user_occupation'];
				$theArray["user_address"] = $row['user_address'];
				$theArray["user_email"] = $row['user_email'];
				$theArray["user_contact"] = $row['user_contact'];
				$theArray["user_username"] = $row['user_username'];
				$result[] = $theArray;

				$response["success"] = "1";
				$response["data"] = $result;
				$response["message"] = "success";
			}
		}

	} else {
		$response["success"] = "0";
		$response["message"] = "Invalid credentials";
	}

	echo json_encode($response);
	mysqli_close($connection);
}
?>