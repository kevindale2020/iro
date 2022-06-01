<?php
include 'db/db_connect.php';

if(isset($_POST['login'])) {
	$username = mysqli_real_escape_string($connection, $_POST['username']);
	$password = mysqli_real_escape_string($connection, $_POST['password']);

	$sql = mysqli_query($connection, "Select * from users inner join user_roles on users.user_id = user_roles.user_id where users.user_username = '$username' and users.user_password = '$password' and user_roles.role_id <> 1");
	if(mysqli_num_rows($sql)==1) {
		while($row = mysqli_fetch_array($sql)) {
			//Admin account only
			if($row['role_id']==2) {
				$_SESSION['user_id'] = $row['user_id'];
				$_SESSION['role_id'] = $row['role_id'];
				echo "<script>window.location.href='admin_index.php'</script>";
			}
			//continue the other accounts
		}
	} else {
		echo "Invalid credentials";
	}
}
?>