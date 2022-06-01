<?php 
//Get Logged In Information
$user_id = $_SESSION['user_id'];
$information = mysqli_query($database, "SELECT * FROM users WHERE user_id = $user_id");

while ($data = mysqli_fetch_array($information)) 
{
  $user_id = $data['user_id'];
  $user_fname = $data['user_fname'];
  $user_lname = $data['user_lname'];
  $nname = $data['user_nname'];
  $occupation = $data['user_occupation'];
  $address = $data['user_address'];
  $email = $data['user_email'];
  $contact = $data['user_contact'];
  $username = $data['user_username'];
  $user_image = $data['user_image'];
  $password = $data['user_password'];
}
//End of Code
 ?>