package com.hu.quirofano;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends Activity {

	Connection conexionMySQL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main3);
		// Boton de entrar
		Button boton_Entrar = (Button) findViewById(R.id.enterButton);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	//Agregar botones para las distintas activities(esqueletos)
	//---------------------------------------------------------
	//Home-Menu(Quirofano central, Cirugia ambulatoria, Traumatologia, Agenda del dia, Contacto)-
	//-Quirofano central-Cirugia ambulatoria-Traumatologia-Agenda global del dia-Contacto-Programar cirugia-
	//-Inspeccionar salas-Cirugias diferidas...

}//Fin de la clase
