<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select pets.pet_id, pets.pet_image, pets.pet_name, pets.pet_gender, pet_type.pet_type_desc, pets.pet_age, pets.acquired_from, pets.date_added, pets.pet_desc, pets.pet_status_id from pets inner join pet_status on pets.pet_status_id = pet_status.pet_status_id inner join pet_type on pets.pet_type_id = pet_type.pet_type_id where pets.pet_id = $id");
	if(mysqli_num_rows($sql)==1) {

		while($row = mysqli_fetch_array($sql))
		{
			$theArray['pet_id'] = $row['pet_id'];
			$theArray['pet_image'] = $row['pet_image'];
			$theArray['pet_name'] = $row['pet_name'];
			$theArray['pet_gender'] = $row['pet_gender'];
			$theArray['pet_type_desc'] = $row['pet_type_desc'];
			$theArray['pet_age'] = $row['pet_age'];
			$theArray['acquired_from'] = $row['acquired_from'];
			$theArray['date_added'] = $row['date_added'];
			$theArray['pet_desc'] = $row['pet_desc'];
			$theArray['pet_status_id'] = $row['pet_status_id'];
			$result[]=$theArray;
		}
		$response["success"] = 1;
		$response["data"] = $result;
	} 
	else {
		$response["success"] = 0;
		//$response["data"] = $result;
	}
	echo json_encode($response);
}
?>