package com.example.stepbystep.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stepbystep.dao.Converters
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@TypeConverters(Converters::class)
@Serializable
@Entity(tableName = "receitas")
data class Receita (
    @PrimaryKey val codigo: Int = 0,
    val nome: String = "",
    val uriFoto: String? = null,
    val ingredientes: ArrayList<String>, // TODO (Refatorar pra usar a classe Ingredientes)
    val passos: MutableList<String>, // TODO (Refatorar pra usar as classes de Passos)
    val tempoPreparoMilis: Long = 0,
    val tempoCozimentoMilis: Long = 0,
    val tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,
    val porcoes: Int = 0,
)