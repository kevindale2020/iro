<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['adopted'])) 
{
  $pet_status_id = $_GET['adopted'];
    //pet details
    $petdetails = mysqli_query($database, "SELECT * FROM pets INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id WHERE pets.pet_status_id = 2 ORDER BY pet_id DESC");
    //end of code
}
else if (isset($_GET['available'])) 
{
  $pet_status_id = $_GET['available'];
    //pet details
    $petdetails = mysqli_query($database, "SELECT * FROM pets INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id WHERE pets.pet_status_id = 1 ORDER BY pet_id DESC");
    //end of code
}
else
{
  //pet details
  $petdetails = mysqli_query($database, "SELECT * FROM pets INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id ORDER BY pet_id DESC");
  //end of code
}

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

if (isset($_GET['showAll'])) 
{
  $showAll = $_GET['showAll'];
}

if (isset($_GET['error_image'])) 
{
  $error_image = $_GET['error_image'];
}

 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Pet List</title>

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
         <?php   if(!empty($showAll)): ?>
                    <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                    <div class="alert alert-success" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$showAll</span>"; // success message
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

        <?php   if(!empty($error_image)): ?>
                    <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error_image</span>"; // success message
                      ?>
                    </div>
        <?php   endif; ?>  
        </center>
        <div class="container-fluid" style="width: 100%; padding-left: 0px; padding-right: 0px;">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary" style="display: inline; font-size: 24px">Pet List</h6>
              <?php echo "<a href='javascript:void(0);' data-href='petListPdf.php' data-toggle='modal' data-target='#petList' class='btn btn-success openPopup1' style='float: right; display: inline;'><i class='fa fa-download'> Download as PDF</i></a>"; ?>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Image</th>
              <th style="color: white">Name</th>
              <th style="color: white">Breed</th>
              <th style="color: white">Gender</th>
              <th style="color: white">Age</th>
              <th style="color: white">Acquired From</th>
              <th style="color: white">Date Added</th>
              <th style="color: white">Availability</th>
              <?php if (isset($_GET['available']))
              {
                ?><th style="color: white">Action</th><?php
              } 
              else if(isset($_GET['showAll']))
              {
                ?><th style="color: white">Action</th><?php
              }
               else if(isset($_GET['success']))
              {
                ?><th style="color: white">Action</th><?php
              }
              else if(isset($_GET['error_image']))
              {
                ?><th style="color: white">Action</th><?php
              }
              ?>
                
            </tr>
          </thead>
          <tbody>
            <?php

            if (isset($_GET['adopted'])) 
            {
              while($row = mysqli_fetch_array($petdetails))
              {     
                    $date_added = $row['date_added'];
                    $new=date_create($date_added);

                    $new_format = date_format($new, "F d, Y");
                ?>  
                    <tr>
                    <td><?php echo "<img src='../Android/images/adoptions/".$row['pet_image']."' width='100' height='100'>"; ?></td>
                    <!-- <td><a download="<?php echo $row['pet_image'] ?>" href="<?php echo $row['pet_image'] ?>">Download</a></td> -->
                    <td><?php echo "$row[pet_name]"; ?></td>
                    <td><?php echo "$row[pet_type_desc]" ?></td>
                    <td><?php echo "$row[pet_gender]"; ?></td>
                    <td><?php echo "$row[pet_age]"; ?></td>
                    <td><?php echo "$row[acquired_from]"; ?></td>
                    <td><?php echo "$new_format" ?></td>
                    <td><?php echo "$row[pet_status_desc]"; ?></td>
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
                    </script>
                <?php
                
              }
            }
            else
            {
              while($row = mysqli_fetch_array($petdetails))
              {     
                    $date_added = $row['date_added'];
                    $new=date_create($date_added);

                    $new_format = date_format($new, "F d, Y");
                ?>  
                    <tr>
                    <td><?php echo "<img src='../Android/images/adoptions/".$row['pet_image']."' width='100' height='100'>"; ?></td>
                    <!-- <td><a download="<?php echo $row['pet_image'] ?>" href="<?php echo $row['pet_image'] ?>">Download</a></td> -->
                    <td><?php echo "$row[pet_name]"; ?></td>
                    <td><?php echo "$row[pet_type_desc]" ?></td>
                    <td><?php echo "$row[pet_gender]"; ?></td>
                    <td><?php echo "$row[pet_age]"; ?></td>
                    <td><?php echo "$row[acquired_from]"; ?></td>
                    <td><?php echo "$new_format" ?></td>
                    <td><?php echo "$row[pet_status_desc]"; ?></td>
                    <td><?php echo "<a href='javascript:void(0);' data-href='getContentPet.php?id=$row[pet_id]' data-toggle='modal' data-target='#myModal' class='btn btn-success openPopup' title='Edit'><i class='fa fa-edit fa-fw'></i></a>"; ?>
                         
                        <?php echo "<a href=\"delete_pet.php?delete_pet=$row[pet_id]\" onclick=\"return confirm('Are you sure you want to remove this pet?')\" class='btn btn-danger openPopup1' title='Delete'>
                        <i class='fa fa-trash fa-fw'></i></a>"; ?> 
                   </td>    

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
                    </script>
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
      </div>
      <!-- End of Main Content -->
      <!-- The Modal -->
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

      <!-- The Modal -->
      <div class="modal fade" id="petList">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
          
            <!-- Modal Header -->
            <div class="modal-header">
              <h4 class="modal-title">Download as PDF</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            
            <!-- Modal body -->
            <div class="modal-body">
              <!-- In this area will display the dynamic modal content -->
            </div>

            <div class="modal-footer">
              <button onclick="printContent('pdf')" class="btn btn-success" style="float: left;"><i class='fa fa-download'> Download as PDF</i></button>
              <a href="admin_viewpet.php?showAll" type="button" class="btn btn-danger" ><i class='fa fa-times-circle'> Cancel</i></a>
            </div>
            
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
