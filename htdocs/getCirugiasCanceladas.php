<?php 
/*Muestra las cirugias que han sido canceladas por el usuario (campo cancelada = 1)*/

$status = $_POST['stat']; //valor de quirofano_id seleccionado
$intQuirofano = (int) $status;

require_once 'funciones_bd.php';

$db = new funciones_BD();
	$db->getCirugiasCanceladas($intQuirofano);	

?>