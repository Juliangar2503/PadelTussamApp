package com.backtussam.services.email

import com.resend.services.emails.model.CreateEmailResponse

interface EmailService {
    suspend fun sendEmail(email: String, subject: String, message: String): CreateEmailResponse
    suspend fun sendEmails(emails: List<String>, subject: String, message: String): CreateEmailResponse
}