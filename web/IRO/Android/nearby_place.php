<?php
$result = array();
$response = array();
$theArray = array();
include 'db/db_connect.php';
$sql = mysqli_query($connection, "Select * from places order by place_id");
while($row = mysqli_fetch_array($sql))
{
	$theArray['place_id'] = $row['place_id'];
	$theArray['place_image'] = $row['place_image'];
	$theArray['place_name'] = $row['place_name'];
	$theArray['place_vicinity'] = $row['place_vicinity'];
	$theArray['place_contact'] = $row['place_contact'];
	$theArray['place_lat'] = $row['place_lat'];
	$theArray['place_lng'] = $row['place_lng'];
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