package com.backtussam.services.match

import com.backtussam.model.Match
import com.backtussam.utils.params.match.CreateMatchParams

interface MatchService {
    suspend fun getMatches(): List<Match?>
    suspend fun getMatchById(id: Int): Match?
    suspend fun createMatch(params:CreateMatchParams): Match?
    suspend fun updateMatch(id: Int, params:CreateMatchParams): Match?
    suspend fun deleteMatch(id: Int): Boolean
}