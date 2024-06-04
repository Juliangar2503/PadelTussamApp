package com.backtussam.repositories.email

import com.backtussam.security.JWTConfig
import com.backtussam.services.email.EmailService
import com.backtussam.services.player.PlayerService
import com.backtussam.utils.BaseResponse

class EmailRepositoryImpl(
    private val emailService: EmailService,
    private val playerService: PlayerService
) : EmailRepository {
    override suspend fun sendEmail(email: String, subject: String, message: String): BaseResponse<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmails(emails: List<String>, subject: String, message: String): BaseResponse<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String): BaseResponse<Any> {
        // Buscar al jugador por su correo electrónico
        val player = playerService.findPlayerByEmail(email)
        if (player != null) {
            // Generar un token único para la recuperación de contraseña
            val resetToken = JWTConfig.instance.createToken(player.id.toString())
            player.authToken = resetToken
            // Crear el enlace de restablecimiento de contraseña
            val resetLink = "https://yourwebsite.com/reset-password?token=$resetToken"
            // Crear el mensaje de correo electrónico
            val message = """
            <p>Para restablecer tu contraseña, por favor haz clic en el siguiente enlace:</p>
            <a href="$resetLink">Restablecer contraseña</a>
            <p>Si no has solicitado un restablecimiento de contraseña, por favor ignora este correo electrónico.</p>
        """.trimIndent()
            // Enviar el correo electrónico
            emailService.sendEmail(email, "Restablecimiento de contraseña", message)
            return BaseResponse.SuccessResponse(
                true,
                "Se ha enviado un correo electrónico con las instrucciones para restablecer tu contraseña."
            )
        }
        return BaseResponse.ErrorResponse(
            null,
            "No se ha encontrado un jugador con el correo electrónico proporcionado."
        )
    }
}