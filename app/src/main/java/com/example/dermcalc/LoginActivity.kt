package com.example.dermcalc

import DataBase.Accessi
import DataBase.DB_Manager
import DataBase.Dottore
import DataBase.Paziente
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textField_Email = findViewById<EditText>(R.id.textFieldEmail)
        val textField_Password = findViewById<EditText>(R.id.textFieldPassword)
        val btn_Login = findViewById<Button>(R.id.btn_login)
        val db = DB_Manager(this)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)

        btn_Login.setOnClickListener {
            val username = textField_Email.text.toString()
            val password = textField_Password.text.toString()
            val accesso = db.checkLogin(username, password)
            val sharedPref = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)

            if (accesso != null) {
                // Inserimento 10 pazienti random
                val nomi = listOf("Marco", "Luca", "Anna", "Giulia", "Paolo", "Elena", "Roberto", "Sara", "Fabio", "Marta")
                val cognomi = listOf("Rossi", "Bianchi", "Verdi", "Russo", "Ferrari", "Esposito", "Romano", "Gallo", "Costa", "Fontana")
                
                for (i in 1..10) {
                    val nome = nomi.random()
                    val cognome = cognomi.random()
                    val randomId = i
                    val paziente = Paziente(
                        nome = nome,
                        cognome = cognome,
                        codFiscale = "${nome.take(3)}${cognome.take(3)}${randomId}X".uppercase(),
                        email = "${nome.lowercase()}.${cognome.lowercase()}@example.com",
                        cellulare = "333${Random.nextInt(1000000, 9999999)}",
                        dataNascita = Date(System.currentTimeMillis() - Random.nextLong(630720000000L, 2522880000000L)) // Età tra 20 e 80 anni
                    )
                    //db.insertPaziente(paziente)
                }

                sharedPref.edit {
                    putBoolean("isLoggedIn", true)
                    putInt("idDottore", accesso.idDottore)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // ... codice esistente per il dottore di test ...
                db.insertDottore(
                    Dottore(
                        0,
                        "davide",
                        "rossi",
                        "3926547890",
                        "DVDRSS87B365SDS",
                        "daviderossi@gmail.com",
                        dateFormat.parse("15/02/1990") ?: Date()
                    )
                )
                db.insertAccessi(Accessi(0, 1, username, password))
                Toast.makeText(this, "Credenziali create. Riprova il login.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
