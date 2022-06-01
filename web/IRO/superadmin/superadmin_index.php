<?php
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user
include '../bdd.php'; //this code will allow you to ge the info of logged user


$sql = "SELECT * FROM events WHERE is_active = 1";

$req = $bdd->prepare($sql);
$req->execute();

$events = $req->fetchAll();

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

if (isset($_POST['submit']))
{
  $userid = $_SESSION['user_id'];
  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/events/".basename($image);// image file directory
  $title = mysqli_real_escape_string($database, $_POST['title']);
  $description = mysqli_real_escape_string($database, $_POST['description']);
  $venue = mysqli_real_escape_string($database, $_POST['venue']);
  $color = mysqli_real_escape_string($database, $_POST['color']);
  $start = mysqli_real_escape_string($database, $_POST['start']);
  $end = mysqli_real_escape_string($database, $_POST['end']);
  $time_start = date('h:i A', strtotime($_POST['time_start']));
  $time_end = date('h:i A', strtotime($_POST['time_end']));
  date_default_timezone_set("Asia/Singapore");
  $date_posted = date("F j, Y, g:i a");
  $time_errors = '';
  $time_error = '';
  $success = '';
  $error_start = '';
  $error_end = '';
  $error = '';

  $getUsers = mysqli_query($database, "SELECT * FROM users INNER JOIN user_roles ON users.user_id = user_roles.user_id INNER JOIN roles ON user_roles.role_id = roles.role_id WHERE roles.role_id = 1");

  $starts = date('Y-m-d', strtotime($start));
  $ends = date('Y-m-d', strtotime($end));
  $new_time = date('H:i s', strtotime($time_start));
  $new_end = date('H:i s', strtotime($time_end));
  
  while ($row = mysqli_fetch_array($getUsers))
  {
    $user_id = $row['user_id'];
    date_default_timezone_set("Asia/Singapore");
    $date_today = date("F j, Y, g:i a");

    $send_notification = mysqli_query($database, "INSERT INTO notifications (`notification_id`, `user_id`, `notification_type_id`, `subject`, `content`, `date`, `is_seen`, `is_seen2`) VALUES (NULL, '$user_id', '2', 'Event', 'New event has been added', '$date_today', '0', '0')");
  }

  if ($starts < date('Y-m-d')) 
  {
    $error_start ='Previous date is not allowed on event date start';
  }
  else if($ends < date('Y-m-d'))
  {
    $error_end ='Previous date is not allowed on event date end';
  }
  else if($ends < $starts)
  {
    $error ='End date should not be earlier than start date';
  }
  else if($new_end < $new_time)
  {
    $time_error ='End time should not be earlier than start time';
  }
  else if($new_end == $new_time)
  {
    $time_errors ='Start time and end time should not the same';
  }
  else
  {
    $add_event = "INSERT INTO `events` (`id`,`user_id`, `image`, `title`,`desc`, `venue`, `color`, `start`, `end`, `time_start`, `time_end`,`date_posted`, `is_active`) VALUES (NULL, '$userid','$image', '$title', '$description', '$venue', '$color', '$start', '$end', '$time_start', '$time_end', '$date_posted', '1');";

    if (mysqli_query($database, $add_event))
    {
      if (move_uploaded_file($_FILES['image']['tmp_name'], $target))
        {
          $success ='New event has been added successfully';
          echo "<script>window.location.href='cityvet_index.php?success=$success';</script>";
        }
    }
  }

  

}
//end of code
 ?>


<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Home</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">
  <link href='css/fullcalendar.css' rel='stylesheet' />
</head>
<style type="text/css">
  a.custom-card,
  a.custom-card:hover {
    color: inherit;
  }
