package com.example.dermcalc

import DataBase.DB_Manager
import DataBase.Paziente
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private var firstTimeLaunch = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.upperToolBar)
        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        val txtNome = findViewById<TextView>(R.id.txtName)
        val btnProfilo = findViewById<ImageButton>(R.id.btnProfilo)
        val idDottore = sharedPref.getInt("idDottore", -1)
        val dottore = db.getDottoreById(idDottore)
        val btnTest = findViewById<Button>(R.id.btnTest)

        txtNome.text = dottore?.nome + " " + dottore?.cognome;


        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
        val listaTest = db.getPazienti()
        for (paziente in listaTest) {
            println(paziente.idPaziente.toString() + " " + paziente.cognome + " " + paziente.nome);
        }
        val recyclerView = findViewById<RecyclerView>(R.id.listaPazienti)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = PazienteAdapter(listaTest)
        recyclerView.adapter = adapter

        btnHome.setOnClickListener {
            val restartIntent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(restartIntent)
        }

        btnProfilo.setOnClickListener {
            val intent = Intent(this, ProfiloActivity::class.java)
            startActivity(intent)
        }

        btnTest.setOnClickListener {
            sharedPref.edit{ putInt("idPaziente", Random.nextInt(1, 11))}
            val intent = Intent(this, ProfiloPaziente::class.java)
            startActivity(intent)
        }
    }
}