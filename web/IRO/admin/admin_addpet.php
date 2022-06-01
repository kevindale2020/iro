<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user
include 'session_checker.php';

//Get Pet Type
$petdetails = mysqli_query($database, "SELECT * FROM pet_type");
//

//Add Pet
if (isset($_POST['submit'])) 
{
  /*$file_type = $_FILES['image']['type'];
  $allowed = array("image/jpeg", "image/gif", "image/png");
  */
 	$image = $_FILES['image']['name'];// Get image name
 	$target = "../Android/images/adoptions/".basename($image);// image file directory
	$pet_name = $_POST['pet_name'];
	$pet_type = $_POST['pet_type'];
	$pet_gender = $_POST['pet_gender'];
  $acquired_from = $_POST['acquired_from'];
	$pet_age = $_POST['pet_age'];
	$pet_desc = $_POST['pet_desc'];
  $today = date('Y-m-d');
	$success = '';

  $check_pet = mysqli_query($database, "SELECT * FROM pets WHERE pet_name = '$pet_name'");

  /*if(!in_array($file_type, $allowed)) 
  {
    $error_image = 'Only jpg, gif and png files are allowed.';
  }
  else */if(mysqli_num_rows($check_pet) > 0)
  {
    $error_pet = 'This pet is already exist.';
  }
  else
  {
  	$addpet = "INSERT INTO `iro_db`.`pets` (`pet_id`, `pet_image`, `pet_name`, `pet_gender`, `pet_age`,`acquired_from`, `pet_desc`, `pet_type_id`, `pet_status_id`,`date_added`) VALUES (NULL, '$image', '$pet_name', '$pet_gender', '$pet_age','$acquired_from', '$pet_desc', '$pet_type', '1','$today')";

  	if (mysqli_query($database, $addpet)) 
  	{
  		if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
  	    {
  	      $success ='Pet has been added successfully';
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

  <title>IRO - Add Pet</title>

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
            <h1 class="h4 mb-2 text-gray-800">Pet Information</h1>
          </div>

          <div class="row">
            <div class="col-xl-12 col-lg-7" style="padding-left: 200px; padding-right: 200px">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">Add Pet</h6>
                  <div class="dropdown no-arrow">
                    
                  </div>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                <form class="user" method="post" action="admin_addpet.php" enctype="multipart/form-data">
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

                  <?php   if(!empty($error_image)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error_image</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error_type)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error_type</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error_gender)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error_gender</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>

                  <?php   if(!empty($error_pet)): ?>
                    <div class="alert alert-danger" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$error_pet</span>"; // error message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>      
                </div>

                <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Pet Image</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01" required="">
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>

                  <div class="form-group row">    
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Pet Name</h1>
                        <p id="petname_error" class="alert-danger"></p>
                        <input type="text" class="form-control" name="pet_name" placeholder="Pet Name" id="petname" autocomplete="off" required="" value=<?= isset($_POST['pet_name']) ? $_POST['pet_name'] : ''; ?>>
                      </div>
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Pet Age</h1>
                        <p id="petage_error" class="alert-danger"></p>
                        <input type="text" class="form-control" name="pet_age" placeholder="Pet Age" id="petage" autocomplete="off" required="" value=<?= isset($_POST['pet_age']) ? $_POST['pet_age'] : ''; ?>>
                      </div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">  
                    <h1 class="h5 mb-0 text-gray-800">Breed</h1>  
                      <select name="pet_type" class="form-control" required="">
                        <?php 
                        while ($row = mysqli_fetch_array($petdetails)) 
                            {
                              $pet_type_id = $row['pet_type_id'];
                              $pet_type = $row['pet_type_desc'];
                              ?>
                              <option value="<?php echo $pet_type_id; ?>"><?php echo "$pet_type"; ?></option>
                              <?php
                            }
                        ?>
                      </select>
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">   
                    <h1 class="h5 mb-0 text-gray-800">Pet Gender</h1> 
                      <select name="pet_gender" class="form-control" required="">
                        <option selected value="Male">Male</option>
                        <option value="Female">Female</option>
                      </select>
                    </div>
                  </div>
                   <div class="form-group">
                    <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Acquired From</h1>
                        <select name="acquired_from" class="form-control" required="">
                          <option selected value="Lost and Found">Lost and Found</option>
                          <option value="Homeless Pet">Homeless Pet</option>
                          <option value="Animal Cruelty">Animal Cruelty</option>
                        </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Pet Description</h1>
                        <p id="description_error" class="alert-danger"></p>
                        <textarea name="pet_desc"  rows="5" class="form-control" id="description" placeholder="Pet Description" required="" value=<?= isset($_POST['pet_desc']) ? $_POST['pet_desc'] : ''; ?>></textarea>
                    </div>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">    
                    <input type="submit" name="submit" value="Submit" class="btn btn-success btn-user btn-block" id="addpet">
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">    
                      <a href="admin_addpet.php" class="btn btn-primary btn-user btn-block">Clear</a>
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

<script type="text/javascript">
    $(document).ready(function(){
        $('#addpet').click(function(){
            var petname = $('#petname').val();
            var petage = $('#petage').val();
            var description = $('#description').val();
            
            //Conditions
            if ($.trim($("#petname").val()) === "" || !petname.match(/^[a-zA-Z0-9\s]*$/) || petname.match(/^[0-9]*$/) || !petname.match(/^[a-zA-Z\ ]+$/) && 
                $.trim($("#petage").val()) === "" || !petage.match(/^[a-zA-Z0-9\s]*$/) && $.trim($("#description").val()) === "") 
            {
              //Pet Name 
              if($.trim($("#petname").val()) === "") 
              {
                $('#petname_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!petname.match(/^[a-zA-Z0-9\s]*$/)) 
              {
                $("#petname_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(petname.match(/^[0-9]*$/)) 
              {
                $("#petname_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!petname.match(/^[a-zA-Z\ ]+$/))
              {
                $("#petname_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#petname_error').html("");
                $('#input-1').addClass('has-error');
              }

              //Pet Age
              if($.trim($("#petage").val()) === "") 
              {
                $('#petage_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!petage.match(/^[a-zA-Z0-9\s]*$/)) {
                $("#petage_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#petage_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Pet Description
              if($.trim($("#description").val()) === "") 
              {
                $('#description_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else
              {
                $('#description_error').html("");
                $('#input-2').addClass('has-error');
              }
              return false;
            }

            

        });
    });
</script>
</body>

</html>
