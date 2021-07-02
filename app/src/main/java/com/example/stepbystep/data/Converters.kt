package com.example.stepbystep.data

import androidx.room.TypeConverter
import com.example.stepbystep.data.Ingrediente
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Classe que contém conversores que o Room possa precisar.
 * Por enquanto, não precisa de nenhum desses
 */

class Converters {

    @TypeConverter
    fun fromList(value: MutableList<Ingrediente>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Ingrediente>>(value)
}