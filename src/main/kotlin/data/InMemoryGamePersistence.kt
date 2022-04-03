package data

import data.exceptions.GameIdAlreadyExistsException
import data.exceptions.GameNotFoundException
import model.Game
import model.GameStatus
import model.TeamScore

class InMemoryGamePersistence : GamePersistence {

    private val gameMap: HashMap<Int, Game> = HashMap()
    private var counter: Int = 0

    override fun update(game: Game): Game {
        if (!gameMap.containsKey(game.id)) {
            throw GameNotFoundException(game.id)
        }
        gameMap[game.id] = game
        return gameMap[game.id]!!
    }

    override fun insertWithHomeAndAwayAndGameStatus(home: TeamScore, away: TeamScore, gameStatus: GameStatus): Game {
        counter = counter.inc()
        val id = counter
        if (gameMap.containsKey(id)) {
            // Should never happen.
            throw GameIdAlreadyExistsException(id)
        }
        gameMap[id] = Game(id = id, home = home, away = away, gameStatus = gameStatus)
        return gameMap[id]!!
    }

    override fun getById(id: Int): Game {
        return gameMap[id] ?: throw GameNotFoundException(id)
    }

    override fun getAll(): Collection<Game> {
        return gameMap.values
    }

    override fun getAllInProgressOrderByIdDescending(): List<Game> {
        return gameMap.values.filter { it.gameStatus == GameStatus.IN_PROGRESS }.sortedByDescending { it.id }
    }

}
