package com.hu.quirofano;

import com.hu.quirofano.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class ProgramarCirugia extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programar_cirugia);
        
        Spinner sp = (Spinner) findViewById(R.id.salaOpciones);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.salas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
           
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
             int position, long id) {
             Toast.makeText(parentView.getContext(), "Has seleccionado " +
             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {
            
            }
        });
        
        /*Ahora para el spinner 2 - Duracion de la cirugia
         ************************************************** */
        Spinner sp1 = (Spinner) findViewById(R.id.duracion);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this, R.array.duraciones, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        
        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
             int position, long id) {
             Toast.makeText(parentView.getContext(), "Has seleccionado " +
             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {
            
            }
        });
        /*Termina el spinner 2*/
        
        /*Ahora para el spinner 3 - Tipo de programacion
         ************************************************** */
        Spinner sp2 = (Spinner) findViewById(R.id.tipoDeProgramacion);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this, R.array.tiposDeProgramacion, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        
        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
             int position, long id) {
             Toast.makeText(parentView.getContext(), "Has seleccionado " +
             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {
            
            }
        });
        /*Termina el spinner 3*/
        
        /*Ahora para el spinner 4 - Servicio
         ************************************************** */
        Spinner sp3 = (Spinner) findViewById(R.id.servicio);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
                this, R.array.servicios, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter3);
        
        sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
             int position, long id) {
             Toast.makeText(parentView.getContext(), "Has seleccionado " +
             parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            
            }
                                 
            public void onNothingSelected(AdapterView<?> parentView) {
            
            }
        });
        /*Termina el spinner 4*/
        
    }
}