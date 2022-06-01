<?php
try
{
	$bdd = new PDO('mysql:host=localhost;dbname=iro_db;charset=utf8', 'root', '');
}
catch(Exception $e)
{
        die('Error : '.$e->getMessage());
}
