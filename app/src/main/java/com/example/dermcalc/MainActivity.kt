package com.example.dermcalc

import DataBase.DB_Manager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    companion object {
        private var firstTimeLaunch = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupero le preferenze
        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)


        if (firstTimeLaunch) {
            sharedPref.edit { clear() }
            firstTimeLaunch = false
        }

        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        val db = DB_Manager(this);


        if (!isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Se arrivo qui, l'utente è loggato
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.upperToolBar)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val txtNome = findViewById<TextView>(R.id.txtName)
        val btnProfilo = findViewById<ImageButton>(R.id.btnProfilo)
        val idDottore = sharedPref.getInt("idDottore", -1)
        val dottore = db.getDottoreById(idDottore);

        txtNome.text = dottore?.nome + " " + dottore?.cognome;

        btnHome.setOnClickListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
        
        btnProfilo.setOnClickListener {
            // Esempio: vai al profilo
        }
    }
}