</style>
 <style>
  #calendar {
    max-width: 800px;
  }
  .col-centered{
    float: none;
    margin: 0 auto;
  }
    </style>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Side Bar -->
    <?php 
    if (isset($_GET['collapse']))
    {
      
    }
    else if(isset($_GET['show']))
    {
      include 'sidebar.php'; 
    }
    else
    {
      include 'sidebar.php'; 
    }
    ?>


    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Navbar -->
        <?php include 'navbar.php'; ?>
        <!-- End -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Home</h1>
          </div>

          

                <script>
                setTimeout(function(){
                  document.getElementById('info-message').style.display = 'none';
                }, 5000);
                </script>
                <center>
                  <?php   if(!empty($success)): ?>
                    <div class="alert alert-success" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$success</span>"; // success message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error_start)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$error_start</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error_end)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$error_end</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$error</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($time_error)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$time_error</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($time_errors)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$time_errors</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>

          <div class="row">

            <div class="col-xl-4 col-lg-4">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Event Legend</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body" style="overflow-y: auto; max-height: 300px">
                  <center><h1>Legend</h1></center>
                <table class="table">
                  <thead>
                    <tr>
                      <th>Event ID</th>
                      <th>Event Color</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    <?php
                    if (isset($_GET['id']))
                    {
                      $id = $_GET['id'];
                      $aws = mysqli_query($database, "SELECT * FROM events WHERE id = $id");
                      while ($d = mysqli_fetch_array($aws)) {
                        $title = $d['title'];
                        $venue = $d['venue'];
                        $start = $d['start'];
                        $arr = explode("/", $start);
                        $new_start = date("m/d/Y",strtotime($start));

                        $end = $d['end'];
                        $arr = explode("/", $end);
                        $new_end = date("m/d/Y",strtotime($end));

                        $timestart = $d['time_start'];
                        $timeend = $d['time_end'];
                      }
                    }
                     ?>
                    <form method="post">
                     <?php

                    $aw = mysqli_query($database, "SELECT * FROM events WHERE is_active = 1");
                    while ($row = mysqli_fetch_array($aw))
                    {
                      $id = $row['id'];
                      echo "<tr>";
                      echo "<td>".$row['id']."</td>";
                      echo "<td><div style='background-color: ".$row['color']."; width: 100px'>&nbsp;</div></td>";
                      echo "<td><a class='btn btn-outline-primary btn-sm' href='cityvet_index.php?id=$id'><i class='fa fa-sm fa-eye'></i></a></td>";
                      echo "</tr>";



                    } ?>
                  </tbody>

                </table>
                </div>
                </div>

                <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary" style="max-width: 500px">Event Details</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                <table class="table table-borderless" style="table-layout: fixed; width: 100%">
                  <tbody>
                    <?php
                      if (!isset($_GET['id'])) {

                      }
                      else
                      {
                         ?>
                        <tr>
                            <td style="width: 85px">Title: <td><?php echo "$title"; ?></td></td>
                        </tr>
                            <td style="width: 85px">Venue: <td><?php echo "$venue"; ?></td></td>
                        </tr>
                        <tr>
                            <td style="width: 85px">Date: </td><td style="word-wrap: break-word;"><?php echo "$new_start"; ?> - <?php echo "$new_end"; ?></td>
                        </tr>
                        <tr>
                            <td style="width: 85px">Time: <td><?php echo "$timestart"; ?> - <?php echo "$timeend"; ?></td></td>
                        </tr>
                        <?php
                      }

                     ?>

                  </tbody>

                </table>
                </form>
                </div>
                </div>
              </div>

            <div class="col-xl-8 col-lg-8">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Event Calendar</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <div class="container">
                    <div class="row">
                      <div class="col-lg-12 text-center animated fadeInUp">
                          <h1>Event Calendar</h1>
                          <div id="calendar" class="col-centered " style="background:#fafafa;">
                          </div>
                      </div>
                    </div>
                  </div>
                </div>
      </div>
    </div>

    </div>
              </div>
            </div>




        </div>
        <!-- /.container-fluid -->

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add Event</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                  <form method="post" action="cityvet_index.php" enctype="multipart/form-data">
                  <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Event Picture</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01" required>
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Title</h1>
                    <input type="text" class="form-control" name="title" required value="<?= isset($_POST['title']) ? $_POST['title'] : ''; ?>">
                  </div>
                </div>
                 <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Description</h1>
                    <textarea class="form-control" name="description" rows="5" required value="<?= isset($_POST['description']) ? $_POST['description'] : ''; ?>"></textarea>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Venue</h1>
                    <input type="text" class="form-control" name="venue" required value="<?= isset($_POST['venue']) ? $_POST['venue'] : ''; ?>">
                  </div>
                </div>
                 <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Color</h1>
                    <select class="form-control" name="color" required>
                      <option value="">Choose</option>
                      <option style="color:#0071c5;" class="fa fa-box" value="#0071c5">&#9724; Dark blue</option>
                      <option style="color:#40E0D0;" class="fa fa-box" value="#40E0D0">&#9724; Turquoise</option>
                      <option style="color:#008000;" class="fa fa-box" value="#008000">&#9724; Green</option>
                      <option style="color:#FFD700;" class="fa fa-box" value="#FFD700">&#9724; Yellow</option>
                      <option style="color:#FF8C00;" class="fa fa-box" value="#FF8C00">&#9724; Orange</option>
                      <option style="color:#FF0000;" class="fa fa-box" value="#FF0000">&#9724; Red</option>
                      <option style="color:#000;" class="fa fa-box" value="#000">&#9724; Black</option>
                    </select>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Date Start</h1>
                    <input type="date" class="form-control" name="start" onkeydown="return false" required value="<?= isset($_POST['start']) ? $_POST['start'] : ''; ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Date End</h1>
                    <input type="date" class="form-control" name="end" onkeydown="return false" required value="<?= isset($_POST['end']) ? $_POST['end'] : ''; ?>">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Time Start</h1>
                    <input type="time" class="form-control" name="time_start" required value="<?= isset($_POST['time_start']) ? $_POST['time_start'] : ''; ?>"> 
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Time End</h1>
                    <input type="time" class="form-control" name="time_end" required value="<?= isset($_POST['time_end']) ? $_POST['time_end'] : ''; ?>">
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-outline-success" name="submit" value="Submit">
              </div>
               </form>
            </div>
          </div>
        </div>
        <script>
          $(document).on('change', '.custom-file-input', function (event) {
          $(this).next('.custom-file-label').html(event.target.files[0].name);
          })
        </script>

      </div>

      <!-- Modal -->
        <div class="modal fade" id="rescueModal" tabindex="-1" role="dialog" aria-labelledby="rescueModal" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="rescueModal">Rescue Option</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="row">
                  <div class="col-xl-6 col-lg-6">
                    <a href="admin_animalcruelty.php" class="custom-card">
                      <div class="card border-left-success shadow h-100 py-2" style="width: 14rem; display: inline-block">
                        <div class="card-body">
                          <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                              <div class="text-xs font-weight-bold text-success text-uppercase mb-1" href="admin_animalcruelty.php" >Animal Cruelty</div>
                              <div class="h3 mb-0 font-weight-bold text-gray-800"><div id="cruelty"></div></div>
                            </div>
                            <div class="col-auto">
                              <i class="fa fa-paper-plane fa-2x text-gray-300"></i>
                            </div>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>

                  <div class="col-xl-6 col-lg-6">
                    <a href="admin_homelesspet.php" class="custom-card">
                      <div class="card border-left-success shadow h-100 py-2" style="width: 14rem; display: inline-block">
                        <div class="card-body">
                          <div class="row no-gutters align-items-center">
                            <div class="col mr-2">
                              <div class="text-xs font-weight-bold text-success text-uppercase mb-1" href="admin_animalcruelty.php" >Homeless Pet</div>
                              <div class="h3 mb-0 font-weight-bold text-gray-800"><div id="homeless"></div></div>
                            </div>
                            <div class="col-auto">
                              <i class="fa fa-paper-plane fa-2x text-gray-300"></i>
                            </div>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Team Redeemers 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">X</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="../logout.php">Logout</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="js/demo/chart-area-demo.js"></script>
  <script src="js/demo/chart-pie-demo.js"></script>

