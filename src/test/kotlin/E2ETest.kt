import data.InMemoryGamePersistence
import model.GameStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import services.GameService
import services.GameServiceImplementation

internal class E2ETest {

    private lateinit var gameService: GameService

    @BeforeEach
    internal fun setUp() {
        gameService = GameServiceImplementation(InMemoryGamePersistence())
    }

    @Test
    internal fun `test inserting a game defaults to 0 - 0, including the summary`() {
        val game = gameService.startGame(TEAM_CANADA, TEAM_SPAIN)
        assertEquals(0, game.away.score)
        assertEquals(0, game.home.score)
        assertEquals(GameStatus.IN_PROGRESS, game.gameStatus)

        val summary = gameService.getInProgressSummary()
        assertEquals(1, summary.games.size)
        assertEquals(0, summary.games[0].home.score)
        assertEquals(0, summary.games[0].away.score)
    }

    @Test
    internal fun `test finish game removes game from the summary`() {
        val game = gameService.startGame(TEAM_CANADA, TEAM_SPAIN)

        gameService.finishGame(game.id)

        val summary = gameService.getInProgressSummary()
        assertEquals(0, summary.games.size)
    }

    @Test
    internal fun `test update score for both teams`() {
        val game = gameService.startGame(TEAM_CANADA, TEAM_SPAIN)

        val updatedGame = gameService.updateScoreForHomeAndAway(game.id, 2, 3)
        assertEquals(2, updatedGame.home.score)
        assertEquals(3, updatedGame.away.score)
    }

    @Test
    internal fun `test update score for home team`() {
        val game = gameService.startGame(TEAM_CANADA, TEAM_SPAIN)

        val updatedGame = gameService.updateHomeScore(game.id, 3)
        assertEquals(3, updatedGame.home.score)
        assertEquals(0, updatedGame.away.score)
    }

    @Test
    internal fun `test update score for away team`() {
        val game = gameService.startGame(TEAM_CANADA, TEAM_SPAIN)

        val updatedGame = gameService.updateAwayScore(game.id, 3)
        assertEquals(0, updatedGame.home.score)
        assertEquals(3, updatedGame.away.score)
    }

    @Test
    internal fun `test summary orders by total score`() {
        val mexicoCanadaGame = gameService.startGame(TEAM_MEXICO, TEAM_CANADA)
        gameService.updateScoreForHomeAndAway(mexicoCanadaGame.id, 0, 5)
        val spainBrazilGame = gameService.startGame(TEAM_SPAIN, TEAM_BRAZIL)
        gameService.updateScoreForHomeAndAway(spainBrazilGame.id, 10, 2)
        val germanyFranceGame = gameService.startGame(TEAM_GERMANY, TEAM_FRANCE)
        gameService.updateScoreForHomeAndAway(germanyFranceGame.id, 2, 2)
        val uruguayItalyGame = gameService.startGame(TEAM_URUGUAY, TEAM_ITALY)
        gameService.updateScoreForHomeAndAway(uruguayItalyGame.id, 6, 6)
        val argentinaAustraliaGame = gameService.startGame(TEAM_ARGENTINA, TEAM_AUSTRALIA)
        gameService.updateScoreForHomeAndAway(argentinaAustraliaGame.id, 3, 1)

        val summary = gameService.getInProgressSummary()

        assertEquals(uruguayItalyGame.id, summary.games[0].id)
        assertEquals(spainBrazilGame.id, summary.games[1].id)
        assertEquals(mexicoCanadaGame.id, summary.games[2].id)
        assertEquals(argentinaAustraliaGame.id, summary.games[3].id)
        assertEquals(germanyFranceGame.id, summary.games[4].id)
    }


    companion object {
        const val TEAM_CANADA = "canada"
        const val TEAM_SPAIN = "spain"
        const val TEAM_MEXICO = "mexico"
        const val TEAM_BRAZIL = "brazil"
        const val TEAM_GERMANY = "germany"
        const val TEAM_FRANCE = "france"
        const val TEAM_URUGUAY = "uruguay"
        const val TEAM_ITALY = "italy"
        const val TEAM_ARGENTINA = "argentina"
        const val TEAM_AUSTRALIA = "australia"
    }

}
