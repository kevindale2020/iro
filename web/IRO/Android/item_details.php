<?php
$result = array();
$response = array();
$theArray = array();
require_once 'db\db_connect.php';

if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$sql = mysqli_query($connection, "Select * from items inner join item_category on items.item_category_id = item_category.item_category_id where items.item_id = $id");
	if(mysqli_num_rows($sql)==1) {

		while($row = mysqli_fetch_array($sql))
		{
			$theArray['item_id'] = $row['item_id'];
			$theArray['item_image'] = $row['item_image'];
			$theArray['item_name'] = $row['item_name'];
			$theArray['item_category_desc'] = $row['item_category_desc'];
			$theArray['item_desc'] = $row['item_desc'];
			$theArray['item_qty'] = $row['item_qty'];
			$theArray['item_price'] = $row['item_price'];
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