</body>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("adoptedpet").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getAdoptedPet.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("users").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getUsers.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("availablepet").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getAvailablePet.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("adoptionrequest").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getAdoptionRequest.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("volunterrequest").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getVolunteerRequest.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("totaldonation").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getTotalDonation.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("rescue").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getRescue.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("cruelty").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getAnimalCruelty.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("lostandfound").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getLostAndFound.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>

</script>

<script type="text/javascript">
 function loadDoc() {


  setInterval(function(){

   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("homeless").innerHTML = this.responseText;
    }
   };
   xhttp.open("GET", "getHomelessPet.php", true);
   xhttp.send();

  },100);


 }
 loadDoc();
</script>



    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

  <!-- FullCalendar -->
  <script src='js/moment.min.js'></script>
  <script src='js/fullcalendar.min.js'></script>

  <script>

  $(document).ready(function() {

    $('#calendar').fullCalendar({
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month'
      },
      defaultDate: new Date(),
      editable: false,
      eventLimit: true, // allow "more" link when too many events
      selectable: true,
      selectHelper: true,

      events: [
      <?php foreach($events as $event):

        $start = explode(" ", $event['start']);
        $end = explode(" ", $event['end']);
        if($start[1] == '00:00:00'){
          $start = $start[0];
        }else{
          $start = $event['start'];
        }
        if($end[1] == '00:00:00'){
          $end = $end[0];
        }else{
          $end = $event['end'];
        }
      ?>
        {
          id: '<?php echo $event['id']; ?>',
          title: '<?php echo $event['id']; ?>',
          start: '<?php echo $start; ?>',
          end: '<?php echo $end; ?>',
          color: '<?php echo $event['color']; ?>',
        },
      <?php endforeach; ?>
      ]
    });



  });

</script>

</html>
