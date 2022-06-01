<?php 
include '../config.php'; //this code will allow you to connect to your database
include '../sessionchecker.php'; //this code will prevent you to bypass any site if not login*/
include '../sessioninfo.php'; //this code will allow you to ge the info of logged user

if (!empty($_GET['id'])) 
{
  $id = $_GET['id'];

  $petdetails = mysqli_query($database, "SELECT * FROM pets INNER JOIN pet_type ON pets.pet_type_id = pet_type.pet_type_id WHERE pets.pet_id = $id");

  $pet_details = mysqli_query($database, "SELECT * FROM pet_type ORDER BY pet_type_desc ASC");

  while ($row = mysqli_fetch_array($petdetails)) 
  {
    $pet_image = $row['pet_image'];
    $pet_name = $row['pet_name'];
    $pet_age = $row['pet_age'];
    $pet_type_id = $row['pet_type_id'];
    $pet_type_desc = $row['pet_type_desc'];
    $pet_desc = $row['pet_desc'];
    $pet_gender = $row['pet_gender'];
  }
}


if (isset($_POST['save'])) 
{
  $image = $_FILES['image']['name'];// Get image name
  $target = "../Android/images/adoptions/".basename($image);// image file directory
  $pet_id = $_POST['pet_id'];
  $pet_name = $_POST['pet_name'];
  $pet_type = $_POST['pet_type'];
  $pet_gender = $_POST['pet_gender'];
  $pet_age = $_POST['pet_age'];
  $pet_desc = $_POST['pet_desc'];
  $success ='Pet details has been edited successfully';

  $check_pet = mysqli_query($database, "SELECT * FROM pets WHERE pet_name = '$pet_name'");


  if (empty($image) || $image == "") 
  {
    if(mysqli_num_rows($check_pet) > 0)
    {
      $error_image = 'Pet name is already exist.';
      echo "<script>window.location.href='admin_viewpet.php?error_image=$error_image';</script>";
    }
    else
    {
      $edit_pet = "UPDATE pets SET pet_name = '$pet_name', pet_age = '$pet_age', pet_gender = '$pet_gender', pet_desc = '$pet_desc', pet_type_id = '$pet_type' WHERE pet_id = $pet_id";

      if (mysqli_query($database, $edit_pet)) 
      {
        echo "<script>window.location.href='admin_viewpet.php?success=$success';</script>";
      }
    }
  }
  else
  {
    $file_type = $_FILES['image']['type'];
    $allowed = array("image/jpeg", "image/gif", "image/png");
    if(!in_array($file_type, $allowed)) 
    {
      $error_image = 'Only jpg, gif and png files are allowed.';

      echo "<script>window.location.href='admin_viewpet.php?error_image=$error_image';</script>";
    }
    else if(mysqli_num_rows($check_pet) > 0)
    {
      $error_image = 'Pet name is already exist.';
      echo "<script>window.location.href='admin_viewpet.php?error_image=$error_image';</script>";
    }
    else
    {
      $edit_pet = "UPDATE pets SET pet_image = '$image', pet_name = '$pet_name', pet_age = '$pet_age', pet_gender = '$pet_gender', pet_desc = '$pet_desc', pet_type_id = '$pet_type' WHERE pet_id = $pet_id";

      if (mysqli_query($database, $edit_pet)) 
      {
        if (move_uploaded_file($_FILES['image']['tmp_name'], $target)) 
        {
          echo "<script>window.location.href='admin_viewpet.php?success=$success';</script>";
        }
      }
    }
  }
}


