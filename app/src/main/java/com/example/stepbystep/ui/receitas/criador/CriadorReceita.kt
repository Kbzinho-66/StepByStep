package com.example.stepbystep.ui.receitas.criador

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stepbystep.R
import com.example.stepbystep.databinding.CriadorFragmentBinding
import com.google.android.material.snackbar.Snackbar

class CriadorReceita : Fragment() {

    private var _binding: CriadorFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // TODO Definir isso aqui
//    private val escolherFotoPrato =
//        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
//            if (ok) {
//                Glide
//                    .with(this)
//            }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CriadorFragmentBinding.inflate(inflater, container, false)

        with (binding) {

            fotoRC.setOnClickListener {
                Snackbar.make(binding.root, "Tirar foto/Pegar da galeria", Snackbar.LENGTH_LONG).show()
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

}