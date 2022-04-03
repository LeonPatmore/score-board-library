package model


data class Game(val id: Int,
                val home: TeamScore,
                val away: TeamScore,
                val gameStartTime: Int,
                val gameStatus: GameStatus = GameStatus.IN_PROGRESS)


data class TeamScore(val teamName: String, val score: Int = 0)


enum class GameStatus {
    NOT_STARTED,
    IN_PROGRESS,
    FINISHED
}
