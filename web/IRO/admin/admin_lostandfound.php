 <?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Get lost and found
$lostandfound = mysqli_query($database, "SELECT * FROM reports INNER JOIN documents ON reports.report_id = documents.doc_form_id INNER JOIN file_attachments ON documents.doc_id = file_attachments.doc_id INNER JOIN users ON reports.user_id = users.user_id INNER JOIN report_type ON reports.report_type_id = report_type.report_type_id WHERE reports.report_type_id = 2 AND documents.doc_type_id = 3");
//end of code

if(isset($_GET['pageSet']))
{
  $seen = mysqli_query($database,"UPDATE notifications_admin SET is_seen = 1 WHERE is_seen = 0 AND notification_type_id = 4");
}

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}
 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Lost & Found</title>

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>
<style>
  .container {
  position: relative;
  text-align: center;
  color: white;
  width: 100%;
}
.top-right {
  position: absolute;
  top: 0px;
  right: 0px;
}
.centered {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
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
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Navbar -->
        <?php include 'navbar.php'; ?>
        <!-- End -->

        <!-- Begin Page Content -->
        <center><h1 class="h3 mb-0 text-gray-800">LOST & FOUND</h1></center>

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
                </center>
        <div class="card-body">
              <div class="container-fluid">
            <div class="container">
            <div class="row" style="width: 105%">
                <?php 
                if (mysqli_num_rows($lostandfound) == 0) 
                {
                  ?>
                  <div class="col-md-2" style="padding:6px;"></div>
                  <div class="col-md-8" style="padding:6px;">
                    <br><br><br><br>
                    <h1 align="center" class="h3 mb-0 text-gray-800">- NO REPORT HAS BEEN POSTED YET -</h1>
                  </div>
                  <div class="col-md-2" style="padding:6px;"></div>
                  <?php
                }
                else
                {
                  while ($row = mysqli_fetch_array($lostandfound)) 
                {
                    $title = $row['title'];
                    $file_name = $row['file_name'];
                    $description = $row['description'];
                    $date_reported = $row['date_reported'];
                    $user_fname = $row['user_fname'];
                    $user_lname = $row['user_lname'];
                    $reporter_location = $row['reporter_location'];
                    $doc_id = $row['doc_id'];

                    if ($row['doc_status_id'] == 1) 
                    {
                      ?>
                    <div class="col-md-6" style="padding:6px;">
                    <div class="card">
                        <div class="container">
                            <div class='container'>
                            <img class='img' src="../Android/images/attachments/<?php echo $file_name ?>" style='width: 500px; height: 300px'>
                            <div class="top-right">
                              <a href="admin_managereport.php?acknowledge=<?php echo $doc_id ?>" onclick="return confirm('Are you sure you want to acknowledge this case?')" title="Acknowledge"><i class="fa fa fa-bookmark fa-3x" style="color: blue;"></i></a>
                              <a href="admin_managereport.php?remove=<?php echo $doc_id ?>" onclick="return confirm('Are you sure you want to remove this report?')" title="Removed"><i class="fa fa fa-times fa-3x" style="color: red;"></i></a>
                            </div>
                           </div>; 
                        </div>
                         
                        <div class="card-body bg-light">
                            <center>
                            <h5 class="card-title">
                                <p class="text-dark" style="margin-bottom: auto;"><?php echo $title ?></p>
                            </h5>
                            </center>
                        </div>
                        <div class="card-footer" style="height: 200px">
                            <div class="float-left">
                                <table>
                                    <tr>
                                        <td style="padding-right: 10px; vertical-align: text-top;">Description</td>
                                        <td style="overflow:hidden;text-overflow:ellipsis; text-align: justify;"><p class="card-text" ><?php echo $description; ?>.</p></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px">Date</td><td class="text-muted"><?php echo "$date_reported"; ?></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px; vertical-align: text-top;">From</td><td class="card-text"><?php echo "$user_fname"; ?> <?php echo "$user_lname"; ?></td>    
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer">
                          <center>
                                 <p class="text-dark" href="#"><?php echo $reporter_location ?></p>
                                 <a href="http://maps.google.com/maps?q=<?=$reporter_location?>" target="_blank">Direction <i class="fa fa-location-arrow"></i></a>
                                </center>
                        </div>
                    </div>
                </div><!--.col-->
                    <?php
                    }
                    else if($row['doc_status_id'] == 5)
                    {
                      ?>
                    <div class="col-md-6" style="padding:6px;">
                    <div class="card">
                        <div class="container">
                            <div class='container'>
                            <img class='img' src="../Android/images/attachments/<?php echo $file_name ?>" style='width: 500px; height: 300px'>
                            <div class="centered">
                              <p style="color: white; font-style: italic; font-size: 36px; background-color: skyblue; opacity: 0.7">ACKNOWLEDGED</p>
                            </div>
                           </div>; 
                        </div>
                         
                        <div class="card-body bg-light">
                            <center>
                            <h5 class="card-title">
                                <p class="text-dark" style="margin-bottom: auto;"><?php echo $title ?></p>
                            </h5>
                            </center>
                        </div>
                        <div class="card-footer" style="height: 200px">
                            <div class="float-left">
                                <table>
                                    <tr>
                                        <td style="padding-right: 10px; vertical-align: text-top;">Description</td>
                                        <td style="overflow:hidden;text-overflow:ellipsis; text-align: justify;"><p class="card-text" ><?php echo $description; ?>.</p></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px">Date</td><td class="text-muted"><?php echo "$date_reported"; ?></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                      <td></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px; vertical-align: text-top;">From</td><td class="card-text"><?php echo "$user_fname"; ?> <?php echo "$user_lname"; ?></td>    
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer">
                          <center>
                                 <p class="text-dark" href="#"><?php echo $reporter_location ?></p>
                                 <a href="http://maps.google.com/maps?q=<?=$reporter_location?>" target="_blank">Direction <i class="fa fa-location-arrow"></i></a>
                                </center>
                        </div>
                    </div>
                </div><!--.col-->
                    <?php
                    }
                    
                }
                }

                 ?>
            </div><!--.row-->
        </div><!--.container-->
    </div><!--.container-fluid-->
            </div>
        <!-- /.container-fluid -->

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
            <span aria-hidden="true">×</span>
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
  <script src="vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="js/demo/datatables-demo.js"></script>

  <script src="jss/jquery-1.11.1.min.js"></script>
  <script src="jss/bootstrap.min.js"></script>
  <script src="jss/jquery.dataTables.min.js"></script>
  <script src="jss/dataTables.bootstrap4.min.js"></script>  
  <script type="text/javascript">
   $(document).ready(function() {
      $('#example').DataTable(
          
           {     

        "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
          "iDisplayLength": 5
         } 
          );
  } );


  function checkAll(bx) {
    var cbs = document.getElementsByTagName('input');
    for(var i=0; i < cbs.length; i++) {
      if(cbs[i].type == 'checkbox') {
        cbs[i].checked = bx.checked;
      }
    }
  }
  </script>
</body>

</html>
