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
    fun buscarReceitaPorCodigo(cod: String): Receita

    /**
     * Queries para procurar uma receita específica.
     * Por seus tipos serem [ReceitaIngredientesPassos], o Room já une
     * as 3 tabelas em uma só e retorna um objeto composto das 3.
     */
//    @Transaction
//    @Query("SELECT * FROM receitas WHERE id = :cod LIMIT 1")
//    fun buscarReceitaCompletaPorCodigo(cod: String): ReceitaIngredientesPassos
//
//    @Transaction
//    @Query("SELECT * FROM receitas WHERE nome = :nome LIMIT 1")
//    fun buscarReceitaCompletaPorNome(nome: String): ReceitaIngredientesPassos
//
//    @Transaction
//    @Query("SELECT * FROM passos WHERE id_receita = :cod")
//    fun buscarPassosReceita(cod: String): MutableList<Passo>

    // Métodos de conveniência
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirReceita(receita: Receita): Long

    @Delete
    fun deletarReceita(receita: Receita)

    @Update
    fun atualizarPassos(passos: MutableList<Passo>)
}