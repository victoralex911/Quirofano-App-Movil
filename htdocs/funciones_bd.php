<?php
 
class funciones_BD {
 
    private $db;
 
    // constructor

    function __construct() {
        require_once 'connectbd.php';
        // connecting to database

        $this->db = new DB_Connect();
        $this->db->connect();

    }
 
    // destructor
    function __destruct() {
 
    }
 
    /**
     * agregar nuevo usuario
     */
    public function adduser($username, $password) {

    $result = mysql_query("INSERT INTO sf_guard_user(username,password) VALUES('$username', '$password')");
        // check for successful store

        if ($result) {

            return true;

        } else {

            return false;
        }

    }
 
 
     /**
     * Verificar si el usuario ya existe por el username
     */

    public function isuserexist($username) {

        $result = mysql_query("SELECT username from usuarios WHERE username = '$username'");
        $num_rows = mysql_num_rows($result); //numero de filas retornadas

        if ($num_rows > 0) {
            // el usuario existe 
            return true;

        } else {
            // no existe
            return false;
        }
    }
 
   
	public function login($user,$password){

	//$pass = sha1($password);

	$result=mysql_query("SELECT COUNT(*) FROM sf_guard_user WHERE username='$user' AND password='$password' "); 
	$count = mysql_fetch_row($result);

	/*como el usuario debe ser unico cuenta el numero de ocurrencias con esos datos*/


		if ($count[0]==0){
			/*no hubo coincidencias*/
		return true;

		}else{
			/*si hubo coincidencias*/
		return false;

		}
	}

	// if (loginstatus(user,pass)==true){    

	public function closeConnection(){
        
    } /*Cerrar conexion con la BD*/

	//public function programarCirugia($date, $hora, $registro, $paciente){
	public function programarCirugia($date, $hora, $registro, $paciente, $edad, $genero, $procedencia, $diagnostico, $medico, $riesgoQuirurgico,
        $insumos, $requerimientosAnestesiologia, $hemoderivados, $requisitosLaboratorio, $otrasNecesidades,$newSala, 
        $newDuracion,$newProgramacion, $newServicio, $newAtencion, $newP, $newR){
        //$newSala, $newDuracion,$newProgramacion, $newServicio, $newAtencion, $newP, $newR
		
        $result = mysql_query("INSERT INTO hc_agenda(programacion, hora, registro, paciente_name, edad, genero, procedencia, diagnostico,
            medico_name, riesgo_qx_pre, req_insumos, req_anestesico, req_hemoderiv, req_laboratorio, requerimiento,
            sala_id, tiempo_est, tipo_proc_id, servicio, atencion_id, protocolo, reintervencion) 
            VALUES('$date', '$hora', '$registro', '$paciente', '$edad', '$genero', '$procedencia', '$diagnostico', '$medico', 
            '$riesgoQuirurgico', '$insumos', '$requerimientosAnestesiologia', '$hemoderivados', '$requisitosLaboratorio', 
            '$otrasNecesidades', $newSala, '$newDuracion', $newProgramacion, $newServicio, $newAtencion, $newP, $newR)");
        

        /*
        $result = mysql_query("INSERT INTO hc_agenda(programacion, hora, registro, paciente_name, edad, genero, procedencia, diagnostico,
            medico_name, riesgo_qx_pre, req_insumos, req_anestesico, req_hemoderiv, req_laboratorio, requerimiento) 
            VALUES('$date', '$hora', '$registro', '$paciente', '$edad', '$genero', '$procedencia', '$diagnostico', '$medico', 
            '$riesgoQuirurgico', '$insumos', '$requerimientosAnestesiologia', '$hemoderivados', '$requisitosLaboratorio', 
            '$otrasNecesidades')");
		*/
        // check for successful store
        //en vez de inicio es registro

		if ($result) {
		    return true;
        }

        else {
		    return false;
		}
	}//Fin de programar cirugia

    public function mostrarAgenda() {
        $result = mysql_query("SELECT id, programacion, hora, hora FROM hc_agenda"); /*AUN NO TERMINADA*/

        if ($result) {
            return $result;
        }

        else{
            return false;
        }
    }//Fin de mostrarAgenda

    /*
    while ($rsEmp = mysql_fetch_assoc($queEmp)){
        echo $rsEmp['username'];
    } 
    */

    public function test(){
        //$cont = mysql_query("SELECT * FROM hc_agenda");
        //$contador = mysql_num_rows($cont);
        //$res_contador[] = array("contador"=>$contador); 
        //echo json_encode($res_contador);

        $result = mysql_query("SELECT hora, sala_id, paciente_name, diagnostico FROM hc_agenda");
        //$result[] = array();
        //$resultado[] = array("dato"=>$result);
        //$rsEmp = mysql_fetch_assoc($result);
        //echo json_encode($rsEmp['id']);
        //$temp = ($rsEmp['programacion']);
        //$temp2 = ($rsEmp['hora']);
        //$temp3 = ($rsEmp['paciente_name']);
        //$temp4 = ($rsEmp['diagnostico']);
        //$resultado[] = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);

        //$resultado = array();
        
        /*
        while ($rsEmp = mysql_fetch_assoc($result)){
            $temp = ($rsEmp['programacion']);
            $temp2 = ($rsEmp['hora']);
            $temp3 = ($rsEmp['paciente_name']);
            $temp4 = ($rsEmp['diagnostico']);

            $resultado[] = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);
            echo json_encode($resultado);
            unset($resultado);
        }//Fin de while
        */

        $resultado = array();

        while ($rsEmp = mysql_fetch_assoc($result)){
            $temp = ($rsEmp['hora']);
            $temp2 = ($rsEmp['sala_id']);
            $temp3 = ($rsEmp['paciente_name']);
            $temp4 = ($rsEmp['diagnostico']);

            $miArray = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);

            $res[] = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);
            array_push($resultado, $miArray);
        }//Fin de while
            
        echo json_encode($resultado);
        //echo json_encode($resultado);
    }//Fin de la funcion test

    public function agendaDelDia(){
        //Obtener fecha actual
        $f = date('Y-m-d');
        $newfecha = (string) $f;

        //Buscar con respecto a la fecha actual
        $result = mysql_query("SELECT hora, sala_id, paciente_name, diagnostico FROM hc_agenda WHERE programacion='$newfecha'");
        $resultado = array();

        while ($rsEmp = mysql_fetch_assoc($result)){
            $temp = ($rsEmp['hora']);
            $temp2 = ($rsEmp['sala_id']);
            $temp3 = ($rsEmp['paciente_name']);
            $temp4 = ($rsEmp['diagnostico']);

            $miArray = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);

            $res[] = array("dato"=>$temp, "dato1"=>$temp2, "dato2"=>$temp3, "dato3"=>$temp4);
            array_push($resultado, $miArray);
        }//Fin de while
            
        echo json_encode($resultado);
    }//Fin de agendaHoy 
  
}//Fin de la clase
 
?>
