package com.example.stepbystep.data.entities

import androidx.room.*

/**
 * Versão concreta de um passo que só tem um Booleano a mais para indicar se o
 * passo foi realizado ou não.
 * Um passo não existe por conta própria, somente vinculado a uma receita.
 */

@Entity(
    tableName = "passos",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
data class PassoNormal(

    @ColumnInfo(name = "id_receita") val idReceita: String,
    @ColumnInfo(name = "descricao_passo") override var descricao: String,
    @ColumnInfo(name = "realizado") override var pronto: Boolean = false,
    @ColumnInfo(name = "ordem") override var ordem: Int,
    @ColumnInfo(name = "cronometrado") override val cronometrado: Boolean = false,

    ) : Passo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idPasso: Long = 0
}
