package com.example.dermcalc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import DataBase.Paziente

class PazienteAdapter(private val listaPazienti: List<Paziente>) : RecyclerView.Adapter<PazienteAdapter.PazienteViewHolder>()
{
  class PazienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
  {
     val textNome   : TextView  = itemView.findViewById(R.id.textFieldNome)
     val textCognome: TextView  = itemView.findViewById(R.id.textFieldCognome)
     val textID     : TextView  = itemView.findViewById(R.id.textFieldID)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PazienteViewHolder
  {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paziente, parent, false)
    return PazienteViewHolder(view)
  }

  override fun onBindViewHolder(holder: PazienteViewHolder, index: Int)
  {
    val pazienteCorrente = listaPazienti[index]

    holder.textNome     .text = pazienteCorrente.nome
    holder.textCognome  .text = pazienteCorrente.cognome
    holder.textID       .text = pazienteCorrente.idPaziente.toString()
  }

  override fun getItemCount(): Int
  {
    return listaPazienti.size
  }
}