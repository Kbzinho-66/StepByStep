package com.example.stepbystep.data.dao

import androidx.room.*
import com.example.stepbystep.data.entities.Passo

/**
 * DAO pra acesso à tabela de [Passo]
 */

@Dao
interface PassoDAO {

    @Transaction
    @Query("SELECT * FROM passos WHERE id_receita = :cod ORDER BY ordem")
    fun buscarPassosReceita(cod: Long): MutableList<Passo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirPassos(passos: MutableList<Passo>)

    @Update
    fun atualizarPassos(passos: MutableList<Passo>)

    @Delete
    fun deletarPasso(passo: Passo)

}