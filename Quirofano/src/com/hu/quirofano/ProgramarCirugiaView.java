package com.hu.quirofano;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

public class ProgramarCirugiaView extends Activity {
	
	/*
	 * 17 oct 
	 * 
	 * Las posiciones de los spinners estan dados por el entero "position"
	 * 
	 * */
	
	EditText fecha, horaPropuesta, registro, nombrePaciente, edad, genero, procedencia;
	EditText diagnostico, nombreMedico, riesgoQuirurgico, insumos, requerimientosAnestesiologia;
	EditText hemoderivados, requisitosLaboratorio, otrasNecesidades;
	
	int spin1, spin2, spin3, spin4, spin5, spin6;
	ArrayList<Integer> spins = new ArrayList<Integer>();
	
	//public ProgramarCirugiaView(final LayoutInflater inflater, View view, final Context context){

	public void prog(final LayoutInflater inflater, View view, final Context context){
		/*Spinner 1 - Salas*/
		
		Spinner sp = (Spinner) view.findViewById(R.id.salaOpciones);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
				context, R.array.salas, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				//Toast.makeText(parentView.getContext(), "Has seleccionado " +
				//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				parentView.getItemAtPosition(position);
				//spins.add(position);
				//Log.e("arreglo spins", "s1="+spins);
				Log.e("spiner1 = ", "posicion1="+position);
				//spin1 = position;
				spin1 = position;
			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

		/*Ahora para el spinner 2 - Duracion de la cirugia
		 ************************************************** */
		Spinner sp1 = (Spinner) view.findViewById(R.id.duracion);
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
				context, R.array.duraciones, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);

		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				//Toast.makeText(parentView.getContext(), "Has seleccionado " +
				//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				parentView.getItemAtPosition(position);
				//spins.add(position);
				spin2 = position;
			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
		/*Termina el spinner 2*/

		/*Ahora para el spinner 3 - Tipo de programacion
		 ************************************************** */
		Spinner sp2 = (Spinner) view.findViewById(R.id.tipoDeProgramacion);
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
				context, R.array.tiposDeProgramacion, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter2);

		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				//Toast.makeText(parentView.getContext(), "Has seleccionado " +
				//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				parentView.getItemAtPosition(position);
				//spins.add(position);
				spin3 = position;
			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
		/*Termina el spinner 3*/

		/*Ahora para el spinner 4 - Servicio
		 ************************************************** */
		Spinner sp3 = (Spinner) view.findViewById(R.id.servicio);
		ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
				context, R.array.servicios, android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp3.setAdapter(adapter3);

		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				//Toast.makeText(parentView.getContext(), "Has seleccionado " +
				//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				parentView.getItemAtPosition(position);
				//spins.add(position);
				spin4 = position;

			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
		/*Termina el spinner 4*/

		/*Ahora para el spinner 5 - En atencion a
		 ************************************************** */
		Spinner sp4 = (Spinner) view.findViewById(R.id.enAtencionA);
		ArrayAdapter adapter4 = ArrayAdapter.createFromResource(
				context, R.array.atencionA, android.R.layout.simple_spinner_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp4.setAdapter(adapter4);

		sp4.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				//Toast.makeText(parentView.getContext(), "Has seleccionado " +
				//parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
				parentView.getItemAtPosition(position);
				//spins.add(position);
				spin5 = position;
			}

			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});
		final LinearLayout parent = (LinearLayout)view.findViewById(R.id.add_linear);
		
		Button agregar = (Button)view.findViewById(R.id.agregar);
		Button eliminar = (Button)view.findViewById(R.id.eliminar);
		agregar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.add_linear, null);
				parent.addView(ll);
				
				Spinner sp5 = (Spinner) ll.findViewById(R.id.region);
				ArrayAdapter adapter5 = ArrayAdapter.createFromResource(
						context, R.array.listaRegion, android.R.layout.simple_spinner_item);
				adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp5.setAdapter(adapter5);

				sp5.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {
						parentView.getItemAtPosition(position);
						//spin6 = position;
						//spins.add(position);
					}
					public void onNothingSelected(AdapterView<?> parentView) {

					}
				});
				
				Spinner sp6 = (Spinner) ll.findViewById(R.id.servicio2);
				ArrayAdapter adapter6 = ArrayAdapter.createFromResource(
						context, R.array.servicios2, android.R.layout.simple_spinner_item);
				adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp6.setAdapter(adapter6);

				sp6.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
							int position, long id) {
						parentView.getItemAtPosition(position);
						//spins.add(position);
					}

					public void onNothingSelected(AdapterView<?> parentView) {

					}
				});
						
			}});
		
		eliminar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int index =  parent.getChildCount();
				parent.removeViewAt(index-1);
					
			}});
	}/*Fin de constructor*/
	
	public ArrayList<Integer> getPosition(){
		//Toast toast1 = Toast.makeText(this, "spinners1 = "+spins, Toast.LENGTH_SHORT);
 	    //toast1.show();
		spins.add(spin1);
		spins.add(spin2);
		spins.add(spin3);
		spins.add(spin4);
		spins.add(spin5);
		
		//Log.e("sp1 = ", "sp1="+getSpin());
		//Log.e("sp2 = ", "sp2="+spin2);
		//Log.e("sp3 = ", "sp3="+spin3);
		//Log.e("sp4 = ", "sp4="+spin4);
		//Log.e("sp5 = ", "sp5="+spin5);
		
		//Log.e("datos = ", "spins="+spins);
		return spins; // regresa arreglo con todas las posiciones de los spins
		
	}/*Fin de getPosition*/

	//public int setSpin1(){

	//}
	
}/*Fin de la clase ProgramarCirugiaView*/
