package com.example.stepbystep.data


/**
 * Versão concreta de um passo, que contém uma duração usada nos cronômetros.
 * Por enquanto não é usada, no futuro, quem sabe.
 */

data class PassoCronometrado(
    override val descricao: String,
    override var pronto: Boolean = false,
    var duracao: Long = 0,
) : PassoAbstrato(descricao, pronto)
