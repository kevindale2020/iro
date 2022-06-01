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
      <a class="icons" href="../city_vet/cityvet_index.php">
        <div class="sidebar-brand-icon">
          <img src="img/logo.png">
        </div>
        
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="../city_vet/cityvet_index.php">
          <i class="fas fa-fw fa-home"></i>
          <span>Home</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Cityvet
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
            <a class="collapse-item" href="cityvet_adduser.php">Add Cityvet Staff</a>
            <a class="collapse-item" href="cityvet_viewuser.php">View User</a>
          </div>
        </div>
      </li>

      <!-- Nav Item - Pages Collapse Menu -->

      <!-- Divider -->
      <hr class="sidebar-divider">

      

    </ul>
    <!-- End of Sidebar -->