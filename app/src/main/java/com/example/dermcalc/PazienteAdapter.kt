package com.example.dermcalc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import DataBase.Paziente
import android.content.Intent

class PazienteAdapter(private var listaPazienti: List<Paziente>) : RecyclerView.Adapter<PazienteAdapter.PazienteViewHolder>()
{
  private var filteredList: List<Paziente> = listaPazienti
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
    val pazienteCorrente = filteredList[index]

    holder.textNome     .text = pazienteCorrente.nome
    holder.textCognome  .text = pazienteCorrente.cognome
    holder.textID       .text = pazienteCorrente.idPaziente.toString()

    holder.itemView.setOnClickListener {
        val context = holder.itemView.context
        val intent = Intent(context, ProfiloPaziente::class.java)
        intent.putExtra("idPaziente", pazienteCorrente.idPaziente)
        context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int
  {
    return filteredList.size
  }

  fun filter(query: String)
  {
    filteredList = if(query.isEmpty()) listaPazienti else listaPazienti.filter {
        val nomeCognome = "${it.nome} ${it.cognome}"
        val cognomeNome = "${it.cognome} ${it.nome}"
        val id          = it.idPaziente.toString()

        nomeCognome.contains(query, ignoreCase = true) ||
        cognomeNome.contains(query, ignoreCase = true) ||
        id         .contains(query, ignoreCase = true)
    }
    notifyDataSetChanged()
  }
}