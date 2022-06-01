<?php
$result = array();
$response = array();
$theArray = array();
include 'db/db_connect.php';
$sql = mysqli_query($connection, "Select * from report_type");
while($row = mysqli_fetch_array($sql))
{
	$theArray['id'] = $row['id'];
	$theArray['report_type_id'] = $row['report_type_id'];
	$theArray['report_type_desc'] = $row['report_type_desc'];
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