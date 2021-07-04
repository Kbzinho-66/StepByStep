package com.example.stepbystep.data.entities

import androidx.room.*


/**
 * Versão concreta de um passo, que contém uma duração usada nos cronômetros.
 * Por enquanto não é usada. No futuro, quem sabe.
 */

@Entity(
    tableName = "passos",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
data class PassoCronometrado(

    @ColumnInfo(name = "id_receita") val idReceita: String,
    @ColumnInfo(name = "descricao_passo") override var descricao: String,
    @ColumnInfo(name = "realizado") override var pronto: Boolean = false,
    @ColumnInfo(name = "ordem") override var ordem: Int,
    @ColumnInfo(name = "duracao") var duracao: Long,
    @ColumnInfo(name = "cronometrado") override val cronometrado: Boolean = true,

    ) : Passo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idPasso: Long = 0
}
