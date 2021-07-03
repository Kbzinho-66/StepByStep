package com.example.stepbystep.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Classe responsável pelo acesso ao banco de dados usando Room.
 * Contém uma referência para o DAO que será usado ao longo do aplicativo.
 */

@Database(entities = [Receita::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun receitaDAO(): DAO

    companion object : SingletonHolder<AppDatabase, Context>( {
        Room.databaseBuilder(it.applicationContext
            , AppDatabase::class.java
            , "receitas.db").build()
    } )
}

open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

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