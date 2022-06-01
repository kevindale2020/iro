<?php 
include 'config.php'; //this code will allow you to connect to your database
session_start(); //this code allow the system to enable the session

if (isset($_SESSION['user_id'])) 
{
  header("location: index404.php");
}

//Register Code
if (isset($_POST['register'])) 
{
  $created_date = date("Y-m-d");
  $fname = mysqli_real_escape_string($database, $_POST['fname']); //Variable name
  $nname = mysqli_real_escape_string($database, $_POST['nname']); //Variable name
  $lname = mysqli_real_escape_string($database, $_POST['lname']); //Variable name
  $address = mysqli_real_escape_string($database, $_POST['address']); //Variable name
  $contact = mysqli_real_escape_string($database, $_POST['contact']); //Variable name
  $email = mysqli_real_escape_string($database, $_POST['email']); //Variable name
  $occupation = mysqli_real_escape_string($database, $_POST['occupation']); //Variable name
  $username = mysqli_real_escape_string($database, $_POST['username']); //Variable name
  $password = mysqli_real_escape_string($database, $_POST['password']); //Variable name
  $cpassword = mysqli_real_escape_string($database, $_POST['cpassword']); //Variable name
  $vkey = md5( rand(0,1000) );
  $success ='';
  $error = '';
  
  $email_checker = mysqli_query($database, "SELECT * FROM users WHERE user_email = '$email'");
  $username_checker = mysqli_query($database, "SELECT * FROM users WHERE user_username = '$username'");

  if ($password != $cpassword) //Password Match Checker
  {
    $error = 'Password does not match';
  }
  elseif (mysqli_num_rows($email_checker) > 0) 
  {
    $error = 'Email is already exist!';
  }
  elseif (mysqli_num_rows($username_checker) > 0) 
  {
    $error = 'Username is already exist!';
  }
  else //mao ni ang code nga mo execute kung ang password kay match na og ang mga fields kay dili na empty
  {
    $sql = "INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`,`vkey`) VALUES (NULL,'user_none.png', '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', '$password', '$vkey')";
    if (mysqli_query($database, $sql)) 
    {

      $userid = mysqli_insert_id($database);
            $sql2 = "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`) VALUES (NULL, '$userid', '1', '$created_date', '1')";

            if (mysqli_query($database, $sql2)) 
            {
              require './Android/phpmailer/PHPMailerAutoload.php';
    
            $mail = new PHPmailer(true);
            try {
              $mail->SMTPDebug = 1; //*
              $mail->isSMTP(); //*
              $mail->Host='smtp.gmail.com'; //*
              $mail->SMTPAuth=true; //*
              $mail->Username='island.rescue.orgranization@gmail.com'; //email username
              $mail->Password='iro123123'; //email password
              $mail->SMTPSecure='tls'; //*
              $mail->Port=587; //*

              $mail->setFrom('island.rescue.orgranization@gmail.com','Iro System'); //email nimo, title sa email 
              $mail->addAddress($email); //kung kinsay sendan nimo

              $body = '
              
              <p>Thanks for signing up!</p>
              <p>Your account has been created, you can login with the following credentials after you have activated your account by pressing the url below.</p>
              <p>------------------------</p>
              <p>Username: '.$username.'</p>
              <p>Password: '.$password.'</p>
              <p>------------------------</p>
              
              <p>Please click this link to activate your account:</p>
              <p>http://localhost:8080/iro/verify_account.php?email='.$email.'&hash='.$vkey.'</p>
               
              '; //email body kung unsay e message nimo
                        

              $mail->isHTML(true); //*
              $mail->Subject='Account Verification'; //Subject sa email
              $mail->Body=$body; 
              $mail->AltBody=strip_tags($body);

              $mail->send();
              echo "<script>window.location.href='send_validation.php?success';</script>";
            } catch (Exception $e){
              echo "Message could not send!";
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

  <title>IRO - Register</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body background="background.jpg">

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-12">
                <div class="p-5">
                  <center><img src="img/logo.png" style="width: 60%" height="80px"></center>
                  <br><br>
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Registration</h1>
                  </div>
                  <script>
                  setTimeout(function(){
                    document.getElementById('info-message').style.display = 'none';
                    document.getElementById('span').style.display = 'none';
                  }, 5000);
                  </script>
                  <?php   if(!empty($error)): ?>
                    <center>
                      <div class="alert alert-danger" id="info-message">
                        <?php echo "<span style='font-size: 16px'>$error</span>"; // error message
                        ?>
                      </div> 
                    </center>
                  <?php   endif; ?>
                  <form class="user" method="post" action="register.php">
                    <div class="form-group form-row">
                      <div class="col-sm-4">
                        <div id="input-1">
                          <p id="username_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="fname" placeholder="Firstname" id="firstname" autocomplete="off" value="<?= isset($_POST['fname']) ? $_POST['fname'] : ''; ?>" required>
                        </div>
                      </div>
                      <div class="col-sm-4">
                        <div id="input-1">
                          <p id="lastname_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="lname" placeholder="Lastname" id="lastname" autocomplete="off" value="<?= isset($_POST['lname']) ? $_POST['lname'] : ''; ?>" required>
                        </div>
                      </div>
                      <div class="col-sm-4">
                        <div id="input-1">
                          <p id="nname_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="nname" placeholder="Nickname" id="nname" autocomplete="off" value="<?= isset($_POST['nname']) ? $_POST['nname'] : ''; ?>" required>
                        </div>
                      </div>
                   
                      <div class="col-sm-12">
                        <div id="input-1">
                          <p id="address_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="address" placeholder="Address" id="address" autocomplete="off" value="<?= isset($_POST['address']) ? $_POST['address'] : ''; ?>" required>
                        </div>
                      </div>
                   
                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="cnumber_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="contact" placeholder="Contact Number" id="cnumber" autocomplete="off" value="<?= isset($_POST['contact']) ? $_POST['contact'] : ''; ?>" required>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="occupation_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="occupation" placeholder="Occupation" id="occupation" autocomplete="off" value="<?= isset($_POST['occupation']) ? $_POST['occupation'] : ''; ?>" required> 
                        </div>
                      </div>
                    
                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="email_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="email" placeholder="Email" id="email" autocomplete="off" value="<?= isset($_POST['email']) ? $_POST['email'] : ''; ?>" required>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="username_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="username" placeholder="Username" id="username" autocomplete="off" value="<?=isset($_POST['username']) ? $_POST['username'] : ''; ?>" required>
                        </div>
                      </div>

                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="password1_error" class="alert-danger"></p>
                          <input type="password" class="form-control" name="password" placeholder="Password" autocomplete="off" required>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div id="input-1">
                          <p id="password2_error" class="alert-danger"></p>
                          <input type="password" class="form-control" name="cpassword" placeholder="Confirm Password" autocomplete="off" required>
                        </div>
                      </div>
                    </div>

                    <div class="form-group form-row">
                      <div class="col-sm-6">
                        <a href="./IRO_WEB/index.php" class="btn btn-primary btn-block">Cancel</a>
                      </div>
                       <div class="col-sm-6">
                        <input type="submit" name="register" class="btn btn-success btn-block" value="Register" id="register">
                      </div>
                    </div>
                    
                   
                  </form>
                </div>
              </div>
            </div>
          </div>
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
  <script>
    $(document).on('change', '.custom-file-input', function (event) {
    $(this).next('.custom-file-label').html(event.target.files[0].name);
    })
  </script>


</body>

</html>
