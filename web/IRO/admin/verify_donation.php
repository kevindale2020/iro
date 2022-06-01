<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['donation_id'])) 
{
  $donation_id = $_GET['donation_id'];
  $success = "Donations verified successfully";
  $date_verified = date('Y-m-d');

  $details = mysqli_query($database, "SELECT * FROM donations WHERE donation_id = $donation_id");

  $res = mysqli_fetch_array($details);

  $userid = $res['user_id'];
  $date_today = date("F j, Y, g:i a");

  $verify = "UPDATE donations SET is_verified = 1, date_verified = '$date_verified', verified_by = $user_id WHERE donation_id = $donation_id";

  $send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$userid', '7', 'Donation', 'Your donation has been verified. Thank you for supporting our organization.', '$date_today', '0', '0')");

  if (mysqli_query($database, $verify)) 
  {
    echo "<script>window.location.href='admin_donationrequest.php?success=$success'</script>";
  }
}


if (isset($_GET['inform'])) 
{
  $donation_id = $_GET['inform'];
  $success = "Donor has been informed successfully";
  $date_verified = date('Y-m-d');

  $details = mysqli_query($database, "SELECT * FROM donations WHERE donation_id = $donation_id");

  $res = mysqli_fetch_array($details);

  $userid = $res['user_id'];
  $date_today = date("F j, Y, g:i a");

  $reject_donation = "DELETE FROM donations WHERE donation_id = $donation_id";

  $send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$userid', '7', 'Donation', 'Sorry your donation has been rejected due to incomplete details. Please resubmit complete donation details. Thank you!', '$date_today', '0', '0')");

  if (mysqli_query($database, $reject_donation)) 
  {
    echo "<script>window.location.href='admin_donationrequest.php?success=$success'</script>";
  }
}
 ?>