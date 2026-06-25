package com.example.dermcalc

import DataBase.DB_Manager
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val db                  = DB_Manager(this)
        val sharedPref          = getSharedPreferences("DermCalcPrefs", MODE_PRIVATE)
        val id                  = sharedPref.getInt("idDottore", -1)
        val dottore             = db.getDottoreById(id)
        val accesso             = db.getAccessoByDottoreId(id)

        val txtUpperName        = findViewById<TextView>    (R.id.txtName)
        val txtCellulare        = findViewById<EditText>    (R.id.txtCellulare)
        val txtEmail            = findViewById<EditText>    (R.id.txtEmail)

        val txtOldPassword      = findViewById<EditText>    (R.id.txtOldPassword)
        val txtNewPassword      = findViewById<EditText>    (R.id.txtNewPassword)
        val txtConfirmPassword  = findViewById<EditText>    (R.id.txtConfirmPassword)

        val btnConferma         = findViewById<Button>      (R.id.btnConferma)


        if (dottore != null)
        {
            txtUpperName.text = "${
                dottore.nome.lowercase().replaceFirstChar { it.uppercase() }
            } ${dottore.cognome.lowercase().replaceFirstChar { it.uppercase() }}"

            txtCellulare    .setText(dottore.cellulare)
            txtEmail        .setText(dottore.email)
        }

        btnConferma.setOnClickListener {
            if (dottore != null)
            {
                val cellulare       = txtCellulare      .text.toString()
                val email           = txtEmail          .text.toString()

                val oldPass         = txtOldPassword    .text.toString()
                val newPass         = txtNewPassword    .text.toString()
                val confPass        = txtConfirmPassword.text.toString()

                if (cellulare.isEmpty() || !Patterns.PHONE.matcher(cellulare).matches() || cellulare.length < 8)
                {
                    txtCellulare.error = getString(R.string.err_cellulare)
                    txtCellulare.requestFocus()
                    return@setOnClickListener
                }

                if (email.isEmpty()     || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    txtEmail.error = getString(R.string.err_email)
                    txtEmail.requestFocus()
                    return@setOnClickListener
                }

                var passwordModificata = false

                if (oldPass.isNotEmpty() || newPass.isNotEmpty() || confPass.isNotEmpty())
                {
                    if (oldPass != accesso.password)
                    {
                        txtOldPassword.error = getString(R.string.err_oldPassword)
                        txtOldPassword.requestFocus()
                        return@setOnClickListener
                    }

                    val erroreSicurezza = controllaSicurezzaPassword(newPass)
                    if (erroreSicurezza != null)
                    {
                        txtNewPassword.error = erroreSicurezza
                        txtNewPassword.requestFocus()
                        return@setOnClickListener
                    }

                    if (newPass != confPass)
                    {
                        txtConfirmPassword.error = getString(R.string.err_confirmPassword)
                        txtConfirmPassword.requestFocus()
                        return@setOnClickListener
                    }

                    if (db.updatePassword(accesso.idAccesso, newPass) <= 0)
                    {
                        Toast.makeText(this, getString(R.string.err_aggPassword), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    passwordModificata = true
                }

                val contattiAggiornati = db.modifyDottore(
                        dottore.nome,
                        dottore.cognome,
                        cellulare,
                        dottore.codFiscale,
                        email,
                        dottore.dataNascita,
                        id
                    )

                if (contattiAggiornati || passwordModificata)
                {
                    Toast.makeText(this, getString(R.string.datiAggiornati), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.err_modifica), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun controllaSicurezzaPassword(password: String): String?
    {
        if (password.length < 8)                    return getString(R.string.err_passChar)
        if (!password.any { it.isUpperCase() })     return getString(R.string.err_passUpper)
        if (!password.any { it.isLowerCase() })     return getString(R.string.err_passLower)
        if (!password.any { it.isDigit() })         return getString(R.string.err_passDigit)
        if (password.all  { it.isLetterOrDigit() }) return getString(R.string.err_passSpecial)
                                                    return null
    }
}