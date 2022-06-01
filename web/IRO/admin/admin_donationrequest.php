<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

//Get donations remittance
$remittance = mysqli_query($database, "SELECT * FROM donations INNER JOIN users ON donations.user_id = users.user_id INNER JOIN donation_type ON donations.donation_type_id = donation_type.donation_type_id WHERE ((donations.donation_type_id = 4) OR (donations.donation_type_id = 5) OR (donations.donation_type_id = 6) OR (donations.donation_type_id = 7) OR (donations.donation_type_id = 8))");
//end of code

//Get donations bank
$bank = mysqli_query($database, "SELECT * FROM donations INNER JOIN users ON donations.user_id = users.user_id INNER JOIN donation_type ON donations.donation_type_id = donation_type.donation_type_id WHERE donations.donation_type_id = 3 OR donations.donation_type_id = 2");
//end of code


if(isset($_GET['pageSet']))
{
  $seen = mysqli_query($database,"UPDATE notifications_admin SET is_seen = 1 WHERE is_seen = 0 AND notification_type_id = 7");
}
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
        <center><h1 class="h3 mb-0 text-gray-800">DONATIONS</h1></center>
        <br>
        <div class="container-fluid" style="width: 50%">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Donation Details</h6>
            </div>
            <div class="card-body">
              <table>
                <tr>
                  <td style="padding-right: 10px">Remittance:</td><td>Western Union / Palawan / Cebuana Lhuiller / LBC</td>
                </tr>



                <tr><td><td></td></td></tr>

                <tr>
                  <td>Fullname</td> <td>Annalyn Aizpuru / Xemenia Judaya</td>
                </tr>

                <tr>
                  <td>Address</td> <td>37 F. Cabahug Street, Mabolo Cebu</td>
                </tr>

                <tr>
                  <td>Contact #</td> <td>09322826312 / 09236084063</td>
                </tr>
                <tr>
                  <td><hr></td><td><hr></td>
                </tr>
                <tr>
                  <td>Bank:</td><td>China Bank / Paypal</td>
                </tr>

                <tr><td><td></td></td></tr>

                <tr>
                  <td>Name</td> <td>Island Rescue Organization, Inc.</td>
                </tr>

                <tr>
                  <td>Number</td> <td>2890396715</td>
                </tr>

                <tr>
                  <td>Contact #</td> <td>09322826312 / 09236084063</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <br>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Remittance Donation</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Donated By</th>
              <th style="color: white">Mode of Donation</th>
              <th style="color: white">Amount</th>
              <th style="color: white">Date</th>
              <th style="color: white">Receipt</th>
              <th style="color: white">Status</th>
              <?php 
              if ($_SESSION['role_id'] == 3) 
              {
                
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
              while($row = mysqli_fetch_array($remittance))
              {

              $amount = $row['amount'];
              $new_amount = number_format($amount, 2);
              $image = $row['image'];

              $new=date_create($row['date']);

              $new_format = date_format($new, "F d, Y");      

              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";
              echo "<td>".$row['donation_type_desc']."</td>";
              echo "<td>₱ ".$new_amount."</td>";
              echo "<td>".$new_format."</td>";
              echo "<td>"."<a download='$row[image]' href='../Android/images/Donations/$image' style='width: 100px; height: 100px'><img src='../Android/images/donations/".$row['image']."' style='width: 150px; height: 150px'></a></div>"."</td>";
              ?>

              <?php
              if ($row['is_verified'] == 0) 
                {
                  echo "<td>Not Verified</td>";
                }
                else
                {
                  echo "<td>Verified</td>";
                }
              if ($_SESSION['role_id'] == 3) 
              {
                
              }
              else
              {
                

                 if ($row['is_verified'] == 0) 
                {
                   echo "<td><a class='btn btn-success' href=\"verify_donation.php?donation_id=$row[donation_id]\" onclick=\"return confirm('Are you sure you want to verify this donation?')\" title='Verify'><i class='fa fa-check fa-fw'></i></a>

                   <a class='btn btn-danger' href=\"verify_donation.php?inform=$row[donation_id]\" onclick=\"return confirm('Are you sure you want to inform the donor?')\" title='Inform Donor'><i class='fa fa-ban fa-fw'></i></a>

                   </td>";
                }
                else
                {
                  echo "<td><a class='btn btn-success' title='Verify'><i class='fa fa-check fa-fw'></i></a></td>";
                }
              }
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
              <h6 class="m-0 font-weight-bold text-primary">Bank Donation</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example2" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Donated By</th>
              <th style="color: white">Mode of Donation</th>
              <th style="color: white">Account Name</th>
              <th style="color: white">Account Number</th>
              <th style="color: white">Amount</th>
              <th style="color: white">Date</th>
              <th style="color: white">Receipt</th>
              <th style="color: white">Status</th>
              <?php 
              if ($_SESSION['role_id'] == 3) 
              {
                
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
              while($row = mysqli_fetch_array($bank))
              {

              $amount = $row['amount'];
              $new_amount = number_format($amount, 2);
              $image = $row['image'];

              $new=date_create($row['date']);

              $new_format = date_format($new, "F d, Y");      

              echo "<tr>";
              echo "<td>".$row['user_fname']." ".$row['user_lname']."</td>";
              echo "<td>".$row['donation_type_desc']."</td>";
              echo "<td>".$row['account_name']."</td>";
              echo "<td>".$row['account_number']."</td>";
              echo "<td>₱ ".$new_amount."</td>";
              echo "<td>".$new_format."</td>";
              echo "<td>"."<a download='$row[image]' href='../Android/images/Donations/$image' style='width: 100px; height: 100px'><img src='../Android/images/donations/".$row['image']."' style='width: 150px; height: 150px'></a></div>"."</td>";
              ?>

              <?php
              if ($row['is_verified'] == 0) 
                {
                  echo "<td>Not Verified</td>";
                }
                else
                {
                  echo "<td>Verified</td>";
                }

              if ($_SESSION['role_id'] == 3) 
              {
                
              }
              else
              {
                 if ($row['is_verified'] == 0) 
                {
                   echo "<td><a class='btn btn-success' href=\"verify_donation.php?donation_id=$row[donation_id]\" onclick=\"return confirm('Are you sure you want to verify this donation?')\" title='Verify'><i class='fa fa-check fa-fw'></i></a>

                   <a class='btn btn-danger' href=\"verify_donation.php?inform=$row[donation_id]\" onclick=\"return confirm('Are you sure you want to inform the donor?')\" title='Inform Donor'><i class='fa fa-ban fa-fw'></i></a></td>";
                }
                else
                {
                  echo "<td><a class='btn btn-success' title='Verify'><i class='fa fa-check fa-fw'></i></a></td>";
                }
              }
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

  <script type="text/javascript">
   $(document).ready(function() {
      $('#example2').DataTable(
          
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
