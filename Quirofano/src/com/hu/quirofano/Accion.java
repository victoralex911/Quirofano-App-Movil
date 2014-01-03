package com.hu.quirofano;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.widget.DatePicker;

import com.actionbarsherlock.app.SherlockFragment;
import com.hu.libreria.HttpPostAux;
import com.hu.quirofano.AccionDiferida.GetDatosCirugiaProgramada;
import com.hu.quirofano.AccionDiferida.GetProcedimientosCirugiaProgramada;
import com.hu.quirofano.AccionDiferida.GetServicios;
import com.hu.quirofano.Item02.GetQuirofanoName;
import com.hu.quirofano.Item1.Agenda;
import com.hu.quirofano.Item1.EnviarProcedimientos;
import com.hu.quirofano.Item1.Formulario;
import com.hu.quirofano.Item1.GetProcedimientosProgramada;
import com.hu.quirofano.Item1.GetSalas;

public class Accion extends SherlockFragment{
	
	//18Dic - llenar el spinner de servicios en la seccion programar cirugia
	static ArrayList<String> nombreServicios = new ArrayList<String>();
	
	boolean result_back2;				//PARA MODIFICAR CIRUGIA
    private ProgressDialog progress2;	//EXISTEN 2
    
	ArrayList<ArrayList<String>> procedimientos = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> arrayServicios = new ArrayList<ArrayList<String>>();
	static int salaSpinner;
	ProgramarCirugiaView object = new ProgramarCirugiaView();
	
	//RadioGroup
	//Devuelve un entero, para conocer el String correspondiente usar RadioButton
	RadioGroup protocolo;
	RadioGroup reintervencion;
	//RadioButton
	RadioButton prot;
	RadioButton reint;
	
	//EditText
	EditText fecha;
	EditText horaPropuesta; 
	EditText registro;
	EditText nombreDelPaciente;
	EditText edad;
	EditText genero;
	EditText procedencia;
	EditText diagnostico;
	EditText medico;
	EditText riesgoQuirurgico;
	EditText insumosIndispensables;
	EditText requerimientos;
	EditText hemoderivados;
	EditText requisitos;
	EditText necesidades;
	
	//Button
	Button botonGuardar;

	
	
	//PARA EL DATE PICKER ************************
	static int anio;
	static int mesDelAnio;
	static int diaDelMes;
	
	//3enero
	static String sAnio;
	static String sMesDelAnio;
	static String sDiaDelMes;
	
	private TextView pDisplayDate;
		
	//PARA EL TIME PICKER ************************ PARA MODIFICAR CIRUGIA
	private TextView mPickedTimeText2;	//HAY OTRO PARA LA HORA DE INGRESAR CIRUGIA
	static int horas2;
	static int minutos2;
	//3 enero
	static String sHoras2;
	static String sMinutos2;
	    
//	ArrayList<String> nombreDeSalas = new ArrayList<String>();	//Alojar nombre de salas
	ArrayList<String> nombreDeSalas = Item1.nombreSalas;
	//nombreDeSalas = Item1.nombreSalas;
	
	HttpPostAux post;
	HttpPostAux envio;
    //String IP_Server="172.16.0.125";//IP DE NUESTRO PC
    String IP_Server = MainActivity.IP_Server;
    String URL_connect="http://"+IP_Server+"/androidlogin/cancelarCirugia.php";
    String URL_connect1="http://"+IP_Server+"/androidlogin/iniciarCirugia.php";
    String URL_connect2="http://"+IP_Server+"/androidlogin/getTurno.php";
    String URL_connect3="http://"+IP_Server+"/androidlogin/diferirCirugia.php";
    String URL_connect4="http://"+IP_Server+"/androidlogin/getCausaDiferido.php";
    String URL_connect5="http://"+IP_Server+"/androidlogin/modificarCirugia.php";
    //MODIFICAR CIRUGIAS
    String URL_connect6="http://"+IP_Server+"/androidlogin/getDatosCirugiaProgramada.php";
    String URL_connect7="http://"+IP_Server+"/androidlogin/getProcedimientosCirugiaProgramada.php";
    String URL_connect8="http://"+IP_Server+"/androidlogin/enviarProcedimientosModificados.php";
    String URL_connect9="http://"+IP_Server+"/androidlogin/getServicios.php";
    
  //mostrar informacion de la cirugia programada, para luego modificarla
    private LinearLayout myLInearLayout;
//    private TextView valueTV;
    //mostrar encabezados de la cirugia programada seleccionada
    ArrayList<String> encabezados = new ArrayList<String>();
    //Para guardar la informacion de la cirugia programada seleccionadad, desde el server
    ArrayList<String> cirugiaProgramadaData = new ArrayList<String>();
    //Para guardar los procedimientos que se mostraran de la cirugia seleccionada a modificar
    ArrayList<ArrayList<String>> arrayProcedimientosCirugiaProgramada = new ArrayList<ArrayList<String>>();
    
    View ll;
    TextView agregarTema;
    String myString[] = new String[7];
    
    ArrayList<ArrayList<String>> padre = new ArrayList<ArrayList<String>>();
    ArrayList<String> nombres = new ArrayList<String>();
    ArrayList<ArrayList<String>> turnos = new ArrayList<ArrayList<String>>();
    ArrayList<String> nombreTurnos = new ArrayList<String>();
    
    //INICIAR CIRUGIA ****************************
    EditText ingresar;
    EditText medico_name;
    EditText cirujano_name;
    EditText anestesiologo;
    EditText anestesiologo_supervisor;
    EditText tecnica;
    EditText instrumentista;
    EditText circulante;
    EditText observaciones;
    
	Button guardar;
	
	//*****************
	
	//Para timePicker
	private TextView mPickedTimeText;
    static int horas;
    static int minutos;
    //3enero
    static String sHoras;
    static String sMinutos;
	
	RadioGroup tiempoFuera;
	RadioButton si;
	RadioButton no;
	RadioButton tiempo;
	
	//spinners*****************
	static int turnoInstrumentista;
	static int turnoCirculante;
	//INICIAR CIRUGIA ***********************
	
	//DIFERIR CIRUGIA ***********************
	EditText causaDiferido;
	Button aceptar;
	
	ArrayList<ArrayList<String>> arrayCausaDiferido = new ArrayList<ArrayList<String>>();
	ArrayList<String> arrayCausaDiferidoNombre = new ArrayList<String>();
	static int causaNumber;
	
	//DIFERIR CIRUGIA ***********************
	
	boolean result_back;
    private ProgressDialog progress;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
    	
    	post = new HttpPostAux();
    	envio = new HttpPostAux();
    	View v = inflater.inflate(R.layout.acciones, container, false);
    	final View modificar = inflater.inflate(R.layout.modificar_cirugia, container, false);
    	final View iniciar = inflater.inflate(R.layout.transoperatorio, container, false);
    	final View diferir = inflater.inflate(R.layout.diferir, container, false);
    	
		final ScrollView sv = (ScrollView)v.findViewById(R.id.vista);
		
    	Bundle bundle = this.getArguments();
		myString = bundle.getStringArray("parametro");
		System.out.println("ITEM1 RECIBIDO nombre del paciente seleccionado = "+myString[3]);
		//System.out.println("ITEM1 RECIBIDO quirofano_name = "+myString[1]);
		TextView nombre = (TextView)v.findViewById(R.id.nombre_paciente);
		nombre.setText("Paciente: "+myString[3]);
		//ID_quirofano = myString[0];
    	//v.addView();
		//ll = v.findViewById(R.id.info);
		
		new GetProcedimientosCirugiaProgramada().execute(myString[5]);
		new GetDatosCirugiaProgramada().execute(myString[5]);
		new GetServicios(inflater, modificar, container).execute(myString[6]);	//OBTENER LOS SERVICIOS PARA PONER EN SPINNER DE PROGRAMAR CIR.
		
		//MODIFICAR CIRUGIA ******************
		
		protocolo = (RadioGroup) modificar.findViewById(R.id.groupProtocolo);
		reintervencion = (RadioGroup) modificar.findViewById(R.id.groupReintervencion);
		
		//EditText
		fecha = (EditText) modificar.findViewById(R.id.fecha);
		horaPropuesta = (EditText) modificar.findViewById(R.id.horaPropuesta);
		registro = (EditText) modificar.findViewById(R.id.registro);
		nombreDelPaciente = (EditText) modificar.findViewById(R.id.nombreDelPaciente);
		edad =(EditText) modificar.findViewById(R.id.edad);
		genero =(EditText) modificar.findViewById(R.id.genero);
		procedencia =(EditText) modificar.findViewById(R.id.procedencia);
		diagnostico =(EditText) modificar.findViewById(R.id.diagnostico);
		medico =(EditText) modificar.findViewById(R.id.medico);
		edad =(EditText) modificar.findViewById(R.id.edad);
		riesgoQuirurgico =(EditText) modificar.findViewById(R.id.riesgoQuirurgico);
		insumosIndispensables =(EditText) modificar.findViewById(R.id.insumosIndispensables);
		requerimientos =(EditText) modificar.findViewById(R.id.requerimientos);
		hemoderivados =(EditText) modificar.findViewById(R.id.hemoderivados);
		requisitos =(EditText) modificar.findViewById(R.id.requisitosDeLaboratorio);
		necesidades =(EditText) modificar.findViewById(R.id.otrasNecesidades);
		
