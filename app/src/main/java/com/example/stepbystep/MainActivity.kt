package com.example.stepbystep

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.MainActivityBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Variáveis de layout e navegação
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    // Pedidos de permissão
    private var pediuPermissao: Boolean = false
    private val pedirPermissao =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissaoDada ->
            if (permissaoDada) {

                Snackbar.make(
                    binding.root,
                    "Permissão de câmera concedida.",
                    Snackbar.LENGTH_SHORT
                ).show()

                pediuPermissao = true

            } else {

                mostraAlerta(
                    Manifest.permission.CAMERA, "uso da Câmera"
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Inicializar o Singleton com o contexto da aplicação
        AppDatabase.getInstance(applicationContext).receitaDAO()

//        popularDB()

        if (!pediuPermissao) {
            pedirPermissao.launch(Manifest.permission.CAMERA)
        }

        // Declarar onde os fragmentos vão ser carregados
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        // Inicializar a action bar
        with(binding) {
            setSupportActionBar(appBarMain.toolbar)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_telaInicial, R.id.nav_livroReceitas, R.id.nav_detalhesReceita
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)

            navView.setupWithNavController(navController)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun mostraAlerta(permissao: String, nome: String) {

        AlertDialog.Builder(this)
            .apply {
                setMessage("Você precisa conceder permissão de $nome para poder tirar uma foto.")
                setTitle("Permissão Necessária")
                setPositiveButton("OK") { _, _ ->
                    pedirPermissao.launch(permissao)
                }
            }
            .create()
            .show()

    }

    private fun popularDB() {

        with (AppDatabase.getInstance(applicationContext)) {

            clearAllTables()
            val receitas: MutableList<Receita> = mutableListOf(
                Receita(
                    nome = "Penne à Carbonara",
                    uriFoto = "",
                    porcoes = 1
                )
                ,
                Receita(
                    nome = "Lasanha Rústica",
                    uriFoto = "shorturl.at/beuM9",
                    porcoes = 5
                ),
                Receita(
                    nome = "Lasanha à Bolonhesa",
                    uriFoto = "shorturl.at/efBEU",
                    porcoes = 5
                )
        )

            receitaDAO().inserirReceitas(receitas)

//            val receitas = receitaDAO().buscarTodasReceitas()
            val codReceita = receitas[0].codigo
            val ingredientes: MutableList<Ingrediente> = mutableListOf(
                Ingrediente(idReceita = codReceita, nome = "Massa Penne", quantidade = "150 gramas"),
                Ingrediente(idReceita = codReceita, nome = "Bacon defumado em cubos", quantidade = "8 gramas"),
                Ingrediente(idReceita = codReceita, nome = "Queijo Parmesão ralado", quantidade = "40 gramas"),
                Ingrediente(idReceita = codReceita, nome = "Ovo", quantidade = "1"),
                Ingrediente(idReceita = codReceita, nome = "Gema de Ovo", quantidade = "2"),
                Ingrediente(idReceita = codReceita, nome = "Sal", quantidade = "A gosto"),
                Ingrediente(idReceita = codReceita, nome = "Água", quantidade = "O suficiente para o cozimento da água"),
                Ingrediente(idReceita = codReceita, nome = "Pimenta-do-reino moída", quantidade = "A gosto")
            )

            val passos: MutableList<Passo> = mutableListOf(
                Passo(codReceita, descricao = "Cozinhar a massa.",
                    ordem = 0),
                Passo(codReceita, descricao = "Misturar o ovo, as gemas a primenta-do-reino e o parmesão" +
                        " até que vire um creme grosso.", ordem = 1)
            )
            ingredienteDAO().inserirIngredientes(ingredientes)
            passoDAO().inserirPassos(passos)
        }
    }



}