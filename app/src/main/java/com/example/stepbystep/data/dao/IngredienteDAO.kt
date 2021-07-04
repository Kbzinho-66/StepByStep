package com.example.stepbystep.data.dao

import androidx.room.*
import com.example.stepbystep.data.entities.Ingrediente

/**
 * DAO pra acesso à tabela de [Ingrediente]
 */

@Dao
interface IngredienteDAO {

    @Transaction
    @Query("SELECT * FROM ingredientes WHERE id_receita = :cod")
    fun buscarIngredientesReceita(cod: String): MutableList<Ingrediente>

    @Update
    fun atualizarIngredientes(ingredientes: MutableList<Ingrediente>)

    @Delete
    fun deletarIngrediente(cod: Long)
}