		//EditText
		botonGuardar = (Button) modificar.findViewById(R.id.botonGuardar);
		//***************************************************************
		//FIN MODIFICAR CIRUGIA ******************
		
		Button cancelar = (Button)v.findViewById(R.id.cancelar);
		Button iniciarButton = (Button)v.findViewById(R.id.iniciar);
		Button diferirButton = (Button)v.findViewById(R.id.diferir);
		guardar = (Button)iniciar.findViewById(R.id.botonGuardar);
		
		Button modificarButton = (Button)v.findViewById(R.id.modificar);
		
		tiempoFuera = (RadioGroup) iniciar.findViewById(R.id.tiempoFuera);
		
		/** LINEAR PARA MOSTRAR LOS DATOS DE LA CIRUGIA PROGRAMADA*/
		myLInearLayout =(LinearLayout) modificar.findViewById(R.id.show_data);
		/** VISTAS PARA MODIFICAR CIRUGIA*/
		ProgramarCirugiaView pcv = new ProgramarCirugiaView();
		pcv.prog(inflater, modificar, getActivity());
		
		//EditText
		//ingresar = (EditText) iniciar.findViewById(R.id.ingreso);
		medico_name = (EditText) iniciar.findViewById(R.id.medico);
		cirujano_name = (EditText) iniciar.findViewById(R.id.cirujano);
		anestesiologo = (EditText) iniciar.findViewById(R.id.anestesiologo);
		anestesiologo_supervisor = (EditText) iniciar.findViewById(R.id.anestesiologoSupervisa);
		tecnica = (EditText) iniciar.findViewById(R.id.tecnicaAnestesica);
		instrumentista = (EditText) iniciar.findViewById(R.id.instrumentista);
		circulante = (EditText) iniciar.findViewById(R.id.circulante);
		observaciones = (EditText) iniciar.findViewById(R.id.observaciones);
		
		//DIFERIR CIRUGIA
		//causaDiferido = (EditText) diferir.findViewById(R.id.causa_diferido);
		aceptar = (Button) diferir.findViewById(R.id.botonGuardar);
		//DIFERIR CIRUGIA
		
		new GetCausaDiferido().execute(myString[5]);	//ID del registro seleccionado
		new GetTurno().execute(myString[6]);			//ID del quirofano

		//Button po = (Button)v.findViewById(R.id.po);
		//Button salas = (Button)v.findViewById(R.id.salas);
		
		//sv.addView(v);
		
//		po.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				sv.removeAllViews();
//				
//			}
//		});
		
		//INICIA MODIFICAR CIRUGIA
		modificarButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				sv.addView(modificar);
				myLInearLayout.removeAllViews();
				
				/** PARA DATEPICKER **************************************************** */
				
				pDisplayDate = (TextView) modificar.findViewById(R.id.displayDate);
		        //pPickDate = (Button) programar.findViewById(R.id.pickDate);
		 
		        pDisplayDate.setOnClickListener(new OnClickListener() {
		        	
		        	   @Override
		        	   public void onClick(View v) {
		        	    showDatePicker();
		        	    //pDisplayDate.setText(all);
		        	   }
		        	  });
				
				/** PARA DATEPICKER ******************************************************/
		        
		        /** PARA TIME PICKER***************************************************** */
		        mPickedTimeText2 = (TextView) modificar.findViewById(R.id.hour);
		        mPickedTimeText2.setOnClickListener(new OnClickListener()
		        {
		            @Override
		            public void onClick(View v ){
		            	showTimePicker2();
		            }
		        });
		        
		        /** PARA TIME PICKER *****************************************************/
		        
		        // SPINNER 1 - SALA ***************************************************************
				Spinner sp = (Spinner) modificar.findViewById(R.id.salaOpciones);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, nombreDeSalas);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {
						parentView.getItemAtPosition(position);
						
						int sala=position;
						AccionDiferida.salaSpinner = sala;
					}
					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
				
				LinearLayout.LayoutParams params = 
						new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				myLInearLayout.setOrientation(LinearLayout.VERTICAL);
				encabezados.clear();
				
				encabezados.add("Sala: ");
				encabezados.add("Fecha: ");
				encabezados.add("Hora propuesta: ");
				encabezados.add("Duración: ");
				encabezados.add("Tipo de programación: ");
				encabezados.add("Registro: ");
				encabezados.add("Nombre del paciente: ");
				encabezados.add("Edad: ");
				encabezados.add("Género: ");
				encabezados.add("Procedencia: ");
				encabezados.add("Servicio: ");
				encabezados.add("Diagnóstico: ");
				encabezados.add("Protocolo: ");
				encabezados.add("Reintervención: ");
				encabezados.add("Nombre del médico que programa: ");
				encabezados.add("En atención a: ");
				encabezados.add("Riesgo quirúrgico: ");
				encabezados.add("Insumos indispensables: ");
				encabezados.add("Requerimientos de anestesiología: ");
				encabezados.add("Hemoderivados necesarios: ");
				encabezados.add("Requisitos de laboratorio: ");
				encabezados.add("Otras necesidades: ");
				encabezados.add("Quirófano: ");
				
				//add textView
				for (int i = 0; i<encabezados.size(); i++){
					TextView valueTV = new TextView(getActivity());
					valueTV.setText(encabezados.get(i)+cirugiaProgramadaData.get(i));
					valueTV.setLayoutParams(params);
					myLInearLayout.addView(valueTV);
				}
				//TextView valueTV = new TextView(getActivity());
				//valueTV.setText("\n");
				//valueTV.setLayoutParams(params);
				//myLInearLayout.addView(valueTV);
				
				//Imprimir los procedimientos de la cirugia a modificar
				for (int j = 0; j < arrayProcedimientosCirugiaProgramada.size(); j++){
					TextView value = new TextView(getActivity());
					TextView value0 = new TextView(getActivity());
					
					TextView value1 = new TextView(getActivity());
					TextView value2 = new TextView(getActivity());
					TextView value3 = new TextView(getActivity());
					TextView value4 = new TextView(getActivity());
					
					value0.setText("Procedimiento: "+(j+1));
					value1.setText("Procedimiento o cirugía planeado: "+arrayProcedimientosCirugiaProgramada.get(j).get(1));
					value2.setText("Región: "+arrayProcedimientosCirugiaProgramada.get(j).get(2));
					value3.setText("Detalles adicionales: "+arrayProcedimientosCirugiaProgramada.get(j).get(3));
					value4.setText("Servicio: "+arrayProcedimientosCirugiaProgramada.get(j).get(4));
					value.setText("\n");
					
					value.setLayoutParams(params);
					value0.setLayoutParams(params);
					value1.setLayoutParams(params);
					value2.setLayoutParams(params);
					value3.setLayoutParams(params);
					value4.setLayoutParams(params);
					
					myLInearLayout.addView(value);
					myLInearLayout.addView(value0);
					myLInearLayout.addView(value1);
					myLInearLayout.addView(value2);
					myLInearLayout.addView(value3);
					myLInearLayout.addView(value4);
				}
				TextView valueTV = new TextView(getActivity());
				valueTV.setText("\n");
				valueTV.setLayoutParams(params);
				myLInearLayout.addView(valueTV);
				
				//CUANDO PRESIONAN GUARDAR
				botonGuardar.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		
//		        		ArrayList<ArrayList<String>> procedimientos = new ArrayList<ArrayList<String>>();
		        		procedimientos.clear();
		        		ArrayList<ObjectoX> ob = new ArrayList<ObjectoX>();
		        		
		        		ob = ProgramarCirugiaView.vista;
		        		int number = ob.size();
		        		System.out.println("No. VISTAS = "+number);
		        		
		        		/*TRAER DATOS DE LOS PROCEDIMIENTOS DINAMICOS*/
		        		//******************************************************************
		        		for (int index = 0; index<number; index++){
							System.out.println(index+"####################################");
							
							//MOVIMIENTO 18-DIC
//							ArrayList<String> temporary = new ArrayList<String>();
//							temporary.clear();
//							String servicioString = Integer.toString(ob.get(index).getServicio());
//							String regionString = Integer.toString(ob.get(index).getRegion());
							
							ArrayList<String> temporary = new ArrayList<String>();
							temporary.clear();
//							String servicioString = Integer.toString(ob.get(index).getServicio());
							String servicioString = arrayServicios.get(ob.get(index).getServicio()).get(0);	
							String regionString = Integer.toString(ob.get(index).getRegion());
							
							temporary.add(servicioString);
							temporary.add(regionString);
							temporary.add(ob.get(index).getProc());
							temporary.add(ob.get(index).getDetalles());
							
							procedimientos.add(temporary);
							
							System.out.println(ob.get(index).getServicio());
							System.out.println(ob.get(index).getRegion());
							System.out.println(ob.get(index).getProc());
							System.out.println(ob.get(index).getDetalles());
							//temporary.clear();
						}
						System.out.println("PROCEDIMIENTOS: "+procedimientos);
						//procedimientos.clear();
						//**********************************************************
		        		/*TRAER DATOS DE LOS PROCEDIMIENTOS DINAMICOS*/
		        		
		        		//String date = fecha.getText().toString();
		        		//String hora=horaPropuesta.getText().toString();
						
