<?php 
include 'config.php'; //this code will allow you to connect to your database
session_start(); //to start the session

//prevent going back to guest page if there's session
if (isset($_SESSION['user_id'])) 
{
	echo "<script>window.history.back();</script>";
}
//end of code

 ?>
 
<!DOCTYPE html>
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


	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

</head>
<body>
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
				<a href="https://twitter.com/iroislandrescue?lang=en"><i style="color: black;" class="fa fa-youtube"></i></a>
			</div>
			<div class="header-bar-warp d-flex" style=" height: 100px;">
				<!-- site logo -->
				
				<img src="./img/irotransparentlogo1.png" height="70%" width="10%" alt="">
				 	
				<nav class="top-nav-area w-100">
					<div class="user-panel">
						<a href="login.php" style="display: inline-block;"><i class="fa fa-sign-in" aria-hidden="true"></i> Login</a><p style="display: inline-block; padding-left: 10px">|</p><a href="register.php" style="display: inline-block; padding-left: 10px"><i class="fa fa-user" aria-hidden="true"></i> Register</a>
					</div>
					<!-- Menu -->
					<ul class="main-menu primary-menu">
						<li><a href="index.php" ><i class="fa fa-home" aria-hidden="true"> </i> Home</a></li>
						<li><a href="#" style="background-image: url(../img/icons/arrow-down.png);"><i class="fa fa-info-circle" aria-hidden="true"></i> About Us <i class="fa fa-angle-down" aria-hidden="true"></i></a>
							<ul class="sub-menu">
								<li><a href="ourhistory.php">Our History</a></li>
								<li><a href="whatwedo.php">What We Do</a></li>
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


	<!-- Hero section -->
	<!-- <section class="hero-section overflow-hidden">
		<div class="hero-slider owl-carousel">
			<div class="hero-item set-bg d-flex align-items-center justify-content-center text-center" data-setbg="img/slider/whatwedo.jpg">
				<div class="container" style="opacity: 1">
					<h2 style="color: #f82c35; opacity: .8">WHAT WE DO</h2>
				</div>
			</div>
		</div>
	</section> -->
	<!-- Hero section end-->


	<!-- Intro section -->
	<br><br><br>
	<section class="intro-section" style="background-image: url('img/bg.jpg'); background-size: 100% 100%;">
		<center>
			<div>
				<div class="container" style="opacity: 1">
					<h2 style="color: #f82c35; opacity: .8; font-size: 60px">WHAT WE DO</h2>
				</div>
				<br>
				<h2 style="color: #b01ba5; background: url(img/get-involved/img-paw.png) center top no-repeat; margin: 0 auto 50px; color: #666 !important; font-size: 1.5em; letter-spacing: 1px; padding-top: 30px;">IRO’s work is based around 3 main principles – Rescue, Educate and Advocate.</h2>
			</div>
			</center>
		<br><br>
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<div class="intro-text-box text-box text-white">
						<center>
						<div class="top-meta"><a href="">Rescue</a></div>
						<img src="img/whatwedo/1.jpg" alt="">
						<br><br>
						</center>
						<p align="justify">IRO’s rescue work takes it out into the community where it has been involved in many cases of cruelty, abuse, neglect and abandonment. In January 2012 IRO was involved in its biggest and most challenging cases to date, the intake of 63 Pit Bulls who were rescued during a raid on a dog fighting ring in Indang, Cavite. Happily, many of the dogs rescued in this operation have found their forever homes.

						But while some of the animals arrive at the shelter with a story, the history of others is more uncertain. Many of the animals in IRO’s care are strays that have been rescued from the street and for some; their rescue story may be a longer journey. However, there is no time limit on how long an animal will remain with IRO while they await adoption into a forever home and IRO has found homes for numerous animals both locally and abroad. With over 100 animals in its care at any time and regular appeals for assistance from the public, animal rescue remains at the core of IRO’s work.</p>
					</div>
				</div>
				<div class="col-md-6">
					<div class="intro-text-box text-box text-white">
						<center>
						<div class="top-meta"><a href="">Educate</a></div>
						<img src="img/whatwedo/2.jpg" alt="">
						<br><br>
						</center>
						<p align="justify">IRO strives to be a source of education to the public about issues such as responsible pet ownership, neutering, adopting, humane treatment of animals, animal behaviour and animal health. IRO is proud to be a community-driven organization that participates in educational talks at various events, schools, groups and businesses to build awareness of animal welfare issues within the community.

						Using the concept of “it takes a village to raise a child”, IRO believes that in order for real change to happen, it must come from society and its people. Education is not only the key to addressing current concerns but about finding long-term solutions to bring about a shift in perspectives where it is hoped that one day, rescue organizations like IRO will no longer be needed.</p>
					</div>
				</div>
				<center>
					<div class="col-md-6">
					<div class="intro-text-box text-box text-white">
						<center>
						<div class="top-meta"><a href="">Educate</a></div>
						<img src="img/whatwedo/2.jpg" alt="">
						<br><br>
						</center>
						<p align="justify">IRO strives to be a source of education to the public about issues such as responsible pet ownership, neutering, adopting, humane treatment of animals, animal behaviour and animal health. IRO is proud to be a community-driven organization that participates in educational talks at various events, schools, groups and businesses to build awareness of animal welfare issues within the community.

						Using the concept of “it takes a village to raise a child”, IRO believes that in order for real change to happen, it must come from society and its people. Education is not only the key to addressing current concerns but about finding long-term solutions to bring about a shift in perspectives where it is hoped that one day, rescue organizations like IRO will no longer be needed.</p>
					</div>
				</div>
				</center>
			</div>
		</div>
	</section>
	<!-- Intro section end -->


	


	<!-- Footer section -->
	<footer class="footer-section" style="height: 30px">
			<div class="copyright">Made by <a href="" style="text-decoration: line-through;"  >REDEEMERS</a> Copyright © 2019</div>
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

	</body>
</html>
