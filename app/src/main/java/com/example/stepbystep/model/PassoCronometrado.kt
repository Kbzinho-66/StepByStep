package com.example.stepbystep.model


/**
 * Versão concreta de um passo, que contém uma duração usada nos cronômetros.
 */

data class PassoCronometrado(
    var pronto: Boolean = false,
    var duracao: Long = 0,
    override val descricao: String,
) : PassoAbstrato(descricao)
