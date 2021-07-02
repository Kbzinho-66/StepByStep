package com.example.stepbystep.data

import androidx.room.Ignore

/**
 * Interface para poder trabalhar com uma lista de diferentes tipos de passos
 */

sealed interface Passo

/**
 * Uma versão abstrata de um passo, com o modificador sealed para mostrar que só todos os
 * possíveis tipos de passos são conhecidos, evitando a necessidade do 'else' quando usar when(Passo)
 */

sealed class PassoAbstrato(
    @Ignore open val descricao: String,
    @Ignore open var pronto: Boolean,
//    open val imagens: MutableList<Uri> = mutableListOf()
) : Passo
