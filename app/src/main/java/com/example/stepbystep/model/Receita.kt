package com.example.stepbystep.model

import kotlinx.serialization.Serializable

/**
 * Classe que agrega todas as informações de uma receita.
 * @property uriFoto O uri da foto, convertido para String. Se for null,
 * uma imagem ainda não foi associada a essa receita.
 * @property codigoIngredientes Código da tabela de ingredientes guardada no SQLite.
 * @property codigoPassos Código da tabela que contém os passos da receita no SQLite.
 * @property tempoPreparoMilis Tempo levado para preparar os ingredientes em milissegundos.
 * @property tempoCozimentoMilis Tempo levado exclusivamente para cozinhar ou assar, também em milissegundos.
 * @property porcoes Quantas porções rende a receita
 */

@Serializable
data class Receita (
    val nome: String = "",
    val uriFoto: String? = null,
    val codigoIngredientes: Int = 0,
    val codigoPassos: Int = 0,
    val tempoPreparoMilis: Long = 0,
    val tempoCozimentoMilis: Long = 0,
    val tempoTotalMilis: Long = tempoPreparoMilis + tempoCozimentoMilis,
    val porcoes: Int = 0,
)
