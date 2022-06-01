<?php 

$get_student_details = mysqli_query($database, "SELECT * FROM student");

 ?>
<html>
<head>
	<title></title>
</head>
<body>


	</tr>
	<?php 
	while ($row = mysqli_fetch_array($get_student_details)) 
	{
		$fname = $row['firstname'];
		$fname = $row['firstname'];
		$fname = $row['firstname'];
		$fname = $row['firstname'];
		$fname = $row['firstname'];
	}
	 ?>

</form>
</body>
</html>