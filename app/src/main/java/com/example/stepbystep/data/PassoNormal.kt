package com.example.stepbystep.data

import androidx.room.*

/**
 * Versão concreta de um passo que só tem um Booleano a mais para indicar se o
 * passo foi realizado ou não.
 * Um passo não existe por conta própria, somente vinculado a uma receita.
 */

@Entity(
    tableName = "passos_normais",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
data class PassoNormal(

    //TODO (Colocar um indicador da ordem dos passos)

    // O id da receita à qual esse passo está vinculado
    @ColumnInfo(name = "id_receita") val idReceita: String,

    @ColumnInfo(name = "descricao_passo") override var descricao: String,
    @Ignore override var pronto: Boolean = false,

) : PassoAbstrato(descricao, pronto) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idPasso: Long = 0

}
