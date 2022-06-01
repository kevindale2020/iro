<?php 
include '../config.php'; //this code will allow you to connect to your database

$case_closed = mysqli_query($database, "SELECT * FROM reports INNER JOIN documents ON reports.report_id = documents.doc_form_id INNER JOIN report_type ON reports.report_type_id = report_type.report_type_id INNER JOIN document_status ON documents.doc_status_id = document_status.doc_status_id INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE ((documents.doc_type_id = 3) AND (documents.doc_status_id = 6) AND (closed_documents.closed_date BETWEEN '2017-01-01' AND '2017-12-31')) ORDER BY documents.doc_id DESC");

//Case Closed
$jan = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-01-01' AND '2017-01-31' AND documents.doc_status_id = 6");
$January = mysqli_num_rows($jan);

$feb = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-02-01' AND '2017-02-31' AND documents.doc_status_id = 6");
$February = mysqli_num_rows($feb);

$mar = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-03-01' AND '2017-03-31' AND documents.doc_status_id = 6");
$March = mysqli_num_rows($mar);

$apr = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-04-01' AND '2017-04-31' AND documents.doc_status_id = 6");
$April = mysqli_num_rows($apr);

$may = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-05-01' AND '2017-05-31' AND documents.doc_status_id = 6");
$May = mysqli_num_rows($may);

$jun = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-06-01' AND '2017-06-31' AND documents.doc_status_id = 6");
$June = mysqli_num_rows($jun);

$jul = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-07-01' AND '2017-07-31' AND documents.doc_status_id = 6");
$July = mysqli_num_rows($jul);

$aug = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-08-01' AND '2017-08-31' AND documents.doc_status_id = 6");
$August = mysqli_num_rows($aug);

$sept = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-09-01' AND '2017-09-31' AND documents.doc_status_id = 6");
$September = mysqli_num_rows($sept);

$oct = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-10-01' AND '2017-10-31' AND documents.doc_status_id = 6");
$October = mysqli_num_rows($oct);

$nov = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-11-01' AND '2017-11-31' AND documents.doc_status_id = 6");
$November = mysqli_num_rows($nov);

$dec = mysqli_query($database, "SELECT * FROM documents INNER JOIN closed_documents ON documents.doc_id = closed_documents.doc_id WHERE closed_documents.closed_date BETWEEN '2017-12-01' AND '2017-12-31' AND documents.doc_status_id = 6");
$December = mysqli_num_rows($dec);
 ?>