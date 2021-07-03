package com.example.stepbystep.data

import androidx.room.*
import kotlinx.serialization.Serializable

/**
 * Classe de modelo para uma receita.
 */

@TypeConverters(Conversores::class)
@Serializable
@Entity(tableName = "receitas")

data class Receita (
    @PrimaryKey @ColumnInfo(name = "id") val codigo: String,
    val nome: String,
    val uriFoto: String = "",
    val tempoPreparoMilis: Long,
    val tempoCozimentoMilis: Long,
    val tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,
    val porcoes: Int,
)