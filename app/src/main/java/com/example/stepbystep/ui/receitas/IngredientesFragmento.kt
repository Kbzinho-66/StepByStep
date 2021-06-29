package com.example.stepbystep.ui.receitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.stepbystep.databinding.ReceitasFragmentRvIngredientesBinding

class IngredientesFragmento: Fragment() {

    private var _binding: ReceitasFragmentRvIngredientesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ReceitasFragmentRvIngredientesBinding.inflate(inflater, container, false)

        val rv: RecyclerView

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
