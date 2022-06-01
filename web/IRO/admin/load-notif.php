<?php
include '../config.php'; //this code will allow you to connect to your database

$sql = "SELECT * FROM notifications_admin INNER JOIN users ON notifications_admin.user_id = users.user_id
        INNER JOIN notification_type ON notification_type.notification_type_id = notifications_admin.notification_type_id WHERE notifications_admin.is_seen = 1 OR notifications_admin.is_seen = 0 ORDER BY notifications_admin.notification_id DESC";

$result = mysqli_query($database, $sql);

if (mysqli_num_rows($result) > 0) {
  while ($row = mysqli_fetch_array($result))
  {
    $is_seen = $row['is_seen'];
    $notification_id = $row['notification_id'];
    $date = $row['date'];
    $new=date_create($date);

    if ($is_seen == 0) {
      ?>
        <style type="text/css">
           .container {
              position: relative;
              color: white;
              width: 100%;
              padding-left: 0px;
              padding-right: 0px;
            }
            .top-right {
              position: absolute;
              top: 5px;
              right: 10px;
            }
            .centered {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
            }
        </style>

        <div class='container'>
        <div class="row">
          <div class="col-sm-12">
             <?php
              if ($row['notification_type_id'] == 1)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 3)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_volunteerrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 4)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_lostandfound.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 5)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_homelesspet.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 6)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_animalcruelty.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 7)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_donationrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 8)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageset=true'>";
              }
               ?>
        <div class="mr-3">
          <div class="icon-circle bg-primary">
            <i class="fas fa-envelope text-white"></i>
          </div>
        </div>
        <div>
        <div class="small text-gray-500"><?php echo date_format($new, "F j, Y, g:i a"); ?> </div>
        <span class="font-weight-bold"><?php echo "$row[subject]"; ?></span>
        <br>
        <span class="font-weight"><?php echo "$row[content]"; ?></span>

        <br><br>
        <div class="small text-gray-500">- <?php echo "$row[user_fname]"; ?> <?php echo "$row[user_lname]"; ?></div>
      </div>
      </a>
          </div>
        </div>
      <?php
    }
    else
    {
      ?>
      <style type="text/css">
           .container {
              position: relative;
              color: white;
              width: 100%;
              padding-left: 0px;
              padding-right: 0px;
            }
            .top-right {
              position: absolute;
              top: 5px;
              right: 10px;
            }
            .centered {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
            }
        </style>
        
      <div class='container'>
        <div class="row">
          <div class="col-sm-12">
             <?php
              if ($row['notification_type_id'] == 1)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 3)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_volunteerrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 4)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_lostandfound.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 5)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_homelesspet.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 6)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_animalcruelty.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 7)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_donationrequest.php?pageSet=true'>";
              }
              else if($row['notification_type_id'] == 8)
              {
                echo "<a class='dropdown-item d-flex align-items-center' href='admin_adoptionrequest.php?pageset=true'>";
              }
               ?>
        <div class="mr-3">
          <div class="icon-circle bg-primary">
            <i class="fas fa-envelope-open text-white"></i>
          </div>
        </div>
        <div>
        <div class="small text-gray-500"><?php echo date_format($new, "F j, Y, g:i a"); ?> </div>
        <span class="font-weight-bold"><?php echo "$row[subject]"; ?></span>
        <br>
        <span class="font-weight"><?php echo "$row[content]"; ?></span>

        <br><br>
        <div class="small text-gray-500">- <?php echo "$row[user_fname]"; ?> <?php echo "$row[user_lname]"; ?></div>
      </div>
      </a>
          </div>
        </div>
         <div class="top-right">
          <a href="remove_seen.php?notification_id=<?php echo $notification_id ?>" onclick="return confirm('Are you sure you want to delete this notification?')"><i class="fa fa-window-close" style="color: red"></i></a>
         </div>
        </div>
        <?php
    }
  }
}
else
{
  echo "<br><center><span class='font-weight text-gray-500'>No notification found</span></center><br>";
}
  ?>
