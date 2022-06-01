<?php 
include '../config.php'; //this code will allow you to connect to your database

//Total Incident 2017
$total_incident = mysqli_query($database, "SELECT * FROM reports INNER JOIN documents ON reports.report_id = documents.doc_form_id INNER JOIN report_type ON reports.report_type_id = report_type.report_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2020-01-01' AND '2020-12-31' ORDER BY documents.doc_id DESC");

$jan = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2020-01-01' AND '2020-01-31'");
$January1 = mysqli_num_rows($jan);

$feb = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2020-02-01' AND '2020-02-31'");
$February1 = mysqli_num_rows($feb);

$mar = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%March%'");
$March1 = mysqli_num_rows($mar);

$apr = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%April%'");
$April1 = mysqli_num_rows($apr);

$may = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%May%'");
$May1 = mysqli_num_rows($may);

$jun = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%June%'");
$June1 = mysqli_num_rows($jun);

$jul = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%July%'");
$July1 = mysqli_num_rows($jul);

$aug = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%August%'");
$August1 = mysqli_num_rows($aug);

$sept = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%September%'");
$September1 = mysqli_num_rows($sept);

$oct = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%October%'");
$October1 = mysqli_num_rows($oct);

$nov = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported LIKE '%November%'");
$November1 = mysqli_num_rows($nov);

$dec = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2020-12-01' AND '2020-12-31'");
$December1 = mysqli_num_rows($dec);

 ?>