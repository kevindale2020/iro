<?php 
include '../config.php'; //this code will allow you to connect to your database

$sql = "SELECT * FROM notifications INNER JOIN notification_type ON notifications.notification_type_id = notification_type.notification_type_id INNER JOIN users ON notifications.user_id = users.user_id WHERE notifications.is_seen_admin = 0 OR notifications.is_seen_admin = 1 ORDER BY notifications.notification_id DESC LIMIT 5";

$result = mysqli_query($database, $sql);

if (mysqli_num_rows($result) > 0) {
  while ($row = mysqli_fetch_array($result)) 
  {
    $is_seen_admin = $row['is_seen_admin'];
    $date = $row['date'];
    $new=date_create($date);

    if ($is_seen_admin == 0) {
      ?>
      <?php 
      if ($row['notification_type_id'] == 1) 
      {
        echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageSet=true'>";
      }
       ?>
        <div class="mr-3">
          <div class="icon-circle bg-primary">
            <i class="fas fa-envelope text-white"></i>
          </div>
        </div>
        <div>
        <div class="small text-gray-500"><?php echo date_format($new, "l, F d, Y"); ?></div>
        <span class="font-weight-bold"><?php echo "$row[subject]"; ?></span>
        <br>
        <span class="font-weight"><?php echo "$row[content]"; ?></span>

        <br><br>
        <div class="small text-gray-500">- <?php echo "$row[user_fname]"; ?> <?php echo "$row[user_lname]"; ?></div>
      </div>
      </a>
      <?php
    }
    else
    {
     ?>
      <?php 
      if ($row['notification_type_id'] == 1) 
      {
        echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageSet=true'>";
      }
      ?>
        <div class="mr-3">
          <div class="icon-circle bg-primary">
            <i class="fas fa-envelope-open text-white"></i>
          </div>
        </div>
        <div>
        <div class="small text-gray-500"><?php echo date_format($new, "l, F d, Y"); ?></div>
        <span class="font-weight-bold"><?php echo "$row[subject]"; ?></span>
        <br>
        <span class="font-weight"><?php echo "$row[content]"; ?></span>

        <br><br>
        <div class="small text-gray-500">- <?php echo "$row[user_fname]"; ?> <?php echo "$row[user_lname]"; ?></div>
      </div>
      </a>
      <?php
    }

  }
}
else
{
  echo "<br><center><span class='font-weight text-gray-500'>No notification found</span></center><br>";
}
  ?>

  