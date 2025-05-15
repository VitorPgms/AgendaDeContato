package com.vitordepaula.agendadecontato

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitordepaula.agendadecontato.adapter.ContatoAdapter
import com.vitordepaula.agendadecontato.dao.UsuarioDao
import com.vitordepaula.agendadecontato.databinding.ActivityMainBinding
import com.vitordepaula.agendadecontato.model.Usuario
import com.vitordepaula.agendadecontato.view.CadastrarUsuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var contatoAdapter: ContatoAdapter
    private val _listaUsuarios = MutableLiveData<MutableList<Usuario>>()  //Variavel observada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            getContato()

            withContext(Dispatchers.Main){
                _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()

                }



            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btCadastrar.setOnClickListener {
            val navegarTelaCadastro = Intent(this, CadastrarUsuario::class.java)
            startActivity(navegarTelaCadastro)
        }

    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            getContato()

            withContext(Dispatchers.Main){
                _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()

                }



            }


        }
    }

    private fun getContato(){
        usuarioDao = AppDatabase.getIntance(this).usuarioDao()
        val listaUsuarios: MutableList<Usuario> = usuarioDao.get()
        _listaUsuarios.postValue(listaUsuarios)
    }
}