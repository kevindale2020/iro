<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Get all the adopted pets
$adoptedpets = mysqli_query($database, "SELECT pets.pet_image, pets.pet_name, pet_type.pet_type_desc, users.user_fname, users.user_lname, approved_documents.approved_date, approved_documents.approved_by FROM adoptions INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN approved_documents ON documents.doc_id = approved_documents.doc_id WHERE pets.pet_status_id = 2 AND documents.doc_status_id = 2");

$approver = mysqli_query($database, "SELECT * FROM users INNER JOIN approved_documents ON users.user_id = approved_documents.approved_by");
//end of code

?>

<style>
  .aw{
  background-color: #23a6da; 
  }
</style>
<div id="pdf" style="padding: 5px">
<center>
	<img src="img/logo.png" width="300" height="200">
	<br>
	<br>
	<h1 class="h1 text-gray-900 mb-4">LIST OF ADOPTED PETS</h1>
</center>
<table class="table table-bordered table-striped">
	<thead class="aw">
 		<tr>
      <th style="color: white">Pet Image</th>
      <th style="color: white">Pet Name</th>
      <th style="color: white">Pet Breed</th>
      <th style="color: white">Adopted By</th>
      <th style="color: white">Approved By</th>
      <th style="color: white">Date Adopted</th>
    </tr>		
	</thead>
	<tbody>
		<?php
    while($row = mysqli_fetch_array($adoptedpets))
    {

                      
      $approver = $row['approved_by'];
      echo "<tr>";
      echo "<td><img src='../Android/images/adoptions/".$row['pet_image']."' width='100' height='100'></td>";
      echo "<td>".$row['pet_name']."</td>";
      echo "<td>".$row['pet_type_desc']."</td>";
      echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";  

      $query = mysqli_query($database, "SELECT * FROM users WHERE user_id = $approver");
      $data=mysqli_fetch_assoc($query);
      $approved_by_fname = $data['user_fname'];
      $approved_by_lname = $data['user_lname'];

      echo "<td>".$approved_by_fname." ".$approved_by_lname."</td>";
      echo "<td>".$row['approved_date']."</td>";
      echo "</tr>";
    }
    ?>
	</tbody>
</table>
</div>

<script src="js/printThis.js"></script>
<script>
function printContent(el)
{
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
</script>

<?php

 ?>