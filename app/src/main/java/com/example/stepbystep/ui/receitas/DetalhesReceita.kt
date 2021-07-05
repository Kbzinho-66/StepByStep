package com.example.stepbystep.ui.receitas

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.stepbystep.R
import com.example.stepbystep.Utilidades
import com.example.stepbystep.data.dao.AppDatabase
import com.example.stepbystep.data.dao.ReceitaDAO
import com.example.stepbystep.data.entities.Receita
import com.example.stepbystep.databinding.DetalhesFragmentBinding
import com.google.android.material.snackbar.Snackbar

class DetalhesReceita : Fragment() {

    private var _binding: DetalhesFragmentBinding? = null
    private val binding get() = _binding!!

    private val db: ReceitaDAO by lazy {
        AppDatabase.getInstance(requireContext()).receitaDAO()
    }

    private val args: DetalhesReceitaArgs by navArgs()
    private var uriFoto: Uri? = null
    private lateinit var receita: Receita

    private val tirarFotoPrato =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
            if (ok) {

                Glide
                    .with(this)
                    .load(uriFoto)
                    .into(binding.fotoRC)

                binding.fotoRC.apply {
                    foreground = null
                    rotation = 90F
                }
            }
        }

    private val escolherFotoPrato =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                uriFoto = uri

                Glide
                    .with(this)
                    .load(uriFoto)
                    .into(binding.fotoRC)

                binding.fotoRC.foreground = null
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DetalhesFragmentBinding.inflate(inflater, container, false)

        var codigoReceita = args.codigoReceita

        receita = if (codigoReceita == -1L) {
            Receita().also {
                codigoReceita = it.codigo
            }
        } else {
            db.buscarReceitaPorCodigo(codigoReceita)
        }

        with(binding) {
            // Setar os listeners dos botões
            fotoRC.setOnClickListener {
                registerForContextMenu(fotoRC)
                mostrarMenu(fotoRC)
            }

            botaoIngredientesRC.setOnClickListener {
                val acaoIngredientes = DetalhesReceitaDirections
                    .actionNavNovaReceitaToNavListaIngredientes(codigoReceita)
                findNavController().navigate(acaoIngredientes)
            }

            botaoPassosRC.setOnClickListener {
                val acaoPassos = DetalhesReceitaDirections
                    .actionNavNovaReceitaToPassosFragmento(codigoReceita)
                findNavController().navigate(acaoPassos)
            }

        }

        if (args.codigoReceita != -1L) {

            // Atualizar os campos de dados se a receita não for null
            with(binding) {

                Glide.with(this@DetalhesReceita).load(receita.uriFoto).into(fotoRC)
                (entradaNome as TextView).text = receita.nome
                (entradaTempoPreparo as TextView).text =
                    Utilidades.millisToString(receita.tempoPreparoMilis)
                (entradaTempoCozimento as TextView).text =
                    Utilidades.millisToString(receita.tempoCozimentoMilis)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_save -> {
            salvarReceita()
            true
        }
        R.id.action_delete -> {
            deletarReceita()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun salvarReceita() {
        with (binding) {
            receita.nome = entradaNome.text.toString()
            receita.uriFoto = uriFoto.toString()
            receita.tempoPreparoMilis = entradaTempoPreparo.text.toString().toLong()
            receita.tempoCozimentoMilis = entradaTempoCozimento.text.toString().toLong()
        }
        db.inserirReceita(receita)
        Snackbar.make(binding.root,"Receita Salva!", Snackbar.LENGTH_LONG).show()
    }

    fun deletarReceita() {
        db.deletarReceita(receita)
        Snackbar.make(binding.root,"Receita Deletada!", Snackbar.LENGTH_LONG).show()
    }

    private fun mostrarMenu(view: View) {

        PopupMenu(context, view).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_tirar_foto -> {
                        Utilidades.criaArquivo(requireContext())?.let {
                            uriFoto = it
                            tirarFotoPrato.launch(it)
                        }
                        true
                    }
                    R.id.action_pegar_galeria -> {
                        escolherFotoPrato.launch("image/*")
                        true
                    }
                    else -> false
                }
            }

            menuInflater.inflate(R.menu.menu_contexto, menu)

            show()
        }
    }

}