package com.hu.quirofano;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.hu.libreria.HttpPostAux;
//<<<<<<< HEAD
//prueba1
//	=======

//>>>>>>> f430b8bdebb5172a872119a5ea0a4c8815fe53e3
public class Item1 extends SherlockFragment{
	
	EditText fecha;
	EditText horaPropuesta; //Asi estan declarados en el xml
	EditText registro;
	EditText nombreDelPaciente;
	Button botonGuardar;
	
	HttpPostAux post;
    String IP_Server="172.16.0.101";//IP DE NUESTRO PC
    String URL_connect="http://"+IP_Server+"/androidlogin/schedule.php";//ruta en donde estan nuestros archivos
  
    boolean result_back;
    private ProgressDialog pDialog;
    
    /*9 de octubre - Fecha - Hora propuesta*/
   
	//Spinner sp;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		post = new HttpPostAux();
		
		//*************************************************************
		
		View v = inflater.inflate(R.layout.qcentral, container, false);
		
		final ScrollView sv = (ScrollView)v.findViewById(R.id.vista);
		
		final View home = inflater.inflate(R.layout.home, container, false); //Layout incorrecto, no sera home
		// ... a diferencia del MainView estas seran cirugias programas del dia, incluyendo diferidas.
		final View programar = inflater.inflate(R.layout.programar_cirugia, container, false);
		new ProgramarCirugiaView(programar, getActivity());
		//spinner 1
		final Spinner sp = (Spinner) programar.findViewById(R.id.salaOpciones);
		final Spinner sp1 = (Spinner) programar.findViewById(R.id.duracion);

		fecha = (EditText) programar.findViewById(R.id.fecha);
		horaPropuesta = (EditText) programar.findViewById(R.id.horaPropuesta);
		registro = (EditText) programar.findViewById(R.id.registro);
		nombreDelPaciente = (EditText) programar.findViewById(R.id.nombreDelPaciente);
		botonGuardar = (Button) programar.findViewById(R.id.botonGuardar);
		//***************************************************************
		
		TableLayout tl = (TableLayout)home.findViewById(R.id.table);
		TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow, container, false);
		tl.addView(tr);

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
		
		sv.addView(home);
		Button agenda = (Button)v.findViewById(R.id.agenda);
		Button pc = (Button)v.findViewById(R.id.pc);
		Button cd = (Button)v.findViewById(R.id.cd);
		Button alas = (Button)v.findViewById(R.id.salas);
		
		agenda.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				sv.removeAllViews();
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
				
				// INICIA SPINNER 1
				
				//Para el spinner 1 - numero de sala
        				
				String [] array = getResources().getStringArray(R.array.salas);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.test_list_item, array);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sp.setAdapter(adapter);
//		        
		        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		           
		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
		             int position, long id) {
		             //Toast.makeText(parentView.getContext(), "Has seleccionado " +
		             //parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
		            
		            }
		                                 
		            public void onNothingSelected(AdapterView<?> parentView) {
		            
		            }
		        });
				
				 
				 
				// TERMINA SPINNER 1
				
		        
		        // INICIA SPINNER 2
				
				//Para el spinner 2 - Duraciones
       				
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
		        		
		        		if (validarFormulario(date, hora, reg, paciente) == true){
				        	enviarFormulario(date, hora, reg, paciente);
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
	public boolean validarFormulario(String d, String h, String r, String p){
		if 	(d.equals("") || h.equals("") || r.equals("") || p.equals("")){
			Log.e("date = ", "d="+d);
			Log.e("hora = ", "h="+h);
			Log.e("Login ui", "checklogindata user or pass error");
        	
        	return false;
        
        }else{
        	return true;
        }
	}//fin de validarFormulario
	
	//Enviar
	public boolean enviarFormulario(String date, String hora, String reg, String paciente){
		int status = -1;
		
		/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
		
		ArrayList<NameValuePair> datosEnviar= new ArrayList<NameValuePair>();
		
		datosEnviar.add(new BasicNameValuePair("date",date));
		datosEnviar.add(new BasicNameValuePair("hora",hora));
		datosEnviar.add(new BasicNameValuePair("registro",reg));
		datosEnviar.add(new BasicNameValuePair("paciente",paciente));

		//realizamos una peticion y como respuesta obtienes un array JSON
  		JSONArray jdata=post.getserverdata(datosEnviar, URL_connect);
  		
  		//SystemClock.sleep(950);
  		  		
  		//si lo que obtuvimos no es null
    	if (jdata!=null && jdata.length() > 0){
    		JSONObject json_data; //creamos un objeto JSON
			try {
				json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
				status=json_data.getInt("status");//accedemos al valor 
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
	
	public void error1(){
    	Vibrator vibrator =(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"Error:Favor de llenar todos los datos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
	
}//Fin de la clase Item1