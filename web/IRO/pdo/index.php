<?php 
require 'config.php';

$sql = 'SELECT * FROM employee';

$stmt = $db->prepare($sql);
$stmt->execute();

$row = $stmt->fetchAll(PDO::FETCH_OBJ);
 ?>

<?php require 'header.php'; ?>
<div class="container">
	<div class="card mt-5">
		<div class="card-header">
			<h2>List of Employee</h2>
		</div>

		<div class="card-body">
			<table class="table table-bordered">
				<tr>
					<th>Firstname</th>
					<th>Lastname</th>
					<th>Address</th>
					<th>Action</th>
				</tr>
				<?php foreach($row as $row): ?>
					<tr>
						<td><?= $row->fname; ?></td>
						<td><?= $row->lname; ?></td>
						<td><?= $row->address; ?></td>
						<td>
							<a href="edit.php?id=<?= $row->id ?>" class="btn btn-info">Edit</a>
							<a href="delete.php?id=<?= $row->id ?>" class="btn btn-danger">Delete</a>
						</td>
					</tr>
				<?php endforeach; ?>
			</table>
		</div>
	</div>
</div>
<?php require 'footer.php'; ?>