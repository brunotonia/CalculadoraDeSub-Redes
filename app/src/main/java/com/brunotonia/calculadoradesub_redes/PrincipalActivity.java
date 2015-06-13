package com.brunotonia.calculadoradesub_redes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.brunotonia.calculadoradesub_redes.calculadora.Calculadora;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends Activity {

    private Calculadora calculadora = null;

    private EditText txtPrimeiroOcteto = null;
    private Button btnCalcular = null;
    private TextView lblClasse = null;
    private Spinner spnCidr = null;
    private TextView lblMascara = null;
    private TextView lblSubRedes = null;
    private TextView lblHosts = null;

    List<String> cidrs_str = new ArrayList<>();
    List<Integer> cidrs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtPrimeiroOcteto = (EditText) findViewById(R.id.txtPrimeiroOcteto);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        lblClasse = (TextView) findViewById(R.id.lblClasse);
        spnCidr = (Spinner) findViewById(R.id.spnCidr);
        lblMascara = (TextView) findViewById(R.id.lblMascara);
        lblSubRedes = (TextView) findViewById(R.id.lblSubRedes);
        lblHosts = (TextView) findViewById(R.id.lblHosts);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                calcular();
            }
        });

        spnCidr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                lblSubRedes.setText(calculadora.calculaQuantidadeSubRedes(spnCidr.getSelectedItemPosition()).toString());
                lblHosts.setText(calculadora.calculaQuantidadeHosts(cidrs.get(spnCidr.getSelectedItemPosition())).toString());
                lblMascara.setText(calculadora.retornaMascara(spnCidr.getSelectedItemPosition()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void calcular () {
        calculadora = new Calculadora(new Integer(txtPrimeiroOcteto.getText().toString()));
        lblClasse.setText(calculadora.determinaClasseIP());
        cidrs_str = calculadora.getCidr_str();
        cidrs = calculadora.getCidr();
        // Povoa os Spinners!
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cidrs_str);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnCidr.setAdapter(arrayAdapter);
    }



}
