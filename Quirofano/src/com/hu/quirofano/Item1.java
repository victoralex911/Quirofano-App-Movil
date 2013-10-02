package com.hu.quirofano;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockFragment;
//<<<<<<< HEAD
//prueba1
//	=======

//>>>>>>> f430b8bdebb5172a872119a5ea0a4c8815fe53e3
public class Item1 extends SherlockFragment{
	
	//Spinner sp;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View v = inflater.inflate(R.layout.qcentral, container, false);

		final ScrollView sv = (ScrollView)v.findViewById(R.id.vista);
		
		//spinner 1
		final Spinner sp = (Spinner)v.findViewById(R.id.salaOpciones);
		//***************************************************************
				
		final View home = inflater.inflate(R.layout.home, container, false); //Layout incorrecto, no sera home
		// ... a diferencia del MainView estas seran cirugias programas del dia, incluyendo diferidas.
		final View programar = inflater.inflate(R.layout.programar_cirugia, container, false);
		
		
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
        
		        //sp = (Spinner) findViewById(R.id.salaOpciones);
				ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(
		                this, R.array.salas, android.R.layout.simple_spinner_item);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        sp.setAdapter(adapter);
		        
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
				
				
			}

		});
		//Intent intent = new Intent(getActivity(), QCentralActivity.class);
		//startActivity(intent);
		return v;



//		return inflater.inflate(R.layout.qcentral, null);
	}
}//Fin de la clase Item1