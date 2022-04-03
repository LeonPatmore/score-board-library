package data.exceptions

class GameNotFoundException(id: Int) : RuntimeException("Game with id $id not found!")
