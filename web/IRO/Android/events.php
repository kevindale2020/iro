<?php
$result = array();
$response = array();
$theArray = array();
include 'db\db_connect.php';
$sql = mysqli_query($connection, "Select events.id, users.user_image, users.user_fname, users.user_lname, roles.role_desc, events.date_posted, events.image, events.title, events.desc from events inner join users on events.user_id = users.user_id inner join user_roles on user_roles.user_id = users.user_id inner join roles on roles.role_id = user_roles.role_id order by events.id desc");
if(mysqli_num_rows($sql)>0) {
	while($row = mysqli_fetch_array($sql))
	{
		$theArray['id'] = $row['id'];
		$theArray['user_image'] = $row['user_image'];
		$theArray['user_fname'] = $row['user_fname'];
		$theArray['user_lname'] = $row['user_lname'];
		$theArray['role_desc'] = $row['role_desc'];
		$theArray['date_posted'] = $row['date_posted'];
		$theArray['image'] = $row['image'];
		$theArray['title'] = $row['title'];
		$theArray['desc'] = $row['desc'];
		$result[]=$theArray;
	}
	$response["success"] = "1";
	$response["data"] = $result;
} else{
	$response["success"] = "0";
	//$response["data"] = $result;
}
echo json_encode($response)
?>