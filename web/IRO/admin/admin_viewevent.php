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

if (isset($_GET['error'])) 
{
  $error = $_GET['error'];
}

$role = mysqli_query($database, "SELECT * FROM user_roles WHERE user_id = $user_id");
while ($aw = mysqli_fetch_array($role)) 
{
  $role_id = $aw['role_id'];
}

//Add event
if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

if (isset($_POST['submit'])) 
{
  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/events/".basename($image);// image file directory
  $title = mysqli_real_escape_string($database, $_POST['title']);
  $venue = mysqli_real_escape_string($database, $_POST['venue']);
  $color = mysqli_real_escape_string($database, $_POST['color']);
  $start = mysqli_real_escape_string($database, $_POST['start']);
  $end = mysqli_real_escape_string($database, $_POST['end']);
  $time_start = date('h:i A', strtotime($_POST['time_start']));
  $time_end = date('h:i A', strtotime($_POST['time_end']));
  $success ='New event has been added successfully';

  $add_event = "INSERT INTO `events` (`id`, `image`, `title`, `venue`, `color`, `start`, `end`, `time_start`, `time_end`, `is_active`) VALUES (NULL, '$image', '$title', '$venue', '$color', '$start', '$end', '$time_start', '$time_end', '1');";

  if (mysqli_query($database, $add_event)) 
  {
    if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
      {
        echo "<script>window.location.href='admin_index.php?success=$success';</script>";
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
            <h1 class="h3 mb-0 text-gray-800">Events</h1> 


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


                  <?php   if(!empty($error)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php
                      echo "<span style='font-size: 16px'>$error</span>"; // success message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>
          
            <style>
            .crop {
             height: 200px;
             width: 300px;
             overflow: hidden;
            }
            .crop img {
             height: auto;
             width: 400px;
            }
            </style>
            <div class="col-xl-12 col-lg-12">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Events List</h6>
                
                <?php 
                  $get_events = mysqli_query($database, "SELECT * FROM events INNER JOIN user_roles ON events.user_id = user_roles.user_id WHERE events.is_active = 1 ORDER BY id DESC");
                 ?>

                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <div class="container">
                    <div class="row">
                      <div class="col-lg-12 text-center animated fadeInUp" style="overflow-y: auto; max-height: 320px">
                         <div class="row">
                            <?php 

                            while ($row = mysqli_fetch_array($get_events)) 
                            {
                              $start = $row['start'];
                              $arr = explode("/", $start);
                              $new_start = date("F d, Y",strtotime($start));

                              $end = $row['end'];
                              $arr = explode("/", $end);
                              $new_end = date("F d, Y",strtotime($end));

                              $timestart = $row['time_start'];
                              $timeend = $row['time_end'];
                              ?>
                              <div class="col-lg-4">
                                <?php echo "<img src='../Android/images/events/".$row['image']."' class='crop'>"; ?>
                                <br><br>
                              </div>    
                               <script>
                                $(document).ready(function(){
                                    $('.openPopup').on('click',function(){
                                        var dataURL = $(this).attr('data-href');
                                        $('.modal-body').load(dataURL,function(){
                                            $('#myModal').modal({show:true});
                                        });
                                    }); 
                                });   
                                </script>
                              <div class="col-lg-8">
                                <table class="table table-borderless">
                                  <tr>
                                    <td width="100px">Title: </td>
                                    <td style="float: left;"><?php echo "$row[title]"; ?><span style="padding-right: 30px"></span>
                                      <?php 
                                      if ($row['role_id'] == $role_id) 
                                      {
                                        echo "<a href='admin_editevent.php?id=$row[id]' title='Edit' class='btn-outline-success btn-sm openPopup'><i class='fa fa-edit'></i>

                                        </a> | <a href='admin_deleteevent.php?id=$row[id]' onclick=\"return confirm('Are you sure you want to remove this event?')\" class='btn-outline-danger btn-sm'><i class='fa fa-trash'></i></a>";
                                      }
                                      else
                                      {

                                      }

                                       ?></td>
                                  </tr>
                                   <tr>
                                    <td width="100px">Description: </td><td style="float: left; text-align: justify;"><?php echo "$row[desc]"; ?></td>
                                  </tr>
                                  <tr>
                                    <td style="padding-right: 10px">Venue: </td><td style="float: left;"><?php echo "$row[venue]"; ?></td>
                                  </tr>
                                   <tr>
                                    <td style="padding-right: 10px">Date: </td><td style="float: left;"><?php echo $new_start; ?> - <?php echo $new_end ?></td>
                                  </tr>
                                   <tr>
                                    <td style="padding-right: 10px">Time: </td><td style="float: left;"><?php echo $timestart; ?> - <?php echo $timeend ?></td>
                                  </tr>
                                </table>
                                <hr>
                              </div>          
                              <?php
                            }

                             ?>                 
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
        
        <div class="modal fade" id="myModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Edit Pet Details</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <!-- Modal body -->
            <div class="modal-body">
              <!-- In this area will display the dynamic modal content -->
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
