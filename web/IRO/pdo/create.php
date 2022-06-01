<?php 	
require 'config.php';

if (isset($_POST['fname']) && isset($_POST['lname']) && isset($_POST['address']) ) 
{
	$x = $_POST['fname'];
	$y = $_POST['lname'];
	$z = $_POST['address'];
	$message ='';

	$mysqli = 'INSERT INTO employee (fname,lname,address) VALUES (:fname, :lname, :address)';

	$stmt = $db->prepare($mysqli);

	if ($stmt->execute([':fname' => $x, ':lname' => $y, ':address' => $z])) 
	{
		$message = 'New employee has been added successfully';
	}
}
 ?>

<?php require 'header.php'; ?>

<div class="container">

	<div class="card mt-5">
		<div class="card-header">
				<h2>Add Employee</h2>
		<div>
	</div>
	
	<div class="card-body">
		<?php 	if(!empty($message)): ?>
			<div class="alert alert-success">
				<?php echo "$message"; ?>
			</div>
		<?php 	endif; ?>
		<form method="post">
			<div class="form-group">
				<label>Firstname</label>
				<input type="text" name="fname" class="form-control">
			</div>

			<div class="form-group">
				<label>Lastname</label>
				<input type="text" name="lname" class="form-control">
			</div>

			<div class="form-group">
				<label>Address</label>
				<input type="text" name="address" class="form-control">
			</div>

			<div>
				<button type="submit" class="btn btn-info">Save</button>
			</div>
		</form>
	</div>
</div>
<?php require 'footer.php'; ?>