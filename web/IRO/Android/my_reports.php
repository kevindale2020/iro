<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select documents.doc_id, document_status.doc_status_id, users.user_id, users.user_image, users.user_fname, users.user_lname, users.user_contact, reports.report_type_id, reports.reporter_location, reports.date_reported, file_attachments.file_name, reports.title, reports.description, document_status.doc_status_desc from reports inner join users on reports.user_id = users.user_id inner join report_type on report_type.report_type_id = reports.report_type_id inner join documents on reports.report_id = documents.doc_form_id inner join document_status on document_status.doc_status_id = documents.doc_status_id inner join file_attachments on documents.doc_id = file_attachments.doc_id where users.user_id = $id and documents.doc_type_id = 3 order by documents.doc_id desc ");
	if(mysqli_num_rows($sql)>0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['doc_id'] = $row['doc_id'];
			$theArray['user_id'] = $row['user_id'];
			$theArray['user_image'] = $row['user_image'];
			$theArray['user_fname'] = $row['user_fname'];
			$theArray['user_lname'] = $row['user_lname'];
			$theArray['user_contact'] = $row['user_contact'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['doc_status_desc'] = $row['doc_status_desc'];
			$theArray['reporter_location'] = $row['reporter_location'];
			$theArray['report_type_id'] = $row['report_type_id'];
			$theArray['date_reported'] = $row['date_reported'];
			$theArray['file_name'] = $row['file_name'];
			$theArray['title'] = $row['title'];
			$theArray['description'] = $row['description'];
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