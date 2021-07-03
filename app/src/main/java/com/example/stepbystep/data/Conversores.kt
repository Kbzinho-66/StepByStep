package com.example.stepbystep.data

import androidx.room.TypeConverter
import com.example.stepbystep.data.Ingrediente
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

/**
 * Classe que contém conversores que o Room possa precisar.
 * Por enquanto, não precisa de nenhum desses
 */

class Conversores {

    @TypeConverter
    fun fromList(value: MutableList<Ingrediente>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Ingrediente>>(value)

    @TypeConverter
    fun millisToString(millis: Long) =
        String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
}