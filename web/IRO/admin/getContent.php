<?php 
include 'config.php';

if (!empty($_GET['id'])) 
{
	$id = $_GET['id'];

	$adoption_request = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE doc_id = $id");


	while ($row = mysqli_fetch_array($adoption_request)) 
	{
		$reason = $row['reason'];
		$fname = $row['user_fname'];
		$lname = $row['user_lname'];
		$pet_name = $row['pet_name'];
		$pet_age = $row['pet_age'];
		$pet_gender = $row['pet_gender'];
		$address = $row['user_address'];
		$occupation = $row['user_occupation'];
		$contact = $row['user_contact'];
		$date_submitted = $row['date_submitted'];
		$children_age = $row['children_age'];
		$pet_live_type_id = $row['pet_live_type_id'];
		$current_pet = $row['current_pet'];
		$past_pet = $row['past_pet'];
		$past_pet_details = $row['past_pet_details'];
		$have_yard = $row['have_yard'];
		$is_fenced = $row['is_fenced'];
		$have_vet = $row['have_vet'];
		$vet_name = $row['vet_name'];
		$vet_address = $row['vet_address'];
		$circumstances = $row['circumstances'];
		$is_agreed = $row['is_agreed'];
		$reason_disagree = $row['reason_disagree'];
		$home_visit_time = $row['home_visit_time'];
		$comments = $row['comments'];
		?> 

		<div id="aw" style="padding: 5px">
		<div class="container">
			<center>
				<br>
				<img src="./img/logo.png" height="10%" width="50%" alt="">
				<br><br>
				<p style="font-size: 16px">Pet Adoption Application From 2020</p>
				<hr>
			</center>
			<form method="post" action="user_adoption.php">
				
					<div class="row">
						<div class="col-sm-9">
							<p style="display: inline;"><b style="color: black">Name of Applicant:</b></p>
							<p style="display: inline; color: black"><?php echo $fname; ?> <?php echo $lname; ?></p>
						</div>
						<div class="col-sm-3">
							<p style="display: inline; color: black"><b>Date:</b></p> <p style="display: inline; color: black"><?php echo $date_submitted; ?></p>
						</div>
					</div>

					<input type="hidden" name="user_id" value="<?php echo $user_id ?>">
					<input type="hidden" name="pet_id" value="<?php echo $pet_id ?>">

					<br>

					<div class="row">
						<div class="col-sm-12">
							<p style="display: inline; color: black"><b>If you have a children, what are their ages:</b></p> 
							<p style="display: inline; color: black"><?php echo $children_age ?></p>
						</div>
					</div>

					<br>

					<div class="row">
						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Address:</b></p> 
							<p style="display: inline; color: black"><?php echo $address ?></p>
						</div>

						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Mobile:</b></p> 
							<p style="display: inline; color: black"><?php echo $contact ?></p>
						</div>

						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Occupation:</b></p> 
							<p style="display: inline; color: black"><?php echo $occupation ?></p>
						</div>
					</div>

					<br>

					<div class="row">
						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Pet Name:</b></p> 
							<p style="display: inline; color: black"><?php echo $pet_name ?></p>
						</div>

						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Age:</b></p> 
							<p style="display: inline; color: black"><?php echo $pet_age ?></p>
						</div>

						<div class="col-sm-4">
							<p style="display: inline; color: black"><b>Gender:</b></p> 
							<p style="display: inline; color: black"><?php echo $pet_gender ?></p>
						</div>
					</div>

					<br>

				<div>
					<b style="color: black">Why do you want this particular dog or cat?:</b>
					<p style="color: black">- <?php echo "$reason"; ?></p>
				</div>
				<div>
					
				</div>
				<?php 
				if ($pet_live_type_id == 2) 
				{
					?> 
					<div>
						<b style="color: black">Will the pet be an indoor or outdoor pet?</b>
						<br>
						<p style="color: black">- Inside</p>
					</div>
					<?php
				}
				else if($pet_live_type_id == 3)
				{
					?> 
					<div>
						<b style="color: black">Will the pet be an indoor or outdoor pet?</b>
						<br>
						<p style="color: black">- Outside</p>
					</div>
					<?php
				}
				else if ($pet_live_type_id == 4)
				{
					?> 
					<div>
						<b style="color: black">Will the pet be an indoor or outdoor pet?</b>
						<br>
						<p style="color: black">- Both</p>
					</div>
					<?php
				}

				 ?>

				<div>
					<b style="color: black">Do you have any others pet in the house? Please list what kind:</b>
					<br>
					<p style="color: black">- <?php echo $current_pet ?></p>
				</div>

				<div>
					<b style="color: black">If you have no pets now, have you had any in the past? When:</b>
					<br>
					<p style="color: black">- <?php echo "$past_pet"; ?></p>
				</div>

				<div>
					<b style="color: black">Please describe what happened to your petn:</b>
					<br>
					<p style="color: black">- <?php echo "$past_pet_details"; ?></p>
				</div>

				<div>
					
					<?php

					if ($have_yard == 1) 
					{
						?> 
						<div>
							<b style="color: black">Do you have backyard or front yard?:</b>
							<br>
							<p style="color: black">- Yes</p>
						</div>
						<?php
					}
					else
					{
						?> 
						<div>
							<b style="color: black">Do you have backyard or front yard:</b>
							<br>
							<p style="color: black">- No</p>
						</div>
						<?php
					}
					?>
				</div>

				<div>
					
					<?php
					if ($is_fenced == 1) 
					{
						?> 
						<div>
							<b style="color: black">Is your backyard or front yard fenced:</b>
							<br>
							<p style="color: black">- Yes</p>
						</div>
						<?php
					}
					else
					{
						?> 
						<div>
							<b style="color: black">Is your backyard or front yard fenced:</b>
							<br>
							<p style="color: black">- No</p>
						</div>
						<?php
					}
					?>
				</div>

				<div>
					<?php

					if ($have_vet == 1) 
					{
						?> 
						<div>
							<b style="color: black">If you have pets now, do you have a veterinarian:</b>
							<br>
							<p style="color: black">- Yes</p>
						</div>
						<?php
					}
					else
					{
						?> 
						<div>
							<b style="color: black">If you have pets now, do you have a veterinarian:</b>
							<br>
							<p style="color: black">- No</p>
						</div>
						<?php
					}
					?>
				</div>

				<div>
					<b style="color: black">If you have a veterinarian, please state his/her name:</b>
					<br>
					<p style="color: black">- <?php echo $vet_name ?></p>
				</div>
				
				<div>
					<b style="color: black">Address:</b>
					<br>
					<p style="color: black">- <?php echo $vet_address ?></p>
				</div>

				<div>
					<b style="color: black">What circumstances would cause you to give up a pet:</b>
					<br>
					<p style="color: black">- <?php echo "$circumstances"; ?></p>
				</div>

					<?php

					if ($is_agreed == 1) 
					{
						?>
						<div>
							<b style="color: black">IRO, Island Rescue Organization, will do a home visit as a part of the adoption evaluation. Is this agreeable to you:</b>
							<br>
							<p style="color: black">- Yes</p>
						</div>
						<?php
					}
					else
					{
						?> 
						<div>
							<b style="color: black">IRO, Island Rescue Organization, will do a home visit as a part of the adoption evaluation. Is this agreeable to you:</b>
							<br>
							<p style="color: black">- No</p>
						</div>
						<?php
					}
					?>

					<div>
					<b style="color: black">If no, please explain why:</b>
					<br>
					<p style="color: black">- <?php echo "$reason_disagree"; ?></p>
				</div>

				<div>
					<b style="color: black">If yes, please tell us when the convenient times are for a home visit:</b>
					<br>
					<p style="color: black">- <?php echo "$home_visit_time"; ?></p>
				</div>

				<div>
					<b style="color: black">Additional Comment:</b>
					<br>
					<p style="color: black">- <?php echo "$comments"; ?></p>
				</div>
				</div>

				
			</form>
		</div>

		</div>

    	<script src="js/printThis.js"></script>
		<script>
	    function printContent(el){
	      var restorepage = document.body.innerHTML;
	      var printcontent = document.getElementById(el).innerHTML;
	      document.body.innerHTML = printcontent;
	      window.print();
	      document.body.innerHTML = restorepage;
		}
   	 	</script>

		<?php
	}
}
 ?>