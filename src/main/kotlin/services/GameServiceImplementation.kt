package services

import data.GamePersistence
import model.Game
import model.GameStatus
import model.Summary
import model.TeamScore
import mu.KotlinLogging
import services.exceptions.GameAlreadyStartedException

private val logger = KotlinLogging.logger {}

class GameServiceImplementation(private val gamePersistence: GamePersistence) : GameService {

    override fun startGame(homeTeam: String, awayTeam: String): Game {
        logger.info { "Starting game for home team $homeTeam and away team $awayTeam" }
        ensureGameMakesSense(homeTeam, awayTeam)
        return gamePersistence.insertWithHomeAndAwayAndGameStatus(
            home = TeamScore(homeTeam),
            away = TeamScore(awayTeam),
            gameStatus = GameStatus.IN_PROGRESS)
    }

    override fun finishGame(id: Int): Game {
        logger.info { "Finishing game with id $id" }
        return gamePersistence.update(gamePersistence.getById(id).copy(gameStatus = GameStatus.FINISHED))
    }

    override fun updateScoreForHomeAndAway(id: Int, homeScore: Int, awayScore: Int): Game {
        logger.info { "Updating home and away score ($homeScore, $awayScore) for game $id" }
        val currentGame = gamePersistence.getById(id)
        return gamePersistence.update(currentGame.copy(
            home = currentGame.home.copy(score = homeScore),
            away = currentGame.away.copy(score = awayScore)))
    }

    override fun updateHomeScore(id: Int, homeScore: Int): Game {
        logger.info { "Updating home score ($homeScore) for game $id" }
        val currentGame = gamePersistence.getById(id)
        return gamePersistence.update(currentGame.copy(home = currentGame.home.copy(score = homeScore)))
    }

    override fun updateAwayScore(id: Int, awayScore: Int): Game {
        logger.info { "Updating away score ($awayScore) for game $id" }
        val currentGame = gamePersistence.getById(id)
        return gamePersistence.update(currentGame.copy(away = currentGame.away.copy(score = awayScore)))
    }

    override fun getInProgressSummary(): Summary {
        val games = gamePersistence.getAllInProgressOrderByIdDescending().sortedByDescending { it.totalScore() }
        return Summary(games)
    }

    private fun Game.totalScore() : Int {
        return this.away.score + this.home.score
    }

    private fun ensureGameMakesSense(homeTeam: String, awayTeam: String) {
        if (teamAlreadyPlaying(homeTeam) || teamAlreadyPlaying(awayTeam)) {
            throw GameAlreadyStartedException()
        }
    }

    private fun teamAlreadyPlaying(team: String) : Boolean {
        return gamePersistence.getWhereHomeTeamAndInProgress(team).isNotEmpty() ||
                gamePersistence.getWhereAwayTeamAndInProgress(team).isNotEmpty()
    }

}
