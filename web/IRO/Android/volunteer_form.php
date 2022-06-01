<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from volunteer inner join documents on volunteer.volunteer_id = documents.doc_form_id inner join document_status on documents.doc_status_id = document_status.doc_status_id left join approved_documents on approved_documents.doc_id = documents.doc_id left join cancelled_documents on cancelled_documents.doc_id = documents.doc_id left join rejected_documents on rejected_documents.doc_id = documents.doc_id where volunteer.user_id = $id and documents.doc_type_id = 2");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['volunteer_id'] = $row['volunteer_id'];
			$theArray['is_adoption'] = $row['is_adoption'];
			$theArray['is_transportation'] = $row['is_transportation'];
			$theArray['is_shelter_clean_up'] = $row['is_shelter_clean_up'];
			$theArray['is_educational_campaign'] = $row['is_educational_campaign'];
			$theArray['is_animal_welfare'] = $row['is_animal_welfare'];
			$theArray['is_fostering'] = $row['is_fostering'];
			$theArray['is_food_donation_drive'] = $row['is_food_donation_drive'];
			$theArray['is_events'] = $row['is_events'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['approved_date'] = $row['approved_date'];
			$theArray['cancelled_date'] = $row['cancelled_date'];
			$theArray['cancelled_reason'] = $row['cancelled_reason'];
			$theArray['rejected_date'] = $row['rejected_date'];
			$theArray['rejected_reason'] = $row['rejected_reason'];
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