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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupero le preferenze
        val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)

        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        //set isloggedin = false

        val db = DB_Manager(this);
        val dottori = db.leggiDottore();
        println("Dottori: ");
        for (dottore in dottori){
            println(dottore.nome);
        }


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

        txtNome.text = "Mauro Rossi"

        btnHome.setOnClickListener {
            // Già in MainActivity, opzionale: refresh o scroll in alto
        }
        
        btnProfilo.setOnClickListener {
            // Esempio: vai al profilo
        }
    }
}
