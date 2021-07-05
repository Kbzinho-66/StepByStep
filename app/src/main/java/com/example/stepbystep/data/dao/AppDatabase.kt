package com.example.stepbystep.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stepbystep.data.entities.Ingrediente
import com.example.stepbystep.data.entities.Passo
import com.example.stepbystep.data.entities.Receita

/**
 * Classe responsável pelo acesso ao banco de dados usando Room.
 * Contém uma referência para o ReceitaDAO que será usado ao longo do aplicativo.
 */

@Database(
    entities = [Receita::class, Ingrediente::class, Passo::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun receitaDAO(): ReceitaDAO

    abstract fun ingredienteDAO(): IngredienteDAO

    abstract fun passoDAO(): PassoDAO

    companion object : SingletonHolder<AppDatabase, Context>({
        Room.databaseBuilder(
            it.applicationContext, AppDatabase::class.java, "receitas.db"
        ).run{
            fallbackToDestructiveMigration()
            allowMainThreadQueries() // TODO (Refatorar pra usar coroutines)
            build()
        }
    })
}

open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}