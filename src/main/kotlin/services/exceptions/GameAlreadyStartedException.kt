package services.exceptions

import model.Game

class GameAlreadyStartedException(game: Game) :
    RuntimeException("Game with teams ${game.away.teamName} and ${game.home.teamName} has already started!")
