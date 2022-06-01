<?php
require_once 'db\db_connect.php';
if($_SERVER['REQUEST_METHOD']=='POST') {
	$id = $_POST['id'];
	$photo = $_POST['photo'];
	$filename = "$id.jpg";
	$path = "images/users/".$filename;
	//$finalpath = "http://192.168.254.110:8080/IRO/".$path;

	$sql = mysqli_query($connection, "Update users set user_image = '$filename' where user_id = $id");

	if($sql) {
		if(file_put_contents($path, base64_decode($photo))) {
			$result['success'] = "1";
			$result['message'] = "success";

			echo json_encode($result);
			mysqli_close($connection);

		}
	}
}

?>