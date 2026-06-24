package com.example.dermcalc

import DataBase.DB_Manager
import DataBase.Diagnosi
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Date

class BmiActivity : AppCompatActivity()
{
    private var calcoloEffettuato   = false
    private var bmi                 = 0.0
    private var statoClinico        = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db                  = DB_Manager(this)
        val idPaziente          = intent.getIntExtra("idPaziente", -1)

        val editPeso            = findViewById<EditText>(R.id.edit_Peso)
        val editAltezza         = findViewById<EditText>(R.id.edit_Altezza)
        val textRisultatoBmi    = findViewById<TextView>(R.id.text_RisultatoBMI)
        val textStatoBmi        = findViewById<TextView>(R.id.text_StatoBMI)
        val btnCalcolaSalva     = findViewById<Button>  (R.id.btn_CalcolaSalva)
        val btnModifica         = findViewById<Button>  (R.id.btn_Modifica)
        val btnAnnulla          = findViewById<Button>  (R.id.btn_Annulla)


        btnCalcolaSalva.setOnClickListener {
            if (!calcoloEffettuato)
            {
                val strPeso     = editPeso      .text.toString()
                val strAltezza  = editAltezza   .text.toString()

                if (strPeso.isEmpty() || strAltezza.isEmpty())
                {
                    Toast.makeText(this, getString(R.string.err_vuoto), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val peso        = strPeso   .toDoubleOrNull() ?: 0.0
                val altezzaCm   = strAltezza.toDoubleOrNull() ?: 0.0

                if (peso <= 0.0 || altezzaCm <= 0.0)
                {
                    Toast.makeText(this, getString(R.string.err_neg), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val altezzaM    = altezzaCm / 100.0
                bmi             = peso      / (altezzaM * altezzaM)

                statoClinico =  if      (bmi < 18.5)                    getString(R.string.stato_Sottopeso)
                                else if (bmi >= 18.5 && bmi <= 24.99)   getString(R.string.stato_Normopeso)
                                else if (bmi >= 25.0 && bmi <= 29.99)   getString(R.string.stato_Sovrappeso)
                                else                                    getString(R.string.stato_Obesità)

                textRisultatoBmi.text       = getString(R.string.text_RisultatoBMI,     bmi)
                textStatoBmi    .text       = getString(R.string.text_StatoBMI,         statoClinico)

                editPeso        .isEnabled  = false
                editAltezza     .isEnabled  = false

                btnCalcolaSalva .text       = getString(R.string.btn_Salva)
                btnModifica     .visibility = View.VISIBLE
                calcoloEffettuato           = true

            }
            else
            {
                val sharedPref          = getSharedPreferences  ("DermCalcPrefs", MODE_PRIVATE)
                val idDottore           = sharedPref.getInt     ("idDottore", -1)

                val idCartellaClinica   = db.getCartellaClinica(idPaziente);


                val pesoFloat           = editPeso      .text.toString().toFloat()
                val altezzaInt          = editAltezza   .text.toString().toInt  ()
                val noteDiagnosi        = getString(R.string.note_BMI, pesoFloat, altezzaInt)

                if (idPaziente != -1 && idDottore != -1)
                {
                    val nuovaDiagnosi = Diagnosi(
                        idDottore           = idDottore,
                        dataDiagnosi        = Date(),
                        tipoCalcolatore     = "BMI",
                        punteggioTotale     = bmi,
                        severita            = statoClinico,
                        idCartellaClinica   = idCartellaClinica,
                        note                = noteDiagnosi
                    )

                    db.insertDiagnosi(nuovaDiagnosi)
                    finish()
                }
                else
                    Toast.makeText(this, getString(R.string.err_paz), Toast.LENGTH_SHORT).show()
            }
        }

        btnModifica.setOnClickListener{
            editPeso        .isEnabled  = true
            editAltezza     .isEnabled  = true

            btnModifica     .visibility = View.GONE

            btnCalcolaSalva .text       = getString(R.string.btn_Calcola)
            calcoloEffettuato           = false
        }

        btnAnnulla.setOnClickListener{
            finish()
        }
    }
}