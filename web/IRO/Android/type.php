<?php
$result = array();
$response = array();
$theArray = array();
include 'db/db_connect.php';
$sql = mysqli_query($connection, "Select * from pet_live_type");
while($row = mysqli_fetch_array($sql))
{
	$theArray['id'] = $row['id'];
	$theArray['pet_live_type_id'] = $row['pet_live_type_id'];
	$theArray['pet_live_type_desc'] = $row['pet_live_type_desc'];
	$result[]=$theArray;
}
if(!$sql){
	$response["success"] = 0;
	$response["data"] = $result;
}
else{
	$response["success"] = 1;
	$response["data"] = $result;
}
echo json_encode($response)
?>