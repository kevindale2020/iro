<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (isset($_GET['success'])) 
{
  $success = $_GET['success'];
}

//Total Incident
if (isset($_POST['load'])) 
{
  $year = $_POST['year'];

  if ($year < 2017) 
  {
    echo "<script>alert('No value from this year')</script>";
    include 'report_2020.php';
    include 'closed_2020.php';
  }
  else if ($year > 2020) 
  {
    echo "<script>alert('No value from this year')</script>";
    include 'report_2020.php';
    include 'closed_2020.php';
  }
  else if ($year == 2017) 
  {
    include 'report_2017.php';
    include 'closed_2017.php';
  }
  else if($year == 2018)
  {
    include 'report_2018.php';
    include 'closed_2018.php';
  }
  else if($year == 2019)
  {
    include 'report_2019.php';
    include 'closed_2019.php';
  }
  else if($year == 2020)
  {
    include 'report_2020.php';
    include 'closed_2020.php';
  }
}
else
{
  include 'report_2020.php';
  include 'closed_2020.php';
}

 ?>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>IRO - Incident Report</title>

  <!-- Custom fonts for this template -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
        <?php 
    if (isset($_GET['collapse']))
    {
      
    }
    else if(isset($_GET['show']))
    {
      include 'sidebar.php'; 
    }
    else
    {
      include 'sidebar.php'; 
    }
    ?>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Navbar -->
        <?php include 'navbar.php'; ?>
        <!-- End -->

        <script>
                setTimeout(function(){
                  document.getElementById('info-message').style.display = 'none';
                }, 5000);
                </script>
                <center>
                  <?php   if(!empty($success)): ?>
                    <div class="alert alert-success" id="info-message">
                      <?php 
                      echo "<span style='font-size: 16px'>$success</span>"; // success message
                      ?>
                    </div>
                  <?php   endif; ?>
                </center>

        <!-- Begin Page Content -->
        <center><button onclick="printContent('awe')" class="btn btn-success"><i class='fa fa-download'> Download as PDF</i></button></center>
        <br>
        <div class="container" style="width: 25%">
            <form method="post">
            <?php 
            if (isset($_POST['load'])) 
            {
              $year = $_POST['year'];

              if ($year == 2017) 
              {
                ?>
                <div class="row">
                  <div class="col-sm-6">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                    <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2017" />
                    <script type="text/javascript">
                    $("#datepicker").datepicker({
                        format: "yyyy",
                        viewMode: "years", 
                        minViewMode: "years"
                    });
                    </script>
                  </div>

                  <div class="col-sm-6">
                      <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
                  </div>
                </div>
                <?php
              }
              else if($year == 2018)
              {
                ?>
                <div class="row">
                  <div class="col-sm-6">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                    <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2018" />
                    <script type="text/javascript">
                    $("#datepicker").datepicker({
                        format: "yyyy",
                        viewMode: "years", 
                        minViewMode: "years"
                    });
                    </script>
                  </div>

                  <div class="col-sm-6">
                      <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
                  </div>
                </div>
                <?php
              }
              else if($year == 2019)
              {
                ?>
                <div class="row">
                <div class="col-sm-6">
                  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                  <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2019" />
                  <script type="text/javascript">
                  $("#datepicker").datepicker({
                      format: "yyyy",
                      viewMode: "years", 
                      minViewMode: "years"
                  });
                  </script>
                </div>

                <div class="col-sm-6">
                    <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
                </div>
              </div>
                <?php
              }
              else if($year == 2020)
              {
                ?>
                <div class="row">
                <div class="col-sm-6">
                  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                  <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2020" />
                  <script type="text/javascript">
                  $("#datepicker").datepicker({
                      format: "yyyy",
                      viewMode: "years", 
                      minViewMode: "years"
                  });
                  </script>
                </div>

                <div class="col-sm-6">
                    <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
                </div>
              </div>
                <?php
              }
              else
              {
                ?>
                <div class="row">
                <div class="col-sm-6">
                  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                  <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2020" />
                  <script type="text/javascript">
                  $("#datepicker").datepicker({
                      format: "yyyy",
                      viewMode: "years", 
                      minViewMode: "years"
                  });
                  </script>
                </div>

                <div class="col-sm-6">
                    <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
                </div>
              </div>
                <?php
              }
            }
            else
            {
              ?>
              <div class="row">
              <div class="col-sm-6">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
                <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css" rel="stylesheet"/>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


                <input type="text" id="datepicker" class="form-control" name="year" autocomplete="off" value="2020" />
                <script type="text/javascript">
                $("#datepicker").datepicker({
                    format: "yyyy",
                    viewMode: "years", 
                    minViewMode: "years"
                });
                </script>
              </div>

              <div class="col-sm-6">
                  <input type="submit" class="btn btn-success" name="load" value="Load" style="float: left;">
              </div>
            </div>
              <?php
            }

             ?>
          </form>
          </div>
        </center>
        <div id="awe" style="padding: 5px">
        <center>
          <img src="./img/logo.png" height="100" width="300" alt="" id="logo">
          <br><br>
          <h1 class="h3 mb-0 text-gray-800">SOLVED INCIDENT REPORTED</h1>
        </center>

        <?php 
          if (isset($_POST['load'])) 
          {
            $year = $_POST['year'];

            if ($year == 2017) 
            {
              ?><center><h1>2017</h1></center><?php
            }
            else if ($year == 2018) 
            {
              ?><center><h1>2018</h1></center><?php
            }
            else if ($year == 2019) 
            {
              ?><center><h1>2019</h1></center><?php
            }
            else if ($year == 2020) 
            {
              ?><center><h1>2020</h1></center><?php
            }
            else
            {
              ?><center><h1>2020</h1></center><?php
            }

          }
          else
          {
            ?><center><h1>2020</h1></center><?php
          }

           ?>
        <br>
        

        
        <br>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Solved Incidents</h6>
            </div>
            <div class="card-body">
              <div id="case_closed" style="height: 400px; width: 100%; "></div>
            </div>
          </div>
        </div>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Solved Incidents References</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Report Type</th>
              <th style="color: white">Title</th>
              <th style="color: white">Description</th>
              <th style="color: white">Address</th>
              <th style="color: white">Date Reported</th>
              <th style="color: white">Date Closed</th>
              <th style="color: white">Status</th>
            </tr>
          </thead>
          <tbody>
            <?php
            while($row = mysqli_fetch_array($case_closed))
            {
              echo "<tr>";
              echo "<td>".$row['report_type_desc']."</td>";
              echo "<td>".$row['title']."</td>";
              echo "<td>".$row['description']."</td>";
              echo "<td>".$row['reporter_location']."</td>";
              echo "<td>".$row['date_reported']."</td>";
              echo "<td>".$row['closed_date']."</td>";
              if ($row['doc_status_id'] == 1) 
              {
                echo "<td>Posted</td>";
              }
              else if ($row['doc_status_id'] == 5) 
              {
                echo "<td>".$row['doc_status_desc']."</td>";
              }
              else if ($row['doc_status_id'] == 6) 
              {
                echo "<td>".$row['doc_status_desc']."</td>";
              }
              
              echo "</tr>";
            }
            ?>
          </tbody>
          </table>
              </div>
            </div>
          </div>
        </div>
        </div>

        <hr>

        <center><button onclick="printContent('pdf')" class="btn btn-success"><i class='fa fa-download'> Download as PDF</i></button></center>
        <br>
        <div id="pdf" style="padding: 5px">
        <center>
          <img src="./img/logo.png" height="100" width="300" alt="" id="logo">
          <br><br>
          <h1 class="h3 mb-0 text-gray-800">TOTAL INCIDENT REPORTED</h1>

          <?php 
          if (isset($_POST['load'])) 
          {
            $year = $_POST['year'];

            if ($year == 2017) 
            {
              ?><center><h1>2017</h1></center><?php
            }
            else if ($year == 2018) 
            {
              ?><center><h1>2018</h1></center><?php
            }
            else if ($year == 2019) 
            {
              ?><center><h1>2019</h1></center><?php
            }
            else if ($year == 2020) 
            {
              ?><center><h1>2020</h1></center><?php
            }
            else
            {
              ?><center><h1>2020</h1></center><?php
            }

          }
          else
          {
            ?><center><h1>2020</h1></center><?php
          }

           ?>
        <br>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Total Incident Reported</h6>
            </div>
            <div class="card-body">
              <div id="total_incident" style="height: 400px; width: 100%; "></div>
            </div>
          </div>
        </div>
        <div class="container-fluid">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Total Incident Reported References</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table id="example" class="table table-hover">
          <thead class="bg-primary" style="background-color: blue;">
            <tr>
              <th style="color: white">Report Type</th>
              <th style="color: white">Title</th>
              <th style="color: white">Description</th>
              <th style="color: white">Address</th>
              <th style="color: white">Date Reported</th>
              <th style="color: white">Status</th>
            </tr>
          </thead>
          <tbody>
            <?php
            while($row = mysqli_fetch_array($total_incident))
            {
              echo "<tr>";
              echo "<td>".$row['report_type_desc']."</td>";
              echo "<td>".$row['title']."</td>";
              echo "<td>".$row['description']."</td>";
              echo "<td>".$row['reporter_location']."</td>";
              echo "<td>".$row['date_reported']."</td>";
              echo "<td>Acknowledged</td>";
              
              echo "</tr>";
            }
            ?>
          </tbody>
          </table>
              </div>
            </div>
          </div>
        </div>
      
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Team Redeemers 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="../logout.php">Logout</a>
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

  <!-- Page level plugins -->
  <script src="vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="js/demo/datatables-demo.js"></script>

  <script src="jss/jquery-1.11.1.min.js"></script>
  <script src="jss/bootstrap.min.js"></script>
  <script src="jss/jquery.dataTables.min.js"></script>
  <script src="jss/dataTables.bootstrap4.min.js"></script> 
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> 
  <script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>


  <script type="text/javascript">
  var January = <?php echo $January ?>;
  var February = <?php echo $February ?>;
  var March = <?php echo $March ?>;
  var April = <?php echo $April ?>;
  var May = <?php echo $May ?>;
  var June = <?php echo $June ?>;
  var July = <?php echo $July ?>;
  var August = <?php echo $August ?>;
  var September = <?php echo $September ?>;
  var October = <?php echo $October ?>;
  var November = <?php echo $November ?>;
  var December = <?php echo $December ?>;
  new Morris.Bar({
  // ID of the element in which to draw the chart.
  element: 'case_closed',
  // Chart data records -- each entry in this array corresponds to a point on
  // the chart.
  data: [
    { year: 'Jan', value: January },
    { year: 'Feb', value: February },
    { year: 'Mar', value: March },
    { year: 'Apr', value: April },
    { year: 'May', value: May },
    { year: 'Jun', value: June },
    { year: 'Jul', value: July },
    { year: 'Aug', value: August },
    { year: 'Sept', value: September },
    { year: 'Oct', value: October },
    { year: 'Nov', value: November },
    { year: 'Dec', value: December }

  ],
  barColors: ['#40e0d0'],
  // The name of the data record attribute that contains x-values.
  xkey: 'year',
  // A list of names of data record attributes that contain y-values.
  ykeys: ['value'],
  // Labels for the ykeys -- will be displayed when you hover over the
  // chart.
  labels: ['Value']
});
</script>

