<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

//add user
if (isset($_POST['submit'])) 
{
  $file_type = $_FILES['image']['type'];
  $allowed = array("image/jpeg", "image/gif", "image/png");

  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/users/".basename($image);// image file directory
  $created_date = date("Y-m-d");
  $fname = mysqli_real_escape_string($database, $_POST['fname']); //Variable name
  $nname = mysqli_real_escape_string($database, $_POST['nname']); //Variable name
  $lname = mysqli_real_escape_string($database, $_POST['lname']); //Variable name
  $address = mysqli_real_escape_string($database, $_POST['address']); //Variable name
  $contact = mysqli_real_escape_string($database, $_POST['contact']); //Variable name
  $email = mysqli_real_escape_string($database, $_POST['email']); //Variable name
  $occupation = mysqli_real_escape_string($database, $_POST['occupation']); //Variable name
  $username = mysqli_real_escape_string($database, $_POST['username']); //Variable name
  $password = "Password123"; //Default Password
  $message ='';
  $registererror = '';
  
    if (empty($image) || $image == "") 
    {
      $check_username = mysqli_query($database, "SELECT * FROM users WHERE user_username = '$username'");
      $check_email = mysqli_query($database, "SELECT * FROM users WHERE user_email = '$email'");

      if (mysqli_num_rows($check_username) > 0) 
      {
        $registererror = 'Username already exist!';
      }
      else if (mysqli_num_rows($check_email) > 0) 
      {
        $registererror = 'Email already exist!';
      }
      else
      {
        $sql = "INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`) VALUES (NULL,'user_none.png', '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', 'Password123')";
        if (mysqli_query($database, $sql)) 
        {

          $userid = mysqli_insert_id($database);
          $sql2 = "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`) VALUES (NULL, '$userid', '2', '$created_date', '1')";

          if (mysqli_query($database, $sql2)) 
          {
              $message = 'New Cityvet staff has been added successfully';
          }
        }
      }
      
    }
    else
    {
      $check_username = mysqli_query($database, "SELECT * FROM users WHERE user_username = '$username'");
      $check_email = mysqli_query($database, "SELECT * FROM users WHERE user_email = '$email'");

      if (mysqli_num_rows($check_username) > 0) 
      {
        $registererror = 'Username already exist!';
      }
      else if (mysqli_num_rows($check_email) > 0) 
      {
        $registererror = 'Email already exist!';
      }
      else if(!in_array($file_type, $allowed))
      {
        $error_image = 'Only jpg, gif and png files are allowed.';
      }
      else
      {
        $sql = "INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`) VALUES (NULL,'$image', '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', 'Password123')";

        if (mysqli_query($database, $sql)) 
        {

          $userid = mysqli_insert_id($database);
          $sql2 = "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`) VALUES (NULL, '$userid', '5', '$created_date', '1')";

          if (mysqli_query($database, $sql2)) 
          {
            if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
            {
              $message = 'New IRO staff has been added successfully';
            }
          }
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

  <title>IRO - Add Cityvet</title>

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
            <h1 class="h4 mb-2 text-gray-800">Add Cityvet Staff</h1>
          </div>

          <div class="row">

            <!-- Area Chart -->
              

            <!-- Pie Chart -->
            <div class="col-xl-12 col-lg-7 ">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                <h6 class="m-0 font-weight-bold text-primary">User Information</h6>
                </div>
                <!-- Card Body -->
                <div class="form-group">
                <script>
                setTimeout(function(){
                  document.getElementById('info-message').style.display = 'none';
                }, 5000);
                </script>
                <center>
                <?php   if(!empty($message)): ?>
                  <div class="alert alert-success" id="info-message">
                    <?php 
                    echo "<span style='font-size: 16px'>$message</span>"; // success message
                    ?>
                  </div>
                <?php   endif; ?>

                <?php   if(!empty($registererror)): ?>
                  <div class="alert alert-danger" id="info-message">
                    <?php 
                    echo "<span style='font-size: 16px'>$registererror</span>"; // error message
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
                </div>
                <div class="card-body">
                <form class="user" method="post" action="superadmin_addcity.php" enctype="multipart/form-data">
                  <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Profile Picture</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01">
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">First Name</h1>
                        <p id="firstname_error" class="alert-danger"></p>
                        <p id="number_error" class="alert-danger"></p>
                        <input type="text" class="form-control form-control-user" name="fname" id="firstname" value="<?= isset($_POST['fname']) ? $_POST['fname'] : ''; ?>">
                    </div>
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-2">
                      <h1 class="h5 mb-0 text-gray-800">Last Name</h1>
                      <p id="lastname_error" class="alert-danger"></p>
                      <input type="text" class="form-control form-control-user" name="lname" id="lastname" value="<?= isset($_POST['lname']) ? $_POST['lname'] : ''; ?>">
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-3">
                      <h1 class="h5 mb-0 text-gray-800">Nickname</h1>
                      <p id="nname_error" class="alert-danger"></p>
                      <input type="text" class="form-control form-control-user" name="nname" id="nname" value="<?= isset($_POST['nname']) ? $_POST['nname'] : ''; ?>">
                    </div>
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-4">
                      <h1 class="h5 mb-0 text-gray-800">Mobile Number</h1>
                      <p id="cnumber_error" class="alert-danger"></p>
                      <input type="text" class="form-control form-control-user" name="contact" id="cnumber" placeholder="09######### / +639########" required value=<?= isset($_POST['cnumber']) ? $_POST['cnumber'] : ''; ?> >
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-5">
                      <h1 class="h5 mb-0 text-gray-800">Occupation</h1>
                      <p id="occupation_error" class="alert-danger"></p>
                      <input type="text" class="form-control form-control-user" name="occupation" id="occupation" value="<?= isset($_POST['occupation']) ? $_POST['occupation'] : ''; ?>" required> 
                    </div>
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-6">
                    <h1 class="h5 mb-0 text-gray-800">Address</h1>
                    <p id="address_error" class="alert-danger"></p>
                    <input type="text" class="form-control form-control-user" name="address" id="address" value="<?= isset($_POST['address']) ? $_POST['address'] : ''; ?>" required>
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-7">
                    <h1 class="h5 mb-0 text-gray-800">Email</h1>
                    <p id="email_error" class="alert-danger"></p>
                    <input type="email" class="form-control form-control-user" name="email" id="email" value="<?= isset($_POST['email']) ? $_POST['email'] : ''; ?>" required>
                    </div>
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <div id="input-8">
                    <h1 class="h5 mb-0 text-gray-800">Username</h1>
                    <p id="username_error" class="alert-danger"></p>
                    <input type="text" class="form-control form-control-user" name="username" id="username" value="<?= isset($_POST['username']) ? $_POST['username'] : ''; ?>" required>
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="submit" name="submit" value="Submit" class="btn btn-success btn-user btn-block" id="add_user">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <a href="admin_adduser.php" class="btn btn-primary btn-user btn-block">Clear</a>
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
        $('#add_user').click(function(){
            var firstname = $('#firstname').val();
            var lastname = $('#lastname').val();
            var nname = $('#nname').val();
            var cnumber = $('#cnumber').val();
            var occupation = $('#occupation').val();
            var address = $('#address').val();
            var email = $('#email').val();
            var username = $('#username').val();
            
            //Firstname
            if ($.trim($("#firstname").val()) === "" || !firstname.match(/^[a-zA-Z0-9\s]*$/) || firstname.match(/^[0-9]*$/) || !firstname.match(/^[a-zA-Z\ ]+$/) && 
                $.trim($("#lastname").val()) === "" || !lastname.match(/^[a-zA-Z0-9\s]*$/) || lastname.match(/^[0-9]*$/) || !lastname.match(/^[a-zA-Z\ ]+$/) && 
                $.trim($("#nname").val()) === "" || !nname.match(/^[a-zA-Z0-9\s]*$/) || nname.match(/^[0-9]*$/) || !nname.match(/^[a-zA-Z\ ]+$/) && 
                $.trim($("#cnumber").val()) === "" || !cnumber.match(/^[0-9\+, ]*$/) || !cnumber.match(/^(09|\+639)\d{9}$/) && 
                $.trim($("#occupation").val()) === "" || !occupation.match(/^[a-zA-Z0-9\s]*$/) || occupation.match(/^[0-9]*$/) || !occupation.match(/^[a-zA-Z]+$/) &&
                $.trim($("#address").val()) === "" || !address.match(/^[a-zA-Z0-9\.\,\-\ ]*$/) && $.trim($("#email").val()) === "" || !email.match(/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/i) && $.trim($("#username").val()) === "" || !username.match(/^[a-zA-Z0-9\s]*$/) || !empty(username)) 
            {
              //Firstname 
              if($.trim($("#firstname").val()) === "") 
              {
                $('#firstname_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!firstname.match(/^[a-zA-Z0-9\s]*$/)) 
              {
                $("#firstname_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(firstname.match(/^[0-9]*$/)) 
              {
                $("#firstname_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!firstname.match(/^[a-zA-Z\ ]+$/))
              {
                $("#firstname_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#firstname_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Lastname
              if($.trim($("#lastname").val()) === "") 
              {
                $('#lastname_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!lastname.match(/^[a-zA-Z0-9\s]*$/)) {
                $("#lastname_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(lastname.match(/^^[0-9]*$/)) {
                $("#lastname_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!lastname.match(/^[a-zA-Z\ ]+$/))
              {
                $("#lastname_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#lastname_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Nickname
              if($.trim($("#nname").val()) === "") 
              {
                $('#nname_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!nname.match(/^[a-zA-Z0-9\s]*$/)) 
              {
                $("#nname_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(nname.match(/^[0-9]*$/)) 
              {
                $("#nname_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!nname.match(/^[a-zA-Z\ ]+$/))
              {
                $("#nname_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#nname_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Contact Number
              if($.trim($("#cnumber").val()) === "") 
              {
                $('#cnumber_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!cnumber.match(/^[0-9\+, ]*$/)) 
              {
                $("#cnumber_error").html("Only + allowed for special characters");
                $("#input-1").addClass('has-error');
              }
              else if(!cnumber.match(/^(09|\+639)\d{9}$/)) 
              {
                $("#cnumber_error").html("Invalid Mobile Number ex. 09######### / +639########");
                $("#input-1").addClass('has-error');
                }
              else
              {
                $('#cnumber_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Occupation
              if($.trim($("#occupation").val()) === "") 
              {
                $('#occupation_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!occupation.match(/^[a-zA-Z0-9\s]*$/)) {
                $("#occupation_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(occupation.match(/^^[0-9]*$/)) {
                $("#occupation_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!occupation.match(/^[a-zA-Z\ ]+$/))
              {
                $("#occupation_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#occupation_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Address
              if($.trim($("#address").val()) === "") {
                $('#address_error').html("Blank spaces are not allowed");
                $('#input-1').addClass('has-error');
              }
              else if(!address.match(/^[a-zA-Z0-9\.\,\-\ ]*$/))
              {
                $("#address_error").html("Special characters are not allowed. Only - , . are allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#address_error').html("");
                $('#input-1').addClass('has-error');
              }

              //Email
              if($.trim($("#email").val()) === "") {
                $('#email_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!email.match(/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/i)) 
              {
                $("#email_error").html("Please enter valid email");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#email_error').html("");
                $('#input-1').addClass('has-error');
              }

              //Username
              if($.trim($("#username").val()) === "") {
                $('#username_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!username.match(/^[a-zA-Z0-9\s]*$/)) 
              {
                $("#username_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#username_error').html("");
                $('#input-1').addClass('has-error');
              }
              return false;
            }

            

        });
    });
</script>
</body>

</html>
