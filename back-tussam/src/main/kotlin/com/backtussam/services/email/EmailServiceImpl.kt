package com.backtussam.services.email

import com.backtussam.security.JWTConfig
import com.resend.Resend
import com.resend.core.exception.ResendException
import com.resend.services.emails.model.CreateEmailOptions
import com.resend.services.emails.model.CreateEmailResponse

class EmailServiceImpl : EmailService {
    override suspend fun sendEmail(email: String, subject: String, message: String): CreateEmailResponse {
        val resend = Resend("re_KScrbY1u_62pY6oMyBf7tGmppC7whRM4r")
        val params = CreateEmailOptions.builder()
            .from("onboarding@resend.dev")
            .to(email)
            .subject(subject)
            .html(message)
            .build()

        return try {
            resend.emails().send(params)
        } catch (e: ResendException) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun sendEmails(emails: List<String>, subject: String, message: String): CreateEmailResponse {
        TODO("Not yet implemented")
    }

}