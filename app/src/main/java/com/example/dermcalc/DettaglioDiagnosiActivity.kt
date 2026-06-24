package com.example.dermcalc

import DataBase.DB_Manager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class DettaglioDiagnosiActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dettaglio_diagnosi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idDiagnosi  = intent.getIntExtra("idDiagnosi", -1)
        val db          = DB_Manager(this)

        val txtTipo         = findViewById<TextView>(R.id.txtDettaglioTipo)
        val txtData         = findViewById<TextView>(R.id.txtDettaglioData)
        val txtRisultato    = findViewById<TextView>(R.id.txtDettaglioRisultato)
        val txtSeverita     = findViewById<TextView>(R.id.txtDettaglioSeverita)
        val txtNote         = findViewById<TextView>(R.id.txtDettaglioNote)
        val btnChiudi       = findViewById<Button>  (R.id.btnChiudiDettaglio)

        if (idDiagnosi != -1)
        {
            val diagnosi = db.getDiagnosiById(idDiagnosi)

            if (diagnosi != null)
            {
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY)

                txtTipo     .text   = getString     (R.string.textDettaglioTipo,         diagnosi.tipoCalcolatore)
                txtData     .text   = getString     (R.string.textDettaglioData,         formatter.format(diagnosi.dataDiagnosi))
                txtRisultato.text   = String.format (Locale.ITALY,                              "%.2f", diagnosi.punteggioTotale)
                txtSeverita .text   = getString     (R.string.textDettaglioSeverita,     diagnosi.severita)

                if (diagnosi.note.isNotEmpty())
                    txtNote.text = diagnosi.note

            } else {
                Toast.makeText(this, "Diagnosi non trovata nel database", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, "Errore caricamento ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnChiudi.setOnClickListener {
            finish()
        }
    }
}