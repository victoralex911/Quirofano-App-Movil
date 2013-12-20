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
import com.hu.quirofano.Item02.GetQuirofanoName;
import com.hu.quirofano.Item1.Agenda;
import com.hu.quirofano.Item1.EnviarProcedimientos;
import com.hu.quirofano.Item1.Formulario;
import com.hu.quirofano.Item1.GetProcedimientosProgramada;
import com.hu.quirofano.Item1.GetSalas;

public class Accion extends SherlockFragment{

	HttpPostAux post;
    //String IP_Server="172.16.0.125";//IP DE NUESTRO PC
    String IP_Server = MainActivity.IP_Server;
    String URL_connect="http://"+IP_Server+"/androidlogin/cancelarCirugia.php";
    String URL_connect1="http://"+IP_Server+"/androidlogin/iniciarCirugia.php";
    String URL_connect2="http://"+IP_Server+"/androidlogin/getTurno.php";
    String URL_connect3="http://"+IP_Server+"/androidlogin/diferirCirugia.php";
    String URL_connect4="http://"+IP_Server+"/androidlogin/getCausaDiferido.php";
    
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
    	View v = inflater.inflate(R.layout.acciones, container, false);
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
		
		Button cancelar = (Button)v.findViewById(R.id.cancelar);
		Button iniciarButton = (Button)v.findViewById(R.id.iniciar);
		Button diferirButton = (Button)v.findViewById(R.id.diferir);
		guardar = (Button)iniciar.findViewById(R.id.botonGuardar);
		
		tiempoFuera = (RadioGroup) iniciar.findViewById(R.id.tiempoFuera);
		
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
				        String ingreso = Integer.toString(horas)+":"+Integer.toString(minutos);
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
			  horas = hour;
			  minutos = minutes;
		   Toast.makeText(getActivity().getApplicationContext(), String.valueOf(hour) + ":" + String.valueOf(minutes), Toast.LENGTH_SHORT).show();
		   String all = Integer.toString(horas)+":"+ Integer.toString(minutos);
		   mPickedTimeText.setText("  "+all);
		  }
		 };
	 
	//PARA TIMEPICKER ***********************************************************************************
    
}//Fin de la clase Accion