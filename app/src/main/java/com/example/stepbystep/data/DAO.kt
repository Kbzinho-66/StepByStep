package com.example.stepbystep.data

import androidx.room.*
import com.example.stepbystep.data.Receita

/**
 * Interface de acesso ao banco de dados para a classe [Receita].
 */

@Dao
interface DAO {

    // Queries para RecyclerViews
    @Query("SELECT * FROM receitas")
    fun buscarTodasReceitas(): MutableList<Receita>

    @Query("SELECT uriFoto FROM receitas")
    fun buscarTodasFotos(): MutableList<String>

    @Query("SELECT * FROM receitas WHERE id = :cod LIMIT 1")
    fun buscarReceitaPorCodigo(cod: String): Receita

    /**
     * Queries para procurar uma receita específica.
     * Por seus tipos serem [ReceitaIngredientesPassos], o Room já une
     * as 3 tabelas em uma só e retorna um objeto composto das 3.
     */
    @Transaction
    @Query("SELECT * FROM receitas WHERE id = :cod LIMIT 1")
    fun buscarReceitaCompletaPorCodigo(cod: String): ReceitaIngredientesPassos

    @Transaction
    @Query("SELECT * FROM receitas WHERE nome = :nome LIMIT 1")
    fun buscarReceitaCompletaPorNome(nome: String): ReceitaIngredientesPassos

    /**
     * Queries pra buscar ingredientes e passos de uma receita específica.
     */
    @Transaction
    @Query("SELECT * FROM ingredientes WHERE id_receita = :cod")
    fun buscarIngredientesReceita(cod: String): MutableList<Ingrediente>

    @Transaction
    @Query("SELECT * FROM passos_normais WHERE id_receita = :cod")
    fun buscarPassosReceita(cod: String): MutableList<Passo>

    // Métodos de conveniência
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirReceita(receita: Receita): Long

    @Delete
    fun deletarReceita(receita: Receita)

    @Update
    fun atualizarIngredientes(ingredientes: MutableList<Ingrediente>)

    @Update
    fun atualizarPassos(passos: MutableList<Passo>)
}