package com.example.stepbystep.data.entities

import androidx.room.*

/**
 * Classe de modelo dos ingredientes.
 * Por enquanto, eles não existem por si só, somente vinculados à uma receita.
 */

@Entity(
    tableName = "ingredientes",
    foreignKeys = [
        ForeignKey(
            entity = Receita::class,
            parentColumns = ["id"],
            childColumns = ["id_receita"]
        )
    ],
    indices = [Index("id_receita")]
)
data class Ingrediente(

    // A receita à qual esse ingrediente está vinculado
    @ColumnInfo(name = "id_receita") val idReceita: Long,

    @ColumnInfo(name = "nome_ingrediente") var nome: String = "",

    // Quantidade do ingrediente TODO(Deixar mais controlado, com Enum talvez)
    @ColumnInfo(name = "quantidade_ingrediente") var quantidade: String = "",

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var idIngrediente: Long = 0

    // Um booleano para marcar se o usuário tem o ingrediente ou não
    @ColumnInfo(name = "possui") var ok: Boolean = false
}

