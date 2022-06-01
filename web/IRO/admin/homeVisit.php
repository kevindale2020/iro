<?php 
include '../config.php'; //this code will allow you to connect to your database

if (!empty($_GET['id'])) 
{
	$id = $_GET['id'];

	$adoption_request = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE doc_id = $id");

	while ($row = mysqli_fetch_array($adoption_request)) 
	{
		$user_id = $row['user_id'];
		$fname = $row['user_fname'];
		$lname = $row['user_lname'];
	}
}

if (isset($_POST['submit'])) 
{
	$user_id = $_POST['user_id'];
	$content = mysqli_real_escape_string($database, $_POST['content']);
	date_default_timezone_set("Asia/Singapore");
	$date_today = date("F j, Y, g:i a");
	$success = 'Notification has been sent successfully!';

	$house_visit = "INSERT INTO `iro_db`.`notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '8', 'House Visit', '$content', '$date_today', '0', '0')";

	if (mysqli_query($database, $house_visit)) 
	{
		echo "<script>window.location.href='admin_adoptionrequest.php?success=$success';</script>";
	}
}
 ?>

 <!DOCTYPE html>
 <html>
 <head>
 	<title>IRO - House Visit</title>
 </head>
 <body>
 	Fullname
 	<div>- <?php echo $fname ?> <?php echo $lname ?></div>
 	<hr>
 	Purpose
 	<div>- House Visit</div>
 	<hr>
 	Message
 	<form method="post" action="homeVisit.php">
	 	<div>
	 		<textarea class="form-control" name="content" style="width: 100%"></textarea>
	 		<input type="hidden" name="user_id" value="<?php echo $user_id ?>">
	 	</div>

	 	<br>
	 	<div class="row">
	 		<div class="col-sm-8">
	 			
	 		</div>

	 		<div class="col-sm-2">
	 			<button class="btn btn-danger" type="button" data-dismiss="modal" style="float: right;">Cancel</button>
	 		</div>

	 		<div class="col-sm-2">
	 			<button class="btn btn-success" type="submit" name="submit" style="float: right;">Submit</button>
	 		</div>
	 		
	 	</div>
 	</form>
 	
 </body>
 </html>