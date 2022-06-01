<?php 
include 'config.php'; //this code will allow you to connect to your database
session_start(); //this code allow the system to enable the session

if (isset($_SESSION['user_id'])) 
{
  header("location: index404.php");
}

//Login Code
if (isset($_POST['login'])) 
{
  $username = mysqli_real_escape_string($database, $_POST['username']); 
  $password = mysqli_real_escape_string($database, $_POST['password']);
  $loginerror = '';
  $login = mysqli_query($database, "SELECT * FROM users INNER JOIN user_roles ON users.user_id = user_roles.user_id WHERE (users.user_username = '$username' AND users.user_password = '$password')");

  if (mysqli_num_rows($login) == 1) 
  {
    while ($data = mysqli_fetch_array($login)) 
    {
      if ($data['role_id'] == 2) 
      {
        if ($data['is_active'] == 0) 
        {
          $loginerror = 'Your account has been temporary blocked';
        }
        else
        {
          $_SESSION['user_id'] = $data['user_id'];
          $_SESSION['role_id'] = $data['role_id'];
          header("location: ../iro/admin/admin_index.php");
        }
      }
      else if($data['role_id'] == 3)
      {
        if ($data['is_active'] == 0) 
        {
          $loginerror = 'Your account has been temporary blocked';
        }
        else
        {
          $_SESSION['user_id'] = $data['user_id'];
          $_SESSION['role_id'] = $data['role_id'];
          header("location: ../iro/admin/admin_index.php");
        }
      }
      else if($data['role_id'] == 5)
      {
        $_SESSION['user_id'] = $data['user_id'];
        $_SESSION['role_id'] = $data['role_id'];
        header("location: ../iro/city_vet/cityvet_index.php");
      }
      else if($data['role_id'] == 4)
      {
        $_SESSION['user_id'] = $data['user_id'];
        $_SESSION['role_id'] = $data['role_id'];
        header("location: ../iro/superadmin/superadmin_index.php");
      }
      else if($data['role_id'] == 1)
      {
        $loginerror = 'User account is not allowed here';
      }
    }
  }
  else
  {
    $loginerror = 'Invalid Credentials';
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

  <title>IRO - Login</title>

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
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <img src="img/logo.png" width="330px" height="80px">
                  <br><br>
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                  </div>
                  <script>
                  setTimeout(function(){
                    document.getElementById('info-message').style.display = 'none';
                    document.getElementById('span').style.display = 'none';
                  }, 5000);
                  </script>
                  <?php   if(!empty($loginerror)): ?>
                    <center>
                      <div class="alert alert-danger" id="info-message">
                        <?php echo "<span style='font-size: 16px'>$loginerror</span>"; // error message
                        ?>
                      </div> 
                    </center>
                  <?php   endif; ?>
                  <form class="user" method="post" action="index.php">
                    <div class="form-group">
                      <div id="input-1">
                        <p id="username_error" class="alert-danger"></p>
                        <input type="text" class="form-control" name="username" id="username" placeholder="Username" autocomplete="off" value="<?= isset($_POST['username']) ? $_POST['username'] : ''; ?>">
                      </div>
                    </div>

                    <div class="form-group">
                     <div class="form-group">
                      <div id="input-2">
                        <p id="password_error" class="alert-danger"></p>
                      </div>
                     <div class="input-group mb-3">
                      <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                       <div class="input-group-prepend">
                        <span class="input-group-text"><i id="pass-status" class="fa fa-eye" aria-hidden="true" onClick="viewPassword()"></i></span>
                      </div>
                    </div>

                    

                    <script type="text/javascript">
                      function viewPassword()
                      {
                        var passwordInput = document.getElementById('password');
                        var passStatus = document.getElementById('pass-status');
                       
                        if (passwordInput.type == 'password'){
                          passwordInput.type='text';
                          passStatus.className='fa fa-eye-slash';
                          
                        }
                        else{
                          passwordInput.type='password';
                          passStatus.className='fa fa-eye';
                        }
                      }
                    </script>
                    </div>
                    <div class="form-group">
                      <br>
                      
                    </div>
                     <div class="form-group">
                      
                    </div>
                    <input type="submit" name="login" class="btn btn-primary btn-user btn-block" value="Login" id="login">
                   
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

  <script type="text/javascript">

    function myFunction() {
    var x = document.getElementById("show_password");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }

    $(document).ready(function(){
        $('#login').click(function(){
            var username = $('#username').val();
            var password = $('#password').val();
            //validate if empty
            if(username==''||password=='')
            {
                if(username=='') {
                    $('#username_error').html("Please fill up this field");
                    $('#input-1').addClass('has-error');
                }
                if(password=='') {
                    $('#password_error').html("Please fill up this field");
                    $('#input-2').addClass('has-error');
                }
                return false;
            }
            //validate if blank spaces
            if(username.replace(/\s/g, "").length<=0||password.replace(/\s/g, "").length<=0)
            {
                if(username.replace(/\s/g, "").length<=0) {
                    $('#username_error').html("Blank spaces are not allowed");
                    $('#input-1').addClass('has-error');
                }
                if(password.replace(/\s/g, "").length<=0) {
                    $('#password_error').html("Blank spaces are not allowed");
                    $('#input-2').addClass('has-error');
                }
                return false;
            }
            //validate if letters
            if(!username.match(/^[a-zA-Z0-9\_]*$/)||!password.match(/^[a-zA-Z0-9\s]*$/))
            {
                if(!username.match(/^[a-zA-Z0-9\_]*$/)) {
                    $("#username_error").html("Special characters are not allowed");
                    $("#input-1").addClass('has-error');
                }
                if(!password.match(/^[a-zA-Z0-9\s]*$/)) {
                    $("#password_error").html("Special characters are not allowed");
                    $("#input-2").addClass('has-error');
                }
                return false;
            }
        });
    });
</script>

</body>

</html>
