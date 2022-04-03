package data.exceptions

class GameIdAlreadyExistsException(id: Int) : RuntimeException("Game with id $id already exists!")
