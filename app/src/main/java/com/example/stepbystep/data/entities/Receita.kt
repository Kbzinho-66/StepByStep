package com.example.stepbystep.data.entities

import androidx.room.*
import kotlinx.serialization.Serializable

/**
 * Classe de modelo para uma receita.
 */

@Serializable
@Entity(tableName = "receitas")

data class Receita(
    var nome: String = "",
    var uriFoto: String = "",
    var tempoPreparoMilis: Long = 0,
    var tempoCozimentoMilis: Long = 0,
    var tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,
    var porcoes: Int = 0,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var codigo: Long = 0
}