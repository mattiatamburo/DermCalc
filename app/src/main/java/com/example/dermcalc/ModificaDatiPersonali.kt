package com.example.dermcalc

import DataBase.DB_Manager
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ModificaDatiPersonali : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.modifica_dati_personali)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db              = DB_Manager(this)
        val sharedPref      = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val id              = sharedPref.getInt("idDottore", -1)
        val dottore         = db.getDottoreById(id)
        val txtUpperName    = findViewById<TextView>(R.id.txtName)
        val btnConferma     = findViewById<Button>  (R.id.btnConferma)
        val txtNomeCognome  = findViewById<EditText>(R.id.txtNomeCognome)
        val txtCellulare    = findViewById<EditText>(R.id.txtCellulare)
        val txtEmail        = findViewById<EditText>(R.id.txtEmail)
        val txtDataNascita  = findViewById<EditText>(R.id.txtDataNascita)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)

        if (dottore != null) {
            txtUpperName.text = "${
                dottore.nome.lowercase().replaceFirstChar { it.uppercase() }
            } ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"

            txtNomeCognome  .setText("${dottore.nome} ${dottore.cognome}")
            txtCellulare    .setText(dottore.cellulare)
            txtEmail        .setText(dottore.email)
            txtDataNascita  .setText(formatter.format(dottore.dataNascita))

            // Configurazione DatePickerDialog
            txtDataNascita.setOnClickListener {
                val calendar = Calendar.getInstance()
                try {
                    val currentData = formatter.parse(txtDataNascita.text.toString())
                    if (currentData != null) calendar.time = currentData
                } catch (e: Exception) {
                }

                val year    = calendar.get(Calendar.YEAR)
                val month   = calendar.get(Calendar.MONTH)
                val day     = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog =
                    DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate    .set    (selectedYear, selectedMonth, selectedDay)
                        txtDataNascita  .setText(formatter.format(selectedDate.time))
                    }, year, month, day)

                datePickerDialog.show()
            }
        }

        btnConferma.setOnClickListener {
            if (dottore != null && txtNomeCognome.text != null && txtCellulare.text != null && txtEmail.text != null && txtDataNascita.text != null)
            {
                val strNomeCognome  = txtNomeCognome.text.toString()
                val strNome         = strNomeCognome.substringBefore    (" ").trim()
                val strCognome      = strNomeCognome.substringAfterLast (" ").trim()
                val cellulare       = txtCellulare  .text.toString()
                val email           = txtEmail      .text.toString()
                val strDataNascita  = txtDataNascita.text.toString()
                
                val dateNascita: Date = try {
                    formatter.parse(strDataNascita) ?: dottore.dataNascita
                } catch (e: Exception) {
                    dottore.dataNascita
                }
                if (db.modifyDottore(
                        strNome,
                        strCognome,
                        cellulare,
                        dottore.codFiscale,
                        email,
                        dateNascita,
                        id
                    )
                ) {
                    Toast.makeText(this, getString(R.string.datiAggiornati), Toast.LENGTH_SHORT).show()
                    finish()
                    //TODO: FARE UN REFRESH DELLA PAGINA PROFILO
                    val intent = Intent(this, ProfiloActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.err_modifica), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}