//						String date = Integer.toString(anio)+"-"+Integer.toString(mesDelAnio)+"-"+Integer.toString(diaDelMes); 
//						String hora = Integer.toString(horas2)+":"+Integer.toString(minutos2);
						
						String date = sAnio+"-"+sMesDelAnio+"-"+sDiaDelMes; 
						String hora = sHoras2+":"+sMinutos2;
						
				        String reg = registro.getText().toString();
				        String paciente = nombreDelPaciente.getText().toString();
				        String sEdad = edad.getText().toString();
				        String sGenero = genero.getText().toString();
				        String sProcedencia = procedencia.getText().toString();
				    	String sDiagnostico = diagnostico.getText().toString();
				        String sMedico = medico.getText().toString();
				        String sRiesgoQuirurgico = riesgoQuirurgico.getText().toString();
				    	String sInsumosIndispensables = insumosIndispensables.getText().toString();
				    	String sRequerimientos = requerimientos.getText().toString();
				    	String sHemoderivados = hemoderivados.getText().toString();
				    	String sRequisitos = requisitos.getText().toString();
				    	String sNecesidades = necesidades.getText().toString();
				    	
				        //Obtener enteros de RadioGroup
				        int protocoloSelected = 0;
				        int reintervencionSelected = 0;
				        protocoloSelected = protocolo.getCheckedRadioButtonId();
				        reintervencionSelected = reintervencion.getCheckedRadioButtonId();
				        
				        prot = (RadioButton) getActivity().findViewById(protocoloSelected);
				        reint = (RadioButton) getActivity().findViewById(reintervencionSelected);
				        //convertir a string para mandar a BD
				        String sProtocolo = prot.getText().toString();
				        String sReintervencion = reint.getText().toString();
				        
				        int p = 0;
				        int r = 0;
				        
				        if (sProtocolo.equals("No")){
				        	p = 0;
				        }
		        		else p = 1;
				        
				        if (sReintervencion.equals("No")){
				        	r = 0;
				        }
				        else p=1;
				        
				        System.out.println("intProtocolo = "+ p);
				        System.out.println("intReintervencion = "+ r);
				        
				        System.out.println("tipo sProtocolo = "+sProtocolo.getClass().getName()+"valor = "+sProtocolo);
				        System.out.println("tipo sReintervencion = "+sReintervencion.getClass().getName()+"valor = "+sReintervencion);
				        
				        //Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"protocolo = "+prot.getText(), Toast.LENGTH_SHORT);
				 	    //toast1.show();
				 	    
				 	    //Toast toast2 = Toast.makeText(getActivity().getApplicationContext(),"reintervencion = "+reint.getText(), Toast.LENGTH_SHORT);
				 	   	//toast2.show();
				        
				        ProgramarCirugiaView pos = new ProgramarCirugiaView();
				        
				        System.out.println("sala = "+getSala());
				        System.out.println("duracion = "+object.getDuracion());
				        System.out.println("programacion = "+object.getProgramacion());
				        System.out.println("servicio = "+object.getServicio());
				        System.out.println("atencion = "+object.getAtencion());
				        
				        /*
				        Toast toa = Toast.makeText(getActivity().getApplicationContext(), 
				        		"sala = "+object.getSala()+" duracion = "+object.getDuracion()+
				        		" programacion = "+object.getProgramacion()+" servicio = "+
				        		object.getServicio()+" atencion = "+object.getAtencion(),
				        		Toast.LENGTH_SHORT);
				        toa.show();
				        */
				        
				        //int sala = object.getSala();
				        int sala = getSala();
				        String sDuracion = object.getDuracion();
				        int programacion = object.getProgramacion();
				        int servicio = object.getServicio();
				        int atencion = object.getAtencion();
				        
				        String sSala = Integer.toString(sala);
				        //String sDuracion = Integer.toString(duracion);
				        String sProgramacion = Integer.toString(programacion);
