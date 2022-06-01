<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select guidelines.guideline_id, guidelines.guideline, guideline_details.details from guideline_details inner join guidelines on guideline_details.guideline_id = guidelines.guideline_id where guideline_details.guideline_id = $id");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['guideline_id'] = $row['guideline_id'];
			$theArray['guideline'] = $row['guideline'];
			$theArray['details'] = $row['details'];
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