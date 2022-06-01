<?php
$result = array();
$response = array();
$theArray = array();
include 'db\db_connect.php';
$sql = mysqli_query($connection, "Select documents.doc_id, document_status.doc_status_id, users.user_id, users.user_contact, users.user_image, users.user_fname, users.user_lname, reports.report_type_id, reports.reporter_location, reports.date_reported, file_attachments.file_name, reports.title, reports.description, document_status.doc_status_desc from reports inner join users on reports.user_id = users.user_id inner join report_type on report_type.report_type_id = reports.report_type_id inner join documents on reports.report_id = documents.doc_form_id inner join document_status on document_status.doc_status_id = documents.doc_status_id inner join file_attachments on documents.doc_id = file_attachments.doc_id left join approved_documents on approved_documents.doc_id = documents.doc_id where documents.doc_status_id = 5 order by documents.doc_id desc");
if(mysqli_num_rows($sql)>0) {
	while($row = mysqli_fetch_array($sql))
	{
		$theArray['doc_id'] = $row['doc_id'];
		$theArray['user_id'] = $row['user_id'];
		$theArray['user_contact'] = $row['user_contact'];
		$theArray['user_image'] = $row['user_image'];
		$theArray['user_fname'] = $row['user_fname'];
		$theArray['user_lname'] = $row['user_lname'];
		$theArray['doc_status_id'] = $row['doc_status_id'];
		$theArray['doc_status_desc'] = $row['doc_status_desc'];
		$theArray['report_type_id'] = $row['report_type_id'];
		$theArray['reporter_location'] = $row['reporter_location'];
		$theArray['date_reported'] = $row['date_reported'];
		$theArray['file_name'] = $row['file_name'];
		$theArray['title'] = $row['title'];
		$theArray['description'] = $row['description'];
		$result[]=$theArray;
	}
	$response["success"] = "1";
	$response["data"] = $result;
} else {
	$response["success"] = "0";
}
/*
if(!$sql){
	$response["success"] = "0";
	//$response["data"] = $result;
}
else{
	$response["success"] = "1";
	$response["data"] = $result;
}
*/
echo json_encode($response)
?>