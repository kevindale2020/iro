<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//Edit Profile
if (isset($_POST['save'])) 
{
  
  $user_fname = mysqli_real_escape_string($database, $_POST['fname']); //Variable name
  $nname = mysqli_real_escape_string($database, $_POST['nname']); //Variable name
  $user_lname = mysqli_real_escape_string($database, $_POST['lname']); //Variable name
  $address = mysqli_real_escape_string($database, $_POST['address']); //Variable name
  $contact = mysqli_real_escape_string($database, $_POST['contact']); //Variable name
  $email = mysqli_real_escape_string($database, $_POST['email']); //Variable name
  $occupation = mysqli_real_escape_string($database, $_POST['occupation']); //Variable name
  $username = mysqli_real_escape_string($database, $_POST['username']); //Variable name
  $success = '';

  $updateprofile = mysqli_query($database, "UPDATE `users` SET `user_fname` = '$user_fname',`user_lname` = '$user_lname',`user_nname` = '$nname',`user_address` = '$address',`user_contact` = '$contact',`user_email` = '$email',`user_occupation` = '$occupation',`user_username`= '$username' WHERE user_id = $user_id");

  $success ='Profile has been successfully updated';

}
//End of Code

//Clear Field Code
if (isset($_POST['clear'])) {
  $user_id = "";
  $fname = "";
  $lname = "";
  $nname = "";
  $occupation = "";
  $address = "";
  $email = "";
  $contact = "";
  $username = "";
}
//End of Code

//Change Picture
if (isset($_POST['SaveImage'])) 
{
  $file_type = $_FILES['image']['type'];
  $allowed = array("image/jpeg", "image/gif", "image/png");
  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/users/".basename($image);// image file directory

  if(!in_array($file_type, $allowed))
  {
    $error_image = 'Only jpg, gif and png files are allowed.';
  }
  else
  {
    $updateprofile = mysqli_query($database, "UPDATE `users` SET user_image = '$image' WHERE user_id = $user_id");

    if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
      {
        $success ='Profile Picture has been successfully updated';
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

  <title>IRO - Edit Profile</title>

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
            <h1 class="h4 mb-2 text-gray-800">Personal Information</h1>
          </div>

          <div class="row">

            <!-- Area Chart -->
            <div class="col-xl-4 col-lg-5">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Profile Picture</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <form method="post" action="cityvet_editprofile.php" enctype="multipart/form-data">
                  <center>
                  <?php echo "<img src='../Android/images/users/".$user_image."' style='margin: 5px; width: 250px; height: 200px;' class='avatar img-circle img-thumbnail' alt='avata'>" ?>
                  <h6>Upload a different photo...</h6>  
                  
                  <div class="form-group">
                    <div class="input-group mb-3">
                      <div class="input-group-prepend">
                        <span class="input-group-text">Choose File</span>
                      </div>

                      <div class="custom-file">
                        <input type="file" class="custom-file-input" id="file" name="image" onchange="selectfile();">
                        <label class="custom-file-label" for="file"></label>
                      </div>
                    </div>
                  </div

                  </center>
                  <script>
                  setTimeout(function(){
                    document.getElementById('info-message').style.display = 'none';
                    document.getElementById('span').style.display = 'none';
                  }, 5000);
                  </script>
                  <center>
                  <?php   if(!empty($profilesuccess)): ?>
                    <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                    <div class="alert alert-success" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$profilesuccess</span>"; // success message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($profileerror)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$profileerror</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>  
                  </center>     
                  <center>
                    <?php   if(!empty($success)): ?>
                      <span style="padding-bottom: 5px" id="span">&nbsp;</span>
                      <div class="alert alert-success" id="info-message">
                        <?php 
                        echo "<span style='font-size: 16px'>$success</span>"; // success message
                        ?>
                      </div>
                    <?php   endif; ?>

                    <?php   if(!empty($error_image)): ?>
                      <div class="alert alert-danger" id="info-message">
                        <?php 
                        echo "<span style='font-size: 16px'>$error_image</span>"; // error message
                        ?>
                      </div>
                    <?php   endif; ?> 
                  </center>      
                  <div id="submit" style="display: none;">
                    <span style="padding-bottom: 5px">&nbsp;</span>
                    <input type="submit" name="SaveImage" id="submit" value="Upload" class="btn btn-primary btn-user btn-block">
                  </div>
                  
                  </form>

                  <script>
                  function selectfile() 
                  {
                    if ($('#file').val()!="") {
                      document.getElementById('submit').style.display = "";
                    }else
                    {
                      document.getElementById('submit').style.display = "none";
                    }
                  }
                  </script>
                </div>
              </div>
            </div>

            <!-- Pie Chart -->
            <div class="col-xl-8 col-lg-7 ">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                <h6 class="m-0 font-weight-bold text-primary">Edit Profile</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                <form class="user" method="post" action="cityvet_editprofile.php">
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
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">First Name</h1>
                    <input type="text" class="form-control form-control-user" name="fname" value="<?php echo $user_fname ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Last Name</h1>
                    <input type="text" class="form-control form-control-user" name="lname" value="<?php echo $user_lname ?>">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Nickname</h1>
                    <input type="text" class="form-control form-control-user" name="nname" value="<?php echo $nname ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Contact Number</h1>
                    <input type="text" class="form-control form-control-user" name="contact" value="<?php echo $contact ?>">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Occupation</h1>
                    <input type="text" class="form-control form-control-user" name="occupation" value="<?php echo $occupation ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Address</h1>
                    <input type="text" class="form-control form-control-user" name="address" value="<?php echo $address ?>">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Email</h1>
                    <input type="text" class="form-control form-control-user" name="email" value="<?php echo $email ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Username</h1>
                    <input type="text" class="form-control form-control-user" name="username" value="<?php echo $username ?>">
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="submit" name="save" value="Save" class="btn btn-success btn-user btn-block">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="submit" name="clear" value="Clear" class="btn btn-primary btn-user btn-block">
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
