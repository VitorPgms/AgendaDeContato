package com.vitordepaula.agendadecontato.dao

import androidx.room.Dao
import androidx.room.Insert
import com.vitordepaula.agendadecontato.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(listaUsuarios: MutableList<Usuario>)
}