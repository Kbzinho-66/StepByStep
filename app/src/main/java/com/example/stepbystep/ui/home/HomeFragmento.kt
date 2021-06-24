package com.example.stepbystep.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stepbystep.R
import com.example.stepbystep.databinding.HomeFragmentBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragmento : Fragment() {

    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        with (binding) {
            botaoAdicionarReceita.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_recipeCreator)
            }
            botaoLivroReceitas.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_gallery)
            }
            botaoProcurarIngrediente.setOnClickListener {
                Snackbar.make(binding.root, "Ainda não implementado", Snackbar.LENGTH_LONG).show()
                //TODO (Implementar tudo isso)
            }
            botaoProcurarTempo.setOnClickListener {
                Snackbar.make(binding.root, "Ainda não implementado", Snackbar.LENGTH_LONG).show()
                //TODO (Implementar tudo isso)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}