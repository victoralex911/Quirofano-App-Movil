package com.hu.quirofano;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.DatePicker;

import com.actionbarsherlock.app.SherlockFragment;
import com.hu.libreria.HttpPostAux;
//<<<<<<< HEAD
//prueba1
//	=======
//>>>>>>> f430b8bdebb5172a872119a5ea0a4c8815fe53e3
public class Item1 extends SherlockFragment{
	
	//22oct
	//ArrayList<String> al = new ArrayList<String>(); 1st version
	
	//3noviembre
	ProgramarCirugiaView object = new ProgramarCirugiaView();
	
	//Se desplegaran en la agenda los datos de hora, sala, paciente y diagnostico.
	
	ArrayList<String> st = new ArrayList<String>();		//Para almacenar las horas ... todavia no implementado
	ArrayList<String> st1 = new ArrayList<String>();	//Para almacenar las salas
	ArrayList<String> st2 = new ArrayList<String>();	//Para almacenar los pacientes
	ArrayList<String> st3 = new ArrayList<String>();	//Para almacenar los diagnosticos
	
	//22oct
	
	//4 noviembre *** Array de array - prueba ***
	ArrayList<ArrayList<String>> padre = new ArrayList<ArrayList<String>>();
	
	//26oct
	private TextView mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;
	//26oct
	
	//Spinners
	/*
	int sala;
	int duracion;
	int tipoDeProgramacion;
	int servicio;
	int enAtencionA;
	*/
	//------------------------------
	//int region;		//Vista dinamica
	//int servicio2;	//Vista dinamica
	
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
	
	HttpPostAux post;
	HttpPostAux envio;
    String IP_Server="192.168.1.73";//IP DE NUESTRO PC
    String URL_connect="http://"+IP_Server+"/androidlogin/schedule.php";//ruta en donde estan nuestros archivos
    String URL_connect2 = "http://"+IP_Server+"/androidlogin/mostrarAgenda.php"; //ruta alternativa
  
    boolean result_back;
    private ProgressDialog progress;
    
    /*9 de octubre - Fecha - Hora propuesta*/
    
	//Spinner sp;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		post = new HttpPostAux();
		envio = new HttpPostAux();
		
		//*************************************************************
		
		View v = inflater.inflate(R.layout.qcentral, container, false);
		
		final ScrollView sv = (ScrollView)v.findViewById(R.id.vista);
		
		final View home = inflater.inflate(R.layout.home, container, false); //Layout incorrecto, no sera home
		// ... a diferencia del MainView estas seran cirugias programas del dia, incluyendo diferidas.
		final View programar = inflater.inflate(R.layout.programar_cirugia, container, false);
		//new ProgramarCirugiaView(inflater, programar, getActivity());
		 
		ProgramarCirugiaView pcv = new ProgramarCirugiaView();
		pcv.prog(inflater, programar, getActivity());
		
		//spinner 1
		//final Spinner sp = (Spinner) programar.findViewById(R.id.salaOpciones);
		//final Spinner sp1 = (Spinner) programar.findViewById(R.id.duracion);

		mDateDisplay = (TextView) programar.findViewById(R.id.stringFecha);
		
		protocolo = (RadioGroup) programar.findViewById(R.id.groupProtocolo);
		reintervencion = (RadioGroup) programar.findViewById(R.id.groupReintervencion);
		
		//EditText
		fecha = (EditText) programar.findViewById(R.id.fecha);
		horaPropuesta = (EditText) programar.findViewById(R.id.horaPropuesta);
		registro = (EditText) programar.findViewById(R.id.registro);
		nombreDelPaciente = (EditText) programar.findViewById(R.id.nombreDelPaciente);
		edad =(EditText) programar.findViewById(R.id.edad);
		genero =(EditText) programar.findViewById(R.id.genero);
		procedencia =(EditText) programar.findViewById(R.id.procedencia);
		diagnostico =(EditText) programar.findViewById(R.id.diagnostico);
		medico =(EditText) programar.findViewById(R.id.medico);
		edad =(EditText) programar.findViewById(R.id.edad);
		riesgoQuirurgico =(EditText) programar.findViewById(R.id.riesgoQuirurgico);
		insumosIndispensables =(EditText) programar.findViewById(R.id.insumosIndispensables);
		requerimientos =(EditText) programar.findViewById(R.id.requerimientos);
		hemoderivados =(EditText) programar.findViewById(R.id.hemoderivados);
		requisitos =(EditText) programar.findViewById(R.id.requisitosDeLaboratorio);
		necesidades =(EditText) programar.findViewById(R.id.otrasNecesidades);
		
