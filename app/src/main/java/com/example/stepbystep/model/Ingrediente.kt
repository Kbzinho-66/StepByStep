package com.example.stepbystep.model

/**
 * Classe base para representar um Ingrediente.
 * @property codigo Identificador numérico para o SQLite
 * @property ok Boolean usado na lista de ingredientes para saber se o usuário tem o ingrediente
 * @property quantidade String que representa a quantidade, por enquanto é totalmente aberta
 * ao que o usuário quiser escrever.
 */

data class Ingrediente(
    val codigo: Int,
    var ok: Boolean,
    val nome: String,
    var quantidade: String,
)

