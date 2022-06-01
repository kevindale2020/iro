<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select users.user_id, users.user_image, users.user_email, users.user_fname, users.user_lname, volunteer.volunteer_id, documents.doc_id, documents.date_submitted, document_status.doc_status_id, document_status.doc_status_desc, volunteer.is_active from volunteer inner join users on volunteer.user_id = users.user_id inner join documents on volunteer.volunteer_id = documents.doc_form_id inner join document_status on document_status.doc_status_id = documents.doc_status_id where volunteer.user_id = $id and documents.doc_type_id = 2");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['volunteer_id'] = $row['volunteer_id'];
			$theArray['user_id'] = $row['user_id'];
			$theArray['user_image'] = $row['user_image'];
			$theArray['user_fname'] = $row['user_fname'];
			$theArray['user_lname'] = $row['user_lname'];
			$theArray['user_email'] = $row['user_email'];
			$theArray['doc_id'] = $row['doc_id'];
			$theArray['date_submitted'] = $row['date_submitted'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['doc_status_desc'] = $row['doc_status_desc'];
			$theArray['is_active'] = $row['is_active'];
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