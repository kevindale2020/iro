<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

if (isset($_GET['error'])) 
{
  $error = $_GET['error'];
}

//Get all users
$userdetails = mysqli_query($database, "SELECT * FROM users INNER JOIN user_roles ON users.user_id = user_roles.user_id INNER JOIN roles ON user_roles.role_id = roles.role_id INNER JOIN volunteer ON users.user_id = volunteer.user_id WHERE volunteer.is_active = 1");
//end of code
 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - View Volunteer</title>

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
                <center>
                  <?php   if(!empty($error)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error</span>"; // success message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>

        <!-- Begin Page Content -->
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Volunteer List</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Fullname</th>
              <th style="color: white">Nickname</th>
              <th style="color: white">Address</th>
              <th style="color: white">Contact</th>
              <th style="color: white">Email</th>
              <th style="color: white">Occupation</th>
              <th style="color: white">User Role</th>
              <th style="color: white">Status</th>
              <th style="color: white">Action</th>
            </tr>
          </thead>
          <tbody>
            <?php
            while($row = mysqli_fetch_array($userdetails))
            {
              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";
              echo "<td>".$row['user_nname']."</td>";
              echo "<td>".$row['user_address']."</td>";
              echo "<td>".$row['user_contact']."</td>";
              echo "<td>".$row['user_email']."</td>";
              echo "<td>".$row['user_occupation']."</td>";
              echo "<td>".$row['role_desc']."</td>";

                if ($row['is_active'] == 0) 
                {
                  echo "<td>Waiting for volunteer activation</td>";
                }
                else
                {
                  echo "<td>Volunteer account activated</td>";
                }

                if ($row['is_active'] == 1) 
                {
                  if ($row['role_id'] == 1) 
                  {
                   echo "<td><a class='btn btn-success' href=\"admin_manageaccount.php?activate_volunteer=$row[user_id]\" onclick=\"return confirm('Are you sure you want to activate this account?')\" title='Activate Account'><i class='fa fa-check fa-fw'></i></a></td>";
                  }
                  else
                  {
                   echo "<td><a class='btn btn-danger' href=\"admin_manageaccount.php?deactivate_volunteer=$row[user_role_id]\" onclick=\"return confirm('Are you sure you want to deactivate this account?')\" title='Deactivate Account'><i class='fa fa-ban fa-fw'></i></a></td>";
                  }
                }
              echo "</tr>";
              echo "</tr>";
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
