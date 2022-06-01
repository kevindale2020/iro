<?php 
include 'config.php'; //this code will allow you to connect to your database
session_start(); //this code allow the system to enable the session

//prevent going back to guest page if there's session
if (isset($_SESSION['user_id'])) 
{
    echo "<script>window.history.back();</script>";
}
//end of code

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
	$message ='';
	$registererror = '';
	
	if ($password != $cpassword) //Password Match Checker
	{
		$registererror = 'Password does not match';
	}
	else //mao ni ang code nga mo execute kung ang password kay match na og ang mga fields kay dili na empty
	{
		$sql = "INSERT INTO `users` (`user_id`, `user_image`, `user_fname`, `user_lname`, `user_nname`, `user_address`, `user_email`, `user_contact`, `user_occupation`, `user_username`, `user_password`) VALUES (NULL,'user_none.png', '$fname', '$lname', '$nname', '$address', '$email', '$contact', '$occupation', '$username', '$password')";
		if (mysqli_query($database, $sql)) 
		{

			$userid = mysqli_insert_id($database);
            $sql2 = "INSERT INTO `user_roles` (`user_role_id`, `user_id`, `role_id`, `created_date`, `is_active`) VALUES (NULL, '$userid', '1', '$created_date', '1')";

            if (mysqli_query($database, $sql2)) {
               $message = 'Register Successfully';
            }


		}
	}
}
//End of Code
 ?>
<html lang="zxx">
<head>
    <title>IRO</title>
    <meta charset="UTF-8">
    <meta name="description" content="EndGam Gaming Magazine Template">
    <meta name="keywords" content="endGam,gGaming, magazine, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Favicon -->
    <link href="img/get-involved/img-paw.png" rel="shortcut icon"/>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,400i,500,500i,700,700i,900,900i" rel="stylesheet">


    <!-- Stylesheets -->
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" href="css/slicknav.min.css"/>
    <link rel="stylesheet" href="css/owl.carousel.min.css"/>
    <link rel="stylesheet" href="css/magnific-popup.css"/>
    <link rel="stylesheet" href="css/animate.css"/>

    <!-- Main Stylesheets -->
    <link rel="stylesheet" href="css/style.css"/>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css">

    <!--Custom styles-->
    <link rel="stylesheet" type="text/css" href="css/login.css">


    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body style="background-image: url('img/bg.jpg'); background-size: 100% 100%;">
    <!-- Page Preloder -->
    <div id="preloder">
        <div class="loader"></div>
    </div>

    <!-- Header section -->
    <header class="header-section">
        <div class="header-warp">
            <div class="header-social d-flex justify-content-end">
                <p style="color: black;">Follow us:</p>
                <a href="https://www.facebook.com/IslandRescueOrganization"><i style="color: black;" class="fa fa-facebook"></i></a>
                <a href="https://twitter.com/iroislandrescue?lang=en"><i style="color: black;" class="fa fa-twitter"></i></a>
                <a href="https://twitter.com/iroislandrescue?lang=en"><i style="color: black;" class="fa fa-instagram"></i></a>
            </div>
            <div class="header-bar-warp d-flex" style=" height: 100px;">
                <!-- site logo -->
                
                <img src="./img/irotransparentlogo1.png" height="70%" width="10%" alt="">
                    
                <nav class="top-nav-area w-100">
                    <div class="user-panel">
                        <a href="login.php" style="display: inline-block;"><i class="fas fa-sign-in-alt" aria-hidden="true"></i> Login</a><p style="display: inline-block; padding-left: 10px">|</p><a href="register.php" style="display: inline-block; padding-left: 10px"><i class="fa fa-user" aria-hidden="true"></i> Register</a>
                    </div>
                    <!-- Menu -->
                    <ul class="main-menu primary-menu">
                        <li><a href="index.php" ><i class="fa fa-home" aria-hidden="true"> </i> Home</a></li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-info-circle" aria-hidden="true"></i> About Us <i class="fa fa-angle-down" aria-hidden="true"></i></a>
                            <ul class="sub-menu">
                                <li><a href="ourhistory.php">Our History</a></li>
                                <li><a href="whatwedo.php" class="active">What We Do</a></li>
                                <li><a href="ourteam.php">Our Team</a></li>
                                <li><a href="game-single.html">Chapters</a></li>
                                <li><a href="game-single.html">Lolo Ali Centers</a></li>
                                <li><a href="game-single.html">Friends for the  Protection of Animals (FPA)</a></li>
                                <li><a href="game-single.html">In Memoriam</a></li>
                            </ul>
                        </li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-heart" aria-hidden="true"></i> Adoption <i class="fa fa-angle-down" aria-hidden="true"></i></a>
                            <ul class="sub-menu">
                                <li><a href="game-single.html">Adoption</a></li>
                                <li><a href="game-single.html">Why Adopt?</a></li>
                                <li><a href="game-single.html">Adoption Process</a></li>
                                <li><a href="game-single.html">Cats and Kittens for Adoption</a></li>
                                <li><a href="game-single.html">Dogs and Puppies for Adoption</a></li>
                                <li><a href="game-single.html">Look beyond the breed</a></li>
                                <li><a href="game-single.html">Happy Tails</a></li>
                                <li><a href="game-single.html">Foster</a></li>
                                <li><a href="game-single.html">Pet Adoption Process</a></li>
                            </ul>
                        </li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-group" aria-hidden="true"></i> Get Involved <i class="fa fa-angle-down" aria-hidden="true"></i></a>
                            <ul class="sub-menu">
                                <li><a href="game-single.html">Donate</a></li>
                                <li><a href="game-single.html">IRO Wishlist</a></li>
                                <li><a href="game-single.html">Guardian Angel Program</a></li>
                                <li><a href="game-single.html">Fundraising</a></li>
                                <li><a href="game-single.html">Volunteer</a></li>
                                <li><a href="game-single.html">Corporate Social Responsibility</a></li>
                                <li><a href="game-single.html">FAQs</a></li>
                                <li><a href="game-single.html">Shop</a></li>
                            </ul>
                        </li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-handshake-o" aria-hidden="true"></i> Help & Advice <i class="fa fa-angle-down" aria-hidden="true"></i></a>
                            <ul class="sub-menu">
                                <li><a href="game-single.html">Pet Health & Wellbeing</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Vacination</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Spaying and &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Neutering</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• External / Internal &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Peast Control</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Vet Visits</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Grooming</a></li>
                                <li><a href="game-single.html">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Nutrition</a></li>
                                <li><a href="game-single.html">Vet Corner</a></li>
                                <li><a href="game-single.html">Responsible Pet Owner</a></li>
                                <li><a href="game-single.html">Lost and Found</a></li>
                                <li><a href="game-single.html">Animal Welfare Act 1998</a></li>
                                <li><a href="game-single.html">Ammendment to the Welfare Act</a></li>
                                <li><a href="game-single.html">Anti-Rabies Act 2007</a></li>
                            </ul>
                        </li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-newspaper-o" aria-hidden="true"></i> News / Events <i class="fa fa-angle-down" aria-hidden="true"></i></a>
                            <ul class="sub-menu">
                                <li><a href="game-single.html">Events</a></li>
                                <li><a href="game-single.html">News</a></li>
                                <li><a href="game-single.html">Blog</a></li>
                                <li><a href="game-single.html">IRO in the Media</a></li>
                            </ul>
                        </li>
                        <li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-phone" aria-hidden="true"></i> Contact Us</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </header>
    <!-- Header section end -->

