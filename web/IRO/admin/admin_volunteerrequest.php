<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user


//Get Adoption Request
$volunteer_request = mysqli_query($database, "SELECT * FROM volunteer INNER JOIN users ON volunteer.user_id = users.user_id INNER JOIN documents ON volunteer.volunteer_id = documents.doc_form_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE documents.doc_type_id = 2");
//end of code

if(isset($_GET['pageSet']))
{
  $seen = mysqli_query($database,"UPDATE notifications_admin SET is_seen = 1 WHERE is_seen = 0 AND notification_type_id = 3");
}

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

if (isset($_GET['rejectmsg'])) 
{
  $rejectmsg = $_GET['rejectmsg'];
}


 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Volunteer Request</title>

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!-- Begin Page Content -->
        <script>
                  setTimeout(function(){
                    document.getElementById('info-message').style.display = 'none';
                    document.getElementById('span').style.display = 'none';
                  }, 5000);
                  </script>
        <center>
        <?php   if(!empty($success)): ?>
                    <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                    <div class="alert alert-success" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$success</span>"; // success message
                      ?>
                    </div>
        <?php   endif; ?>  
        <?php   if(!empty($rejectmsg)): ?>
                    <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                    <div class="alert alert-success" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$rejectmsg</span>"; // success message
                      ?>
                    </div>
        <?php   endif; ?>  
        </center>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Volunteer Request</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Fullname</th>
              <th style="color: white">Adoption</th>
              <th style="color: white">Transportation</th>
              <th style="color: white">Clean-up</th>
              <th style="color: white">Educational Campaign</th>
              <th style="color: white">Animal Welfare</th>
              <th style="color: white">Fostering</th>
              <th style="color: white">Donation Drive</th>
              <th style="color: white">Events</th>
              <th style="color: white">Status</th>
              <?php 
               if ($_SESSION['role_id'] == 3) 
                {
                  # code...
                }
                else
                {
                 ?><th style="color: white">Action</th><?php 
                }
                
               ?>
              
            </tr>
          </thead>
          <tbody>
            <?php
            while($row = mysqli_fetch_array($volunteer_request))
            { 
               $inform_id = $row['user_id'];
              $inform_volunteer = mysqli_query($database, "SELECT * FROM notifications WHERE user_id = $inform_id AND subject = 'Volunteer'");

              $res = mysqli_fetch_array($inform_volunteer);
              $content = $res['content'];

              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";

              if ($row['is_adoption'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_transportation'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_shelter_clean_up'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_educational_campaign'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_animal_welfare'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_fostering'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_food_donation_drive'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

              if ($row['is_events'] == 1) 
              {
                echo "<td style='text-align: center'><i class='fa fa-check' style='color: green;'></i></td>";
              }
              else
              {
                echo "<td style='text-align: center'><i class='fa fa-times' style='color: red;'></i></td>";
              }

             
                echo "<td style='text-align: center'>$row[doc_status_desc]</td>";
              

              if ($_SESSION['role_id'] == 3) 
              {
                # code...
              }
              else
              {
                ?>
              <td>

              <?php
             

              if ($content == 'Kindly visit our booth in parkmall on 2nd or 4th saturday of the month for volunteer interview.') 
              {
                if ($row['doc_status_desc'] == 'Approved') 
                {
                  echo "<a class='btn btn-danger' title='Reject' style='margin: 2px'>
                               <i class='fa fa-ban fa-fw'></i></a>";
                }
                else if ($row['doc_status_desc'] == 'Rejected') 
                {
                  echo "<a class='btn btn-danger' title='Reject' style='margin: 2px'>
                               <i class='fa fa-ban fa-fw'></i></a>";
                }
                else
                {
                   echo "<a href='javascript:void(0);' data-href='getReasonVolunteer.php?id=$row[doc_id]' data-toggle='modal1' data-target='#myModal1' class='btn btn-danger openPopup1' title='Reject' style='margin: 2px'>
                  <i class='fa fa-ban fa-fw'></i></a>";
                }

                if ($row['doc_status_desc'] == 'Approved') 
                {
                  echo "<a class='btn btn-success' title='Approve' style='margin: 2px'>
                               <i class='fa fa-check fa-fw'></i></a>";
                }
                else if ($row['doc_status_desc'] == 'Rejected') 
                {
                  echo "<a class='btn btn-success' title='Approve' style='margin: 2px'>
                               <i class='fa fa-check fa-fw'></i></a>";
                }
                else
                {
                   echo "<a class='btn btn-success' href=\"admin_approvedvolunteer.php?doc_id=$row[doc_id]\" onclick=\"return confirm('Are you sure you want to approved this request?')\" title='Approve' style='margin: 2px'> 
                               <i class='fa fa-check fa-fw'></i></a>";
                }
                  echo "<a class='btn btn-primary' title='Inform Volunteer' style='margin: 2px'>
                               <i class='fa fa-bell fa-fw'></i></a>";
                }
              else
              {
                 echo "<a class='btn btn-primary' href=\"inform_volunteer.php?inform=$row[doc_id]\" onclick=\"return confirm('Are you sure you want to inform the requestor?')\" title='Inform Volunteer' style='margin: 2px'>
                             <i class='fa fa-bell fa-fw'></i></a>";
              }
              
              ?>


              <?php
              }

              }
            ?>
          </tbody>
          </table>
              </div>
            </div>
          </div>
        </div>
        <!-- /.container-fluid -->

        <script>
          $(document).ready(function(){
                      $('.openPopup1').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#myModal1').modal({show:true});
                          });
                      }); 
                  });
        </script>
      </div>
      <!-- End of Main Content -->
      <!-- The Modal -->
      <div class="modal fade" id="myModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Adoption Details</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <!-- Modal body -->
            <div class="modal-body">
              <!-- In this area will display the dynamic modal content -->
            </div>
            
            <!-- Modal footer -->
            <div class="modal-footer">
              <button onclick="printContent('aw')" class="btn btn-success" style="float: left;">Download as PDF</button>
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
            
          </div>
        </div>
      </div>

      <!-- The Modal -->
      <div class="modal fade" id="myModal1">
        <div class="modal-dialog">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Reject Reason</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <!-- Modal body -->
            <div class="modal-body">
              <!-- In this area will display the dynamic modal content -->
            </div>
            
            <!-- Modal footer -->
            
            
          </div>
        </div>
      </div>
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
