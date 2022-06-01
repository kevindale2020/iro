<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user



if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

//Get donations remittance
$remittance = mysqli_query($database, "SELECT * FROM donations INNER JOIN users ON donations.user_id = users.user_id INNER JOIN donation_type ON donations.donation_type_id = donation_type.donation_type_id WHERE donations.is_verified = 1 AND ((donations.donation_type_id = 2) OR (donations.donation_type_id = 4) OR (donations.donation_type_id = 5) OR (donations.donation_type_id = 6) OR (donations.donation_type_id = 7) OR (donations.donation_type_id = 8))");
//end of code

//Get donations bank
$bank = mysqli_query($database, "SELECT * FROM donations INNER JOIN users ON donations.user_id = users.user_id INNER JOIN donation_type ON donations.donation_type_id = donation_type.donation_type_id WHERE  donations.is_verified = 1 AND donations.donation_type_id = 3");
//end of code
 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Donations</title>

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
        <!-- Begin Page Content -->
        <center><button onclick="printContent('pdf')" class="btn btn-success"><i class='fa fa-download'> Download as PDF</i></button></center><br>
        <div id="pdf" style="padding: 5px">
        <center>
          <img src="./img/logo.png" height="100" width="300" alt="logo here" id="logo">
          <br><br>
          <h1 class="h3 mb-0 text-gray-800">DONATION REPORT</h1>
          
          <br>
        </center>
        <br>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Remittance Donation List</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Donated By</th>
              <th style="color: white">Donation Type</th>
              <th style="color: white">Donation Amount</th>
              <th style="color: white">Donation Date</th>
              <th style="color: white">Verified By</th>
              <th style="color: white">Date Verified</th>
            </tr>
          </thead>
          <tbody>
            <?php
              while($row = mysqli_fetch_array($remittance))
              {

              $amount = $row['amount'];
              $new_amount = number_format($amount, 2);
              $image = $row['image'];

              $new=date_create($row['date']);
              $date_verified =date_create($row['date_verified']);

              $new_format = date_format($new, "F d, Y");      
              $new_date_verified = date_format($date_verified, "F d, Y");      

              $verified_by = $row['verified_by'];

              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";
              echo "<td>".$row['donation_type_desc']."</td>";
              echo "<td>₱ ".$new_amount."</td>";
              echo "<td>".$new_format."</td>";
              $verifiedby = mysqli_query($database, "SELECT * FROM users WHERE user_id = $verified_by");
              $res = mysqli_fetch_array($verifiedby);
              echo "<td>".$res['user_fname']." ".$res['user_lname']."</td>";
              echo "<td>".$new_date_verified."</td>";
              echo "</tr>";
            }
            ?>
          </tbody>
          </table>
              </div>
            </div>
          </div>
        </div>

        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Bank Donation List</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Donated By</th>
              <th style="color: white">Donation Type</th>
              <th style="color: white">Account Name</th>
              <th style="color: white">Account Number</th>
              <th style="color: white">Donation Amount</th>
              <th style="color: white">Donation Date</th>
              <th style="color: white">Verified By</th>
              <th style="color: white">Date Verified</th>
            </tr>
          </thead>
          <tbody>
            <?php
              while($row = mysqli_fetch_array($bank))
              {

              $amount = $row['amount'];
              $new_amount = number_format($amount, 2);
              $image = $row['image'];

              $new=date_create($row['date']);
              $date_verified =date_create($row['date_verified']);

              $new_format = date_format($new, "F d, Y");     
              $new_date_verified = date_format($date_verified, "F d, Y");   

              $verified_by = $row['verified_by'];

              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";
              echo "<td>".$row['donation_type_desc']."</td>";
              echo "<td>".$row['account_name']."</td>";
              echo "<td>".$row['account_number']."</td>";
              echo "<td>₱ ".$new_amount."</td>";
              echo "<td>".$new_format."</td>";
              $verifiedby = mysqli_query($database, "SELECT * FROM users WHERE user_id = $verified_by");
              $res = mysqli_fetch_array($verifiedby);
              echo "<td>".$res['user_fname']." ".$res['user_lname']."</td>";
              echo "<td>".$new_date_verified."</td>";
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
  <!-- <script type="text/javascript">
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
  </script> -->

  <script src="js/printThis.js"></script>
    <script>
      function printContent(el){
        var restorepage = document.body.innerHTML;
        var printcontent = document.getElementById(el).innerHTML;
        document.body.innerHTML = printcontent;
        window.print();
        document.body.innerHTML = restorepage;
    }
      </script>
</body>

</html>
