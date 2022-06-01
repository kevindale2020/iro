<?php 
include '../config.php'; //this code will allow you to connect to your database

//Get Event Detailss
if (isset($_GET['id'])) 
{
  $id = $_GET['id'];

  $get_event = mysqli_query($database, "SELECT * FROM events WHERE id = $id");

  while ($row = mysqli_fetch_array($get_event)) 
  {
    $title = $row['title'];
    $desc = $row['desc'];
    $venue = $row['venue'];
    $color = $row['color'];
    $start = $row['start'];
    $arr = explode("/", $start);
    $new_start = date("m-d-Y",strtotime($start));
    $end = $row['end'];
    $arr = explode("/", $end);
    $new_end = date("m-d-Y",strtotime($end));
    $time_start = $row['time_start'];
    $time_end = $row['time_end'];
  }
}
//End of Code
 ?>

 <script>
  $(document).on('change', '.custom-file-input', function (event) {
  $(this).next('.custom-file-label').html(event.target.files[0].name);
  })
</script>
<form method="post" action="getContentEvent.php" enctype="multipart/form-data">
                  <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Event Picture</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01">
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Title</h1>
                    <input type="text" class="form-control" name="title" value="<?php echo $title ?>">
                  </div>
                </div>
                 <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Description</h1>
                    <textarea class="form-control" name="description" rows="5"><?php echo "$desc"; ?></textarea>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Venue</h1>
                    <input type="text" class="form-control" name="venue" value="<?php echo $venue ?>">
                  </div>
                </div>
                 <div class="form-group row">
                  <div class="col-sm-12 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Color</h1>
                    <select class="form-control" name="color">
                      <option style="color: <?php echo $color ?>;" class="fa fa-box" value="<?php echo $color ?>">
                        <?php 
                          if ($color == '#0071c5') {
                            echo "Dark Blue";
                          }
                          elseif ($color == '#40E0D0') {
                            echo "&#9724; Turquoise";
                          }
                          elseif ($color == '#008000') {
                            echo "&#9724; Green";
                          }
                          elseif ($color == '#FFD700') {
                            echo "&#9724; Yellow";
                          }
                          elseif ($color == '#FF8C00') {
                            echo "&#9724; Orange";
                          }
                          elseif ($color == '#FF0000') {
                            echo "&#9724; Red";
                          }
                          else
                          {
                            echo "&#9724; Black";
                          }
                        ?>
                      </option>
                      <option style="color:#0071c5;" class="fa fa-box" value="#0071c5">&#9724; Dark blue</option>
                      <option style="color:#40E0D0;" class="fa fa-box" value="#40E0D0">&#9724; Turquoise</option>
                      <option style="color:#008000;" class="fa fa-box" value="#008000">&#9724; Green</option>             
                      <option style="color:#FFD700;" class="fa fa-box" value="#FFD700">&#9724; Yellow</option>
                      <option style="color:#FF8C00;" class="fa fa-box" value="#FF8C00">&#9724; Orange</option>
                      <option style="color:#FF0000;" class="fa fa-box" value="#FF0000">&#9724; Red</option>
                      <option style="color:#000;" class="fa fa-box" value="#000">&#9724; Black</option>
                    </select>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-5 mb-3 mb-sm-0" id="start">
                    <h1 class="h5 mb-0 text-gray-800">Date Start</h1>
                    <input type="text" class="form-control" name="start" value="<?php echo $new_start ?>">
                  </div>

                  <div class="col-sm-5 mb-3 mb-sm-0" id="new_start" style="display: none;">
                    <h1 class="h5 mb-0 text-gray-800">New Date Start</h1>
                    <input type="date" class="form-control" name="start">
                  </div>

                  <script>
                    function edit_start() {
                      document.getElementById('new_start').style.display = "";
                      document.getElementById('start').style.display = "none";
                      document.getElementById('btn_edit1').style.display = "none";
                      document.getElementById('cancel_edit1').style.display = "";
                    }

                     function cancel_start() {
                      document.getElementById('new_start').style.display = "none";
                      document.getElementById('start').style.display = "";
                      document.getElementById('cancel_edit1').style.display = "none";
                      document.getElementById('btn_edit1').style.display = "";
                    }
                  </script>

                  <div class="col-sm-1 mb-3 mb-sm-0" style="padding-top: 30px">
                    <a class="btn btn-outline-success btn-sm" onclick="edit_start();" id="btn_edit1"><i class='fa fa-edit'></i></a>
                    <a class="btn btn-outline-danger btn-sm" onclick="cancel_start();" id="cancel_edit1" style="display: none;"><i class='fa fa-ban'></i></a>
                  </div>

                  <div class="col-sm-5 mb-3 mb-sm-0" id="end">
                    <h1 class="h5 mb-0 text-gray-800">Date End</h1>
                    <input type="text" class="form-control" name="end" value="<?php echo $new_end ?>">
                  </div>

                  <div class="col-sm-5 mb-3 mb-sm-0" id="new_end" style="display: none;">
                    <h1 class="h5 mb-0 text-gray-800">New Date End</h1>
                    <input type="date" class="form-control" name="end">
                  </div>

                  <script>
                    function edit_end() {
                      document.getElementById('new_end').style.display = "";
                      document.getElementById('end').style.display = "none";
                      document.getElementById('btn_edit').style.display = "none";
                      document.getElementById('cancel_edit').style.display = "";
                    }

                     function cancel_end() {
                      document.getElementById('new_end').style.display = "none";
                      document.getElementById('end').style.display = "";
                      document.getElementById('cancel_edit').style.display = "none";
                      document.getElementById('btn_edit').style.display = "";
                    }
                  </script>

                  <div class="col-sm-1 mb-3 mb-sm-0" style="padding-top: 30px">
                    <a class="btn btn-outline-success btn-sm" onclick="edit_end();" id="btn_edit"><i class='fa fa-edit'></i></a>
                    <a class="btn btn-outline-danger btn-sm" onclick="cancel_end();" id="cancel_edit" style="display: none;"><i class='fa fa-ban'></i></a>
                  </div>
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Time Start</h1>
                    <input type="time" class="form-control" name="time_start" value="<?php echo $time_start ?>">
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <h1 class="h5 mb-0 text-gray-800">Time End</h1>
                    <input type="time" class="form-control" name="time_end" value="<?php echo $time_end ?>">
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                <input type="submit" class="btn btn-outline-success" name="submit" value="Submit">
              </div>
               </form>