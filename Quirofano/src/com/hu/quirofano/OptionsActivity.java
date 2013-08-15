package com.hu.quirofano;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entrar);
		//Boton de home
		
		Button boton_home = (Button) findViewById(R.id.homeButton);
		boton_home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mostrar("Has presionado el boton Entrar");
				try{
					Class<?> clazz = Class.forName("com.hu.quirofano.HomeActivity");
					Intent intent = new Intent(getApplicationContext(), clazz);
					startActivity(intent);
					
				}catch (ClassNotFoundException e){
					e.printStackTrace();
				}
			}
		});
		
		//Boton de Quirofano central
		
				Button boton_q_central = (Button) findViewById(R.id.qCentralButton);
				boton_q_central.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//mostrar("Has presionado el boton Entrar");
						try{
							Class<?> clazz = Class.forName("com.hu.quirofano.QCentralActivity");
							Intent intent = new Intent(getApplicationContext(), clazz);
							startActivity(intent);
							
						}catch (ClassNotFoundException e){
							e.printStackTrace();
						}
					}
				});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}//Fin de onCreate
}//Fin de la clase
