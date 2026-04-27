package com.example.dermcalc

import DataBase.DB_Manager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ModificaDatiPersonali : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modifica_dati_personali)

        val db = DB_Manager(this)
        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val id = sharedPref.getInt("idDottore", -1)
        val dottore = db.getDottoreById(id)
        val txtName = findViewById<TextView>(R.id.txtName)

        if (dottore != null) {
            txtName.text = "${dottore.nome.lowercase().replaceFirstChar { it.uppercase() }} ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"

        }
    }
}