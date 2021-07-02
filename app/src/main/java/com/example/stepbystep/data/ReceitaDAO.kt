package com.example.stepbystep.data

import androidx.room.*
import com.example.stepbystep.data.Receita

/**
 * Interface de acesso ao banco de dados para a classe [Receita].
 */

interface ReceitaDAO {

    // Queries para RecyclerViews
    @Query("SELECT * FROM receitas")
    fun buscarTodasReceitas(): MutableList<Receita>

    @Query("SELECT uriFoto FROM receitas")
    fun buscarTodasFotos(): MutableList<String>

    /**
     * Queries para procurar uma receita específica.
     * Por seus tipos serem [ReceitaIngredientesPassos], o Room já une
     * as 3 tabelas em uma só e retorna um objeto composto das 3.
     */
    @Transaction
    @Query("SELECT * FROM receitas WHERE id = :cod LIMIT 1")
    fun buscarReceitaPorCodigo(cod: Int): ReceitaIngredientesPassos

    @Transaction
    @Query("SELECT * FROM receitas WHERE nome = :nome LIMIT 1")
    fun buscarReceitaPorNome(nome: String): ReceitaIngredientesPassos

    // Outras funções
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirReceita(receita: Receita): Long

    @Update
    fun deletarReceita(receita: Receita)
}