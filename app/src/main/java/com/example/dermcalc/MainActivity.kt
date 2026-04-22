package com.example.dermcalc

import DataBase.DB_Manager
import DataBase.Paziente
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

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

        //listaPazienti
        //-----
        val listaTest = listOf(
            Paziente(0, "Mario", " Rossi", "12345678901", "james.monroe@examplepetstore.com", "123456789", Date("2018-12-12")),
            Paziente(1, "Giulia", "Bianchi", "98765432109", "john.hessin.clarke@examplepetstore.com", "987654321", Date("2018-12-12")),
            Paziente(2, "Luca", "Verdi", "45678901234", "william.henry.harrison@example-pet-store.com", "456789012", Date("2018-12-12")),
        )

        val recyclerView            = findViewById<RecyclerView>(R.id.listaPazienti)
        recyclerView.layoutManager  = LinearLayoutManager(this)

        val adapter                 = PazienteAdapter(listaTest)
        recyclerView.adapter        = adapter
        //-----

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