//				        String sServicio = Integer.toString(servicio);
				        String sServicio = arrayServicios.get(servicio).get(0);
				        String sAtencion = Integer.toString(atencion);
				        
				        String sP = Integer.toString(p);
				        String sR = Integer.toString(r);
				        
				        //Toast toast10 = Toast.makeText(getActivity().getApplicationContext(),"posiciones = "+sala+duracion, Toast.LENGTH_SHORT);
				 	    //toast10.show();
				        //ArrayList<Integer> posicion = pos.getPosition();
				        //Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"posiciones = "+pos.getPosition(), Toast.LENGTH_SHORT);
				 	    //toast1.show();
		        		
		        		if (validarFormulario2(date, hora, reg, paciente, sEdad, sGenero, sProcedencia,
		        				sDiagnostico, sMedico, sRiesgoQuirurgico, sInsumosIndispensables,
		        				sRequerimientos, sHemoderivados, sRequisitos, sNecesidades) == true){
		        			
		        			// LOOP PARA OBTENER TODOS LOS DATOS DE LOS PROCEDIMIENTOS DINAMICOS*********
		        			//System.out.println(procedimientos);
		        			//Orden: Servicio-Region-Procedimiento-Detalles
		        			
		        			// LOOP PARA OBTENER TODOS LOS DATOS DE LOS PROCEDIMIENTOS DINAMICOS*********
		        			
		        			//Primero mandamos los Spinners luego los RadioButton
		        			String registro_ID = myString[5];
		        			new Formulario2().execute(date, hora, reg, paciente, sEdad, sGenero, 
		        					sProcedencia, sDiagnostico, sMedico, sRiesgoQuirurgico, sInsumosIndispensables,
		        					sRequerimientos, sHemoderivados, sRequisitos, sNecesidades, 
		        					sSala, sDuracion, sProgramacion, sServicio, sAtencion, sP, sR, registro_ID);
		        			//sv.removeAllViews();
		        			//sv.addView(home);
				        }
				        else{
				        	error1();
				        }//Fin de else
		        	}//Fin de onClick
		        });//boton de guardar formulario de cirugia
				//FIN DE CUANDO PRESIONAN GUARDAR
			}
		});
		//TERMINA MODIFICAR CIRUGIA
		
		//CANCELAR BUTTON
		cancelar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());  
		        dialogo1.setTitle("Importante");  
		        dialogo1.setMessage("¿Desea cancelar la cirugía?");            
		        dialogo1.setCancelable(false);  
		        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialogo1, int id) {  
		                aceptar();  
		            }  
		        });  
		        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialogo1, int id) {  
		                cancelar();
		            }  
		        });            
		        dialogo1.show();
			}
		});
		
		//INICIAR BUTTON *************************************************
		
		iniciarButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				sv.addView(iniciar);
				
				/** PARA TIME PICKER***************************************************** */
		        mPickedTimeText = (TextView) iniciar.findViewById(R.id.hour);
		        mPickedTimeText.setOnClickListener(new OnClickListener()
		        {
		            @Override
		            public void onClick(View v ){
		            	showTimePicker();
		            }
		        });
		        
		        /** PARA TIME PICKER *****************************************************/
				
				//Primer spinner - turno del instrumentista
				Spinner sp = (Spinner) iniciar.findViewById(R.id.turnoInstrumentista);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, nombreTurnos);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp.setAdapter(adapter);
				
				sp.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {
						//Toast.makeText(parentView.getContext(), "Has seleccionado " +
						//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
						parentView.getItemAtPosition(position);
						turnoInstrumentista = position;
						Log.e("spiner1 = ", "turnoInstrumentista="+turnoInstrumentista);
					}

					public void onNothingSelected(AdapterView<?> parentView) {

					}
				});
				
				//Segundo spinner - turno del circulante
				Spinner sp1 = (Spinner) iniciar.findViewById(R.id.turnoCirculante);
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, nombreTurnos);
						adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp1.setAdapter(adapter1);
				
				sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {
						//Toast.makeText(parentView.getContext(), "Has seleccionado " +
						//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
						parentView.getItemAtPosition(position);
						
						turnoCirculante = position;
						Log.e("spiner2 = ", "turnoCirculante="+turnoCirculante);
					}

					public void onNothingSelected(AdapterView<?> parentView) {

					}
				});
				
				guardar.setOnClickListener(new OnClickListener(){
					public void onClick(View v){
				
						//Obtener enteros de RadioGroup
				        int tiempoFueraSelected = 0;
				        tiempoFueraSelected = tiempoFuera.getCheckedRadioButtonId();
						tiempo = (RadioButton) getActivity().findViewById(tiempoFueraSelected);	
						
						String time = tiempo.getText().toString();
				        int t = 0;
				        
				        if (time.equals("No")){
				        	t = 0;
				        }
		        		else t = 1;
						
						System.out.println("Tiempo fuera int = "+t);
				        System.out.println("tipo sProtocolo = "+time.getClass().getName()+"tiempo fuera st = "+time);
						
		        		//String ingreso = ingresar.getText().toString();
//				        String ingreso = Integer.toString(horas)+":"+Integer.toString(minutos);
				        String ingreso = sHoras+":"+sMinutos;
				        
		        		String nombre_medico = medico_name.getText().toString();
		        		String nombre_cirujano = cirujano_name.getText().toString();
		        		String nombre_anestesiologo = anestesiologo.getText().toString();
		        		String nombre_supervisor = anestesiologo_supervisor.getText().toString();
		        		String tipo_tecnica = tecnica.getText().toString();
		        		String nombre_instrumentista = instrumentista.getText().toString();
		        		String nombre_circulante = circulante.getText().toString();
		        		String obs = observaciones.getText().toString();
		        		
		        		System.out.println("ingreso: "+ingreso);
		        		System.out.println("nombre_medico: "+nombre_medico);
		        		System.out.println("nombre_cirujano: "+nombre_cirujano);
		        		
		        		//Convertir int de radio button en string
		        		String tiempoFuera = Integer.toString(t);
		        		
		        		//Convertir turnos en string para enviarlos
		        		String sTurnoInstrumentista = turnos.get(turnoInstrumentista).get(0);
		        		String sTurnoCirculante = turnos.get(turnoCirculante).get(0);
		        		
		        		if (validarFormulario(ingreso, nombre_medico, nombre_cirujano, 
		        				nombre_anestesiologo, nombre_supervisor, tipo_tecnica, nombre_instrumentista,
		        				nombre_circulante, obs) == true){
		        			new Formulario().execute(ingreso, nombre_medico, nombre_cirujano, nombre_anestesiologo,
		        					nombre_supervisor, tipo_tecnica, nombre_instrumentista, nombre_circulante,
		        					obs, tiempoFuera, sTurnoInstrumentista, sTurnoCirculante);
		        			
		        		}//Fin de if
		        		else{
		        			error1();
		        		}
				
					}//Fin de onclick
				});//fin de iniciar boton setOnClick
			}//Fin de onclick
		});//fin de iniciar boton setOnClick
		
		diferirButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				sv.addView(diferir);
				
				Spinner sp = (Spinner) diferir.findViewById(R.id.causa_diferido);
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, arrayCausaDiferidoNombre);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {

						parentView.getItemAtPosition(position);
						int causa=position;
						causaNumber = causa;
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}
				});
				
				aceptar.setOnClickListener(new OnClickListener(){
					public void onClick(View v){
						//String causa_dif = causaDiferido.getText().toString();
						String causa_dif = arrayCausaDiferido.get(causaNumber).get(0);
						
						if (validarDiferido(causa_dif) == true){
		        			new CausaDiferido().execute(causa_dif);
		        		}//Fin de if
		        		else{
		        			error1();
		        		}
					}//Fin de onclick
				});//fin de iniciar boton setOnClick
			}
		});
		
		return v;
    	
    }//Fin de onCreateView
    
    public boolean validarDiferido(String causa_diferido){
		if 	(causa_diferido.equals("")){
			Log.e("formulario-diferido", "formulario incompleto");
        	return false;
        
        }else{
        	return true;
        }
	}//fin de validarDiferido
    
    public boolean validarFormulario(String ingreso, String nombre_medico, String nombre_cirujano, 
			String nombre_anestesiologo, String nombre_supervisor, String tipo_tecnica, 
			String nombre_instrumentista, String nombre_circulante, String obs){
		if 	(ingreso.equals("") || nombre_medico.equals("") || nombre_cirujano.equals("") || 
				nombre_anestesiologo.equals("") || nombre_supervisor.equals("") || tipo_tecnica.equals("") || 
				nombre_instrumentista.equals("") || nombre_circulante.equals("") || obs.equals("")){
//			Log.e("date = ", "date="+date);
//			Log.e("hora = ", "hora="+hora);
			Log.e("formulario-iniciar cirugia", "formulario incompleto");
			//Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h, Toast.LENGTH_SHORT);
	 	    //toast1.show();
        	return false;
        
        }else{
        	//Toast toast2 = Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h, Toast.LENGTH_SHORT);
        	//toast2.show();
        	return true;
        }
	}//fin de validarFormulario
    
    public void error1(){
    	Vibrator vibrator =(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Error:Favor de llenar todos los campos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
    public void aceptar() {
    	new CancelarCirugia().execute(myString[0], myString[1], myString[2], myString[3], myString[4], myString[5]);
        //Toast t=Toast.makeText(getActivity(),"Cirugía cancelada", Toast.LENGTH_SHORT);
        //t.show();
    }
    
    public void cancelar() {
        finish();
    }
    
    private void finish() {
		// TODO Auto-generated method stub
    	Toast t=Toast.makeText(getActivity(),"Operación cancelada.", Toast.LENGTH_SHORT);
        t.show();
	}
    public boolean cancelarCirugia(String ID){
    	int status = -1;
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("ID",ID));
				
		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect);
  		  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento para conocer el status de la operacion
				status=json_data.getInt("logstatus");//accedemos al valor
				System.out.println("status cancelacion="+status);
				//qId = json_data.getString("quirofano_id");
				Log.e("getQuirofanoId","status= "+status);//muestro por log que obtuvimos
				return true;
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}		            
    		
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON getQuirofanoId  ", "ERROR");
	    	return false;
	    }//Fin de else
    }//Fin de cancelarCirugia
    
    public void getTurno(String id_quir){
    	turnos.clear();
    	nombreTurnos.clear();
    	int status = -1;
    	
    	String id = "";
    	String nombre = ""; 
    	
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("ID",id_quir));
				
		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect2);
  		  		  		
  		//si lo que obtuvimos no es null
		if (jdata!=null && jdata.length() > 0){
    		//JSONObject json_data; //creamos un objeto JSON
			try {
				
				for(int n = 0; n < jdata.length(); n++){
					//st.clear();
					System.out.println("vuelta:"+n);
					JSONObject json_data = jdata.getJSONObject(n);
					
					int temporal_id = json_data.getInt("dato");
					id = Integer.toString(temporal_id);
					
					nombre = json_data.getString("dato1");
					
					ArrayList<String> temporary = new ArrayList<String>();
					
					temporary.add(id);
					temporary.add(nombre);
					
					Log.e("log-st", "array temprary = "+temporary);
					
					turnos.add(temporary);
					//st.clear();
				}
				
				Log.e("array turnos", "turnos = "+turnos);
				
				for (int index = 0; index<turnos.size(); index ++){
					nombreTurnos.add(turnos.get(index).get(1));
				}
				Log.e("array nombreTurnos", "turnos agregados en spinner = "+nombreTurnos);
								
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("hi", "hi");
			}		            
			//return st;
			
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON  ", "ERROR");
	    	//return st;
	    }
    }//Fin de getTurno
    
    public boolean enviarFormulario(String ingreso, String nombre_medico, String nombre_cirujano, 
			String nombre_anestesiologo, String nombre_supervisor, String tipo_tecnica, 
			String nombre_instrumentista, String nombre_circulante, String obs, String tiempoFuera,
			String sTurnoInstrumentista, String sTurnoCirculante){
	
		int status = -1;
		
		/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		
		datosEnviar.add(new BasicNameValuePair("ingreso",ingreso));
		datosEnviar.add(new BasicNameValuePair("nombre_medico",nombre_medico));
		datosEnviar.add(new BasicNameValuePair("nombre_cirujano",nombre_cirujano));
		datosEnviar.add(new BasicNameValuePair("nombre_anestesiologo",nombre_anestesiologo));
		datosEnviar.add(new BasicNameValuePair("nombre_supervisor",nombre_supervisor));
		datosEnviar.add(new BasicNameValuePair("tipo_tecnica",tipo_tecnica));
		datosEnviar.add(new BasicNameValuePair("nombre_instrumentista",nombre_instrumentista));
		datosEnviar.add(new BasicNameValuePair("nombre_circulante",nombre_circulante));
		datosEnviar.add(new BasicNameValuePair("observaciones",obs));
		//EditText hasta aqui
		
		//Spinners y RadioButton
		datosEnviar.add(new BasicNameValuePair("tiempoFuera",tiempoFuera));
		datosEnviar.add(new BasicNameValuePair("turnoInstrumentista",sTurnoInstrumentista));
		datosEnviar.add(new BasicNameValuePair("turnoCirculante",sTurnoCirculante));
		datosEnviar.add(new BasicNameValuePair("agenda_id",myString[5]));

		//Nueva forma de obtener el id del quirofano
		//System.out.println("q_id = "+ID_quirofano); 
		//datosEnviar.add(new BasicNameValuePair("q_id", ID_quirofano));

		// FIN - Nueva forma de obtener el id del quirofano
		
		Log.e("datosEnviar","datosEnviar = "+datosEnviar);
		Log.e("URL_connect","URL_connect iniciar = "+URL_connect1);
		
		//realizamos una peticion y como respuesta obtienes un array JSON
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect1);
  		  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
