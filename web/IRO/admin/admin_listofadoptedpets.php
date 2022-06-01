<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user


//Get all the adopted pets
$adoptedpets = mysqli_query($database, "SELECT pets.pet_image, pets.pet_name, pet_type.pet_type_desc, users.user_fname, users.user_lname, approved_documents.approved_date, approved_documents.approved_by FROM adoptions INNER JOIN pets ON adoptions.pet_id = pets.pet_id INNER JOIN users ON adoptions.user_id = users.user_id INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id INNER JOIN pet_status ON pets.pet_status_id = pet_status.pet_status_id INNER JOIN documents ON adoptions.adoption_id = documents.doc_form_id INNER JOIN approved_documents ON documents.doc_id = approved_documents.doc_id WHERE pets.pet_status_id = 2 AND documents.doc_status_id = 2");

$approver = mysqli_query($database, "SELECT * FROM users INNER JOIN approved_documents ON users.user_id = approved_documents.approved_by");
//end of code

 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - List of Adopted Pets</title>

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

        <!-- Begin Page Content -->
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary" style="display: inline; font-size: 24px">List of Adopted Pets</h6>
              <?php echo "<a href='javascript:void(0);' data-href='adoptedPetListPdf.php' data-toggle='modal' data-target='#petList' class='btn btn-success openPopup' style='float: right; display: inline;'><i class='fa fa-download'> Download as PDF</i></a>"; ?>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <div id="aw" style="padding: 5px">
                <table id="example" class="table table-hover">
                  <thead class="bg-primary" style="background-color: #23a6da";">
                    <tr>
                      <th style="color: white">Pet Image</th>
                      <th style="color: white">Pet Name</th>
                      <th style="color: white">Pet Breed</th>
                      <th style="color: white">Adopted By</th>
                      <th style="color: white">Approved By</th>
                      <th style="color: white">Date Adopted</th>
                    </tr>
                  </thead>
                  <tbody>
                    <?php
                    while($row = mysqli_fetch_array($adoptedpets))
                    {

                      
                      $approver = $row['approved_by'];
                      echo "<tr>";
                      echo "<td><img src='../Android/images/adoptions/".$row['pet_image']."' width='100' height='100'></td>";
                      echo "<td>".$row['pet_name']."</td>";
                      echo "<td>".$row['pet_type_desc']."</td>";
                      echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>"; 
                      
                      $query = mysqli_query($database, "SELECT * FROM users WHERE user_id = $approver");
                      $data=mysqli_fetch_assoc($query);
                      $approved_by_fname = $data['user_fname'];
                      $approved_by_lname = $data['user_lname'];

                      echo "<td>".$approved_by_fname." ".$approved_by_lname."</td>";
                      echo "<td>".$row['approved_date']."</td>";
                      echo "</tr>";
                    }
                    ?>
                  </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /.container-fluid -->

       <div class="modal fade" id="adoptedPetListPdf">
        <div class="modal-dialog modal-xl">
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
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class='fa fa-times-circle'> Cancel</i></button>
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
 <script>
                  $(document).ready(function(){
                      $('.openPopup').on('click',function(){
                          var dataURL = $(this).attr('data-href');
                          $('.modal-body').load(dataURL,function(){
                              $('#adoptedPetListPdf').modal({show:true});
                          });
                      }); 
                  });
                  </script>
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
