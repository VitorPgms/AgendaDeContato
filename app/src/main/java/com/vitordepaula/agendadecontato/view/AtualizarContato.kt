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
import com.vitordepaula.agendadecontato.databinding.ActivityAtualizarContatoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarContato : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarContatoBinding
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarContatoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val idadeRecuperado = intent.extras?.getString("idade")
        val celularRecuperado = intent.extras?.getString("celular")
        val uid = intent.extras!!.getInt("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobrenomeRecuperado)
        binding.editIdade.setText(idadeRecuperado)
        binding.editCelular.setText(celularRecuperado)

        binding.btnAtualizar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val idade = binding.editIdade.text.toString()
                val celular = binding.editCelular.text.toString()

                val mensagem: Boolean


                if(nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()) {
                    mensagem = false
                } else {
                    mensagem = true
                    atualizarContato(uid, nome, sobrenome, idade, celular)
                }

                withContext(Dispatchers.Main){
                    if (mensagem){
                        Toast.makeText(applicationContext, "Sucesso ao atualizar usuario!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    private fun atualizarContato(uid: Int, nome: String, sobrenome: String, idade: String, celular: String){
        usuarioDao = AppDatabase.getIntance(this).usuarioDao()
        usuarioDao.atualizar(uid, nome, sobrenome, idade, celular)
    }
}