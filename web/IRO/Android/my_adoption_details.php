<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select documents.date_submitted, pets.pet_id, pets.pet_image, pets.pet_name, pets.pet_gender, pet_type.pet_type_desc, pets.pet_age, pets.pet_desc, pets.pet_status_id, document_status.doc_status_id, document_status.doc_status_desc, cancelled_documents.cancelled_date, cancelled_documents.reason, rejected_documents.rejected_reason, rejected_documents.rejected_date, approved_documents.approved_date from adoptions inner join pets on pets.pet_id = adoptions.pet_id inner join pet_status on pets.pet_status_id = pet_status.pet_status_id inner join pet_type on pets.pet_type_id = pet_type.pet_type_id inner join documents on documents.doc_form_id = adoptions.adoption_id INNER JOIN document_status on document_status.doc_status_id = documents.doc_status_id LEFT join cancelled_documents on cancelled_documents.doc_id = documents.doc_id LEFT join rejected_documents on rejected_documents.doc_id = documents.doc_id LEFT JOIN approved_documents on approved_documents.doc_id = documents.doc_id where documents.doc_id = $id");
	if(mysqli_num_rows($sql)==1) {

		while($row = mysqli_fetch_array($sql))
		{
			$theArray['date_submitted'] = $row['date_submitted'];
			$theArray['pet_id'] = $row['pet_id'];
			$theArray['pet_image'] = $row['pet_image'];
			$theArray['pet_name'] = $row['pet_name'];
			$theArray['pet_gender'] = $row['pet_gender'];
			$theArray['pet_type_desc'] = $row['pet_type_desc'];
			$theArray['pet_age'] = $row['pet_age'];
			$theArray['pet_desc'] = $row['pet_desc'];
			$theArray['pet_status_id'] = $row['pet_status_id'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['doc_status_desc'] = $row['doc_status_desc'];
			$theArray['cancelled_date'] = $row['cancelled_date'];
			$theArray['reason'] = $row['reason'];
			$theArray['rejected_reason'] = $row['rejected_reason'];
			$theArray['rejected_date'] = $row['rejected_date'];
			$theArray['approved_date'] = $row['approved_date'];
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