package com.example.stepbystep.data.entities

import androidx.room.*


@Entity(
    tableName = "passos",
    foreignKeys = [
        ForeignKey(entity = Receita::class, parentColumns = ["id"], childColumns = ["id_receita"])
    ],
    indices = [Index("id_receita")]
)
data class Passo(

    @ColumnInfo(name = "id_receita") val idReceita: String,
    @ColumnInfo(name = "descricao_passo") var descricao: String,
    @ColumnInfo(name = "realizado") var pronto: Boolean,
    @ColumnInfo(name = "ordem") var ordem: Int,
    @ColumnInfo(name = "duracao") var duracao: Long,
    @ColumnInfo(name = "cronometrado") val cronometrado: Boolean = true,

    ) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idPasso: Long = 0
}
