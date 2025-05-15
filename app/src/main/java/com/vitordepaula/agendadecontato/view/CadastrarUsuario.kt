package com.vitordepaula.agendadecontato.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vitordepaula.agendadecontato.AppDatabase
import com.vitordepaula.agendadecontato.R
import com.vitordepaula.agendadecontato.dao.UsuarioDao
import com.vitordepaula.agendadecontato.databinding.ActivityCadastrarUsuarioBinding
import com.vitordepaula.agendadecontato.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastrarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarUsuarioBinding
    private var usuarioDao: UsuarioDao? = null
    private val listaUsuarios: MutableList<Usuario> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)



            binding.btnCadastrar.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch { //Tread que roda em segundo plano para nao da erro na tread principal

                    val nome = binding.editNome.text.toString()
                    val sobreNome = binding.editSobrenome.text.toString()
                    val idade = binding.editIdade.text.toString()
                    val celular = binding.editCelular.text.toString()
                    val mensagem: Boolean


                    if(nome.isEmpty() || sobreNome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
                        mensagem = false
                    } else {
                        mensagem = true
                        cadastrar(nome, sobreNome, idade, celular)
                    }

                    withContext(Dispatchers.Main){
                        if (mensagem){
                            Toast.makeText(applicationContext, "Sucesso ao cadastrar usuario!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    private fun cadastrar(nome: String, sobrenome: String, idade: String, celular: String){
        val usuario = Usuario(nome, sobrenome, idade, celular)
        listaUsuarios.add(usuario)
        usuarioDao = AppDatabase.getIntance(this).usuarioDao()
        usuarioDao!!.inserir(listaUsuarios)
    }

}