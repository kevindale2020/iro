<!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <style>
    #icons {
      position: absolute;
      bottom: 22%;
      right: 8%;
      width: 400px;
      height: 80px;
      z-index: 8;
      transform: rotate(-57deg); 
      -ms-transform: rotate(-57deg); 
      -webkit-transform: rotate(-57deg); 
      -moz-transform: rotate(-57deg);
    }
    img { 
      max-width: 100%; 
      height: auto; 
    }
    </style>
      <!-- Sidebar - Brand -->
      <a class="icons" href="?collapse" title="Hide Sidebar">
        <div class="sidebar-brand-icon">
          <img src="img/logo.png">
        </div>
        
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="../admin/admin_index.php">
          <i class="fas fa-fw fa-home"></i>
          <span>Home</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Admin
      </div>

      <!-- Nav Item - Utilities Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#user" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-user"></i>
          <span>User</span>
        </a>
        <div id="user" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">User option</h6>
            <?php 
            if ($_SESSION['role_id'] == 3) 
            {
              # code...
            }
            else
            {
              ?><a class="collapse-item" href="admin_adduser.php">Add User</a><?php
            }

             ?>

             <?php 
            if ($_SESSION['role_id'] == 3) 
            {
              ?><a class="collapse-item" href="volunteer_viewuser.php">View User</a><?php
            }
            else
            {
              ?><a class="collapse-item" href="admin_viewuser.php">View User</a><?php
            }

             ?>
            <!-- <a class="collapse-item" href="add_volunteer.php">Add Volunteer</a> -->
            
          </div>
        </div>
      </li>

      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#report" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-file"></i>
          <span>Generate Reports</span>
        </a>
        <div id="report" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Reports option</h6>
            <a class="collapse-item" href="admin_listofadoptedpets.php">List of Adopted Pets</a>
            <a class="collapse-item" href="admin_listofincidents.php">List of Incidents</a>
            <a class="collapse-item" href="admin_donation.php">Total Donations</a>
          </div>
        </div>
      </li>

       <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#merchandise" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa fa-archive"></i>
          <span>Merchandise</span>
        </a>
        <div id="merchandise" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Merchandise option</h6>
            <a class="collapse-item" href="admin_addmerchandise.php">Add Merchandise</a>
            <a class="collapse-item" href="admin_viewmerchandise.php">View Merchandise</a>
          </div>
        </div>
      </li>

      

      <!-- Nav Item - Pages Collapse Menu -->

      <!-- Nav Item - Utilities Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-bars"></i>
          <span>Request</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Request Type</h6>
            <a class="collapse-item" href="admin_adoptionrequest.php">Adoption</a>
            <a class="collapse-item" href="admin_volunteerrequest.php">Volunteer</a>
            <a class="collapse-item" href="admin_donationrequest.php">Donation</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Pages Collapse Menu -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#pet" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-paw"></i>
          <span>Pet</span>
        </a>
        <div id="pet" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Pet Option</h6>
            <a class="collapse-item" href="admin_addpet.php">Add Pet</a>
            <a class="collapse-item" href="admin_viewpet.php?showAll">View Pet</a>
          </div>
        </div>
      </li>


       <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Reports
      </div>

      <li class="nav-item">
        <a class="nav-link" href="admin_lostandfound.php">
          <i class="fas fa-fw fa-paw"></i>
          <span>Lost and Found</span></a>
      </li>

      <li class="nav-item">
        <a class="nav-link" href="admin_homelesspet.php">
          <i class="fas fa-fw fa-paw"></i>
          <span>Homeless Pet</span></a>
      </li>

      <li class="nav-item">
        <a class="nav-link" href="admin_animalcruelty.php">
          <i class="fas fa-fw fa-paw"></i>
          <span>Animal Cruelty</span></a>
      </li>



    </ul>
    <!-- End of Sidebar -->


    