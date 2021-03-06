package com.example.stepbystep.data.entities

import androidx.room.*
import kotlinx.serialization.Serializable
import java.time.Duration

/**
 * Classe de modelo que contém as características básicas de uma receita.
 *
 * Não se fez necessário até agora manter uma referência aos ingredientes
 * e passos de execução.
 */

@Serializable
@Entity(tableName = "receitas")

data class Receita(
    var nome: String = "",
    var uriFoto: String = "", // Caminho pra foto no celular

    // TODO (Mudar isso pra um formato mais utilizável)
    var tempoPreparoMilis: Long = 0,
    var tempoCozimentoMilis: Long = 0,
    var tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,

    var porcoes: Int = 0,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var codigo: Long = 0
}