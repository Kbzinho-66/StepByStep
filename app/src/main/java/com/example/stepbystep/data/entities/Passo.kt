package com.example.stepbystep.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Interface para poder trabalhar com uma lista de diferentes tipos de passos.
 */

@Entity(
    tableName = "passos",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
sealed interface Passo {
    val descricao: String
    var pronto: Boolean
    var ordem: Int
    val cronometrado: Boolean
}