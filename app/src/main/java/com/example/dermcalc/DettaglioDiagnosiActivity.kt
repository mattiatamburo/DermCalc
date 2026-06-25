package com.example.dermcalc

import DataBase.DB_Manager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class DettaglioDiagnosiActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dettaglio_diagnosi)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idDiagnosi      = intent.getIntExtra("idDiagnosi", -1)
        val db              = DB_Manager(this)

        val txtTipo         = findViewById<TextView>(R.id.txtDettaglioTipo)
        val txtData         = findViewById<TextView>(R.id.txtDettaglioData)
        val txtRisultato    = findViewById<TextView>(R.id.txtDettaglioRisultato)
        val txtSeverita     = findViewById<TextView>(R.id.txtDettaglioSeverita)
        val txtNote         = findViewById<TextView>(R.id.txtDettaglioNote)
        val btnChiudi       = findViewById<Button>  (R.id.btnChiudiDettaglio)

        if (idDiagnosi != -1)
        {
            val diagnosi = db.getDiagnosiById(idDiagnosi)

            if (diagnosi != null)
            {
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY)

                txtTipo     .text   = getString     (R.string.textDettaglioTipo,         diagnosi.tipoCalcolatore)
                txtData     .text   = getString     (R.string.textDettaglioData,         formatter.format(diagnosi.dataDiagnosi))
                txtRisultato.text   = String.format (Locale.ITALY,                      "%.2f", diagnosi.punteggioTotale)
                txtSeverita .text   = getString     (R.string.textDettaglioSeverita,    traduciSeverita(diagnosi.severita, diagnosi.tipoCalcolatore))

                if (diagnosi.note.isNotEmpty())
                {
                    try
                    {
                        val dati = diagnosi.note.split("|")

                        val testoTradotto =
                            if(diagnosi.tipoCalcolatore == "BMI")
                                getString(R.string.note_BMI, dati[0].toFloat(), dati[1].toInt())
                            else if(diagnosi.tipoCalcolatore == "BSA")
                                getString(R.string.note_BSA, dati[0].toDouble(), dati[1].toDouble(), dati[2].toDouble(), dati[3].toDouble())

                            else if(diagnosi.tipoCalcolatore == "PASI")
                                getString(R.string.nota_diagnosi_PASI,
                                            dati[0] .toInt(),       dati[1] .toInt(),       dati[2] .toInt(),
                                            dati[3] .toInt(),       dati[4] .toInt(),       dati[5] .toInt(),
                                            dati[6] .toInt(),       dati[7] .toInt(),       dati[8] .toInt(),
                                            dati[9] .toInt(),       dati[10].toInt(),       dati[11].toInt(),
                                            dati[12].toDouble(),    dati[13].toDouble(),    dati[14].toDouble(), dati[15].toDouble())
                            else if(diagnosi.tipoCalcolatore == "EASI")
                                getString(R.string.nota_diagnosi_EASI,
                                            dati[0] .toInt(),       dati[1] .toInt(),       dati[2] .toInt(),       dati[3] .toInt(),
                                            dati[4] .toInt(),       dati[5] .toInt(),       dati[6] .toInt(),       dati[7] .toInt(),
                                            dati[8] .toInt(),       dati[9] .toInt(),       dati[10].toInt(),       dati[11].toInt(),
                                            dati[12].toInt(),       dati[13].toInt(),       dati[14].toInt(),       dati[15].toInt(),
                                            dati[16].toDouble(),    dati[17].toDouble(),    dati[18].toDouble(),    dati[19].toDouble())
                            else
                                diagnosi.note

                        txtNote.text = testoTradotto
                    }
                    catch (e: Exception)
                    {
                        txtNote.text = diagnosi.note
                    }
                }

            }
            else
            {
                Toast.makeText(this, getString(R.string.err_diagnosiDB), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.err_diagnosiID), Toast.LENGTH_SHORT).show()
            finish()
        }

        btnChiudi.setOnClickListener {
            finish()
        }
    }

    private fun traduciSeverita(severita: Int, calcolatore: String): String
    {
      if(calcolatore == "BMI")
      {
        if      (severita==0)   return getString(R.string.stato_Sottopeso)
        else if (severita==1)   return getString(R.string.stato_Normopeso)
        else if (severita==2)   return getString(R.string.stato_Sovrappeso)
        else                    return getString(R.string.stato_Obesità)
      }
      else
      {
        if      (severita==0)   return getString(R.string.stato_Lieve)
        else if (severita==1)   return getString(R.string.stato_Moderata)
        else                    return getString(R.string.stato_Severa)
      }
    }


}