<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from documents inner join reports on documents.doc_form_id = reports.report_id inner join file_attachments on file_attachments.doc_id = documents.doc_id left join approved_documents on approved_documents.doc_id = documents.doc_id left join cancelled_documents on cancelled_documents.doc_id = documents.doc_id left join rejected_documents on rejected_documents.doc_id = documents.doc_id left join acknowledged_documents on acknowledged_documents.doc_id = documents.doc_id left join closed_documents on closed_documents.doc_id = documents.doc_id where documents.doc_id = $id");
	if(mysqli_num_rows($sql)!=0) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['doc_id'] = $row['doc_id'];
			$theArray['doc_status_id'] = $row['doc_status_id'];
			$theArray['report_id'] = $row['report_id'];
			$theArray['file_name'] = $row['file_name'];
			$theArray['user_id'] = $row['user_id'];
			$theArray['report_type_id'] = $row['report_type_id'];
			$theArray['title'] = $row['title'];
			$theArray['description'] = $row['description'];
			$theArray['approved_date'] = $row['approved_date'];
			$theArray['acknowledged_date'] = $row['acknowledged_date'];
			$theArray['closed_date'] = $row['closed_date'];
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