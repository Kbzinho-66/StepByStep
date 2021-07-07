package com.example.stepbystep.data.dao

import androidx.room.*
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.data.entities.Receita
//import com.example.stepbystep.data.entities.ReceitaIngredientesPassos

/**
 * Interface de acesso ao banco de dados para a classe [Receita].
 */

@Dao
interface ReceitaDAO {

    @Query("SELECT * FROM receitas")
    fun buscarTodasReceitas(): MutableList<Receita>

    @Query("SELECT * FROM receitas WHERE id = :cod LIMIT 1")
    fun buscarReceitaPorCodigo(cod: Long): Receita

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirReceita(receita: Receita): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirReceitas(receitas: MutableList<Receita>)

    @Delete
    fun deletarReceita(receita: Receita)

    @Update
    fun atualizarPassos(passos: MutableList<Passo>)
}