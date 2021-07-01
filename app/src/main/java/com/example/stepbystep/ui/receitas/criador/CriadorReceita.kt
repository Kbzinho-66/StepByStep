package com.example.stepbystep.ui.receitas.criador

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.stepbystep.R
import com.example.stepbystep.databinding.CriadorFragmentBinding
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CriadorReceita : Fragment(){


    private var _binding: CriadorFragmentBinding? = null
    private val binding get() = _binding!!

    private var uriFoto: Uri? = null

    // TODO Definir isso aqui
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

        _binding = CriadorFragmentBinding.inflate(inflater, container, false)

        with (binding) {

            fotoRC.setOnClickListener {
                registerForContextMenu(fotoRC)
                mostrarMenu(fotoRC)
                // TODO Criar um Uri pra receita e passar aqui no launch()
            }

            botaoIngredientesRC.setOnClickListener {
                findNavController().navigate(R.id.action_nav_novaReceita_to_nav_listaIngredientes)
            }

            botaoPassosRC.setOnClickListener {
                findNavController().navigate(R.id.action_nav_novaReceita_to_passosFragmento)
            }

            return root
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

}