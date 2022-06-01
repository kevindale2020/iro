<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from file_attachments where doc_id = $id");
	if(mysqli_num_rows($sql)>0) {

		while($row = mysqli_fetch_array($sql))
		{
			$theArray['file_name'] = $row['file_name'];
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