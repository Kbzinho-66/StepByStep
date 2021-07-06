package com.example.stepbystep

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.databinding.MainActivityBinding
import com.example.stepbystep.ui.receitas.DetalhesReceita
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Variáveis de layout e navegação
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    // Variáveis de permissão
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

        if (!pediuPermissao) {
            pedirPermissao.launch(Manifest.permission.CAMERA)
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

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

}