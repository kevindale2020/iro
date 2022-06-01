<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select pets.pet_id, pet_type.pet_type_desc, documents.doc_status_id, pets.pet_status_id, documents.doc_id, adoptions.user_id, pets.pet_image, pets.pet_name, pets.pet_gender, pets.pet_age, document_status.doc_status_desc from adoptions inner join pets on adoptions.pet_id = pets.pet_id inner join pet_type on pet_type.pet_type_id = pets.pet_type_id inner join documents on adoptions.adoption_id = documents.doc_form_id inner join document_status on documents.doc_status_id = document_status.doc_status_id where adoptions.user_id = $id and documents.doc_type_id = 1");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['pet_id'] = $row['pet_id'];
			$theArray['pet_status_id'] = $row['pet_status_id'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['doc_id'] = $row['doc_id'];
			$theArray['user_id'] = $row['user_id'];
			$theArray['pet_image'] = $row['pet_image'];
			$theArray['pet_name'] = $row['pet_name'];
			$theArray['pet_type_desc'] = $row['pet_type_desc'];
			$theArray['pet_age'] = $row['pet_age'];
			$theArray['doc_status_desc'] = $row['doc_status_desc'];
			$result[]=$theArray;
		}
		$response["success"] = "1";
		$response["data"] = $result;
	}
	else{
		$response["success"] = "0";
		//$response["data"] = $result;
	}
	echo json_encode($response);
}
?>