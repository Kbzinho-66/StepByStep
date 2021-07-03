package com.example.stepbystep.ui.receitas

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.stepbystep.R
import com.example.stepbystep.data.AppDatabase
import com.example.stepbystep.data.DAO
import com.example.stepbystep.data.Receita
import com.example.stepbystep.databinding.DetalhesFragmentBinding
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class DetalhesReceita : Fragment(){


    private var _binding: DetalhesFragmentBinding? = null
    private val binding get() = _binding!!

    private val db: DAO by lazy {
        AppDatabase.getInstance(requireContext()).receitaDAO()
    }
    private val args: DetalhesReceitaArgs by navArgs()
    private var uriFoto: Uri? = null

    private val tirarFotoPrato =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
            if (ok) {

                Glide
                    .with(this)
                    .load(uriFoto)
                    .into(binding.fotoRC)

                binding.fotoRC.apply{
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

        val codigoReceita = args.codigoReceita

        val receita: Receita? = if (codigoReceita == "null") {
            null
        } else {
            db.buscarReceitaPorCodigo(codigoReceita)
        }

        if (receita != null) {
            with (binding) {

                // Atualizar os campos de dados
                Glide.with(this@DetalhesReceita).load(receita.uriFoto).into(fotoRC)
                (entradaNome as TextView).text = receita.nome
                (entradaTempoPreparo as TextView).text = millisToString(receita.tempoPreparoMilis)
                (entradaTempoCozimento as TextView).text = millisToString(receita.tempoCozimentoMilis)

                // Setar os listeners dos botões
                fotoRC.setOnClickListener {
                    registerForContextMenu(fotoRC)
                    mostrarMenu(fotoRC)
                }

                botaoIngredientesRC.setOnClickListener {
                    val acao = DetalhesReceitaDirections.actionNavNovaReceitaToNavListaIngredientes(codigoReceita)
                    findNavController().navigate(acao)
                }

                botaoPassosRC.setOnClickListener {
                    val acao = DetalhesReceitaDirections.actionNavNovaReceitaToPassosFragmento(codigoReceita)
                    findNavController().navigate(acao)
                }

                return root
            }
        } else {

            return binding.root
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mostrarMenu(view: View) {

        PopupMenu(context, view).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_tirar_foto -> {
                        criaArquivo()?.let {
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

    private fun criaArquivo(): Uri? {

        val contexto = requireActivity()

        val data = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
        val timestamp = data.format(formato)


        val pasta = contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imagem = pasta?.let { it.path + File.separator + "${timestamp}.jpg" }
        val caminhoImagem: File? = imagem?.let { File(it) }


        return if (caminhoImagem != null) {
                FileProvider.getUriForFile(
                    contexto,
                    "${contexto.packageName}.provider",
                    caminhoImagem)
        } else null

    }

    fun millisToString(millis: Long) =
        String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )

}