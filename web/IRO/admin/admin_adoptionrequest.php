<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Get Adoption Request
$adoption_request = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE ((documents.doc_type_id = 1) AND (documents.doc_status_id = 1) OR (documents.doc_status_id = 2) OR documents.doc_status_id = 3)");

$getattachments = mysqli_query($database, "SELECT * FROM documents INNER JOIN file_attachments ON documents.doc_id = file_attachments.doc_id");
//end of code

if(isset($_GET['pageSet']))
{
  $seen = mysqli_query($database,"UPDATE notifications_admin SET is_seen = 1 WHERE is_seen = 0 AND notification_type_id = 1");
}

if (isset($_GET['pageset'])) 
{
  $seen = mysqli_query($database,"UPDATE notifications_admin SET is_seen = 1 WHERE is_seen = 0 AND notification_type_id = 8");
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

  <title>IRO - Adoption Request</title>

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
              <h6 class="m-0 font-weight-bold text-primary">Adoption Request</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Fullname</th>
              <th style="color: white">Pet Name</th>
              <th style="color: white">Pet Breed</th>
              <th style="color: white">Date Submitted</th>
              <th style="color: white">Attachments</th>
              <th style="color: white">Status</th>
              <th style="color: white">Action</th>
            </tr>
          </thead>
          <tbody>
            <?php
            while($row = mysqli_fetch_array($adoption_request))
            { 
              $pet_id = $row['pet_id'];
             
              $adoption_id = $row['adoption_id'];
              ?>  <tr>
                  <td><?php echo $row['user_fname']; ?> <?php echo $row['user_lname']; ?></td>
                  <td><?php echo $row['pet_name']; ?></td>
                  <td><?php echo $row['pet_type_desc']; ?></td>
                  <td><?php echo $row['date_submitted']; ?></td>
                  <td><?php echo "<a href='javascript:void(0);' data-href='getattachment.php?doc_id=$row[doc_id]' data-toggle='modal' data-target='#myModal2' class='openPopup2' title='View Attachments'><i class='fa fa-eye'></i> View</a>"; ?></td>
                   
                  <td><?php echo $row['doc_status_desc']; ?></td>
                  <td><?php echo "<a href='javascript:void(0);' data-href='getContent.php?id=$row[doc_id]' data-toggle='modal' data-target='#myModal' class='btn btn-info openPopup' title='View'><i class='fa fa-eye fa-fw'></i></a>"; ?>

                        <?php 
                        if ($row['doc_status_id'] == 2) 
                        {
                          if ($row['is_agreed']) 
                          {
                            echo "<a href='javascript:void(0);' data-href='homeVisit.php?id=$row[doc_id]' data-toggle='modal' data-target='#homeVisit' class='btn btn-success openPopup4' title='House Visit'><i class='fa fa-home fa-fw'></i></a>";
                          }
                          else
                          {

                          }
                        }
                        else
                        {
                          
                        }
                          
                         ?>

                        <?php 
                        if ($row['doc_status_id'] == 2 || $row['doc_status_id'] == 3 || $row['doc_status_id'] == 4) 
                        {
                          echo "";
                        }
                        else
                        {
                           $check = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN pets ON adoptions.pet_id = pets.pet_id WHERE pets.pet_id = $pet_id");

                           $res = mysqli_fetch_array($check);

                           $pet_status_id = $res['pet_status_id'];

                           if ($pet_status_id == 2) 
                           {
                             echo "<a class='btn btn-primary' href=\"inform_requestor.php?inform=$row[doc_id]\" onclick=\"return confirm('Are you sure you want to inform_requestor the requestor?')\" title='Inform Requestor'>
                             <i class='fa fa-bell fa-fw'></i></a>";
                           }
                           else
                           {
                            echo "<a class='btn btn-success' href=\"admin_approvedadoption.php?doc_id=$row[doc_id]\" onclick=\"return confirm('Are you sure you want to approved this request?')\" title='Approve'>
                             <i class='fa fa-check fa-fw'></i></a>";
                           }
                        }
                          
                         ?>


                        <?php 
                        if ($row['doc_status_id'] == 2 || $row['doc_status_id'] == 3 || $row['doc_status_id'] == 4) 
                        {
                          echo "";
                        }
                        else
                        {
                          $check = mysqli_query($database, "SELECT * FROM adoptions INNER JOIN pets ON adoptions.pet_id = pets.pet_id WHERE pets.pet_id = $pet_id");

                           $ress = mysqli_fetch_array($check);

                           $pet_status_id = $ress['pet_status_id'];

                           if ($pet_status_id == 2) 
                           {
                             
                           }
                           else
                           {
                            echo "<a href='javascript:void(0);' data-href='getReason.php?id=$row[doc_id]' data-toggle='modal1' data-target='#myModal1' class='btn btn-danger openPopup1' title='Reject'>
                            <i class='fa fa-ban fa-fw'></i></a>";
                           }
                          
                        }
                          
                        ?>
                        
                         

                  </td>        
                  </tr>
                  </tr>

                  <script>
                  $(document).ready(function(){
                      $('.openPopup').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#myModal').modal({show:true});
                          });
                      }); 
                  });

                  $(document).ready(function(){
                      $('.openPopup1').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#myModal1').modal({show:true});
                          });
                      }); 
                  });

                   $(document).ready(function(){
                      $('.openPopup2').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#myModal2').modal({show:true});
                          });
                      }); 
                  });

                   $(document).ready(function(){
                      $('.openPopup4').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#homeVisit').modal({show:true});
                          });
                      }); 
                  });
                  </script>
              <?php
              
            }
            ?>
          </tbody>
          </table>
              </div>
            </div>
          </div>
        </div>
        <!-- /.container-fluid -->
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
              <a href="admin_adoptionrequest.php" class="btn btn-secondary" data-dismiss="modal">Close</a>
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

      <!-- The Modal -->
      <div class="modal fade" id="homeVisit">
        <div class="modal-dialog">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">House Visit</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <!-- Modal body -->
            <div class="modal-body">
              <!-- In this area will display the dynamic modal content -->
            </div>
          </div>
        </div>
      </div>

       <!-- The Modal -->
      <div class="modal fade" id="myModal2">
        <div class="modal-dialog">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">File Attachment</h4>
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
