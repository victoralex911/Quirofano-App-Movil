
<?php 
/*Agenda del dia - Envia los registros del dia a la aplicacion Android*/

$status = $_POST['stat'];

require_once 'funciones_bd.php';
$db = new funciones_BD();
	if($status == "ok"){
		$db->agendaDelDia();	
	}
	//if($status == "ok"){
		//$resultado[]=array("dato"=>"1");
		//$resultado[]=array("logstatus"=>"0");
	//	$resultado[]=array("dato"=>$db->mostrarAgenda());

	//	echo json_encode($resultado);
	//}//Fin de if
?>


