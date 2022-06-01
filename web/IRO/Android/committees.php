<?php
$result = array();
$response = array();
$theArray = array();
include 'db\db_connect.php';
$sql = mysqli_query($connection, "Select * from committees order by id");
while($row = mysqli_fetch_array($sql))
{
	$theArray['committee_id'] = $row['committee_id'];
	$theArray['committee'] = $row['committee'];
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