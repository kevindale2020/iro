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
	<?php include 'header.php'; ?>
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
				<h2 style="color: #b01ba5; background: url(img/get-involved/paw.png) center top no-repeat; margin: 0 auto 50px; color: #666 !important; font-size: 1.5em; letter-spacing: 1px; padding-top: 30px;">OUR HISTORY</h2>
			</div>
			</center>
		<br><br>
		<style type="text/css">
			.paw li {
			    background: url(img/get-involved/paw.png) left 12px no-repeat;
			    padding: 8px 0 8px 30px;
			    position: relative;
			    list-style-type: none;
			}

			.spacer-2 {
			    display: block;
			    margin-bottom: 20px !important;
			}
		</style>
		<div class="container">
			<p align="justify">In February 2010 Island Rescue Organization Inc. (IRO), the first non-profit animal rescue organization in Cebu, was formally established by a small team of dedicated volunteers. The goals of the organization remain the same today:</p>
			<ul class="paw" style="display: block;margin-bottom: 20px !important;">
				<li>To promote responsible ownership and humane treatment of all animals through cooperation, outreach, and education with the community.</li>
				<li>To advocate the development and enforcement of humane and effective animal welfare laws.</li>
				<li>To rescue and rehabilitate abused, abandoned and neglected animals.</li>
				<li>To provide a healthy, loving, no-kill sanctuary for rescue animals.</li>
				<li>To promote adoption of companion animals into permanent homes.</li>
				<li>To promote control and health management of the animal population through spaying/neutering and vaccinating.</li>
			</ul>
			<p align="justify">IRO cares for over 100 companion animals who are awaiting adoption into forever homes.  IRO also cares for a number of animals who are receiving treatment for medical injuries or illnesses or are being rehabilitated for behavioural issues relating to their backgrounds.</p>
			<p align="justify">IRO is run entirely by a team of volunteers and as a community-focused organization; IRO not only relies on the support of the public (both local and international) for funding but also to assist with the organization’s operations.</p>
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
