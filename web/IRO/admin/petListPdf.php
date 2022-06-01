<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

$petdetails = mysqli_query($database, "SELECT * FROM pets INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id ORDER BY pet_id ASC");

?>


<div id="pdf" style="padding: 5px">
<center>
	<img src="img/logo.png" width="300" height="200">
	<br>
	<br>
	<h1 class="h1 text-gray-900 mb-4">PET LIST</h1>
</center>
<table class="table table-bordered table-striped">
	<thead style="background-color: #23a6da">
 		<tr>
            <th style="color: white">Image</th>
            <th style="color: white">Name</th>
            <th style="color: white">Breed</th>
            <th style="color: white">Gender</th>
            <th style="color: white">Age</th>
            <th style="color: white">Availability</th>
      	</tr>		
	</thead>
	<tbody>
		<?php 
		while($row = mysqli_fetch_array($petdetails))
            {     
   
              ?>  
                  <tr>
                  <td><?php echo "<img src='../Android/images/adoptions/".$row['pet_image']."' width='100' height='100'>"; ?></td>
                  <td><?php echo "$row[pet_name]"; ?></td>
                  <td><?php echo "$row[pet_type_desc]" ?></td>
                  <td><?php echo "$row[pet_gender]"; ?></td>
                  <td><?php echo "$row[pet_age]"; ?></td>
                  <td><?php echo "$row[pet_status_desc]"; ?></td>
              	  </tr>
              <?php
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