package data

import model.Game

interface GamePersistence {

    fun update(game: Game) : Game

    fun insert(game: Game) : Game

    fun getById(id: Int) : Game

    fun getAll() : Collection<Game>

    fun getAllOrderByGameStartTime() : Collection<Game>

}
