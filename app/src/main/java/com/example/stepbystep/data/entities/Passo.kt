package com.example.stepbystep.data.entities

/**
 * Interface para poder trabalhar com uma lista de diferentes tipos de passos
 */

sealed interface Passo {
    val descricao: String
    var pronto: Boolean
    var ordem: Int
    val cronometrado: Boolean
}