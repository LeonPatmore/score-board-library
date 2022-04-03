package data

import model.Game
import model.GameStatus
import model.TeamScore

interface GamePersistence {

    fun update(game: Game) : Game

    fun insertWithHomeAndAwayAndGameStatus(home: TeamScore, away: TeamScore, gameStatus: GameStatus) : Game

    fun getById(id: Int) : Game

    fun getAll() : Collection<Game>

    fun getAllInProgressOrderByIdDescending() : List<Game>

}
