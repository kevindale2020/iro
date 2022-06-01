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
				<br>
				<h2 style="color: #b01ba5; background: url(img/get-involved/paw.png) center top no-repeat; margin: 0 auto 50px; color: #666 !important; font-size: 1.5em; letter-spacing: 1px; padding-top: 30px; ">OUR TEAM</h2>
			</div>
			</center>
		<br><br>
		<style type="text/css">
		</style>
		<div class="container">
			<h2 style="background: url(img/get-involved/paw.png) left 10px no-repeat;padding: 0px 0 0px 30px;position: relative; color: #069fdd !important; line-height: 1.5em font-size: 25px; font-weight: 700;">Nena Hernandez
			</h2>
			<span style="font-style: italic;">Founder, Executive Director, Lolo Ali Centre Manager and Manager of Pit Bull Rehabilitation Centre and President</span>
			<div class="row">
				<div class="col-md-6">
					<div class="intro-text-box text-box text-white">
						<center>
						<img src="img/ourteam/1.jpg" style=" opacity: 1.5; filter: alpha(opacity=40);" alt="">
						<br><br>
						</center>
					</div>
				</div>
				<p align="justify">When Nena repatriated to Cebu in December 2009, there were no animal rescue organizations in the Visayan region. Having volunteered in rescue organizations in the U.S., Nena understood the demand for such an organization within the community and along with her nephew, Enzo Nable, and friend, Biggie Tantuico, IRO was formed. In February 2010, IRO ‘went public’ attending its first animal-related event and the rest, they say, is history.</p>
				<p align="justify">Nena’s role within IRO extends beyond Founder and Executive Director to Manager of IRO’s Pit Bull Rehabilitation Centre based out of its Talamban shelter. Nena was heavily involved with the rescue of the Pit Bulls following a raid on a dog fighting ring in Indang, Cavite in December 2011 and in San Pablo, Laguna in March 2012. Having been active in Pit Bull rescue, rehabilitation and rehoming in the US as one of the founders of Fresno Bully Rescue, Nena had one day hoped to incorporate Pit Bull rescue into IRO’s work but the addition of 63 rescue Pit Bulls was something she had not prepared for! A new facility was set up specifically for the Pit Bulls and the mammoth task of rehabilitating the dogs and finding them each a forever home began. It was through one of these rescued Pit Bulls, Lolo Ali, that a new IRO facility was established at Nena’s own farm in Barili to provide care for special needs dogs. Nena is Manager of the Lolo Ali Centre which houses up to 10 dogs at a time that are receiving hospice care, medical treatment or rehabilitation.</p>
				<p style="justify">Though funding remains one of the biggest challenges for IRO, Nena hopes that one day the organization will be financially self-sufficient so it may have the means to not only provide for the animals in its care but assist other rescue organizations and boost animal adoption numbers. One of her proudest achievements has been the construction of IRO’s Talamban shelter where the first residents to move in were the Pit Bulls in March 2015.</p>
				<p align="justify">As it has grown over the years, Nena has seen the need for a greater awareness and education within the community in order to really make a difference in issues such as responsible pet ownership and animal welfare. “We realized early on that what IRO had to focus on was developing a long term vision where through education and being role models, IRO and other rescue organizations would no longer be needed”. Using the concept of “it takes a village to raise a child”, Nena applies this to IRO’s practices, “in order for real change to happen, it must come from society and its people”.</p>
				<p align="justify">As well as sharing her home with the residents of the Lolo Ali Centre, Nena’s ‘pack’ includes 8 dogs, 3 cats and one of IRO’s more unusual rescues, a pig called Junior.</p>
			</div>
		</div>
	</section>
	<!-- Intro section end -->


	


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

	</body>
</html>
