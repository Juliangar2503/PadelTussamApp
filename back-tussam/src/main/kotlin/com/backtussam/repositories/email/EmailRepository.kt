package com.backtussam.repositories.email

import com.backtussam.utils.BaseResponse

interface EmailRepository {
    suspend fun sendEmail(email: String, subject: String, message: String): BaseResponse<Any>
    suspend fun sendEmails(emails: List<String>, subject: String, message: String): BaseResponse<Any>
    suspend fun resetPassword(email: String): BaseResponse<Any>
}