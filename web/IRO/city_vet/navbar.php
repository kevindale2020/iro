<!-- Topbar -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <?php if (isset($_GET['collapse'])): ?>
            <a href="?show"><i class="fa fa-bars fa-2x" style="color: gray" title="Show Sidebar"></i></a>
          <?php endif ?>
<!-- Topbar Search -->
          <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            <div class="input-group">
              <script type="text/javascript" src="../admin/js/aw.js"></script>
              Date:&nbsp;<span id="date_time" style="color: blue"></span>
              <script type="text/javascript">window.onload = date_time('date_time');</script>


            </div>
            <script type="text/javascript">
                window.onload = function () {
                    DisplayCurrentTime();
                };
                function DisplayCurrentTime() {
                    var date = new Date();
                    var hours = date.getHours() > 12 ? date.getHours() - 12 : date.getHours();
                    var am_pm = date.getHours() >= 12 ? "PM" : "AM";
                    hours = hours < 10 ? "0" + hours : hours;
                    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                    time = hours + ":" + minutes + ":" + seconds + " " + am_pm;
                    var lblTime = document.getElementById("lblTime");
                    lblTime.innerHTML = time;
                };

                $(function(){
              setInterval(DisplayCurrentTime, 1000);
            });
            </script>
            Time:&nbsp;<span style="color: blue" id="lblTime"></span>
          </form>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

            <!-- Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><?php echo "$user_fname"; ?> <?php echo "$user_lname"; ?></span>
                <?php echo "<img src='../Android/images/users/".$user_image."' class='img-profile rounded-circle'>"?>
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="cityvet_profile.php">
                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  Profile
                </a>
                <a class="dropdown-item" href="cityvet_editprofile.php">
                  <i class="fas fa-edit fa-sm fa-fw mr-2 text-gray-400"></i>
                  Edit Profile
                </a>
                <a class="dropdown-item" href="cityvet_changepassword.php">
                  <i class="fas fa-cog fa-sm fa-fw mr-2 text-gray-400"></i>
                  Change Password
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                  Logout
                </a>
              </div>
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <script>
          $(document).ready(function () {
            var notifCounter = 2;
            $("a").click(function () {
              notifCounter = notifCounter + 2;
              $("#notif").load("load-notif.php", {
                notifNewCount: notifCounter
              });
            });
          });
        </script>

        <script type="text/javascript">
         function loadDoc() {
          

          setInterval(function(){

           var xhttp = new XMLHttpRequest();
           xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
             document.getElementById("count").innerHTML = this.responseText;
            }
           };
           xhttp.open("GET", "notif_counter.php", true);
           xhttp.send();

          },100);


         }
         loadDoc();
        </script>

