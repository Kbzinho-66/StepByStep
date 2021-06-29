package com.example.stepbystep.model

/**
 * Versão concreta de um passo, somente com um booleano que diz se o passo já foi realizado.
 */

data class PassoNormal(
    var pronto: Boolean,
    override var descricao: String,
) : PassoAbstrato(descricao)
