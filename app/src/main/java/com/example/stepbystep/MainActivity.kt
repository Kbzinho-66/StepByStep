package com.example.stepbystep

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.stepbystep.data.AppDatabase
import com.example.stepbystep.databinding.MainActivityBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Variáveis de layout e navegação
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var navController : NavController

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
        val dao = AppDatabase.getInstance(applicationContext).receitaDAO()

        if (!pediuPermissao) {
            pedirPermissao.launch(Manifest.permission.CAMERA)
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        with (binding) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_detalhesReceita) {
                menuInflater.inflate(R.menu.criador, menu)
            } else {
                menu.clear()
                menuInflater.inflate(R.menu.main, menu)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_save -> {
            Snackbar.make(binding.root, "Salvar", Snackbar.LENGTH_LONG).show()
            true
        }

        R.id.action_delete -> {
            Snackbar.make(binding.root, "Deletar", Snackbar.LENGTH_LONG).show()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun mostraAlerta(permissao: String, nome: String) {
        /**
         * Mostrar um alerta quando uma permissão é negada, além de avisar
         * sobre a necessidade da permissão e gerar o prompt para pedir de novo.
         *
         * @param permissao A permissao que foi negada, de forma a lançar a requisição de novo
         * com esse parâmetro
         *
         * @param nome O nome que será usado para identificar a permissão na mensagem do alerta.
         */

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