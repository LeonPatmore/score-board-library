package services

import model.Game
import model.Summary

interface GameService {

    fun startGame(homeTeam: String, awayTeam: String) : Game

    fun finishGame(id: Int) : Game

    fun updateScoreForHomeAndAway(id: Int, homeScore: Int, awayScore: Int)

    fun updateHomeScore(id: Int, homeScore: Int)

    fun updateAwayScore(id: Int, awayScore: Int)

    fun getInProgressSummary(): Summary

}