//				status=json_data.getInt("logstatus");//accedemos al valor
				status=json_data.getInt("logstatus");//nuevo valor 18-noviembre
				Log.e("enviarFormulario","status= "+status);//muestro por log que obtuvimos
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		            
             
			//validamos el valor obtenido
    		if (status==0){// [{"logstatus":"0"}] 
    			Log.e("Los datos no han sido guardados ", "envio fallido");
    			return false;
    		}
    		else{// [{"logstatus":"1"}]
    			Log.e("Los datos de iniciar cirugia han sido guardados ", "envio exitoso");
    			return true;
    		}
    		 
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON  ", "ERROR");
	    	return false;
	    }//Fin de else
		
	}//Fin de enviar formulario
    
    public boolean causaDiferido(String causa_dif){
	
		int status = -1;
		
		/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		
		datosEnviar.add(new BasicNameValuePair("causa_diferido",causa_dif));
		datosEnviar.add(new BasicNameValuePair("agenda_id",myString[5]));
		
		Log.e("datosEnviar","datosEnviar = "+datosEnviar);
		Log.e("URL_connect","URL_connect iniciar = "+URL_connect3);
		
		//realizamos una peticion y como respuesta obtienes un array JSON
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect3);
  		  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
//				status=json_data.getInt("logstatus");//accedemos al valor
				status=json_data.getInt("logstatus");//nuevo valor 18-noviembre
				Log.e("diferirCirugia","status= "+status);//muestro por log que obtuvimos
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		            
             
			//validamos el valor obtenido
    		if (status==0){// [{"logstatus":"0"}] 
    			Log.e("Los datos de diferir cirugia no han sido guardados ", "envio fallido");
    			return false;
    		}
    		else{// [{"logstatus":"1"}]
    			Log.e("Los datos de diferir cirugia han sido guardados ", "envio exitoso");
    			return true;
    		}
    		 
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON  ", "ERROR");
	    	return false;
	    }//Fin de else
		
	}//Fin de causaDiferido
    
    //Traer las causas de diferido del server, para llenar el spinner de diferir cirugia
  	public ArrayList<String> getCausaDiferido(String registro_id){
  		arrayCausaDiferido.clear();
  		
  		ArrayList<String> causaDiferidoTemporal = new ArrayList<String>();
  		
  		String val, value;
  		
  		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
  		datosEnviar.add(new BasicNameValuePair("registro_id",registro_id));
  		
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect4);
  		
  		if (jdata!=null && jdata.length() > 0){
  		
  			try {
  				
  				for(int n = 0; n < jdata.length(); n++){

  					JSONObject json_data = jdata.getJSONObject(n);
  					val = json_data.getString("dat");		//Obtiene el id de la causa de diferido
  					value = json_data.getString("dato");	//Obtiene los nombres de la causa de diferido
  					
  					ArrayList<String> temporary = new ArrayList<String>();
  					temporary.add(val);
  					temporary.add(value);
  					
  					arrayCausaDiferido.add(temporary);
  					
  					causaDiferidoTemporal.add(value);
  				}
  			}
  			catch (JSONException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  				Log.e("hi", "hi");
  			}		            			
      	}//Fin de if(comprueba si lo obtenido no es "null")
      	
      	else{	//json obtenido invalido verificar parte WEB.
      		Log.e("JSON getQuirofanoId  ", "ERROR");
  	    	//return false;
  	    }//Fin de else
  		return causaDiferidoTemporal;
  	}//Fin de getCausaDiferido
    
    public void mostrarLeyenda(){
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Datos guardados con éxito", Toast.LENGTH_SHORT);
		t.show();
	}
    public void error2(){
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Error: error en el envío de datos", Toast.LENGTH_SHORT);
		t.show();
	}

    class CancelarCirugia extends AsyncTask< String, String, String> {
		
		//String date, hora, reg, paciente;
    	String st1; //String fecha
    	String st2; //String hora
    	String st3; //String sala
    	String st4; //String paciente
    	String st5; //String diagnostico 
    	String st6; //String ID
		
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string fecha 
			st2=params[1]; //obtenemos el string hora
			st3=params[2]; //obtenemos el string sala
			st4=params[3]; //obtenemos el string paciente
			st5=params[4]; //obtenemos el string diagnostico
			st6=params[5];	
			
			if (cancelarCirugia(st6) == true){
				return "ok";
			}
			else return "error";
    		
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
        	//progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=","status="+resultado);
            
            if (resultado.equals("ok")){
            	Toast t=Toast.makeText(getActivity(),"Cirugía cancelada", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
            	Toast t=Toast.makeText(getActivity(),"No se pudo realizar la acción", Toast.LENGTH_SHORT);
                t.show();
            }
            
        }//Fin de onPostExecute        
	}//Fin de la subclase CancelarCirugia
    
    class Formulario extends AsyncTask< String, String, String > {
      	 
    	String ingreso, nombre_medico, nombre_cirujano, 
		nombre_anestesiologo, nombre_supervisor, tipo_tecnica, nombre_instrumentista,
		nombre_circulante, obs, tiempoFuera, sTurnoInstrumentista, sTurnoCirculante;
    	
    	//Seran convertidos a enteros, excepto duracion, sera convertido a TIME
    	String sSala, sDuracion, sProgramacion, sServicio, sAtencion, sP, sR;
    	
    	protected void onPreExecute() {
    		progress = ProgressDialog.show(
    		getActivity(), null, "Guardando ...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			//obtnemos datos
        	//EditText
			ingreso=params[0];						
			nombre_medico=params[1];				
			nombre_cirujano=params[2];  			
			nombre_anestesiologo=params[3];			
			nombre_supervisor=params[4];			
			tipo_tecnica=params[5];					
			nombre_instrumentista=params[6];		
			nombre_circulante=params[7];			
			obs=params[8];	
			
			//Radiobutton "tiempo fuera"
			tiempoFuera = params[9];
			
			//spinners
			sTurnoInstrumentista = params[10];
			sTurnoCirculante = params[11];
			
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		if (enviarFormulario(ingreso, nombre_medico, nombre_cirujano, nombre_anestesiologo,
    				nombre_supervisor, tipo_tecnica, nombre_instrumentista, nombre_circulante, obs,
    				tiempoFuera, sTurnoInstrumentista, sTurnoCirculante)==true){    		    		
    			return "ok"; //login valido
    		}else{    		
    			return "error"; //login invalido     	          	  
    		}	
		}//Fin de doInBackground
        
        protected void onPostExecute(String result) {
        	progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=",""+result);
            
            if (result.equals("ok")){
            	mostrarLeyenda();
            }
            else {
             	error2();
            }
        }//Fin de onPostExecute        
	}//Fin de la subclase Formulario
    
    class GetTurno extends AsyncTask< String, String, String> {
		String st1; //String con el del quirofano
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string con el id del quirofano 
//			if (getTurno(st1) == true){
//				return "ok";
//			}
//			else return "error";
			getTurno(st1);
			return "ok";
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
            Log.e("onPostExecute=","status="+resultado);            
        }//Fin de onPostExecute        
	}//Fin de la subclase GetTurno
    
    class CausaDiferido extends AsyncTask< String, String, String > {
     	 
    	String causa_dif;
    	
    	protected void onPreExecute() {
    		progress = ProgressDialog.show(
    		getActivity(), null, "Guardando ...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			//obtnemos datos
        	//EditText
			causa_dif=params[0];						
			
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		if (causaDiferido(causa_dif)==true){    		    		
    			return "ok"; //login valido
    		}else{    		
    			return "error"; //login invalido     	          	  
    		}	
		}//Fin de doInBackground
        
        protected void onPostExecute(String result) {
        	progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=",""+result);
            
            if (result.equals("ok")){
            	mostrarLeyenda();
            }
            else {
             	error2();
            }
        }//Fin de onPostExecute        
	}//Fin de la subclase CausaDiferido
    
    //Sub clase para obtener las causas de diferido
  	class GetCausaDiferido extends AsyncTask< String, String, ArrayList<String>> {
  		String quir; //El string id del registro seleccionado
  			
  	    protected void onPreExecute() {
  	    	super.onPreExecute();
  	    }
  	    	
      	protected ArrayList<String> doInBackground(String... params) {
  			quir=params[0]; //obtenemos el string de id del registro 
  			ArrayList<String> tempoCausaDiferido = new ArrayList<String>();
  			tempoCausaDiferido = getCausaDiferido(quir);
      		return tempoCausaDiferido;
  		}//Fin de doInBackground
  	       
      	protected void onPostExecute(ArrayList<String> resultado) {	    		
  	    	arrayCausaDiferidoNombre = resultado;
  	    	System.out.println("LAS-CAUSAS-DIFERIDO = "+arrayCausaDiferidoNombre);
      	}//Fin de onPostExecute        		
  	}//Fin de la subclase GetCausaDiferido
    
    //PARA TIMEPICKER ***********************************************************************************
	 
	 private void showTimePicker() {
		  TimePickerFragment time = new TimePickerFragment();
		  /**
		   * Set Up Current Date Into dialog
		   */
		  Calendar calender = Calendar.getInstance();
		  Bundle args = new Bundle();
		  args.putInt("hour", calender.get(Calendar.YEAR));
		  args.putInt("minutes", calender.get(Calendar.MONTH));
		  time.setArguments(args);
		  /**
		   * Set Call back to capture selected date
		   */
		  time.setCallBack(ontime);
		  time.show(getFragmentManager(), "Date Picker");
	}
	
	//2
	OnTimeSetListener ontime = new OnTimeSetListener() {
		  @Override
		  public void onTimeSet(TimePicker view, int hour, int minutes) {
//			  horas = hour;
//			  minutos = minutes;
			  
			  if (hour<10){
				  sHoras = "0"+Integer.toString(hour);
			  }
			  else{
				  sHoras = Integer.toString(hour);
			  }
			  if (minutes<10){
				  sMinutos = "0"+Integer.toString(minutes);
			  }
			  else{
				  sMinutos = Integer.toString(minutes); 
			  }
			  horas = hour;
			  minutos = minutes;
			  
		   Toast.makeText(getActivity().getApplicationContext(), String.valueOf(sHoras) + ":" + String.valueOf(sMinutos), Toast.LENGTH_SHORT).show();
//		   String all = Integer.toString(horas)+":"+ Integer.toString(minutos);
		   String all = sHoras+":"+sMinutos;
		   mPickedTimeText.setText("  "+all);
		  }
		 };
	 
	//PARA TIMEPICKER ***********************************************************************************
	
	//PARA TIMEPICKER 2 ***********************************************************************************
		 
	private void showTimePicker2() {
		TimePickerFragment time2 = new TimePickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender2 = Calendar.getInstance();
		Bundle args2 = new Bundle();
		args2.putInt("hour", calender2.get(Calendar.YEAR));
		args2.putInt("minutes", calender2.get(Calendar.MONTH));
		time2.setArguments(args2);
		/**
		 * Set Call back to capture selected date
		 */
		time2.setCallBack(ontime2);
		time2.show(getFragmentManager(), "Date Picker");
	}

	// 2
	OnTimeSetListener ontime2 = new OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hour, int minutes) {
//			horas2 = hour;
//			minutos2 = minutes;
			
			if (hour<10){
				  sHoras2 = "0"+Integer.toString(hour);
			  }
			  else{
				  sHoras2 = Integer.toString(hour);
			  }
			  if (minutes<10){
				  sMinutos2 = "0"+Integer.toString(minutes);
			  }
			  else{
				  sMinutos2 = Integer.toString(minutes); 
			  }
			  
			  horas2 = hour;
			  minutos2 = minutes;
			
			Toast.makeText(getActivity().getApplicationContext(),
					String.valueOf(sHoras2) + ":" + String.valueOf(sMinutos2),
					Toast.LENGTH_SHORT).show();
//			String all = Integer.toString(horas2) + ":"
//					+ Integer.toString(minutos2);
			
			String all = sHoras2+":"+sMinutos2;
			
			mPickedTimeText2.setText("  " + all);
		}
	};

	// PARA TIMEPICKER
	// ***********************************************************************************
			 
	// *** PARA EL DATE PICKER
	// **************************************************************************

	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();
		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	// 2
	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, // antes
																			// int
				int dayOfMonth) {
//			anio = year;
//			mesDelAnio = monthOfYear + 1;
//			diaDelMes = dayOfMonth;
			
			sAnio = Integer.toString(year);
			  monthOfYear = monthOfYear+1;
			  if(monthOfYear<10){
				  sMesDelAnio = "0"+Integer.toString(monthOfYear);
			  }
			  else{
				  sMesDelAnio = Integer.toString(monthOfYear);
			  }
			  
			  if(dayOfMonth<10){
				  sDiaDelMes = "0"+Integer.toString(dayOfMonth);
			  }
			  else{
				  sDiaDelMes = Integer.toString(dayOfMonth);
			  }
			
			Toast.makeText(
					getActivity().getApplicationContext(),
					String.valueOf(sAnio) + "-"
							+ String.valueOf(sMesDelAnio) + "-"
							+ String.valueOf(sDiaDelMes), Toast.LENGTH_SHORT)
					.show();
			
//			String all = Integer.toString(anio) + "-"
//					+ Integer.toString(mesDelAnio) + "-"
//					+ Integer.toString(diaDelMes);
			String all = sAnio+"-"+sMesDelAnio+"-"+sDiaDelMes;
			pDisplayDate.setText("  " + all);
		}
	};

	// *** PARA EL DATE PICKER
	// **************************************************************************
	
	// GETTERS
	public int getSala() {
		return salaSpinner;
	}// Fin de getSala
	
	// Primero validamos los campos
	public boolean validarFormulario2(String date, String hora, String reg,
			String paciente, String sEdad, String sGenero, String sProcedencia,
			String sDiagnostico, String sMedico, String sRiesgoQuirurgico,
			String sInsumosIndispensables, String sRequerimientos,
			String sHemoderivados, String sRequisitos, String sNecesidades) {
		if (date.equals("") || hora.equals("") || reg.equals("")
				|| paciente.equals("") || sEdad.equals("")
				|| sGenero.equals("") || sProcedencia.equals("")
				|| sDiagnostico.equals("") || sMedico.equals("")
				|| sRiesgoQuirurgico.equals("")
				|| sInsumosIndispensables.equals("")
				|| sRequerimientos.equals("") || sHemoderivados.equals("")
				|| sRequisitos.equals("") || sNecesidades.equals("")) {
			Log.e("date = ", "date=" + date);
			Log.e("hora = ", "hora=" + hora);
			Log.e("Login ui", "checklogindata user or pass error");
			// Toast toast1 =
			// Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h,
			// Toast.LENGTH_SHORT);
			// toast1.show();
			return false;

		} else {
			// Toast toast2 =
			// Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h,
			// Toast.LENGTH_SHORT);
			// toast2.show();
			return true;
		}
	}// fin de validarFormulario
	
	class GetProcedimientosCirugiaProgramada extends AsyncTask< String, String, String> {
		
    	String st1; //String con el id del quirofano seleccionado
		
    	protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string con "ok" 
			
    		try {
    			getProcedimientosCirugiaProgramada(st1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Log.e("last-array", "last-array = "+al.get(0));
    		return "ok"; //login valido
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
        	//progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=","Todo bien="+resultado);
            System.out.println(">>>PROCEDIMIENTOS-CIRUGIA"+arrayProcedimientosCirugiaProgramada);
        }//Fin de onPostExecute        
	}//Fin de la subclase GetProcedimientosProgramada
	
	/** OBTENER PROCEDIMIENTOS PARA MOSTRAR EN LA SECCION DE MODIFICAR CIRUGIA */
	public void getProcedimientosCirugiaProgramada(String s) throws JSONException{	
		arrayProcedimientosCirugiaProgramada.clear();
		String val = "", value = "", value1 = "", value2 = "", value3 = "", value4 = "", 
				value5 = "", value6 = "", cont = "";
			
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("stat", s));

		JSONArray jdata = envio.getserverdata(datosEnviar, URL_connect7);
		//System.out.println("jdata = "+jdata.getString(0));
		System.out.println(jdata.toString());
		System.out.println("largo de jdata = "+jdata.length());
		if (jdata!=null && jdata.length() > 0){
	   		//JSONObject json_data; //creamos un objeto JSON
			try {
					
				for(int n = 0; n < jdata.length(); n++){
					System.out.println("vuelta:"+n);
					JSONObject json_data = jdata.getJSONObject(n);
					val = json_data.getString("d0");	//id de procedimiento
					value = json_data.getString("d1");	//cie9mc
					value1 = json_data.getString("d2");	//region
					value2 = json_data.getString("d3");	//detalles
					value3 = json_data.getString("d4");	//servicio
						
					ArrayList<String> temporary = new ArrayList<String>();
						
					temporary.add(val);
					temporary.add(value);
					temporary.add(value1);
					temporary.add(value2);
					temporary.add(value3);
									
					arrayProcedimientosCirugiaProgramada.add(temporary);
				}

				Log.e("array ProcedimientosCirugiaProgramada", "arrayProcedimientosCirugiaProgramada = "+arrayProcedimientosCirugiaProgramada);
			
				//Log.e("enviarFormulario","status= "+status);//muestro por log que obtuvimos
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("hi", "hi");
			}		            	
	    }//Fin de if(comprueba si lo obtenido no es "null")
	    	
	    else{	//json obtenido invalido verificar parte WEB.
	    	Log.e("JSON  ", "ERROR");
		   	//return st;
			}
	}//Fin de getProcedimientosProgramada
	
	class GetDatosCirugiaProgramada extends AsyncTask< String, String, ArrayList<String>> {
  		
  		String quir; //El string llevara el id de la cirugia seleccionada
  			
  	    protected void onPreExecute() {
  	    	super.onPreExecute();
  	    }
  	    	
      	protected ArrayList<String> doInBackground(String... params) {
  			quir=params[0]; //obtenemos el string de la cirugia seleccionada 
  			ArrayList<String> tempoDatosCirugia = new ArrayList<String>();
  			tempoDatosCirugia = getDatosCirugiaProgramada(quir);
      		return tempoDatosCirugia;
  		}//Fin de doInBackground
  	       
      	protected void onPostExecute(ArrayList<String> resultado) {	    		
      		cirugiaProgramadaData = resultado;
      		System.out.println("DATOS_CIRUGIA = "+cirugiaProgramadaData);
      	}//Fin de onPostExecute        		
  	}//Fin de la subclase GetDatosCirugiaProgramada
	
	//Traer los datos de la cirugia programada seleccionada
  	public ArrayList<String> getDatosCirugiaProgramada(String quir_id){
  		
  		ArrayList<String> datosCirugiaTemporal = new ArrayList<String>();
  		
  		String valor;
  		
  		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
  		datosEnviar.add(new BasicNameValuePair("registro_id",quir_id));
  		
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect6);
  		
  		if (jdata!=null && jdata.length() > 0){
  		
  			try {
  				for(int n = 0; n < jdata.length(); n++){
  					JSONObject json_data = jdata.getJSONObject(n);
  					for (int j = 0; j<23; j++){
//	  					System.out.println("VUELTA = "+n);
	  					valor = json_data.getString("dato"+j);
	  					
	  					datosCirugiaTemporal.add(valor);
  					}
  				}
  			}
  			catch (JSONException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  				Log.e("hi", "hi");
  			}		            			
      	}//Fin de if(comprueba si lo obtenido no es "null")
      	
      	else{	//json obtenido invalido verificar parte WEB.
      		Log.e("JSON getQuirofanoId  ", "ERROR");
  	    }//Fin de else
  		return datosCirugiaTemporal;
  	}//Fin de getDatosCirugiaProgramada
  	
  	class GetServicios extends AsyncTask< String, String, ArrayList<String>> {
		
		LayoutInflater inflater;
		View programar;
		ViewGroup container;
		
		GetServicios(LayoutInflater inflater,View programar, ViewGroup container){
			this.inflater = inflater;
			this.programar = programar;
			this.container = container;
		}
		
		String quir; //El string llevara el quirofano_id
			
	    protected void onPreExecute() {
	    	super.onPreExecute();
	    }
	    	
    	protected ArrayList<String> doInBackground(String... params) {
			quir=params[0]; //obtenemos el string de quirofano_id 
			ArrayList<String> tempoServicios = new ArrayList<String>();
			tempoServicios = getServicios(quir);
    		return tempoServicios;
		}//Fin de doInBackground
	       
    	protected void onPostExecute(ArrayList<String> resultado) {	    		
	    	nombreServicios = resultado;
	    	System.out.println("LOS-SERVICIOS = "+nombreServicios);
	    	ProgramarCirugiaView pcv = new ProgramarCirugiaView();
			pcv.prog(inflater, programar, getActivity());
    	}//Fin de onPostExecute        		
	}//Fin de la subclase GetServicios
  	
  //Traer los servicios del server, para llenar el spinner de programar cirugia
  		public ArrayList<String> getServicios(String quir_id){
  			arrayServicios.clear();
  			
  			ArrayList<String> serviciosTemporal = new ArrayList<String>();
  			
  			String val, value;
  			
  			ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
  			datosEnviar.add(new BasicNameValuePair("quirofano_id",quir_id));
  			
  			JSONArray jdata=post.getserverdata(datosEnviar, URL_connect9);
  			
  			if (jdata!=null && jdata.length() > 0){
  			
  				try {
  					
  					for(int n = 0; n < jdata.length(); n++){

  						JSONObject json_data = jdata.getJSONObject(n);
  						val = json_data.getString("dat");		//Obtiene el id de los servicios
  						value = json_data.getString("dato");	//Obtiene los nombres de los servicios
  						
  						ArrayList<String> temporary = new ArrayList<String>();
  						temporary.add(val);
  						temporary.add(value);
  						
  						arrayServicios.add(temporary);
  						
  						serviciosTemporal.add(value);
  					}
  				}
  				catch (JSONException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  					Log.e("hi", "hi");
  				}		            			
  	    	}//Fin de if(comprueba si lo obtenido no es "null")
  	    	
  	    	else{	//json obtenido invalido verificar parte WEB.
  	    		Log.e("JSON getQuirofanoId  ", "ERROR");
  		    	//return false;
  		    }//Fin de else
  			return serviciosTemporal;
  		}//Fin de getServicios
  	
	class Formulario2 extends AsyncTask<String, String, String> {
		String status;
		
		// String date, hora, reg, paciente;
		String registro_ID, date, hora, reg, paciente, sEdad, sGenero,
				sProcedencia, sDiagnostico, sMedico, sRiesgoQuirurgico,
				sInsumosIndispensables, sRequerimientos, sHemoderivados,
				sRequisitos, sNecesidades;

		// Seran convertidos a enteros, excepto duracion, sera convertido a TIME
		String sSala, sDuracion, sProgramacion, sServicio, sAtencion, sP, sR;

		protected void onPreExecute() {
			progress = ProgressDialog.show(getActivity(), null,
					"Programando cirugía ...");
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {
			// obtnemos datos
			date = params[0]; // Para date
			hora = params[1]; // Para hora
			reg = params[2]; // Para registro
			paciente = params[3]; // Para nombre del paciente
			sEdad = params[4]; // Para edad del paciente
			sGenero = params[5]; // Para genero del paciente
			sProcedencia = params[6]; // Para procedencia
			sDiagnostico = params[7]; // Para diagnostico
			sMedico = params[8]; // Para nombre del medico
			sRiesgoQuirurgico = params[9]; // Para riesgo quirurgico
			sInsumosIndispensables = params[10]; // Para insumos indispensables
			sRequerimientos = params[11]; // Para requerimientos de
											// anestesiologia
			sHemoderivados = params[12]; // Para hemoderivados necesarios
			sRequisitos = params[13]; // Para requisitos de laboratorio
			sNecesidades = params[14]; // Para otras necesidades

			sSala = params[15];
			sDuracion = params[16];
			sProgramacion = params[17];
			sServicio = params[18];
			sAtencion = params[19];
			sP = params[20];
			sR = params[21];
			registro_ID = params[22];

			// enviamos y recibimos y analizamos los datos en segundo plano.
//			if (enviarFormulario2(date, hora, reg, paciente, sEdad, sGenero,
//					sProcedencia, sDiagnostico, sMedico, sRiesgoQuirurgico,
//					sInsumosIndispensables, sRequerimientos, sHemoderivados,
//					sRequisitos, sNecesidades, sSala, sDuracion, sProgramacion,
//					sServicio, sAtencion, sP, sR, registro_ID) == true) {
//				return "ok"; // login valido
//			} else {
//				return "error"; // login invalido
//			}
			
			//3enero
			status = enviarFormulario2(date, hora, reg, paciente, sEdad, sGenero,
					sProcedencia, sDiagnostico, sMedico, sRiesgoQuirurgico,
					sInsumosIndispensables, sRequerimientos, sHemoderivados,
					sRequisitos, sNecesidades, sSala, sDuracion, sProgramacion,
					sServicio, sAtencion, sP, sR, registro_ID);
			
			return status;
			
		}// Fin de doInBackground

		protected void onPostExecute(String result) {
			progress.dismiss();// ocultamos progess dialog.
			Log.e("onPostExecute=", "" + result);

//			if (result.equals("ok")) {
//				// mostrarLeyenda(); //cambiar esto, mostrar leyenda despues de
//				// enviar los procedimientos
//				// Nueva subclase: EnviarProcedimientos
//				new EnviarProcedimientos().execute(registro_ID);
//			} else {
//				error2();
//			}
			
			//3enero
			String sub = result.substring(0,1);
            System.out.println("SUBSTRING: "+sub);
           
            if (result.equals("error1")) {
             	error3();
//            	System.out.println("ERROR EN PROGRAMACION DE CIRUGIA!!");
            }
            else if (result.equals("error2")) {
             	error4();
//            	System.out.println("ERROR EN PROGRAMACION DE CIRUGIA!!");
            }
            else if (result.equals("error")) {
             	error2();
            	System.out.println("ERROR EN PROGRAMACION DE CIRUGIA!!");
            }
            else if (sub.equals("w")){
            	error5(result);
            }
            else {
            	new EnviarProcedimientos().execute(registro_ID);
            }
			
		}// Fin de onPostExecute
	}// Fin de la subclase Formulario2
	
	// Enviar formulario para modificar cirugia
	public String enviarFormulario2(String date, String hora, String reg,
			String paciente, String sEdad, String sGenero, String sProcedencia,
			String sDiagnostico, String sMedico, String sRiesgoQuirurgico,
			String sInsumosIndispensables, String sRequerimientos,
			String sHemoderivados, String sRequisitos, String sNecesidades,
			String sSala, String sDuracion, String sProgramacion,
			String sServicio, String sAtencion, String sP, String sR,
			String registro_ID) {

		// int status = -1;
		String status = "";

		/*
		 * Creamos un ArrayList del tipo nombre valor para agregar los datos
		 * recibidos por los parametros anteriores y enviarlo mediante POST a
		 * nuestro sistema para relizar la validacion
		 */

		ArrayList<NameValuePair> datosEnviar = new ArrayList<NameValuePair>();

		// String laFecha =
		// Integer.toString(anio)+"-"+Integer.toString(mesDelAnio)+"-"+Integer.toString(diaDelMes);
		// String laHora =
		// Integer.toString(horas)+":"+Integer.toString(minutos);

		// datosEnviar.add(new BasicNameValuePair("date",date));
		// datosEnviar.add(new BasicNameValuePair("hora",hora));
		datosEnviar.add(new BasicNameValuePair("date", date));
		datosEnviar.add(new BasicNameValuePair("hora", hora));
		datosEnviar.add(new BasicNameValuePair("reg", reg));
		datosEnviar.add(new BasicNameValuePair("paciente", paciente));
		datosEnviar.add(new BasicNameValuePair("sEdad", sEdad));
		datosEnviar.add(new BasicNameValuePair("sGenero", sGenero));
		datosEnviar.add(new BasicNameValuePair("sProcedencia", sProcedencia));
		datosEnviar.add(new BasicNameValuePair("sDiagnostico", sDiagnostico));
		datosEnviar.add(new BasicNameValuePair("sMedico", sMedico));
		datosEnviar.add(new BasicNameValuePair("sRiesgoQuirurgico",
				sRiesgoQuirurgico));
		datosEnviar.add(new BasicNameValuePair("sInsumosIndispensables",
				sInsumosIndispensables));
		datosEnviar.add(new BasicNameValuePair("sRequerimientos",
				sRequerimientos));
		datosEnviar
				.add(new BasicNameValuePair("sHemoderivados", sHemoderivados));
		datosEnviar.add(new BasicNameValuePair("sRequisitos", sRequisitos));
		datosEnviar.add(new BasicNameValuePair("sNecesidades", sNecesidades));

		// EditText hasta aqui

		// Spinners y RadioButton
		datosEnviar.add(new BasicNameValuePair("sSala", sSala));
		datosEnviar.add(new BasicNameValuePair("sDuracion", sDuracion));
		datosEnviar.add(new BasicNameValuePair("sProgramacion", sProgramacion));
		datosEnviar.add(new BasicNameValuePair("sServicio", sServicio));
		datosEnviar.add(new BasicNameValuePair("sAtencion", sAtencion));
		datosEnviar.add(new BasicNameValuePair("sP", sP));
		datosEnviar.add(new BasicNameValuePair("sR", sR));

		// Nueva forma de obtener el id del quirofano
		datosEnviar.add(new BasicNameValuePair("q_id", myString[6]));
		datosEnviar.add(new BasicNameValuePair("registro_ID", registro_ID));

		// realizamos una peticion y como respuesta obtienes un array JSON
		JSONArray jdata = post.getserverdata(datosEnviar, URL_connect5);

		// si lo que obtuvimos no es null
		if (jdata != null && jdata.length() > 0) {
			JSONObject json_data; // creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); // leemos el primer segmento
													// en nuestro caso el unico
				// status=json_data.getInt("logstatus");//accedemos al valor
				status = json_data.getString("resultado");// nuevo valor
															// 18-noviembre
				Log.e("enviarFormulario", "status= " + status);// muestro por
																// log que
																// obtuvimos
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// validamos el valor obtenido
			if (status.equals("error")) {// [{"logstatus":"0"}]
				Log.e("programacion-status ", "envio fallido");
				return status;
			} else {// [{"logstatus":"1"}]
				Log.e("programacion-status ", "envio exitoso");
				return status;
			}

		}// Fin de if(comprueba si lo obtenido no es "null")

		else { // json obtenido invalido verificar parte WEB.
			Log.e("JSON  ", "ERROR");
			return status;
		}// Fin de else

	}// Fin de enviar formulario
	
	class EnviarProcedimientos extends AsyncTask< String, String, String> {
		
		String registro; //lleva el identificador del registro programado para programar los
		//procedimientos en la tabla: siga_procedimientos
		
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			registro=params[0]; //obtenemos el string de quirofano_name 
			
			if (enviarProcedimientos(registro) == true){
	    		return "ok"; 
			}
			else return "error";
    		
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
        	//progress.dismiss();//ocultamos progess dialog.
        	if (resultado == "ok"){
            Log.e("EnviarProcedimientos=","Todo bien="+resultado);
            mostrarLeyenda();
        	}
        	else{ 
        		Log.e("EnviarProcedimientos=","Todo mal="+resultado);
	        	System.out.println("RESULTADO DEL ENVIO DE PROCEDIMIENTOS:"+resultado);
	        	error2();
        	}
        }//Fin de onPostExecute        
	}//Fin de la subclase EnviarProcedimientos
	
	public boolean enviarProcedimientos(String registro){
		
		int status = -1;
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		//Primero enviamos el tamaño del arreglo procedimientos*4
		//int num = procedimientos.size()*4;
		//String largo = Integer.toString(num);
		//datosEnviar.add(new BasicNameValuePair("info", largo));
		
		//Luego enviamos el id del registro con que programamos la cirugia principal
		System.out.println("REGISTROOOOOOOOOO_ID = "+registro);
		datosEnviar.add(new BasicNameValuePair("numRegistro", registro));
		
		//Luego enviamos el tamaño del arreglo procedimientos
		//int num2 = procedimientos.size();
		//String largo2 = Integer.toString(num2);
		//datosEnviar.add(new BasicNameValuePair("info2", largo2));
		
		//procedimientos: ArrayList de ArrayList- lleva cada procedimiento y dentro cada campo de formulario
		/*Orden: servicio, region, procedimientos, detalles*/
		for (int i=0;i<procedimientos.size();i++){
			//for (int j=0; j<4; j++){
				//datosEnviar.add(new BasicNameValuePair("info"+i+j,procedimientos.get(i).get(j)));	
			datosEnviar.add(new BasicNameValuePair("servicio"+i,procedimientos.get(i).get(0)));
			datosEnviar.add(new BasicNameValuePair("region"+i,procedimientos.get(i).get(1)));
			datosEnviar.add(new BasicNameValuePair("procedimiento"+i,procedimientos.get(i).get(2)));
			datosEnviar.add(new BasicNameValuePair("detalles"+i,procedimientos.get(i).get(3)));
		}
		System.out.println("PROCEDIMIENTOS A ENVIAR = "+datosEnviar);
		//String largo = Integer.toString(datosEnviar.size());
		
		JSONArray jdata = envio.getserverdata(datosEnviar, URL_connect8); 
		
		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
//				status=json_data.getInt("logstatus");//accedemos al valor
				status=json_data.getInt("respuesta");//acceder al valor 1 o 0
				Log.e("enviarProcedimientos","status= "+status);//muestro por log que obtuvimos
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		            
             
			//validamos el valor obtenido
    		if (status==0){// [{"logstatus":"0"}] 
    			Log.e("programacion-procedimientos ", "procedimientos fallidos");
    			procedimientos.clear();
    			return false;
    		}
    		else{// [{"logstatus":"1"}]
    			Log.e("programacion-procedimientos ", "procedimientos exitosos");
    			procedimientos.clear();
    			return true;
    		}
    		 
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON  ", "ERROR");
	    	return false;
	    }//Fin de else
		
	}//Fin de enviarProcedimientos
	
	public void error3(){
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Error: la fecha programada debe ser mayor a la fecha actual", Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void error4(){
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Error: la fecha programada no debe ser mayor a 30 días", Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void error5(String result){
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Error: la fecha y hora se empalman con otra cirugía: "+result.substring(1, (result.length())), Toast.LENGTH_SHORT);
		t.show();
	}

    
}//Fin de la clase Accion