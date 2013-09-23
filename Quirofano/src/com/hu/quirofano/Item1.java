package com.hu.quirofano;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
//prueba1
public class Item1 extends SherlockFragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View v = inflater.inflate(R.layout.qcentral, container, false);

		final ScrollView sv = (ScrollView)v.findViewById(R.id.vista);

		final View home = inflater.inflate(R.layout.home, container, false);
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
				sv.addView(programar);
			}

		});
		Intent intent = new Intent(getActivity(), QCentralActivity.class);
		startActivity(intent);
		return v;



//		return inflater.inflate(R.layout.qcentral, null);
	}
}//Fin de la clase Item1