<?php
$result = array();
$response = array();
$theArray = array();
include 'db/db_connect.php';
$sql = mysqli_query($connection, "Select * from donation_type");
while($row = mysqli_fetch_array($sql))
{
	$theArray['id'] = $row['id'];
	$theArray['donation_type_id'] = $row['donation_type_id'];
	$theArray['donation_type_desc'] = $row['donation_type_desc'];
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