<br><br><br><br><br>
 <div id="logreg-forms">
        <form class="form-signin" method="post" action="register.php">           
            <form action="/signup/" class="form-signup">
            <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> REGISTER</h1>
            <hr>
            <img src="./img/irotransparentlogo1.png" height="20%" width="100%" alt="">
            <br><br>
            <?php 	if(!empty($message)): ?>
						<div class="alert alert-success">
							<?php echo "$message"; ?>
						</div>
			<?php 	endif; ?>
			<?php 	if(!empty($registererror)): ?>
						<div class="alert alert-danger">
							<?php echo "$registererror"; ?>
						</div>
			<?php 	endif; ?>
            <input type="text" id="user-name" class="form-control" placeholder="fistname" name="fname" required="" autofocus="">
            <input type="text" id="user-email" class="form-control" placeholder="lastname" name="lname" required autofocus="">
            <input type="text" id="user-name" class="form-control" placeholder="nickname" name="nname" required="" autofocus="">
            <input type="text" id="user-email" class="form-control" placeholder="address" name="address" required autofocus="">
            <input type="number" id="user-name" class="form-control" placeholder="contact number" name="contact" required="" autofocus="">
            <input type="email" id="user-email" class="form-control" placeholder="email address" name="email" required autofocus="">
            <input type="text" id="user-email" class="form-control" placeholder="occupation" name="occupation" required autofocus="">
            <input type="text" id="user-email" class="form-control" placeholder="username" name="username" required autofocus="">
            <input type="password" id="user-pass" class="form-control" placeholder="Password" name="password" required autofocus="">
            <input type="password" id="user-repeatpass" class="form-control" placeholder="confirm Password" name="cpassword" required autofocus="">

            <button class="btn btn-primary btn-block" type="submit" name="register"><i class="fas fa-user-plus"></i> Register</button>
            <hr>
            <button class="btn btn-success btn-block" type="button" onclick="window.location.href = 'login.php';"><i class="fas fa-sign-in-alt"></i> Login Existing Account</button>
        </form>

            <form action="/reset/password/" class="form-reset">
                <input type="email" id="resetEmail" class="form-control" placeholder="Email address" required="" autofocus="">
                <button class="btn btn-primary btn-block" type="submit">Reset Password</button>
                <a href="#" id="cancel_reset"><i class="fas fa-angle-left"></i> Back</a>
        </form>
            <br>
            
    </div>
</div>

    <style>
      .footer {
      position: fixed;
      left: 0;
      bottom: 0;
      width: 100%;
      background-color: #3374FF;
      color: white;
      text-align: center;
    }
    </style>
    <!-- Footer section -->
    <footer class="footer" style="height: 30px">
            <div style="color: black">Made by <a href="" style="text-decoration: line-through; color: black"  >REDEEMERS</a> Copyright © 2019</div>
    </footer>
    <!-- Footer section end -->

    <!--====== Javascripts & Jquery ======-->
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.slicknav.min.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/jquery.sticky-sidebar.min.js"></script>
    <script src="js/jquery.magnific-popup.min.js"></script>
    <script src="js/main.js"></script>
    <p style="text-align:center">
    <a href="http://bit.ly/2RjWFMfunction toggleResetPswd(e){
    e.preventDefault();
    $('#logreg-forms .form-signin').toggle() // display:block or none
    $('#logreg-forms .form-reset').toggle() // display:block or none
    }
    
    function toggleSignUp(e){
        e.preventDefault();
        $('#logreg-forms .form-signin').toggle(); // display:block or none
        $('#logreg-forms .form-signup').toggle(); // display:block or none
    }
    
    $(()=>{
        // Login Register Form
        $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
        $('#logreg-forms #cancel_reset').click(toggleResetPswd);
        $('#logreg-forms #btn-signup').click(toggleSignUp);
        $('#logreg-forms #cancel_signup').click(toggleSignUp);
    })g" 
        </p>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </body>
    </body>
</html>
