# Score Board Library

## Running Tests

`./gradlew test`

## Interfaces

### GamePersistence

Simple DAL for game model.

Provides CRUD operations and also some simple sorting/filtering operations.

### GameService

Exposes leaderboard functionality to user (or possibly a view).

We assume that it is not possible to start a game when a team is already playing another game.

We assume that it is desirable to keep the data about finished games
persisted. However if it is not required, we could remove the game status and instead simply
remove the game from the persistence when it is finished instead.

## Implementations

### InMemoryGamePersistence

Very simple in memory persistence via a hashmap.

### GameServiceImplementation

Implementation that uses game persistence.
