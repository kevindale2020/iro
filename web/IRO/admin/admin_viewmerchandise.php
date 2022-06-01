<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Get Pet Type
$merchandise_details = mysqli_query($database, "SELECT * FROM items INNER JOIN item_category ON items.item_category_id = item_category.item_category_id ORDER BY item_id DESC");
//end of code

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

  <title>IRO - Merchandise List</title>

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
  top: 8px;
  right: 16px;
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
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Merchandise List</h6>
            </div>
            <div class="card-body">
              <div class="container-fluid">
        <div class="container">
            <div class="row" style="width: 100%">
                <?php while ($row = mysqli_fetch_array($merchandise_details)) 
                {
                    $item_image = $row['item_image'];
                    $item_name = $row['item_name'];
                    $item_desc = $row['item_desc'];
                    $item_qty = $row['item_qty'];
                    $item_id = $row['item_id'];

                    $item_price = $row['item_price'];
                    $new_price = number_format($item_price, 2);
                    ?>
                    <div class="col-md-4" style="padding:6px;">
                    <div class="card">
                      <div class="container">
                         <?php echo "<img class='img' src='../Android/images/items/".$item_image."' style='width: 300px; height: 200px'>";  ?>
                        <div class="top-right">
                         <a href="admin_deletemerchandise.php?item_id=<?php echo $item_id ?>"><i class="fa fa-trash fa-lg" style="color: red" onclick="return confirm('Are you sure you want to remove this merchandise?')"></i></a>
                        </div>
                      </div>
                       
                        <div class="card-body" style="background-color: #e8e8e8;">
                            <center>
                            <h5 class="card-title">
                                <a href="#" class="text-dark"><?php echo "$item_name"; ?> </a>
                            </h5>
                            </center>

                        </div>
                        <div class="card-footer" >
                            <div class="float-left">
                              <br>
                                <table>
                                    <tr>
                                        <td style="padding-right: 10px">Category</td><td class="text-muted"><?php echo "$row[item_category_desc]"; ?></td> 
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px">Quantity</td><td class="text-muted"><?php echo "$item_qty"; ?></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 10px; vertical-align: text-top;">Description</td><td class="card-text"><?php echo "$item_desc"; ?></td>    
                                    </tr>
                                </table>
                                <br>
                            </div>
                        </div>
                        <div class="alert alert-primary" style="height: 50px">
                          <center>
                            <p class="text-dark">₱ <?php echo $new_price ?></p>
                          </center>

                        </div>
                    </div>
                </div><!--.col-->
                    <?php
                } ?>
            </div><!--.row-->
        </div><!--.container-->
    </div><!--.container-fluid-->
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
