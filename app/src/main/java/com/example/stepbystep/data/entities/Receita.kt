package com.example.stepbystep.data.entities

import androidx.room.*
import com.example.stepbystep.data.Conversores
import kotlinx.serialization.Serializable

/**
 * Classe de modelo para uma receita.
 */

@Serializable
@Entity(tableName = "receitas")

data class Receita(
    @PrimaryKey @ColumnInfo(name = "id") val codigo: String,
    val nome: String,
    val uriFoto: String = "",
    val tempoPreparoMilis: Long,
    val tempoCozimentoMilis: Long,
    val tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,
    val porcoes: Int,
)