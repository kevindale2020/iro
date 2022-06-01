<?php
$result = array();
$response = array();
$theArray = array();
include 'db\db_connect.php';
$sql = mysqli_query($connection, "Select * from guidelines order by id");
while($row = mysqli_fetch_array($sql))
{
	$theArray['guideline_id'] = $row['guideline_id'];
	$theArray['guideline'] = $row['guideline'];
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