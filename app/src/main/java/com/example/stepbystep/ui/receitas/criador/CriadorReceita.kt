package com.example.stepbystep.ui.receitas.criador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stepbystep.databinding.CriadorFragmentBinding

class CriadorReceita : Fragment() {

    private var _binding: CriadorFragmentBinding? = null
//    private val fm: FragmentManager = parentFragmentManager
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private lateinit var fragmentAtual: Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
        _binding = CriadorFragmentBinding.inflate(inflater, container, false)

//        binding.botaoIngredientesRC.setOnClickListener {
//            if (childFragmentManager.fragments.size == 0) {
//                fragmentAtual = IngredientsFragment()
//                fm.beginTransaction().also {
//                    it.add(R.id.containerView_recipeCreator, fragmentAtual, "Ingredientes")
//                    it.commit()
//                }
//            } else {
//                when (fragmentAtual) {
//                    is IngredientsFragment -> fm.beginTransaction().run {
//                        remove(fragmentAtual)
//                    }
//                    is StepsFragment -> fm.beginTransaction().also {
//                        fragmentAtual = StepsFragment()
//                        it.add(R.id.containerView_recipeCreator, fragmentAtual, "Passos")
//                        it.commit()
//                    }
//                }
//            }
//        }
//
//        binding.botaoPassosRC.setOnClickListener {
//            if (childFragmentManager.fragments.size == 0) {
//                fragmentAtual = StepsFragment()
//                fm.beginTransaction().also {
//                    it.add(R.id.containerView_recipeCreator, fragmentAtual, "Passos")
//                    it.commit()
//                }
//            } else {
//                when (fragmentAtual) {
//                    is StepsFragment -> fm.beginTransaction().run {
//                        remove(fragmentAtual)
//                    }
//                    is IngredientsFragment -> fm.beginTransaction().also {
//                        fragmentAtual = IngredientsFragment()
//                        it.add(R.id.containerView_recipeCreator, fragmentAtual, "Ingredientes")
//                        it.commit()
//                    }
//                }
//            }
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}