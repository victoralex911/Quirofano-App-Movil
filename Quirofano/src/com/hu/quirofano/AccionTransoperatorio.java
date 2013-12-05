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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import android.widget.DatePicker;

import com.actionbarsherlock.app.SherlockFragment;
import com.hu.libreria.HttpPostAux;
import com.hu.quirofano.Accion.CausaDiferido;
import com.hu.quirofano.Item02.GetQuirofanoName;
import com.hu.quirofano.Item1.Agenda;

public class AccionTransoperatorio extends SherlockFragment{

	//5DIC
	static int spinnerTipo = 0;
	static int spinnerStatus = 0;
	static int spinnerTurno = 7;	//Cualquiera excepto 0 o 1
	
	HttpPostAux post;
	HttpPostAux envio;
    //String IP_Server="172.16.0.125";//IP DE NUESTRO PC
	String IP_Server = MainActivity.IP_Server;
	String URL_connect="http://"+IP_Server+"/androidlogin/agregarPersonal.php";
	String URL_connect1="http://"+IP_Server+"/androidlogin/mostrarPersonal.php";
	String URL_connect2="http://"+IP_Server+"/androidlogin/finalizarCirugia.php";
    
    View ll;
    
    TableLayout tl;
    
    TextView agregarTema;
    String myString[] = new String[7];
    
    ArrayList<ArrayList<String>> personal = new ArrayList<ArrayList<String>>();
    //ArrayList<String> nombres = new ArrayList<String>();
    ArrayList<String> spinners = new ArrayList<String>();
    
    //AGREGAR PERSONAL
    EditText nombre_personal;
    Button agregar;
    static int tipo;
    //AGREGAR PERSONAL
    
    boolean result_back;
    private ProgressDialog progress;
	
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
    	
    	post = new HttpPostAux();
    	envio = new HttpPostAux();
    	View v = inflater.inflate(R.layout.acciones_transoperatorio, container, false);
    	final View finalizar = inflater.inflate(R.layout.postoperatorio, container, false);
    	final View agregarPersonal = inflater.inflate(R.layout.cambio_personal, container, false);
    	
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
		
		tl = (TableLayout)agregarPersonal.findViewById(R.id.table);
		TableRow tr = (TableRow) inflater.inflate(R.layout.row_agregarpersonal, container, false);
		tl.addView(tr);
		
//		new MostrarPersonal(inflater, container).execute(myString[5]);
		
		Button finalizarCirugia = (Button)v.findViewById(R.id.finalizar);
		Button cambioPersonal = (Button)v.findViewById(R.id.agregarPersonal);
		
		nombre_personal = (EditText) agregarPersonal.findViewById(R.id.nombrePersonal);
		agregar = (Button) agregarPersonal.findViewById(R.id.botonGuardar);
		
