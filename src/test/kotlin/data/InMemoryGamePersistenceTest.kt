package data

import data.exceptions.GameNotFoundException
import model.Game
import model.GameStatus
import model.TeamScore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class InMemoryGamePersistenceTest {

    private lateinit var inMemoryGamePersistence: InMemoryGamePersistence

    @BeforeEach
    internal fun setUp() {
        inMemoryGamePersistence = InMemoryGamePersistence()
    }

    @Test
    internal fun testGetById_whenNoGame_throwsException() {
        assertThrows<GameNotFoundException> { inMemoryGamePersistence.getById(6) }
    }

    @Test
    internal fun testInsert_generatesId() {
        val game = inMemoryGamePersistence.insertWithHomeAndAwayAndGameStatus(
            home = TeamScore("team 1", 0),
            away = TeamScore("team 2", 3),
            gameStatus = GameStatus.IN_PROGRESS)

        assertNotNull(game.id)
    }

    @Test
    internal fun testUpdate() {
        val game = insertGame()
        val updatedGame = inMemoryGamePersistence.update(game.copy(gameStatus = GameStatus.FINISHED))

        assertEquals(GameStatus.FINISHED, updatedGame.gameStatus)
    }

    @Test
    internal fun testUpdate_whenNoGameExists_throwsException() {
        assertThrows<GameNotFoundException> { inMemoryGamePersistence.update(Game(
            id = 1,
            home = TeamScore("team 1", 0),
            away = TeamScore("team 2", 3),
            gameStatus = GameStatus.IN_PROGRESS)) }
    }

    @Test
    internal fun testGetAllInProgressOrderById() {
        val game1 = insertGame()
        val game2 = insertGame()

        val games = inMemoryGamePersistence.getAllInProgressOrderByIdDescending()
        assertEquals(game2, games[0])
        assertEquals(game1, games[1])
    }

    private fun insertGame(): Game {
        return inMemoryGamePersistence.insertWithHomeAndAwayAndGameStatus(
            home = TeamScore("team 1", 0),
            away = TeamScore("team 2", 3),
            gameStatus = GameStatus.IN_PROGRESS)
    }

}
