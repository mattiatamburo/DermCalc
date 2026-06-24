package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfiloPaziente : AppCompatActivity()
{
    override fun onResume()
    {
        super.onResume()

        val db = DB_Manager(this)
        val id_paziente = intent.getIntExtra("idPaziente", -1)

        if (id_paziente != -1)
        {
            val recyclerView = findViewById<RecyclerView>(R.id.listaDiagnosi)

            val listaAggiornata = db.getDiagnosiByPaziente(id_paziente)

            val adapter = DiagnosiAdapter(listaAggiornata)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profilo_paziente)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DB_Manager(this)
        val sharedPref      = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val id_paziente     = intent.getIntExtra("idPaziente", -1)
        val id_dottore      = sharedPref.getInt("idDottore", -1)
        val paziente        = db.getPazienteById(id_paziente)
        val dottore         = db.getDottoreById(id_dottore)
        
        val txtNomeCognome          = findViewById<TextView>    (R.id.txtNomeCognome)
        val txtCodFiscale           = findViewById<TextView>    (R.id.txtCodFiscale)
        val txtDataNascita          = findViewById<TextView>    (R.id.txtDataNascita)
        val txtCellulare            = findViewById<TextView>    (R.id.txtCellulare)
        val txtEmail                = findViewById<TextView>    (R.id.txtEmail)
        val txtUpperName            = findViewById<TextView>    (R.id.txtName)
        val btnHome                 = findViewById<ImageButton> (R.id.btnHome)
        val btnInsesrisciDiagnosi   = findViewById<Button>      (R.id.btnInserisciDiagnosi)
        
        if (paziente != null)
        {
            if (dottore != null)
                txtUpperName.text = "${dottore.nome.lowercase().replaceFirstChar { it.uppercase() }} ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"

            txtNomeCognome  .text = "${paziente.nome.lowercase().replaceFirstChar { it.uppercase() }} ${paziente.cognome.lowercase().replaceFirstChar { it.uppercase() }}"
            txtCodFiscale   .text = paziente.codFiscale
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
            txtDataNascita  .text = formatter.format(paziente.dataNascita)
            txtCellulare    .text = paziente.cellulare
            txtEmail        .text = paziente.email


            val recyclerView = findViewById<RecyclerView>(R.id.listaDiagnosi)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val listaTest = db.getDiagnosiByPaziente(paziente.idPaziente)
            val adapter = DiagnosiAdapter(listaTest)
            recyclerView.adapter = adapter

            val barraRicerca: EditText = findViewById(R.id.ricercaDiagnosi)

            barraRicerca.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.filter(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        }



        btnHome.setOnClickListener {
            finish()
        }

        btnInsesrisciDiagnosi.setOnClickListener {
            val intent = Intent(this, SelezioneCalcolatoreActivity::class.java)
            intent.putExtra("idPaziente", id_paziente)
            startActivity(intent)
        }
    }
}