?>
<form method="post" action="getContentPet.php" enctype="multipart/form-data">
                <center>
                <div class="form-group" style="padding-left: 250px; padding-right: 230px">
                  <div class="input-group mb-3">
                    <?php echo "<img src='../Android/images/adoptions/".$pet_image."' style='margin: 5px; width: 250px; height: 200px;' class='avatar img-circle img-thumbnail' alt='avatar'>"?>
                  </div>
                </div>
                </center>
                  <script>
                    $(document).on('change', '.custom-file-input', function (event) {
                    $(this).next('.custom-file-label').html(event.target.files[0].name);
                    })
                  </script>
                  <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text">Pet Image</span>
                    </div>

                    <div class="custom-file">
                      <input type="file" class="custom-file-input" name="image" id="inputGroupFile01">
                      <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                    </div>
                  </div>
                </div>

                  <div class="form-group row">    
                    <div class="col-sm-6 mb-3 mb-sm-0">
                      <input type="hidden" name="pet_id" value="<?php echo $id ?>">
                       <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Pet Name</h1>
                          <p id="petname_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="pet_name" id="petname" value="<?php echo $pet_name ?>" autocomplete="off" required>
                        </div>
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">
                       <div id="input-1">
                        <h1 class="h5 mb-0 text-gray-800">Pet Age</h1>
                          <p id="petage_error" class="alert-danger"></p>
                          <input type="text" class="form-control" name="pet_age" id="petage" value="<?php echo $pet_age ?>" autocomplete="off">
                        </div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">    
                      <select name="pet_type" class="form-control" required>
                        <option value="<?php echo $pet_type_id; ?>"><?php echo $pet_type_desc; ?></option>
                        <?php 
                        while ($row = mysqli_fetch_array($pet_details)) 
                            {
                              $pet_type_id = $row['pet_type_id'];
                              $pet_type = $row['pet_type_desc'];
                              ?>
                              <option value="<?php echo $pet_type_id; ?>"><?php echo "$pet_type"; ?></option>
                              <?php
                            }
                        ?>
                      </select>
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">    
                      <select name="pet_gender" class="form-control" required>
                        <option readonly selected><?php echo "$pet_gender"; ?></option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                      </select>
                    </div>
                  </div>

                  <div class="form-group">
                    <textarea name="pet_desc"  rows="5" class="form-control" required><?php echo "$pet_desc"; ?></textarea>
                  </div>

                  <div class="form-group row">
                    <div class="col-sm-6 mb-3 mb-sm-0">    
                    <input type="submit" name="save" value="Save" class="btn btn-success btn-user btn-block" id="addpet">
                    </div>

                    <div class="col-sm-6 mb-3 mb-sm-0">    
                    <input type="submit" name="clear" value="Cancel" data-dismiss="modal" class="btn btn-primary btn-user btn-block">
                    </div>
                  </div>
</form>


<script type="text/javascript">
    $(document).ready(function(){
        $('#addpet').click(function(){
            var petname = $('#petname').val();
            var petage = $('#petage').val();
            var description = $('#description').val();
            
            //Conditions
            if ($.trim($("#petname").val()) === "" || !petname.match(/^[a-zA-Z0-9\s]*$/) || petname.match(/^[0-9]*$/) || !petname.match(/^[a-zA-Z\ ]+$/) && $.trim($("#petage").val()) === "" || !petage.match(/^[a-zA-Z0-9\s]*$/) && $.trim($("#description").val()) === "") 
            {
              //Pet Name 
              if($.trim($("#petname").val()) === "") 
              {
                $('#petname_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!petname.match(/^[a-zA-Z0-9\s]*$/)) 
              {
                $("#petname_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(petname.match(/^[0-9]*$/)) 
              {
                $("#petname_error").html("Numbers are not allowed");
                $("#input-1").addClass('has-error');
              }
              else if(!petname.match(/^[a-zA-Z\ ]+$/))
              {
                $("#petname_error").html("Letters with number are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#petname_error').html("");
                $('#input-1').addClass('has-error');
              }

              //Pet Age
              if($.trim($("#petage").val()) === "") 
              {
                $('#petage_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else if(!petage.match(/^[a-zA-Z0-9\s]*$/)) {
                $("#petage_error").html("Special characters are not allowed");
                $("#input-1").addClass('has-error');
              }
              else
              {
                $('#petage_error').html("");
                $('#input-2').addClass('has-error');
              }

              //Pet Description
              if($.trim($("#description").val()) === "") 
              {
                $('#description_error').html("Blank spaces are not allowed");
                $('#input-2').addClass('has-error');
              }
              else
              {
                $('#description_error').html("");
                $('#input-2').addClass('has-error');
              }
              return false;
            }

            

        });
    });
</script>
<?php
 ?> 
                