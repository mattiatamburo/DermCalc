package com.example.dermcalc

import DataBase.Accessi
import DataBase.CartellaClinica
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
import java.util.Calendar
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
                // Genera i pazienti random solo se non sono già presenti nel database
                if (!db.checkPazienti()) {
                    val nomi = listOf(
                        "Leonardo", "Francesco", "Alessandro", "Edoardo", "Tommaso", "Mattia", "Riccardo", "Gabriele", "Lorenzo", "Elia",
                        "Sofia", "Aurora", "Giulia", "Ginevra", "Vittoria", "Beatrice", "Alice", "Ludovica", "Emma", "Matilde",
                        "Giuseppe", "Giovanni", "Antonio", "Mario", "Luigi", "Vincenzo", "Pietro", "Salvatore", "Carlo", "Felice",
                        "Franco", "Guido", "Paolo", "Sergio", "Roberto", "Stefano", "Angelo", "Domenico", "Marco", "Luca",
                        "Andrea", "Filippo", "Giacomo", "Matteo", "Nicola", "Pasquale", "Claudio", "Bruno", "Giorgio", "Alberto",
                        "Daniele", "Romano", "Massimo", "Federico", "Enzo", "Maurizio", "Giulio", "Enrico", "Carmelo", "Davide",
                        "Gianluca", "Simone", "Christian", "Manuel", "Ivan", "Samuel", "Alessio", "Mirko", "Yuri", "Oscar",
                        "Igor", "Boris", "Fabio", "Valerio", "Flavio", "Adriano", "Luciano", "Renzo", "Aldo", "Remo",
                        "Dino", "Elio", "Italo", "Arturo", "Silvano", "Tullio", "Vasco", "Clara", "Lucia", "Elena",
                        "Anna", "Rosa", "Paola", "Chiara", "Marta", "Sara", "Elena", "Francesca", "Valentina", "Giorgia"
                    )
                    val cognomi = listOf(
                        "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci", "Marino", "Greco",
                        "Bruno", "Gallo", "Conti", "De Luca", "Mancini", "Costa", "Giordano", "Rizzo", "Lombardi", "Moretti",
                        "Barbieri", "Fontana", "Santoro", "Mariani", "Rinaldi", "Caruso", "Ferrara", "Galli", "Martini", "Leone",
                        "Longo", "Gentile", "Martinelli", "Vitale", "Serra", "Coppola", "De Santis", "De Angelis", "Marchetti", "Messina",
                        "D'Angelo", "Gatti", "Pellegrini", "Palumbo", "Sanna", "Farina", "Monti", "Pasquali", "Morelli", "Villa",
                        "Basile", "Pellegrino", "Grassi", "Ferraro", "Silvestri", "Castelli", "Bernardi", "Sala", "Grasso", "Marini",
                        "Ferri", "Orlando", "Amato", "Pagano", "Mauro", "De Rosa", "D'Agostino", "Caputo", "Mazza", "Romeo",
                        "Battaglia", "Bellini", "Donati", "Fabbri", "Guerra", "Negri", "Oliva", "Piras", "Riva", "Tosi",
                        "Valenti", "Zani", "Brambilla", "Cattaneo", "Villa", "Bonomi", "Molinari", "Parisi", "Fonti", "Riva",
                        "Donadoni", "Giacometti", "Sartori", "Pavan", "Russo", "Neri", "Sorace", "Marrone", "Anzani", "Montanari"
                    )
                    
                    db.removeDiagnosi()
                    db.removeCartelleCliniche()
                    db.removePazienti()
                    db.resetPazienteIndex()

                    for (i in 1..100) {
                        val nome = nomi.random()
                        val cognome = cognomi.random()
                        val dataNascita = Date(System.currentTimeMillis() - Random.nextLong(630720000000L, 2522880000000L))
                        val paziente = Paziente(
                            nome = nome,
                            cognome = cognome,
                            codFiscale = calcolaCodiceFiscale(nome, cognome, dataNascita),
                            email = "${nome.lowercase(Locale.ITALY)}.${cognome.lowercase(Locale.ITALY)}$i@example.com",
                            cellulare = "333${Random.nextInt(1000000, 9999999)}",
                            dataNascita = dataNascita
                        )
                        val idGenerato = db.insertPaziente(paziente)
                        val nuovaCartella = CartellaClinica(
                            idPaziente = idGenerato.toInt()
                        )
                        db.insertCartellaClinica(nuovaCartella)
                    }
                }

                sharedPref.edit {
                    putBoolean("isLoggedIn", true)
                    putInt("idDottore", accesso.idDottore)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Se non esiste ancora nessun account (primo accesso assoluto)
                if (!db.checkPrimoAccesso()) {
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
                } else {
                    // Account esistente ma credenziali errate
                    Toast.makeText(this, "Credenziali errate. Riprova.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun calcolaCodiceFiscale(nome: String, cognome: String, data: Date): String {
        fun getConsonanti(s: String) = s.uppercase(Locale.ITALY).filter { it in "BCDFGHJKLMNPQRSTVWXYZ" }
        fun getVocali(s: String) = s.uppercase(Locale.ITALY).filter { it in "AEIOU" }

        // Cognome: prime 3 consonanti (o vocali se mancano, o X se corto)
        val consC = getConsonanti(cognome)
        val vocC = getVocali(cognome)
        val partCognome = (consC + vocC + "XXX").take(3)

        // Nome: 1a, 3a e 4a consonante se >= 4, altrimenti prime 3 (o vocali, o X)
        val consN = getConsonanti(nome)
        val vocN = getVocali(nome)
        val partNome = if (consN.length >= 4) {
            "${consN[0]}${consN[2]}${consN[3]}"
        } else {
            (consN + vocN + "XXX").take(3)
        }

        val cal = Calendar.getInstance()
        cal.time = data
        val anno = String.format(Locale.ITALY, "%02d", cal.get(Calendar.YEAR) % 100)
        val mesi = "ABCDEHLMPRST"
        val mese = mesi[cal.get(Calendar.MONTH)]

        // Giorno: se femmina si aggiunge 40 (qui randomizziamo il sesso)
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val giorno = String.format(Locale.ITALY, "%02d", if (Random.nextBoolean()) d else d + 40)

        val comune = "H501" // Roma (uguale per tutti come richiesto)
        val check = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".random() // Carattere di controllo casuale

        return (partCognome + partNome + anno + mese + giorno + comune + check).uppercase(Locale.ITALY)
    }
}
