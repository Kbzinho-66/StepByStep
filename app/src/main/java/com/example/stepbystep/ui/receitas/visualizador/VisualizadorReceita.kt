package com.example.stepbystep.ui.receitas.visualizador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stepbystep.data.AppDatabase
import com.example.stepbystep.data.ReceitaDAO
import com.example.stepbystep.databinding.VisualizadorFragmentBinding

class VisualizadorReceita : Fragment() {

    private var _binding: VisualizadorFragmentBinding? = null
    private val binding get() = _binding!!

    private val dao: ReceitaDAO by lazy {
        requireContext().let { AppDatabase.getInstance(it).receitaDAO() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = VisualizadorFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}