		//EditText
		botonGuardar = (Button) programar.findViewById(R.id.botonGuardar);
		//***************************************************************
		
		TableLayout tl = (TableLayout)home.findViewById(R.id.table);
		TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow, container, false);
		tl.addView(tr);
		
		//-- FABRICADO POR VICTOR
		/*
		for(int index = 0; index < 10; index++){
			View tabler = inflater.inflate(R.layout.tablerow_editable, container, false);

			TextView hora = (TextView)tabler.findViewById(R.id.hora);
			TextView sala = (TextView)tabler.findViewById(R.id.sala);
			TextView pa = (TextView)tabler.findViewById(R.id.pa);
			TextView dg = (TextView)tabler.findViewById(R.id.dg);

			TableRow trow = (TableRow) tabler;

			hora.setText("Hora "+ index);	
			sala.setText("Sala "+index);	
			pa.setText("Paciente "+ index);	
			dg.setText("Diagnostico "+ index);

			tl.addView(trow);
		}
		*/
		
				
		// 21 oct-----------------------------------------------------------------------
		
		/*Lo siguiente ira dentro de la sub-clase Agenda*/
		String val = "ok";
		Log.e("antes de agenda", "antes de agenda");
		new Agenda().execute(val);
		
		/*
		1. Lo primero que traiga todos los registros
		*/
		
		
		SystemClock.sleep(40);
		Log.e("paso1", "paso1");
		
		//FABRICADO POR TRIANA *****************************************************
		
		//System.out.println("largo de padre = "+padre.size());
		//System.out.println("PADRE = "+padre);
			
		//for(int index = 0; index < 10; index++){
		for(int index = 0; index < padre.size(); index++){
		
			View tabler = inflater.inflate(R.layout.tablerow_editable, container, false);
			
			TextView hora = (TextView)tabler.findViewById(R.id.hora);
			TextView sala = (TextView)tabler.findViewById(R.id.sala);
			TextView pa = (TextView)tabler.findViewById(R.id.pa);
			TextView dg = (TextView)tabler.findViewById(R.id.dg);
			
			TableRow trow = (TableRow) tabler;
						
			hora.setText(padre.get(index).get(0));	
			sala.setText(padre.get(index).get(1));
			pa.setText(padre.get(index).get(2));
			dg.setText(padre.get(index).get(3));
				
			tl.addView(trow);
		}
		//FABRICADO POR TRIANA *********************************************************		
		
		
		//ArrayList<String> data = new ArrayList<String>();
		//data = mostrarAgenda(); YA ESTA EN LA SUB-CLASE
		
		//********************************ESCRIBIR EN LA VISTA*****************************+
		/*
		View tabler = inflater.inflate(R.layout.tablerow_editable, container, false);

		TextView hora = (TextView)tabler.findViewById(R.id.hora);
		TextView sala = (TextView)tabler.findViewById(R.id.sala);
		TextView pa = (TextView)tabler.findViewById(R.id.pa);
		TextView dg = (TextView)tabler.findViewById(R.id.dg);

		TableRow trow = (TableRow) tabler;
		
		Log.e("ultimo-array", "ultimo = "+padre);
		//Log.e("unico-elemento", "unico = "+al.get(0));
		
		//SystemClock.sleep(4450);
		
		if( padre.size() != 0){
			hora.setText(padre.get(0).get(0)); //hora
			sala.setText(padre.get(0).get(1)); //sala
			pa.setText(padre.get(0).get(2));   //paciente(nombre)
			dg.setText(padre.get(0).get(3));   //diagnostico
		}
		*/
		//tl.addView(trow);
		// 21 oct ----------------------------------------------------------------------
		
		sv.addView(home);
		Button agenda = (Button)v.findViewById(R.id.agenda);
		Button pc = (Button)v.findViewById(R.id.pc);
		Button cd = (Button)v.findViewById(R.id.cd);
		Button salas = (Button)v.findViewById(R.id.salas);
		
		agenda.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				
								
				//SystemClock.sleep(40); //Tiempo de llegada de datos
				
				TextView t = new TextView(getActivity());
				t.setText("Hola otra vez");
				sv.addView(home);
			}

		});
		

		pc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				TextView t = new TextView(getActivity());
				t.setText("Hola de nuevo");
				sv.addView(programar); //aqui termina victor
				
				/*Para DatePicker*/
				//mDateDisplay.setOnClickListener (new OnClickListener(){
					//public void onClick(View v){
					//showDialog(DATE_DIALOG_ID);
					//}
				//});//Fin de setOnClickListener
				
				//final Calendar c = Calendar.getInstance();        
		    	//mYear = c.get(Calendar.YEAR);        
		    	//mMonth = c.get(Calendar.MONTH);        
		    	//mDay = c.get(Calendar.DAY_OF_MONTH);        
		    	// display the current date (this method is below)        
		    	//updateDisplay();    
				
				// INICIA SPINNER 1
				
				//Para el spinner 1 - numero de sala
        		
				/*
				
				String [] array = getResources().getStringArray(R.array.salas);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sp.setAdapter(adapter);
//		        
		        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		           
		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		             int position, long id) {
		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		            
		            }
		                                 
		            public void onNothingSelected(AdapterView<?> parentView) {
		            
		            }
		        });
				
				*/
				 
				// TERMINA SPINNER 1
				
		        
		        // INICIA SPINNER 2
				
				//Para el spinner 2 - Duraciones
       				
				/*
				
				String [] array1 = getResources().getStringArray(R.array.duraciones);
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array1);
		        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sp1.setAdapter(adapter1);
//		        
		        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
		           
		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		             int position, long id) {
		             //Toast.makeText(parentView.getContext(), "Has seleccionado " +
		             //parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		            
		            }
		                                 
		            public void onNothingSelected(AdapterView<?> parentView) {
		            
		            }
		        });
				
				*/ 
				 
				// TERMINA SPINNER 2
		        
		        //Recoleccion y envio de datos
		        
		        //final String date=fecha.getText().toString();
		        //final String hora=horaPropuesta.getText().toString();
		        //final String reg = registro.getText().toString();
		        //final String paciente = nombreDelPaciente.getText().toString();
		        
		        botonGuardar.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v){
		        		
		        		String date = fecha.getText().toString();
		        		String hora=horaPropuesta.getText().toString();
				        String reg = registro.getText().toString();
				        String paciente = nombreDelPaciente.getText().toString();
				        String sEdad = edad.getText().toString();
				        String sGenero = genero.getText().toString();
				        String sProcedencia = procedencia.getText().toString();
				    	String sDiagnostico = diagnostico.getText().toString();
				        String sMedico = medico.getText().toString();
				        String sRiesgoQuirurgico = medico.getText().toString();
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
				        
				        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"protocolo = "+prot.getText(), Toast.LENGTH_SHORT);
				 	    toast1.show();
				 	    
				 	    Toast toast2 = Toast.makeText(getActivity().getApplicationContext(),"reintervencion = "+reint.getText(), Toast.LENGTH_SHORT);
				 	   	toast2.show();
				        
				        ProgramarCirugiaView pos = new ProgramarCirugiaView();
				        
				        System.out.println("sala = "+object.getSala());
				        System.out.println("duracion = "+object.getDuracion());
				        System.out.println("programacion = "+object.getProgramacion());
				        System.out.println("servicio = "+object.getServicio());
				        System.out.println("atencion = "+object.getAtencion());
				        
				        Toast toa = Toast.makeText(getActivity().getApplicationContext(), 
				        		"sala = "+object.getSala()+" duracion = "+object.getDuracion()+
				        		" programacion = "+object.getProgramacion()+" servicio = "+
				        		object.getServicio()+" atencion = "+object.getAtencion(),
				        		Toast.LENGTH_SHORT);
				        toa.show();
				        
				        int sala = object.getSala();
				        int duracion = object.getDuracion();
				        int programacion = object.getProgramacion();
				        int servicio = object.getServicio();
				        int atencion = object.getAtencion();
				        
				        String sSala = Integer.toString(sala);
				        String sDuracion = Integer.toString(duracion);
				        String sProgramacion = Integer.toString(programacion);
				        String sServicio = Integer.toString(servicio);
				        String sAtencion = Integer.toString(atencion);
				        
				        String sP = Integer.toString(p);
				        String sR = Integer.toString(r);
				        
				        //Toast toast10 = Toast.makeText(getActivity().getApplicationContext(),"posiciones = "+sala+duracion, Toast.LENGTH_SHORT);
				 	    //toast10.show();
				        //ArrayList<Integer> posicion = pos.getPosition();
				        //Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"posiciones = "+pos.getPosition(), Toast.LENGTH_SHORT);
				 	    //toast1.show();
		        		
		        		if (validarFormulario(date, hora, reg, paciente, sEdad, sGenero, sProcedencia,
		        				sDiagnostico, sMedico, sRiesgoQuirurgico, sInsumosIndispensables,
		        				sRequerimientos, sHemoderivados, sRequisitos, sNecesidades) == true){
		        			
		        			
		        			//Primero mandamos los Spinners luego los RadioButton
		        			new Formulario().execute(date, hora, reg, paciente, sEdad, sGenero, sProcedencia,
		        					sDiagnostico, sMedico, sRiesgoQuirurgico, sInsumosIndispensables,
		        					sRequerimientos, sHemoderivados, sRequisitos, sNecesidades, 
		        					sSala, sDuracion, sProgramacion, sServicio, sAtencion, sP, sR);
		        			//sv.removeAllViews();
		        			//sv.addView(home);
				        }
				        else{
				        	error1();
				        }//Fin de else
		        	}//Fin de onClick
		        });//boton de guardar formulario de cirugia
		        
			}//Fin de onClick(Seccion - boton de programar cirugia)

		});
		//Intent intent = new Intent(getActivity(), QCentralActivity.class);
		//startActivity(intent);
		return v;



		//return inflater.inflate(R.layout.qcentral, null);
	}
	
	//Primero validamos los campos
	public boolean validarFormulario(String date, String hora, String reg, String paciente, 
			String sEdad, String sGenero, String sProcedencia, String sDiagnostico, String sMedico,
			String sRiesgoQuirurgico, String sInsumosIndispensables, String sRequerimientos,
			String sHemoderivados, String sRequisitos, String sNecesidades){
		if 	(date.equals("") || hora.equals("") || reg.equals("") || paciente.equals("") ||
				sEdad.equals("") || sGenero.equals("") || sProcedencia.equals("") || 
				sDiagnostico.equals("") || sMedico.equals("") || sRiesgoQuirurgico.equals("") ||
				sInsumosIndispensables.equals("") || sRequerimientos.equals("") || sHemoderivados.equals("") ||
				sRequisitos.equals("") || sNecesidades.equals("") ){
			Log.e("date = ", "date="+date);
			Log.e("hora = ", "hora="+hora);
			Log.e("Login ui", "checklogindata user or pass error");
			//Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h, Toast.LENGTH_SHORT);
	 	    //toast1.show();
        	return false;
        
        }else{
        	//Toast toast2 = Toast.makeText(getActivity().getApplicationContext(),"Validacion d = "+d+" h = "+h, Toast.LENGTH_SHORT);
        	//toast2.show();
        	return true;
        }
	}//fin de validarFormulario
	
	//Enviar
	public boolean enviarFormulario(String date, String hora, String reg, String paciente, String sEdad,
			String sGenero, String sProcedencia, String sDiagnostico, String sMedico,
			String sRiesgoQuirurgico, String sInsumosIndispensables, String sRequerimientos, 
			String sHemoderivados, String sRequisitos, String sNecesidades, 
			String sSala, String sDuracion, String sProgramacion, String sServicio, String sAtencion, 
			String sP, String sR){
	
		int status = -1;
		
		/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		
		datosEnviar.add(new BasicNameValuePair("date",date));
		datosEnviar.add(new BasicNameValuePair("hora",hora));
		datosEnviar.add(new BasicNameValuePair("reg",reg));
		datosEnviar.add(new BasicNameValuePair("paciente",paciente));
		datosEnviar.add(new BasicNameValuePair("sEdad",sEdad));
		datosEnviar.add(new BasicNameValuePair("sGenero",sGenero));
		datosEnviar.add(new BasicNameValuePair("sProcedencia",sProcedencia));
		datosEnviar.add(new BasicNameValuePair("sDiagnostico",sDiagnostico));
		datosEnviar.add(new BasicNameValuePair("sMedico",sMedico));
		datosEnviar.add(new BasicNameValuePair("sRiesgoQuirurgico",sRiesgoQuirurgico));
		datosEnviar.add(new BasicNameValuePair("sInsumosIndispensables",sInsumosIndispensables));
		datosEnviar.add(new BasicNameValuePair("sRequerimientos",sRequerimientos));
		datosEnviar.add(new BasicNameValuePair("sHemoderivados",sHemoderivados));
		datosEnviar.add(new BasicNameValuePair("sRequisitos",sRequisitos));
		datosEnviar.add(new BasicNameValuePair("sNecesidades",sNecesidades));
		//EditText hasta aqui
		
		//Spinners y RadioButton
		datosEnviar.add(new BasicNameValuePair("sSala", sSala));
		datosEnviar.add(new BasicNameValuePair("sDuracion", sDuracion));
		datosEnviar.add(new BasicNameValuePair("sProgramacino", sProgramacion));
		datosEnviar.add(new BasicNameValuePair("sServicio", sServicio));
		datosEnviar.add(new BasicNameValuePair("sAtencion", sAtencion));
		datosEnviar.add(new BasicNameValuePair("sP", sP));
		datosEnviar.add(new BasicNameValuePair("sR", sR));
		
		Log.e("datosEnviar","datosEnviar = "+datosEnviar);
		Log.e("URL_connect","URL_connect = "+URL_connect);
		
		//realizamos una peticion y como respuesta obtienes un array JSON
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect);
  		
  		SystemClock.sleep(1450);
  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
				status=json_data.getInt("logstatus");//accedemos al valor
				Log.e("enviarFormulario","status= "+status);//muestro por log que obtuvimos
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		            
             
			//validamos el valor obtenido
    		if (status==0){// [{"logstatus":"0"}] 
    			Log.e("loginstatus ", "envio fallido");
    			return false;
    		}
    		else{// [{"logstatus":"1"}]
    			Log.e("loginstatus ", "envio exitoso");
    			return true;
    		}
    		 
    	}//Fin de if(comprueba si lo obtenido no es "null")
    	
    	else{	//json obtenido invalido verificar parte WEB.
    		Log.e("JSON  ", "ERROR");
	    	return false;
	    }//Fin de else
		
	}//Fin de enviar formulario
	
	//public ArrayList<String> mostrarAgenda(String s){
	public void mostrarAgenda(String s) throws JSONException{	
		//ArrayList<String> st = new ArrayList<String>();
		
		String value = "";
		String value1 = "";
		String value2 = "";
		String value3 = "";
		String cont = "";
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("stat", s));
		
		//SystemClock.sleep(4450);
		//JSONObject json_objeto;
		JSONArray jdata = envio.getserverdata(datosEnviar, URL_connect2);
		//System.out.println("jdata = "+jdata.getString(0));
		System.out.println(jdata.toString());
		System.out.println("largo de jdata = "+jdata.length());
		if (jdata!=null && jdata.length() > 0){
    		//JSONObject json_data; //creamos un objeto JSON
			try {
				
				for(int n = 0; n < jdata.length(); n++){
					//st.clear();
					System.out.println("vuelta:"+n);
					JSONObject json_data = jdata.getJSONObject(n);
					value = json_data.getString("dato");
					value1 = json_data.getString("dato1");
					value2 = json_data.getString("dato2");
					value3 = json_data.getString("dato3");
					
					ArrayList<String> temporary = new ArrayList<String>();
					
					temporary.add(value);
					temporary.add(value1);
					temporary.add(value2);
					temporary.add(value3);
					//st.add(value);
					//st.add(value1);
					//st.add(value2);
					//st.add(value3);
					
					Log.e("log-st", "array temprary = "+temporary);
					
					padre.add(temporary);
					//st.clear();
				}
				
				/*
				json_data = jdata.getJSONObject(0);
				cont = json_data.getString("contador");
				Log.e("cont", "contador = "+cont);
				
				json_data = jdata.getJSONObject(0);	//leemos el primer segmento en nuestro caso el unico
				value=json_data.getString("dato");	//accedemos al valor
				
				json_data = jdata.getJSONObject(0);
				value1=json_data.getString("dato1");
				
				json_data = jdata.getJSONObject(0);
				value2=json_data.getString("dato2");
				
				json_data = jdata.getJSONObject(0);
				value3=json_data.getString("dato3");
				
				System.out.println("Contador = "+cont);
	    		*/
	    						
				//st.add(value);
				//st.add(value1);
				//st.add(value2);
				//st.add(value3);
				Log.e("array padre", "padre = "+padre);
								
				//return st;
				
				//Log.e("enviarFormulario","status= "+status);//muestro por log que obtuvimos
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
		
		
		
	}//Fin de mostrarAgenda
	
	public void error1(){
    	Vibrator vibrator =(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Error:Favor de llenar todos los datos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
	
	public void error2(){
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(200);
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Error: error en el envio de datos", Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void mostrarLeyenda(){
		Toast t = Toast.makeText(getActivity().getApplicationContext(), "Cirugia programada con éxito", Toast.LENGTH_SHORT);
		t.show();
	}
	
	/*Uso de Asnyctask - [Documentacion]*/
	/*2 subclases apartir de aqui: Formulario - Agenda*/
	
	class Formulario extends AsyncTask< String, String, String > {
   	 
    	//String date, hora, reg, paciente;
    	String date, hora, reg, paciente, sEdad, sGenero, sProcedencia, sDiagnostico, sMedico,
    	sRiesgoQuirurgico, sInsumosIndispensables, sRequerimientos, sHemoderivados, sRequisitos,
    	sNecesidades;
    	
    	//Seran convertidos a enteros, excepto duracion, sera convertido a TIME
    	String sSala, sDuracion, sProgramacion, sServicio, sAtencion, sP, sR;
    	
    	protected void onPreExecute() {
    		progress = ProgressDialog.show(
    		getActivity(), null, "Programando cirugía ...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			//obtnemos datos
			date=params[0];						//Para date
			hora=params[1];						//Para hora
			reg=params[2];  					//Para registro
			paciente=params[3];					//Para nombre del paciente
			sEdad=params[4];					//Para edad del paciente
			sGenero=params[5];					//Para genero del paciente
			sProcedencia=params[6];				//Para procedencia
			sDiagnostico=params[7];				//Para diagnostico
			sMedico=params[8];					//Para nombre del medico
			sRiesgoQuirurgico=params[9];		//Para riesgo quirurgico
			sInsumosIndispensables=params[10];  //Para insumos indispensables
			sRequerimientos=params[11];			//Para requerimientos de anestesiologia
			sHemoderivados=params[12];			//Para hemoderivados necesarios
			sRequisitos=params[13];				//Para requisitos de laboratorio
			sNecesidades=params[14];			//Para otras necesidades
			
			sSala = params[15];
			sDuracion = params[16];
			sProgramacion = params[17];
			sServicio = params[18];
			sAtencion = params[19];
			sP = params[20];
			sR = params[21];
			
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		if (enviarFormulario(date, hora, reg, paciente, sEdad, sGenero, sProcedencia,
    				sDiagnostico, sMedico, sRiesgoQuirurgico, sInsumosIndispensables, sRequerimientos,
    				sHemoderivados, sRequisitos, sNecesidades, sSala, sDuracion, sProgramacion,
    				sServicio, sAtencion, sP , sR)==true){    		    		
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
            	
            	//Intent i = new Intent(MainActivity.this, MainActivity2.class);
 				//i.putExtra("user",user);
 				//startActivity(i); 
            }
            else {
             	error2();
            }
        }//Fin de onPostExecute        
	}//Fin de la subclase Formulario
	
	/*Asyntask*/
	
	class Agenda extends AsyncTask< String, String, String> {
		
		//String date, hora, reg, paciente;
    	String st1; //El string que llevara la cadena "ok"
		
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string con "ok" 
			
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		//al = mostrarAgenda(st1);		
    		try {
				mostrarAgenda(st1);
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
            //return al;
            //mostrarLeyenda();
            
        }//Fin de onPostExecute        
		
	}//Fin de la subclase Agenda
	
	
	/*DatePicker - 26 octubre*/
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
			new DatePickerDialog.OnDateSetListener(){
		
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}//Fin de onDataSet
	};//Fin de DatePickerDialog
	
	private void updateDisplay(){
		mDateDisplay.setText(
				new StringBuilder()
				//agregar 1, empieza en 0
				.append(mMonth +1).append("-")
				.append(mDay).append("-")
				.append(mYear).append(" "));
	}//Fin de updateDisplay
	
	protected Dialog onCreateDialog(int id){
		switch (id){
			case DATE_DIALOG_ID:
				return new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth, mDay);
		}//Fin de switch
		return null;
	}//Fin de onCreateDialog
	
	
}//Fin de la clase Item1