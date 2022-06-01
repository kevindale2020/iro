<?php 
include 'config.php';

if (isset($_GET['activate'])) 
{
	$activate = $_GET['activate'];
	$success = "Account has been activated successfully";

	$activate_account = "UPDATE user_roles SET is_active = 1 WHERE user_role_id = $activate";

	if (mysqli_query($database, $activate_account)) 
	{
		echo "<script>window.location.href='cityvet_viewuser.php?success=$success'</script>";
	}
}

if (isset($_GET['deactivate'])) 
{
	$deactivate = $_GET['deactivate'];
	$success = "Account has been deactivated successfully";

	$deactivate_account = "UPDATE user_roles SET is_active = 0 WHERE user_role_id = $deactivate";

	if (mysqli_query($database, $deactivate_account)) 
	{
		echo "<script>window.location.href='cityvet_viewuser.php?success=$success'</script>";
	}
}

if (isset($_GET['activate_volunteer'])) 
{
	$user_id = $_GET['activate_volunteer'];
	$date_today = date('Y-m-d');
	$success = "Volunteer account has been activated successfully";
	$error = "Volunteer account already activated";

	$check_volunteer = mysqli_query($database, "SELECT * FROM user_roles WHERE user_id = $user_id AND role_id = 3");

	if (mysqli_num_rows($check_volunteer) > 0) 
	{
		echo "<script>window.location.href='add_volunteer.php?error=$error'</script>";
	}
	else
	{
		$activate_volunteer = "INSERT INTO user_roles (user_id,role_id,created_date,is_active,verified) VALUES ('$user_id','3','$date_today','1','1')";

		if (mysqli_query($database, $activate_volunteer)) 
		{
			echo "<script>window.location.href='add_volunteer.php?success=$success'</script>";
		}
	}
}

if (isset($_GET['deactivate_volunteer'])) 
{
	$user_role_id = $_GET['deactivate_volunteer'];
	$success = "Volunteer account has been deactivated successfully";

	$deactivate_volunteer = "DELETE FROM user_roles WHERE user_role_id = $user_role_id";

		if (mysqli_query($database, $deactivate_volunteer)) 
		{
			echo "<script>window.location.href='add_volunteer.php?success=$success'</script>";
		}
}
 ?>