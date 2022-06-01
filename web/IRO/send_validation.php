<?php 
include 'config.php'; //this code will allow you to connect to your database
session_start(); //this code allow the system to enable the session

if (isset($_SESSION['user_id'])) 
{
  header("location: index404.php");
}

if (isset($_GET['success'])) 
{

}
else
{
  header("location: register.php");
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
    <div class="row justify-content-center">
      <div class="col-xl-10 col-lg-12 col-md-9">
        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <div class="row">
              <div class="col-lg-12">
                <div class="p-5">
                  <center><img src="img/logo.png" style="width: 60%" height="80px"></center>
                  <br><br>
                  <h3 class="alert alert-success"><i class="fa fa-check"></i> A verification link has been sent to your email address.</h3>
                  <hr>
                  <center><h3>Please click on the link that has been sent to your email address to verify your account and continue the registration process.</h3></center>
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

  <!-- https://code.tutsplus.com/tutorials/how-to-implement-email-verification-for-new-members--net-3824 -->
</body>

</html>
