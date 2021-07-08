package com.example.stepbystep

import android.Manifest
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
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
import com.example.stepbystep.data.dao.IngredienteDAO
import com.example.stepbystep.data.dao.PassoDAO
import com.example.stepbystep.data.dao.ReceitaDAO
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.MainActivityBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Variáveis de layout e navegação
    private lateinit var configAppBar: AppBarConfiguration
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var controladorNavegacao: NavController

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

    // DAOs usadas para prepopular o banco de dados
    private val db: AppDatabase by lazy { AppDatabase.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (!pediuPermissao) {
            pedirPermissao.launch(Manifest.permission.CAMERA)
        }

        setContentView(binding.root)

        // Inicializar o Singleton com o contexto da aplicação e aproveitar pra prepopular
        try {
            if (db.receitaDAO().buscarTodasReceitas().isEmpty()) {
                criarReceitas()
            }

            if (db.ingredienteDAO()
                    .buscarIngredientesReceita(db.receitaDAO().buscarReceitaPorNome("Cookies"))
                    .isEmpty()) {
                criarIngredientes()
            }

            if (db.passoDAO()
                    .buscarPassosReceita(db.receitaDAO().buscarReceitaPorNome("Cookies"))
                    .isEmpty()) {
                criarPassos()
            }
        } catch (erro: SQLiteConstraintException) {
            Log.e(erro.toString(), "Erro ao prepopular o banco de dados")
        } finally {
            Snackbar.make(binding.root, "Receitas inseridas(sem foto)", Snackbar.LENGTH_SHORT).show()
        }


        // Declarar onde os fragmentos vão ser carregados
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        controladorNavegacao = navHostFragment.navController

        // Inicializar a action bar
        with(binding) {
            setSupportActionBar(appBarMain.toolbar)

            configAppBar = AppBarConfiguration(
                setOf(
                    R.id.nav_telaInicial, R.id.nav_livroReceitas, R.id.nav_detalhesReceita
                ), drawerLayout
            )
            setupActionBarWithNavController(controladorNavegacao, configAppBar)

            navView.setupWithNavController(controladorNavegacao)
        }

    }

    private fun criarReceitas(){
        val receitas = mutableListOf(
            Receita(nome = "Cookies", porcoes = 9),
        )

        db.receitaDAO().inserirReceitas(receitas)
    }

    private fun criarIngredientes() {
        val codigoReceita = db.receitaDAO().buscarReceitaPorNome("Cookies")

        val ingredientes = mutableListOf(
            Ingrediente(idReceita = codigoReceita, nome = "Açúcar Refinado", quantidade = "1/2 Xícara"),
            Ingrediente(idReceita = codigoReceita, nome = "Açúcar Mascavo", quantidade = "1/2 Xícara"),
            Ingrediente(idReceita = codigoReceita, nome = "Sal Fino", quantidade = "1 colher de chá"),
            Ingrediente(idReceita = codigoReceita, nome = "Manteiga sem sal", quantidade = "1/2 Xícara"),
            Ingrediente(idReceita = codigoReceita, nome = "Ovo", quantidade = "1"),
            Ingrediente(idReceita = codigoReceita, nome = "Extrato de Baunilha", quantidade = "1 colher de chá"),
            Ingrediente(idReceita = codigoReceita, nome = "Farinha Multiuso", quantidade = "155 gramas"),
            Ingrediente(idReceita = codigoReceita, nome = "Bicarbonato de Sódio", quantidade = "1/2 colher de chá"),
            Ingrediente(idReceita = codigoReceita, nome = "Chocolate ao leite", quantidade = "110 gramas"),
            Ingrediente(idReceita = codigoReceita, nome = "Chocolate amargo", quantidade = "110 gramas"),
        )

        db.ingredienteDAO().inserirIngredientes(ingredientes)
    }

    private fun criarPassos() {
        val codigoReceita = db.receitaDAO().buscarReceitaPorNome("Cookies")

        val passos = mutableListOf(
            Passo(idReceita = codigoReceita, descricao = "Derreter a manteiga"),
            Passo(idReceita = codigoReceita, descricao = "Misturar a manteiga, os açúcares e o sal até formar uma pasta uniforme"),
            Passo(idReceita = codigoReceita, descricao = "Peneirar a farinha e o bicarbonato de sódio e misturar levemente com o resto"),
            Passo(idReceita = codigoReceita, descricao = "Cortar o chocolate em pequenos pedaços e juntar com a mistura"),
            Passo(idReceita = codigoReceita, descricao = "Deixar descansando na geladeira por pelo menos 30 minutos." +
                    " Quanto mais tempo ficar descansando, mais intenso fica o sabor."),
            Passo(idReceita = codigoReceita, descricao = "Pré aquecer o forno a 180°C e cobrir uma forma com papel pardo"),
            Passo(idReceita = codigoReceita, descricao = "Pegar bolinhas da massa com um pegador de sorvete e espalhar na forma"),
            Passo(idReceita = codigoReceita, descricao = "Assar até que as bordas estejam douradas"),
        )

        db.passoDAO().inserirPassos(passos)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(configAppBar) || super.onSupportNavigateUp()
    }

    @Suppress("SameParameterValue")
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


}