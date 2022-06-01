<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from adoptions inner join documents on adoptions.adoption_id = documents.doc_form_id inner join document_status on document_status.doc_status_id = documents.doc_status_id left join approved_documents on approved_documents.doc_id = documents.doc_id left join rejected_documents on rejected_documents.doc_id = documents.doc_id left join cancelled_documents on cancelled_documents.doc_id = documents.doc_id inner join users on users.user_id = adoptions.user_id inner join pets on pets.pet_id = adoptions.pet_id where documents.doc_id = $id");
	if(mysqli_num_rows($sql)!=0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['adoption_id'] = $row['adoption_id'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['user_id'] = $row['user_id'];
			$theArray['user_fname'] = $row['user_fname'];
			$theArray['user_lname'] = $row['user_lname'];
			$theArray['user_address'] = $row['user_address'];
			$theArray['user_contact'] = $row['user_contact'];
			$theArray['user_occupation'] = $row['user_occupation'];
			$theArray['pet_id'] = $row['pet_id'];
			$theArray['pet_name'] = $row['pet_name'];
			$theArray['pet_age'] = $row['pet_age'];
			$theArray['pet_gender'] = $row['pet_gender'];
			$theArray['children_age'] = $row['children_age'];
			$theArray['reason'] = $row['reason'];
			$theArray['pet_live_type_id'] = $row['pet_live_type_id'];
			$theArray['current_pet'] = $row['current_pet'];
			$theArray['past_pet'] = $row['past_pet'];
			$theArray['past_pet_details'] = $row['past_pet_details'];
			$theArray['have_yard'] = $row['have_yard'];
			$theArray['is_fenced'] = $row['is_fenced'];
			$theArray['have_vet'] = $row['have_vet'];
			$theArray['vet_name'] = $row['vet_name'];
			$theArray['vet_address'] = $row['vet_address'];
			$theArray['circumstances'] = $row['circumstances'];
			$theArray['is_agreed'] = $row['is_agreed'];
			$theArray['reason_disagree'] = $row['reason_disagree'];
			$theArray['home_visit_time'] = $row['home_visit_time'];
			$theArray['comments'] = $row['comments'];
			$theArray['date_submitted'] = $row['date_submitted'];
			$theArray['approved_date'] = $row['approved_date'];
			$theArray['rejected_reason'] = $row['rejected_reason'];
			$theArray['rejected_date'] = $row['rejected_date'];
			$theArray['cancelled_reason'] = $row['cancelled_reason'];
			$theArray['cancelled_date'] = $row['cancelled_date'];
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