<script type="text/javascript">
  var January1 = <?php echo $January1 ?>;
  var February1 = <?php echo $February1 ?>;
  var March1 = <?php echo $March1 ?>;
  var April1 = <?php echo $April1 ?>;
  var May1 = <?php echo $May1 ?>;
  var June1 = <?php echo $June1 ?>;
  var July1 = <?php echo $July1 ?>;
  var August1 = <?php echo $August1 ?>;
  var September1 = <?php echo $September1 ?>;
  var October1 = <?php echo $October1 ?>;
  var November1 = <?php echo $November1 ?>;
  var December1 = <?php echo $December1 ?>;
  new Morris.Bar({
  // ID of the element in which to draw the chart.
  element: 'total_incident',
  // Chart data records -- each entry in this array corresponds to a point on
  // the chart.
  data: [
    { year: 'Jan', value: January1 },
    { year: 'Feb', value: February1 },
    { year: 'Mar', value: March1 },
    { year: 'Apr', value: April1 },
    { year: 'May', value: May1 },
    { year: 'Jun', value: June1 },
    { year: 'Jul', value: July1 },
    { year: 'Aug', value: August1 },
    { year: 'Sept', value: September1 },
    { year: 'Oct', value: October1 },
    { year: 'Nov', value: November1 },
    { year: 'Dec', value: December1 }

  ],
  barColors: ['#40e0d0'],
  // The name of the data record attribute that contains x-values.
  xkey: 'year',
  // A list of names of data record attributes that contain y-values.
  ykeys: ['value'],
  // Labels for the ykeys -- will be displayed when you hover over the
  // chart.
  labels: ['Value']
});
</script>
  <script type="text/javascript">
   $(document).ready(function() {
      $('#example').DataTable(
          
           {     

        "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
          "iDisplayLength": 5
         } 
          );
  } );


  function checkAll(bx) {
    var cbs = document.getElementsByTagName('input');
    for(var i=0; i < cbs.length; i++) {
      if(cbs[i].type == 'checkbox') {
        cbs[i].checked = bx.checked;
      }
    }
  }
  </script>

  </script>
  <script type="text/javascript">
   $(document).ready(function() {
      $('#example2').DataTable(
          
           {     

        "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
          "iDisplayLength": 5
         } 
          );
  } );


  function checkAll(bx) {
    var cbs = document.getElementsByTagName('input');
    for(var i=0; i < cbs.length; i++) {
      if(cbs[i].type == 'checkbox') {
        cbs[i].checked = bx.checked;
      }
    }
  }
  </script>

  <script src="js/printThis.js"></script>
    <script>
      function printContent(el){
        var restorepage = document.body.innerHTML;
        var printcontent = document.getElementById(el).innerHTML;
        document.body.innerHTML = printcontent;
        window.print();
        document.body.innerHTML = restorepage;
    }
      </script>
</body>

</html>
