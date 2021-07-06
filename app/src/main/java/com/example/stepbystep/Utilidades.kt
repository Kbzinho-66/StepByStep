package com.example.stepbystep

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class Utilidades {

    companion object {

        fun stringToMillis(string: String): Long {
            //TODO (Implementar isso, ou refatorar pra Duration)
            return 0L
        }


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


        fun criaArquivo(contexto: Context): Uri? {

            val data = LocalDateTime.now()
            val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
            val timestamp = data.format(formato)


            val pasta = contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imagem = pasta?.let { it.path + File.separator + "${timestamp}.jpg" }
            val caminhoImagem: File? = imagem?.let { File(it) }


            return if (caminhoImagem != null) {
                FileProvider.getUriForFile(
                    contexto,
                    "${contexto.packageName}.provider",
                    caminhoImagem
                )
            } else null

        }
    }
}