package com.backtussam.repositories.court

import com.backtussam.services.court.CourtService
import com.backtussam.utils.BaseResponse
import com.backtussam.utils.params.court.CreateCourtParams

class CourtRepositoryImpl(
    private val courtService: CourtService
) : CourtRepository {
    override suspend fun getCourts(): BaseResponse<Any> {
        val courts = courtService.getCourts()
        return if (courts.isNotEmpty()) {
            // Pistas encontradas
            BaseResponse.SuccessResponse(data = courts)
        } else {
            // No hay pìstas
            BaseResponse.ErrorResponse(message = "No courts found")
        }
    }

    override suspend fun getCourtByName(name: String): BaseResponse<Any> {
        // Comprobar si la pista existe
        if (isCourtExist(name)) {
            val court = courtService.getCourtByName(name)
            return BaseResponse.SuccessResponse(data = court)
        } else {
            return BaseResponse.ErrorResponse(message = "Court not found")
        }
    }

    override suspend fun getCourtById(id: Int): BaseResponse<Any> {
        val court = courtService.getCourtById(id)
        return if (court != null) {
            // Pista encontrada
            BaseResponse.SuccessResponse(data = court)
        } else {
            // Pista no encontrada
            BaseResponse.ErrorResponse(message = "Court not found")
        }
    }

    override suspend fun createCourt(name:String): BaseResponse<Any> {


            return if (isCourtExist(name)) {
                BaseResponse.ErrorResponse(message = "Court already registered")
            } else {
                // Registrar pista
                val court = courtService.createCourt(name)
                if (court != null) {
                    // Responder con la pista creada
                    BaseResponse.SuccessResponse(data = court)
                } else {
                    BaseResponse.ErrorResponse(message = "Error creating court")
                }
            }

    }

    override suspend fun updateCourt(name: String, params: CreateCourtParams): BaseResponse<Any> {
        return if (isCourtExist(name)) {
            // Actualizar pista
            val court = courtService.updateCourt(name, params)
            BaseResponse.SuccessResponse(data = court)
        } else {
            BaseResponse.ErrorResponse(message = "Court not found")
        }
    }

    override suspend fun deleteCourt(name: String): BaseResponse<Any> {
        return if (isCourtExist(name)) {
            // Eliminar liga
            val deleted = courtService.deleteCourt(name)
            if (deleted) {
                // Liga eliminada
                BaseResponse.SuccessResponse(data = "Court deleted")
            } else {
                // Error al eliminar liga
                BaseResponse.ErrorResponse(message = "Error deleting Court")
            }
        } else {
            // Liga no encontrada
            BaseResponse.ErrorResponse(message = "Court not found")
        }
    }

    //Primero comprobar si la pista existe
    private suspend fun isCourtExist(name: String): Boolean {
        return courtService.getCourtByName(name) != null
    }


}