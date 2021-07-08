package com.example.stepbystep.data.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * Classe de modelo dos ingredientes.
 * Por enquanto, eles não existem por si só, somente vinculados à uma receita.
 */

@Entity(
    tableName = "ingredientes",
    foreignKeys = [ForeignKey(
        entity = Receita::class,
        parentColumns = ["id"],
        childColumns = ["id_receita"],
        onDelete = CASCADE
        )],
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

    override fun equals(other: Any?): Boolean =
        (other is Ingrediente)
                && idReceita == other.idReceita
                && idIngrediente == idIngrediente

    override fun hashCode(): Int {
        var result = idReceita.hashCode()
        result = 31 * result + nome.hashCode()
        result = 31 * result + quantidade.hashCode()
        result = 31 * result + idIngrediente.hashCode()
        return result
    }
}

