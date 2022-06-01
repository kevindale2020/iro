<?php 
include '../config.php'; //this code will allow you to connect to your database

if (isset($_GET['doc_id']))
{
	$doc_id = $_GET['doc_id'];

$getattachments = mysqli_query($database, "SELECT * FROM documents INNER JOIN file_attachments ON documents.doc_id = file_attachments.doc_id WHERE file_attachments.doc_id = $doc_id");
                   
	
}
?>

<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
	<?php 
	while ($data = mysqli_fetch_array($getattachments)) 
	{                
		$path = $data['file_path'];
		$filename = $data['file_name'];

		?>
		<td><a download="<?php echo $filename ?>" target="_blank" href="../Android/<?php echo $path ?>"><i class="form download fa fa-download"></i></a> - <a target="_blank" href="../Android/<?php echo $path ?>"><?php echo "$filename"; ?></a><br></td>
		<?php
	}
	 ?>

</body>
</html>

	
                    

