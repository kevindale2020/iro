<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select committees.committee, committee_details.responsibilities from committee_details inner join committees on committee_details.committee_id = committees.committee_id where committee_details.committee_id = $id");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql))
		{
			$theArray['committee'] = $row['committee'];
			$theArray['responsibilities'] = $row['responsibilities'];
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