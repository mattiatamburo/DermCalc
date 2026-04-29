package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class ProfiloPaziente : AppCompatActivity() {
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
        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val id_paziente = sharedPref.getInt("idPaziente", -1)
        val id_dottore = sharedPref.getInt("idDottore", -1)
        val paziente = db.getPazienteById(id_paziente)
        val dottore = db.getDottoreById(id_dottore)
        
        val txtNomeCognome = findViewById<TextView>(R.id.txtNomeCognome)
        val txtCodFiscale = findViewById<TextView>(R.id.txtCodFiscale)
        val txtDataNascita = findViewById<TextView>(R.id.txtDataNascita)
        val txtCellulare = findViewById<TextView>(R.id.txtCellulare)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtUpperName = findViewById<TextView>(R.id.txtName)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        
        if (paziente != null) {
            if (dottore != null) {
                txtUpperName.text = "${dottore.nome.lowercase().replaceFirstChar { it.uppercase() }} ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"
            }
            txtNomeCognome.text = "${paziente.nome.lowercase().replaceFirstChar { it.uppercase() }} ${paziente.cognome.lowercase().replaceFirstChar { it.uppercase() }}"
            txtCodFiscale.text = paziente.codFiscale
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
            txtDataNascita.text = formatter.format(paziente.dataNascita)
            txtCellulare.text = paziente.cellulare
            txtEmail.text = paziente.email
        }

        btnHome.setOnClickListener {
            finish()
        }
    }
}
