<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user
include '../bdd.php'; //this code will allow you to ge the info of logged user

//Add event
if (isset($_GET['success']))
{
  $success = $_GET['success'];
}

if (isset($_POST['save']))
{
  $file_type = $_FILES['image']['type'];
  $allowed = array("image/jpeg", "image/gif", "image/png");
  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/events/".basename($image);// image file directory
  $title = mysqli_real_escape_string($database, $_POST['title']);
  $description = mysqli_real_escape_string($database, $_POST['description']);
  $venue = mysqli_real_escape_string($database, $_POST['venue']);
  $start = date('Y-m-d', strtotime($_POST['start']));
  $end = date('Y-m-d', strtotime($_POST['end']));
  $time_start = date('h:i A', strtotime($_POST['time_start']));
  $time_end = date('h:i A', strtotime($_POST['time_end']));
  $id = mysqli_real_escape_string($database, $_POST['id']);
  $success ='Event has been updated successfully';
  $time_errors = '';
  $time_error = '';
  $success = '';
  $error_start = '';
  $error_end = '';
  $error = '';

  $starts = date('Y-m-d', strtotime($start));
  $ends = date('Y-m-d', strtotime($end));
  $new_time = date('H:i s', strtotime($time_start));
  $new_end = date('H:i s', strtotime($time_end));

  if (empty($image) || $image == '') 
  {
     if($ends < $starts)
    {
      $error ='End date should not be earlier than start date';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if($new_end < $new_time)
    {
      $error ='End time should not be earlier than start time';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if($new_end == $new_time)
    {
      $error ='Start time and end time should not the same';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if(!in_array($file_type, $allowed))
    {
      $error = 'Only jpg, gif and png files are allowed.';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else
    {
      $update_event = "UPDATE `events` SET `title`= '$title', `desc` = '$description', `venue` = '$venue', `start` = '$start', `end` = '$end',`time_start` = '$time_start', `time_end` = '$time_end' WHERE id = $id";

      if (mysqli_query($database, $update_event))
      {
            echo "<script>window.location.href='admin_viewevent.php?success=$success';</script>";
      }
    }
  }
  else
  {
    if($ends < $starts)
    {
      $error ='End date should not be earlier than start date';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if($new_end < $new_time)
    {
      $error ='End time should not be earlier than start time';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if($new_end == $new_time)
    {
      $error ='Start time and end time should not the same';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else if(!in_array($file_type, $allowed))
    {
      $error = 'Only jpg, gif and png files are allowed.';
      echo "<script>window.location.href='admin_viewevent.php?error=$error';</script>";
    }
    else
    {
      $update_event = "UPDATE `events` SET `image`= '$image', `title`= '$title', `desc` = '$description', `venue` = '$venue', `start` = '$start', `end` = '$end',`time_start` = '$time_start', `time_end` = '$time_end' WHERE id = $id";

      if (mysqli_query($database, $update_event))
      {
        if (move_uploaded_file($_FILES['image']['tmp_name'], $target))
          {
            echo "<script>window.location.href='admin_viewevent.php?success=$success';</script>";
          }
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
            <h1 class="h3 mb-0 text-gray-800">Events</h1> 
          </div>

          
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
            <form method="post" action="admin_editevent.php" enctype="multipart/form-data">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Edit Event</h6>
                  

                  <div><button type="submit" class="btn btn-outline-primary fa fa-save" name="save"></button>&nbsp;<button type="submit" class="btn btn-outline-danger fa fa-window-close" name="save"></button></div>
                  
                <?php 

                if (isset($_GET['id'])) 
                {
                  $id = $_GET['id'];

                  $get_events = mysqli_query($database, "SELECT * FROM events WHERE id = $id");
                }
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
                              $new_start = date("m/d/Y",strtotime($start));

                              $end = $row['end'];
                              $arr = explode("/", $end);
                              $new_end = date("m/d/Y",strtotime($end));

                              $timestart = $row['time_start'];
                              $timeend = $row['time_end'];
                              ?>
                              <div class="col-lg-4">
                                <?php echo "<img src='../Android/images/events/".$row['image']."' class='crop'>"; ?>
                                <br><br>
                                <input type="file" name="image">
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
                                    <td style="float: left;"><input type="text" required="" class="form-control" name="title" value="<?php echo $row['title']; ?>"></td>
                                    <input type="hidden" name="id" value="<?php echo $id ?>">
                                  </tr>
                                   <tr>
                                    <td width="100px">Description: </td>
                                    <td><textarea class="form-control" rows="5" required="" name="description"><?php echo $row['desc'] ?></textarea></td>
                                  </tr>
                                  <tr>
                                    <td style="padding-right: 10px">Venue: </td>
                                    <td style="float: left;"><input type="text" required="" name="venue" class="form-control" value="<?php echo $row['venue'] ?>" ></td>
                                  </tr>

                                  <!-- Datepicker -->
                                  
                                   <tr>
                                    <td style="padding-right: 10px">Date Start: </td>
                                    <td style="float: left;">
                                    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
                                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
                                    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
                                    <!-- end -->
                                    <input id="datepicker" class="form-control" type="text" required="" name="start" readonly='true' value="<?php echo $new_start ?>" />
                                    <script>
                                        $('#datepicker').datepicker({
                                            uiLibrary: 'bootstrap4'
                                        });
                                    </script>
                                  </tr>

                                   <tr>
                                    <td style="padding-right: 10px">Date End: </td>
                                    <td style="float: left;">
                                    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
                                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                                    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
                                    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
                                    <!-- end -->
                                    <input id="datepicker2" class="form-control" required="" type="text" name="end" readonly='true' value="<?php echo $new_end ?>"/>
                                    <script>
                                        $('#datepicker2').datepicker({
                                            uiLibrary: 'bootstrap4'
                                        });
                                    </script>
                                  </tr>
                                   <tr>
                                    <td style="padding-right: 10px">Time Start: </td>
                                    <td style="float: left;"><input type="time" required="" name="time_start" value=<?php echo $timestart ?> class="form-control"></td>
                                  </tr>

                                   <tr>
                                    <td style="padding-right: 10px">Time End: </td>
                                    <td style="float: left;"><input type="time" required="" name="time_end" value=<?php echo $timeend ?> class="form-control"></td>
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
    </form>
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
