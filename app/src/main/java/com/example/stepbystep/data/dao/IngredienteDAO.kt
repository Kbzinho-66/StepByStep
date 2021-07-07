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
    fun buscarIngredientesReceita(cod: Long): MutableList<Ingrediente>

    @Insert
    fun inserirIngrediente(ingrediente: Ingrediente)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirIngredientes(ingredientes: MutableList<Ingrediente>)

    @Update
    fun atualizarIngrediente(ingrediente: Ingrediente)

    @Update
    fun atualizarIngredientes(ingredientes: MutableList<Ingrediente>)

    @Delete
    fun deletarIngrediente(ingrediente: Ingrediente)

}