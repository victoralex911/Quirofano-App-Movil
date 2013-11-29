package com.hu.quirofano;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.hu.libreria.HttpPostAux;

public class MainActivity extends Activity {

	EditText user; //asi esta expresado en el xml
    EditText pass; //asi esta expresado en el xml
    Button boton_Entrar;
    HttpPostAux post;
    String IP_Server="172.16.0.125";//IP DE NUESTRO PC
    String URL_connect="http://"+IP_Server+"/androidlogin/acces.php";//ruta en donde estan nuestros archivos
  
    boolean result_back;
    private ProgressDialog pDialog;
    
	//Connection conexionMySQL; ya no se usa :v
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main3);
		post=new HttpPostAux();
		
		user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        boton_Entrar= (Button) findViewById(R.id.enterButton);
		//sqlThread.start();
		// Boton de entrar

      //Login button action
        boton_Entrar.setOnClickListener(new View.OnClickListener(){
       
        	public void onClick(View view){
        		 
        		//Extreamos datos de los EditText
        		String usuario=user.getText().toString();
        		String passw=pass.getText().toString();
        		
        		//verificamos si estan en blanco
        		if( checklogindata( usuario , passw )==true){

        			//si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros
        			new asynclogin().execute(usuario,passw);        		               
        			      		
        		}else{
        			//si detecto un error en la primera validacion vibrar y mostrar un Toast con un mensaje de error.
        			err_login();
        		}
        		
        	}
        });
	}
        
      //vibra y muestra un Toast
        public void err_login(){
        	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    	    vibrator.vibrate(200);
    	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
     	    toast1.show();    	
        }
        
        
        /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
        public boolean loginstatus(String username ,String password ) {
        	int logstatus=-1;
        	
        	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
        	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
         		
    		    		postparameters2send.add(new BasicNameValuePair("usuario",username));
    		    		postparameters2send.add(new BasicNameValuePair("password",password));

    		   //realizamos una peticion y como respuesta obtienes un array JSON
          		JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);

          
          		 // observar el progressdialog
          		 
    		    SystemClock.sleep(950);
    		    		
    		    //si lo que obtuvimos no es null
    		    	if (jdata!=null && jdata.length() > 0){

    		    		JSONObject json_data; //creamos un objeto JSON
    					try {
    						json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
    						 logstatus=json_data.getInt("logstatus");//accedemos al valor 
    						 Log.e("loginstatus","logstatus= "+logstatus);//muestro por log que obtuvimos
    					} catch (JSONException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}		            
    		             
    					//validamos el valor obtenido
    		    		 if (logstatus==0){// [{"logstatus":"0"}] 
    		    			 Log.e("loginstatus ", "invalido");
    		    			 return false;
    		    		 }
    		    		 else{// [{"logstatus":"1"}]
    		    			 Log.e("loginstatus ", "valido");
    		    			 return true;
    		    		 }
    		    		 
    			  }else{	//json obtenido invalido verificar parte WEB.
    		    			 Log.e("JSON  ", "ERROR");
    			    		return false;
    			  }
        	
        }
		
		
      //validamos si no hay ningun campo en blanco
        public boolean checklogindata(String username ,String password ){
        	
        if 	(username.equals("") || password.equals("")){
        	Log.e("Login ui", "checklogindata user or pass error");
        return false;
        
        }else{
        	
        	return true;
        }
        
    }           
        
    /*		CLASE ASYNCTASK
     * 
     * usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
     * podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir    
     * si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
     * ademas observariamos el mensaje de que la app no responde.     
     */
        
        class asynclogin extends AsyncTask< String, String, String > {
        	 
        	String user,pass;
            protected void onPreExecute() {
            	//para el progress dialog
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Autenticando....");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }
     
    		protected String doInBackground(String... params) {
    			//obtnemos usr y pass
    			user=params[0];
    			pass=params[1];
                
    			//enviamos y recibimos y analizamos los datos en segundo plano.
        		if (loginstatus(user,pass)==true){    		    		
        			return "ok"; //login valido
        		}else{    		
        			return "err"; //login invalido     	          	  
        		}
            	
    		}
           
    		/*Una vez terminado doInBackground segun lo que haya ocurrido 
    		pasamos a la sig. activity
    		o mostramos error*/
            protected void onPostExecute(String result) {

               pDialog.dismiss();//ocultamos progess dialog.
               Log.e("onPostExecute=",""+result);
              
               if (result.equals("ok")){

    				Intent i=new Intent(MainActivity.this, MainActivity2.class);
    				i.putExtra("user",user);
    				startActivity(i); 
    				
                }else{
                	err_login();
                }
                   									}
    		
            }
     
        //}
            
        //-----------------------------------------------------------------------
     
		
		
		
		
		
		
		
		
		
		
		
		
		///***********************************************
		
		
	/*	
		boton_Entrar.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mostrar("Has presionado el boton Entrar");
				try{
					Class<?> clazz = Class.forName("com.hu.quirofano.OptionsActivity");
					Intent intent = new Intent(getApplicationContext(), clazz);
					startActivity(intent);
					
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		});
	}//Fin de onCreate
	*/
	
	//Nueva conexion***************************************************************+
	
	
	
	/******************************* conexion ***********************/
	/*
	Thread sqlThread = new Thread(){
		public void run(){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
				"jdbc:mysql://192.168.1.73:3306/android_login", "root", "PonTuPasswd");
				String stsql = "Select version()";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(stsql);
				rs.next();
				System.out.println( rs.getString(1) );
				System.out.println("Conexion exitosa");
				conn.close();
			}//Fin de try
			catch (SQLException se) {
				System.out.println("No se puede conectar. Error: " + se.toString());
			}//Fin de catch
			catch (ClassNotFoundException e) {
			//	 System.out.println("No se encuentra la clase. Error: " + e.getMessage());
			}//Fin de catch2
		}//Fin de run
	};
	//Error 01, no hay linkeo entre la aplicacion y la BD
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// no hacemos nada.
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
	
	//Agregar botones para las distintas activities(esqueletos)
	//---------------------------------------------------------
	//Home-Menu(Quirofano central, Cirugia ambulatoria, Traumatologia, Agenda del dia, Contacto)-
	//-Quirofano central-Cirugia ambulatoria-Traumatologia-Agenda global del dia-Contacto-Programar cirugia-
	//-Inspeccionar salas-Cirugias diferidas...

}//Fin de la clase
