<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Get Pet Type
$item_category = mysqli_query($database, "SELECT * FROM item_category");
//

//Add item
if (isset($_POST['add_item'])) 
{
  $file_type = $_FILES['image']['type'];
  $allowed = array("image/jpeg", "image/gif", "image/png");
 	$image = $_FILES['image']['name'];// Get image name
 	$target = "../Android/images/items/".basename($image);// image file directory
	$item_category = mysqli_real_escape_string($database, $_POST['item_category']);
  $item_name = mysqli_real_escape_string($database, $_POST['item_name']);
  $item_desc = mysqli_real_escape_string($database, $_POST['item_desc']);
  $item_qty = mysqli_real_escape_string($database, $_POST['item_qty']);
  $item_price = mysqli_real_escape_string($database, $_POST['item_price']);
	$success = '';
	$error = '';


  if(!in_array($file_type, $allowed))
  {
    $error_image = 'Only jpg, gif and png files are allowed.';
  }
  else
  {
    $add_merchandise = "INSERT INTO `items` (`item_id`, `item_category_id`, `item_image`, `item_name`, `item_desc`, `item_qty`, `item_price`) VALUES (NULL, '$item_category', '$image', '$item_name', '$item_desc', '$item_qty', '$item_price');";

    if (mysqli_query($database, $add_merchandise)) 
    {
      if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
        {
          $success ='Merchandise has been added successfully';
        }
    }
  }
	

}
//End of Code
 ?>

<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Add Merchandise</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

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

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-2">
            <h1 class="h4 mb-2 text-gray-800">Merchandise Information</h1>
          </div>

          <div class="row">
            <div class="col-xl-12 col-lg-7" style="padding-left: 200px; padding-right: 200px">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Add Merchandise</h6>
                  <div class="dropdown no-arrow">
                    
                  </div>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                <form class="user" method="post" action="admin_addmerchandise.php" enctype="multipart/form-data">
                <div class="form-group">
                
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

                  <?php   if(!empty($error)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>      
                </div>

                <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Item Image</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01" required="">
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>

                  <div class="form-group row">    
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <input type="text" class="form-control" name="item_name" placeholder="Item Name" autocomplete="off" required="">
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">    
                      <select name="item_category" class="form-control" required="">
                        <option disabled selected>Select Category</option>
                        <?php 
                        while ($row = mysqli_fetch_array($item_category)) 
                            {
                              $item_category_id = $row['item_category_id'];
                              $item_category_desc = $row['item_category_desc'];
                              ?>
                              <option value="<?php echo $item_category_id; ?>"><?php echo "$item_category_desc"; ?></option>
                              <?php
                            }
                        ?>
                      </select>
                    </div>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <input type="text" class="form-control" name="item_qty" placeholder="Item Quantity" autocomplete="off" required="">
                    </div>
                    

                   <div class="col-sm-6 mb-3 mb-sm-0">
                      <input type="text" class="form-control" name="item_price" placeholder="Item Price" autocomplete="off" required="">
                    </div>
                  </div>

                  <div class="form-group">
                    <textarea name="item_desc"  rows="5" class="form-control" placeholder="Item Description" required=""></textarea>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">    
                    <input type="submit" name="add_item" value="Submit" class="btn btn-success btn-user btn-block">
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">    
                    <a href="admin_addmerchandise.php" class="btn btn-primary btn-user btn-block">Clear</a>
                    </div>
                  </div>
              </form>
                </div>
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

  <script>
    $(document).on('change', '.custom-file-input', function (event) {
    $(this).next('.custom-file-label').html(event.target.files[0].name);
    })
  </script>

</body>

</html>
