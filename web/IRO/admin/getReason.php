<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (!empty($_GET['id'])) 
{
	$id = $_GET['id'];

	$adoption_request = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE doc_id = $id");

	while ($row = mysqli_fetch_array($adoption_request)) 
	{
		$doc_id = $row['doc_id'];
		$userid = $row['user_id'];
	}

	?> 
	<?php 
	if (isset($_POST['reject'])) 
	{
		$rejected_reason = $_POST['reason'];
		$doc_id = $_POST['doc_id'];
		$rejected_date = date("Y-m-d");
		$rejected_by = $user_id;
		$userid = mysqli_real_escape_string($database, $_POST['userid']);
		$rejectmsg = 'Adoption has been successfully rejected';
		date_default_timezone_set("Asia/Singapore");
		$date_today = date("F j, Y, g:i a");

		$reject_request = "INSERT INTO `iro_db`.`rejected_documents` (`id`, `doc_id`, `rejected_reason`, `rejected_by`, `rejected_date`) VALUES (NULL, '$doc_id', '$rejected_reason', '$rejected_by', '$rejected_date')";

		$reject_notification = mysqli_query($database, "INSERT INTO `notifications` (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$userid', '1', 'Adoption', 'Your request has been rejected', '$date_today', '0', '0')");

		if (mysqli_query($database, $reject_request)) 
		{
			$reject_document = mysqli_query($database, "UPDATE documents SET doc_status_id = 3 WHERE doc_id = $doc_id");
			echo "<script>window.location.href='admin_adoptionrequest.php?rejectmsg=$rejectmsg';</script>";
		}
	}
	 ?>

		<form method="post" action="admin_rejectadoption.php">
			<div class="form-group">
		  		<textarea class="form-control" type="text" name="rejected_reason"></textarea>
		  		<input type="hidden" name="doc_id" value="<?php echo $doc_id ?>">
		  		<input type="hidden" name="rejected_by" value="<?php echo $user_id ?>">
		  		<input type="hidden" name="userid" value="<?php echo $userid ?>">
		  	</div>
		    <div class="modal-footer">
		    	<input type="submit" name="reject" class="btn btn-success" onclick="return confirm('Are you sure you want to reject this request?');" value="Submit" style="float: right;">
		    	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
		  	</div>
		</form>

	<?php
}
 ?>