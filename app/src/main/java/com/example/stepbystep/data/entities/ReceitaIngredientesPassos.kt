package com.example.stepbystep.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Classe que representa uma receita completa.
 * É responsável por mapear uma lista de [Ingrediente] e uma de [Passo]
 * para uma [Receita].
 */

data class ReceitaIngredientesPassos(

    @Embedded
    val receita: Receita,

    @Relation(parentColumn = "id", entityColumn = "id_receita")
    val ingredientes: MutableList<Ingrediente> = mutableListOf(),

    @Relation(parentColumn = "id", entityColumn = "id_receita")
    val passos: List<Passo> = emptyList()
)
