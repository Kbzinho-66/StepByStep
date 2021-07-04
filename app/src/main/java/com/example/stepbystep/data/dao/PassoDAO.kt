package com.example.stepbystep.data.dao

import androidx.room.*
import com.example.stepbystep.data.entities.Passo

/**
 * DAO pra acesso à tabela de [Passo]
 */

@Dao
interface PassoDAO {

    @Transaction
    @Query("SELECT * FROM passos WHERE id_receita = :cod")
    fun buscarPassosReceita(cod: String): MutableList<Passo>

    @Update
    fun atualizarPassos(passos: MutableList<Passo>)

    @Delete
    fun deletarPasso(cod: Long)
}