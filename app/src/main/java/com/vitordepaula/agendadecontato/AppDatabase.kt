package com.vitordepaula.agendadecontato

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitordepaula.agendadecontato.dao.UsuarioDao
import com.vitordepaula.agendadecontato.model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object{


        private const val DATABASE_NOME: String = "DB_USUARIOS"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getIntance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NOME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}