package com.example.stepbystep.data.entities

import androidx.room.*

/**
 * Classe de modelo para um passo no modo de fazer de uma receita.
 *
 * Inicialmente foi pensada como uma implementação de uma interface abstrata,
 * pra facilitar o polimorfismo entre um passo normal e um cronometrado, mas
 * o Room não lidava muito bem com isso, então virou uma entidade só.
 */

@Entity(
    tableName = "passos",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
data class Passo(

    // Características de ambos tipos de passos
    @ColumnInfo(name = "id_receita") val idReceita: Long,
    @ColumnInfo(name = "descricao_passo") var descricao: String = "",
    @ColumnInfo(name = "realizado") var pronto: Boolean = false,
    @ColumnInfo(name = "ordem") var ordem: Int = 0,

    // Características de passos cronometrados
    @ColumnInfo(name = "duracao") var duracao: Long = 0,
    @ColumnInfo(name = "cronometrado") val cronometrado: Boolean = false,

    ) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idPasso: Long = 0
}
