package com.vitordepaula.agendadecontato.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitordepaula.agendadecontato.databinding.ContatoItemBinding
import com.vitordepaula.agendadecontato.model.Usuario
import com.vitordepaula.agendadecontato.view.AtualizarContato

class ContatoAdapter(
    private val context: Context,
    private val listaUsuarios: MutableList<Usuario>): RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }

    override fun getItemCount(): Int = listaUsuarios.size

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) { //Metodo de visualizar itens
        holder.txtNome.text = listaUsuarios[position].nome
        holder.txtSobrenome.text = listaUsuarios[position].sobrenome
        holder.txtIdade.text = listaUsuarios[position].idade
        holder.txtCelular.text = listaUsuarios[position].celular

        holder.btnAtualizar.setOnClickListener {
            val intent = Intent(context, AtualizarContato::class.java)
            intent.putExtra("nome", listaUsuarios[position].nome)
            intent.putExtra("sobrenome", listaUsuarios[position].sobrenome)
            intent.putExtra("idade", listaUsuarios[position].idade)
            intent.putExtra("celular", listaUsuarios[position].celular)
            intent.putExtra("uid", listaUsuarios[position].uid)
            context.startActivity(intent)

        }
    }

    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) { //Usando o binding para recuperar itens
        val txtNome = binding.txtNome
        val txtSobrenome = binding.txtSobrenome
        val txtIdade = binding.txtIdade
        val txtCelular = binding.txtCelular

        val btnAtualizar = binding.btnAtualizar
        val btnDeletar = binding.btnDeletar
    }


}