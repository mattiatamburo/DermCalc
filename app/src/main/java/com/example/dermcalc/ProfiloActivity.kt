package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class ProfiloActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profilo_doc)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val txtUpperName = findViewById<TextView>(R.id.txtName)
        val textViewCellulare = findViewById<TextView>(R.id.textViewCellulare)
        val textViewCodFiscale = findViewById<TextView>(R.id.textViewCodFiscale)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        val textViewDataNascita = findViewById<TextView>(R.id.textViewDataNascita)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val btnModifica = findViewById<TextView>(R.id.btnModifica)

        val db = DB_Manager(this)
        val id = sharedPref.getInt("idDottore", -1)
        val dottore = db.getDottoreById(id)

        if (dottore != null) {
            txtUpperName.text = "${dottore.nome.lowercase().replaceFirstChar { it.uppercase() }} ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"
            textViewCellulare.text = HtmlCompat.fromHtml("<b>cellulare:</b> ${dottore.cellulare}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            textViewCodFiscale.text = HtmlCompat.fromHtml("<b>codice fiscale:</b> ${dottore.codFiscale}", HtmlCompat.FROM_HTML_MODE_LEGACY)
            textViewEmail.text = HtmlCompat.fromHtml("<b>email:</b> ${dottore.email}", HtmlCompat.FROM_HTML_MODE_LEGACY)

            // Formattazione della data
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
            textViewDataNascita.text = HtmlCompat.fromHtml("<b>data di nascita:</b> ${formatter.format(dottore.dataNascita)}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        btnHome.setOnClickListener {
            finish()
        }

        btnModifica.setOnClickListener {
            val intent = Intent(this, ModificaDatiPersonali::class.java)
            startActivity(intent)
        }
    }
}
