package com.backtussam.services.match

import com.backtussam.model.Match

interface MatchService {
    fun getMatches(): List<Match?>
    fun getMatchByName(id: Int): Match?
    fun createMatch(match: Match): Match?
    fun updateMatch(match: Match): Match?
    fun deleteMatch(id: Int)
}