		finalizarCirugia.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				sv.addView(finalizar);
				
			}
		});
		
		cambioPersonal.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				sv.removeAllViews();
				tl.removeAllViews();
				new MostrarPersonal(inflater, container).execute(myString[5]);
				sv.addView(agregarPersonal);
				
				spinners.clear();
				
				//Para el spinner 1 - tipo de personal
				Spinner sp = (Spinner) agregarPersonal.findViewById(R.id.tipo);
				String [] array = getResources().getStringArray(R.array.tipo_personal);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sp.setAdapter(adapter);
    
		        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		           
		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		             final int position, long id) {
//		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		            	spinnerTipo = position;
		            	spinnerTurno = 7;
		            	switch(position){
		            		case 0:
		            			//System.out.println("Seleccionado cirujano");
		            			//SPINNER DINAMICO PARA DESPLEGAR AYUDANTE Y SUPERVISOR
		            			LinearLayout ll = (LinearLayout) agregarPersonal.findViewById(R.id.spinnerLayout);
		            			Spinner spin = new Spinner(getActivity());
		            			
		            			String [] array = getResources().getStringArray(R.array.status1);
		        				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array);
		        		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin.setAdapter(adapter);
		        		        ll.removeAllViews();
		        		        ll.addView(spin);
		        		        
		        		        //************************************
		        		        
		        		        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             int posicion1, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	System.out.println("tipo:"+position+ " - status:"+posicion1);
		        		            	spinnerStatus=posicion1;
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		        });
		        		        
		        		        //************************************
		        		        
		            			break;
		            			
		            		case 1:
		            			System.out.println("Seleccionado anestesista");
		            			//SPINNER DINAMICO PARA DESPLEGAR AYUDANTE Y SUPERVISOR
		            			LinearLayout ll2 = (LinearLayout) agregarPersonal.findViewById(R.id.spinnerLayout);
		            			Spinner spin2 = new Spinner(getActivity());
		            			
		            			String [] array2 = getResources().getStringArray(R.array.status1);
		        				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array2);
		        		        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin2.setAdapter(adapter2);
		        		        ll2.removeAllViews();
		        		        ll2.addView(spin2);	
		        		        
		        		        spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             int posicion2, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	System.out.println("tipo:"+position+ " - status:"+posicion2);
		        		            	spinnerStatus = posicion2;
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		        });
		        		        
		            			break;
		            			
		            		case 2:
		            			//System.out.println("Seleccionado enfermeria");
		            			//SPINNER DINAMICO PARA DESPLEGAR INSTRUMENTISTA Y CIRCULANTE
		            			final LinearLayout ll3 = (LinearLayout) agregarPersonal.findViewById(R.id.spinnerLayout);
		            			final Spinner spin3 = new Spinner(getActivity());
		            			
		            			String [] array3 = getResources().getStringArray(R.array.status2);
		        				ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array3);
		        		        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin3.setAdapter(adapter3);
		        		        
		        		        spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             final int posicion3, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	spinnerStatus = posicion3;
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		            
		        		        });        
		        		        
		        		        Spinner spin31 = new Spinner(getActivity());
		            			
		            			String [] array31 = getResources().getStringArray(R.array.turno);
		        				ArrayAdapter<String> adapter31 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array31);
		        		        adapter31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin31.setAdapter(adapter31);
		        		        
		        		        spin31.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             int posicion4, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	spinnerTurno = posicion4;
		        		            	
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		        });
		        		        
		        		        ll3.removeAllViews();
		        		        ll3.addView(spin3);
		        		        ll3.addView(spin31);	

		            			break;
		            			
		            		case 3:
		            			System.out.println("Seleccionado otro");
		            			//SPINNER DINAMICO PARA DESPLEGAR AYUDANTE, SUPERVISOR, INSTRUMENTISTA Y CIRCULANTE
		            			LinearLayout ll4 = (LinearLayout) agregarPersonal.findViewById(R.id.spinnerLayout);
		            			Spinner spin4 = new Spinner(getActivity());
		            			
		            			String [] array4 = getResources().getStringArray(R.array.status3);
		        				ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array4);
		        		        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin4.setAdapter(adapter4);
		        		        
		        		        spin4.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             int posicion5, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	spinnerStatus = posicion5;
		        		            	
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		        });
		        		        
		        		        Spinner spin41 = new Spinner(getActivity());
		            			
		            			String [] array41 = getResources().getStringArray(R.array.turno);
		        				ArrayAdapter<String> adapter41 = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array41);
		        		        adapter41.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        		        spin41.setAdapter(adapter41);
		        		        
		        		        spin41.setOnItemSelectedListener(new OnItemSelectedListener() {
		        			           
		        		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		        		             int posicion6, long id) {
//		        		             Toast.makeText(parentView.getContext(), "Has seleccionado " +
//		        		             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		        		            	spinnerTurno = posicion6;
		        		            	
		        		            }
		        		                                 
		        		            public void onNothingSelected(AdapterView<?> parentView) {
		        		            
		        		            }
		        		        });
		        		        
		        		        ll4.removeAllViews();
		        		        ll4.addView(spin4);
		        		        ll4.addView(spin41);
		        		        
		            			break;
		            			
		            		default:
		            			System.out.println("nada");
		            	}
		            }
		                                 
		            public void onNothingSelected(AdapterView<?> parentView) {
		            
		            }
		        });
				
				
				
				agregar.setOnClickListener(new OnClickListener(){
					public void onClick(View v){
						String nombre = nombre_personal.getText().toString();
						
						//Evaluar turno (EVALUACION CAMBIADA AL SCRIPT PHP)
//						if(spinnerTurno == 7){
//							String sTipo = Integer.toString(spinnerTipo);
//							String sStatus = Integer.toString(spinnerStatus);
//							spinners.add(sTipo);
//							spinners.add(sStatus);
//						}
//						else{
//							String sTipo = Integer.toString(spinnerTipo);
//							String sStatus = Integer.toString(spinnerStatus); 
//							String sTurno = Integer.toString(spinnerTurno);
//							spinners.add(sTipo);
//							spinners.add(sStatus);
//							spinners.add(sTurno);
//						}
						
						String sTipo = Integer.toString(spinnerTipo);
						String sStatus = Integer.toString(spinnerStatus); 
						String sTurno = Integer.toString(spinnerTurno);
						spinners.add(sTipo);
						spinners.add(sStatus);
						spinners.add(sTurno);
						
						//System.out.println("AL spinners = "+spinners);
						
						System.out.println("tipo = "+sTipo+" - status = "+sStatus+" - Turno = "+sTurno);
						
						if (validarFormulario(nombre) == true){
		        			new AgregarPersonal(inflater, container).execute(nombre, sTipo, sStatus, sTurno);
		        			sv.removeAllViews();
		    				tl.removeAllViews();
		    				sv.addView(agregarPersonal);
		        		}//Fin de if
		        		else{
		        			error1();
		        		}
						
						spinners.clear();
					}//Fin de onclick
				});//fin de iniciar boton setOnClick
				
			}
		});
		
		return v;
    	
    }//Fin de onCreateView
    
    public boolean validarFormulario(String nombrePersonal){
		if 	(nombrePersonal.equals("")){
			Log.e("formulario-agregar personal", "formulario incompleto");
        	return false;
        
        }else{
        	return true;
        }
	}//fin de validarDiferido
    
    public void error1(){
    	Vibrator vibrator =(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Error:Favor de llenar todos los campos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
    public void cancelar() {
        finish();
    }
    
    private void finish() {
		// TODO Auto-generated method stub
    	Toast t=Toast.makeText(getActivity(),"Operación cancelada.", Toast.LENGTH_SHORT);
        t.show();
	}
    
    public void mostrarPersonal(String registro_id) throws JSONException{	
		personal.clear();

		String val = "";
		String value = "";
		String cont = "";
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("registro_id", registro_id));
		
		//JSONObject json_objeto;
		JSONArray jdata = envio.getserverdata(datosEnviar, URL_connect1);
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
					val = json_data.getString("dat");		//nombre
					value = json_data.getString("dato");	//tipo
					
					ArrayList<String> temporary = new ArrayList<String>();
					
					temporary.add(val);
					temporary.add(value);
				
					Log.e("log-st", "array temprary = "+temporary);
				
					personal.add(temporary);
					//st.clear();
				}
				Log.e("array personal", "personal = "+personal);		
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
	}//Fin de mostrarPersonal
    
    public boolean agregarPersonal(String nombreDelPersonal, String tipoDelPersonal, String statusDelPersonal,
    		String turnoDelPersonal){
    	int status = -1;
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		datosEnviar.add(new BasicNameValuePair("nombre_personal",nombreDelPersonal));
		datosEnviar.add(new BasicNameValuePair("tipo_personal",tipoDelPersonal));
		datosEnviar.add(new BasicNameValuePair("status_personal",statusDelPersonal));
		datosEnviar.add(new BasicNameValuePair("turno_personal",turnoDelPersonal));
		datosEnviar.add(new BasicNameValuePair("registro_ID",myString[5])); //Mandar id del registro
				
		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect);
  		  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento para conocer el status de la operacion
				status=json_data.getInt("logstatus");//accedemos al valor
				System.out.println("status agregarPersonal="+status);
				//qId = json_data.getString("quirofano_id");
				Log.e("getAgregarPersonal","status= "+status);//muestro por log que obtuvimos
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
    }//Fin de agregarPersonal

    class AgregarPersonal extends AsyncTask< String, String, String> {
    	
    	LayoutInflater inflater;
		ViewGroup container;
		
		AgregarPersonal(LayoutInflater inflater, ViewGroup container){
			this.inflater = inflater;
			this.container = container;
		}
		
    	String st1; //String con el nombre del personal a agregar
    	String st2; //String con el tipo del personal
    	String st3; //String con el status del personal
    	String st4; //String con el turno del personal (si lo hay)
		
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string con el nombre del personal a agregar 
			st2=params[1]; //obtenemos el string con el tipo del personal a agregar
			st3=params[2]; //obtenemos el string con el status del personal a agregar
			st4=params[3]; //obtenemos el string con el turno del personal a agregar(si lo hay)
			
			if (agregarPersonal(st1, st2, st3, st4) == true){
				return "ok";
			}
			else return "error";
    		
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
        	//progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=","status="+resultado);
            new MostrarPersonal(inflater, container).execute(myString[5]);
        }//Fin de onPostExecute        
	}//Fin de la subclase CancelarCirugia
    
    class MostrarPersonal extends AsyncTask< String, String, String> {
    	
    	LayoutInflater inflater;
		ViewGroup container;
		
		MostrarPersonal(LayoutInflater inflater, ViewGroup container){
			this.inflater = inflater;
			this.container = container;
		}
		
    	String st1; //El string que llevara el numero de registro de la cirugia
		
    	protected void onPreExecute() {
    		//progress = ProgressDialog.show(
    		//getActivity(), null, "Accesando a agenda...");
            super.onPreExecute();
        }
    	
        protected String doInBackground(String... params) {
			st1=params[0]; //obtenemos el string con el numero de registro de la cirugia 
					
    		try {
				mostrarPersonal(st1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return "ok"; //login valido
    		
		}//Fin de doInBackground
       
        protected void onPostExecute(String resultado) {
        	//progress.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=","Todo bien="+resultado);
            
            View tab1 = inflater.inflate(R.layout.row_agregarpersonal, container, false);
			TableRow tabRow1 = (TableRow) tab1;
			tl.addView(tabRow1);

        	for (int i = 0; i<personal.size(); i++){
        		int intTipo;
        		String stringTipo;
    			
    			View tabler = inflater.inflate(R.layout.row_agregarpersonal_dinamico, container, false);
    			TextView nombre = (TextView)tabler.findViewById(R.id.nombre_Personal);
				TextView tipo = (TextView)tabler.findViewById(R.id.tipo_Personal);
				
				TableRow trow = (TableRow) tabler;
				
				nombre.setText(personal.get(i).get(0));	//nombre del personal
				intTipo = Integer.parseInt(personal.get(i).get(1));	//convertir el tipo de string a entero
				switch(intTipo){
					case 0:
						stringTipo = "cirujano";
						break;
					case 1:
						stringTipo = "anestesista";
						break;
					case 2:
						stringTipo = "enfermeria";
						break;
					default:
						stringTipo = "otro";
				}
				
				//tipo.setText(personal.get(i).get(1));	//tipo del personal
				tipo.setText(stringTipo);
					
				tl.addView(trow);
    		}//Fin de for
        	
        	View tab = inflater.inflate(R.layout.row_agregarpersonal_dinamico, container, false);
        	TextView titulo = (TextView)tab.findViewById(R.id.nombre_Personal);
        	TextView tipo = (TextView)tab.findViewById(R.id.tipo_Personal);
        	titulo.setText("");
        	tipo.setText("");
			TableRow tabRow = (TableRow) tab;
			tl.addView(tabRow);
        	
        }//Fin de onPostExecute        
		
	}//Fin de la subclase MostrarPersonal
    
}//Fin de la clase AccionTransoperatorio