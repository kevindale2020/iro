<?php
include '../config.php'; //this code will allow you to connect to your database


if ($database->connect_error) {
    die("Connection failed: " . $database->connect_error);
} 

$sql = "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE ((reports.report_type_id = 3) AND (documents.doc_type_id = 3) AND (documents.doc_status_id = 1))";

$result = $database->query($sql);

echo $result->num_rows;



mysqli_close($database);
?>