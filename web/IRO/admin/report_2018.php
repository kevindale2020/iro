<?php 
include '../config.php'; //this code will allow you to connect to your database

//Total Incident 2018
$total_incident = mysqli_query($database, "SELECT * FROM reports INNER JOIN documents ON reports.report_id = documents.doc_form_id INNER JOIN report_type ON reports.report_type_id = report_type.report_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-01-01' AND '2018-12-31' ORDER BY documents.doc_id DESC");

$jan = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-01-01' AND '2018-01-31'");
$January1 = mysqli_num_rows($jan);

$feb = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-02-01' AND '2018-02-31'");
$February1 = mysqli_num_rows($feb);

$mar = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-03-01' AND '2018-03-31'");
$March1 = mysqli_num_rows($mar);

$apr = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-04-01' AND '2018-04-31'");
$April1 = mysqli_num_rows($apr);

$may = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-05-01' AND '2018-05-31'");
$May1 = mysqli_num_rows($may);

$jun = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-06-01' AND '2018-06-31'");
$June1 = mysqli_num_rows($jun);

$jul = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-07-01' AND '2018-07-31'");
$July1 = mysqli_num_rows($jul);

$aug = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-08-01' AND '2018-08-31'");
$August1 = mysqli_num_rows($aug);

$sept = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-09-01' AND '2018-09-31'");
$September1 = mysqli_num_rows($sept);

$oct = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-10-01' AND '2018-10-31'");
$October1 = mysqli_num_rows($oct);

$nov = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-11-01' AND '2018-11-31'");
$November1 = mysqli_num_rows($nov);

$dec = mysqli_query($database, "SELECT * FROM documents INNER JOIN reports ON documents.doc_form_id = reports.report_id WHERE documents.doc_type_id = 3 AND reports.date_reported BETWEEN '2018-12-01' AND '2018-12-31'");
$December1 = mysqli_num_rows($dec);

 ?>