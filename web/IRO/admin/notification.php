<?php
if(isset($_GET['pageSet']))
{
  $seen = mysqli_query($database,"UPDATE notifications SET is_seen_admin = 1 WHERE is_seen_admin = 0");
}
?>