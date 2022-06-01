<?php
$result = array();
$response = array();
$theArray = array();
include 'db\db_connect.php';
$sql = mysqli_query($connection, "Select * from pets inner join pet_type on pet_type.pet_type_id = pets.pet_type_id  inner join pet_status on pets.pet_status_id = pet_status.pet_status_id order by pets.date_added desc");
while($row = mysqli_fetch_array($sql))
{
	$theArray['pet_id'] = $row['pet_id'];
	$theArray['pet_image'] = $row['pet_image'];
	$theArray['pet_name'] = $row['pet_name'];
	$theArray['pet_type_desc'] = $row['pet_type_desc'];
	$theArray['pet_age'] = $row['pet_age'];
	$theArray['date_added'] = $row['date_added'];
	$theArray['pet_status_id'] = $row['pet_status_id'];
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