package eu.scisneromam.game

import eu.scisneromam.game.snake.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import java.util.concurrent.ThreadLocalRandom

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class Game(override val gameInfo: GameInfo) : IGame
{
    override val tiles: Array<Array<Tile>> =
        Array(gameInfo.gameWidth) { x -> Array(gameInfo.gameHeight) { y -> Tile(x, y, game = this) } }


    lateinit var appleTile: Tile

    override val changedTiles: MutableList<Tile> = ArrayList()
    /**
     * Keeps track of all tiles where things can be put
     */
    val grassTiles: MutableList<Tile> = tiles.flatten().toMutableList()
    var moveDirection: MoveDirection = MoveDirection.NORTH

    val random = ThreadLocalRandom.current()

    override var paused: Boolean = true
        set(value)
        {
            field = value
            gameStatus = if (value)
            {
                GameStatus.PAUSED
            } else
            {
                GameStatus.RUNNING
            }
        }
    override var gameStatus: GameStatus = GameStatus.PAUSED

    val snake: Snake = when (gameInfo.snakeType)
    {
        SnakeType.STRICT_HAMILTONIAN -> HamiltonianSnake(this)
        SnakeType.CUTTING_HAMILTONIAN -> CuttingHamiltonianSnake(this)
        else -> PlayerSnake(this)
    }

    override val snakes: MutableMap<Snake, MoveDirection> = HashMap()
    override val appleTiles: MutableList<Tile> = ArrayList()

    init
    {
        init()
    }

    override fun restart()
    {
        paused = true
        tiles.forEach { tiles2 ->
            tiles2.forEach { tile ->
                tile.state = TileState.GRASS
                tile.direction = TileDirection.NONE
            }
        }
        init()
    }

    override fun init()
    {
        paused = true
        moveDirection = MoveDirection.NORTH
        if (gameInfo.walls)
        {
            addWalls()
            grassTiles.removeAll { it.state == TileState.WALL }
        }

        val distance = gameInfo.startingSnakeLength / 10 + if (gameInfo.walls)
        {
            1
        } else
        {
            0
        }
        val headX = random.nextInt(distance, gameInfo.gameWidth - distance)
        val headY = random.nextInt(distance, gameInfo.gameHeight - distance)

        val headTile = getTile(headX, headY)
        changedTiles.add(headTile)
        grassTiles.remove(headTile)
        headTile.state = TileState.SNAKE_HEAD
        headTile.direction = TileDirection.SOUTH_NORTH
        var currentTile = headTile
        var previousTile = headTile
        for (i in 2..gameInfo.startingSnakeLength)
        {
            var directions = currentTile.direction.from()
            var index = random.nextInt(directions.size)

            var direction = directions[index]
            var nextTile = getTile(currentTile, currentTile.direction.getOpposite().toMoveDirection())
            var iterations = 0
            while (nextTile.state != TileState.GRASS)
            {
                val directions2 = previousTile.direction.from()
                val index2 = random.nextInt(directions.size)

                val direction2 = directions2[index2]
                currentTile.direction = direction2
                directions = currentTile.direction.from()
                index = random.nextInt(directions.size)
                direction = directions[index]
                nextTile = getTile(currentTile, currentTile.direction.getOpposite().toMoveDirection())
                if (iterations++ > 25)
                {
                    restart()
                    return
                }
            }
            nextTile.direction = direction
            nextTile.state = if (i == gameInfo.startingSnakeLength)
            {
                TileState.SNAKE_TAIL
            } else
            {
                TileState.SNAKE_BODY
            }
            previousTile = currentTile
            currentTile = nextTile
            grassTiles.remove(currentTile)
            changedTiles.add(currentTile)
        }

        snake.headTile = headTile
        snake.tailTile = currentTile
        snake.length = gameInfo.startingSnakeLength

        placeApple()
    }

    override fun handleKeyPress(event: KeyEvent)
    {
        if (!snake.player)
        {
            if (event.code == KeyCode.ESCAPE)
            {
                paused = !paused
            }
        } else
        {
            when (event.code)
            {
                KeyCode.ESCAPE -> paused = !paused
                KeyCode.A, KeyCode.LEFT -> moveDirection = MoveDirection.WEST
                KeyCode.W, KeyCode.UP -> moveDirection = MoveDirection.NORTH
                KeyCode.S, KeyCode.DOWN -> moveDirection = MoveDirection.SOUTH
                KeyCode.D, KeyCode.RIGHT -> moveDirection = MoveDirection.EAST
            }
        }
    }

    override fun doLoop(): Boolean
    {
        if (!snake.player)
        {
            moveDirection = snake.getMoveDirection()
            if (moveDirection == MoveDirection.NONE)
            {
                println("Snake gave up!")
                gameStatus = GameStatus.DEAD
                return true
            }
        }
        return moveSnake()
    }

    override fun moveSnake(): Boolean
    {
        val oldHeadTile = snake.headTile
        val headTile = getTile(snake.headTile, moveDirection)
        val state = headTile.state
        if (headTile.state.deadly || (headTile.state == TileState.SNAKE_TAIL && snake.apples > 0))
        {
            println("GAMEOVER")
            gameStatus = GameStatus.DEAD
            return true
        }

        val oldTailDirection = snake.tailTile.direction.toMoveDirection()

        changedTiles.add(oldHeadTile)
        changedTiles.add(headTile)
        oldHeadTile.state = TileState.SNAKE_BODY
        headTile.direction = moveDirection.toTileDirection()
        headTile.state = TileState.SNAKE_HEAD
        oldHeadTile.direction = oldHeadTile.direction.to(moveDirection)
        snake.headTile = headTile
        grassTiles.remove(headTile)

        if (snake.apples > 0)
        {
            snake.apples--
            snake.length++
            if (state == TileState.APPLE)
            {
                snake.apples += gameInfo.appleValue
                return placeApple()
            }
        } else if (state == TileState.APPLE)
        {
            snake.apples += gameInfo.appleValue
            snake.apples--
            snake.length++
            return placeApple()
        } else
        {
            val oldTailTile = snake.tailTile
            snake.tailTile = getTile(snake.tailTile, oldTailDirection)
            val tailTile = snake.tailTile
            changedTiles.add(oldTailTile)
            if (oldTailTile.state == TileState.SNAKE_TAIL)
            {
                grassTiles.add(oldTailTile)
                oldTailTile.state = TileState.GRASS
                oldTailTile.direction = TileDirection.NONE
            }
            tailTile.state = TileState.SNAKE_TAIL
            changedTiles.add(tailTile)
        }



        return false
    }

    override fun placeApple(): Boolean
    {
        if (grassTiles.size == 0)
        {
            gameStatus = GameStatus.WON
            return true
        }
        appleTile = grassTiles[random.nextInt(grassTiles.size)]
        appleTile.state = TileState.APPLE
        grassTiles.remove(appleTile)
        changedTiles.add(appleTile)
        return false
    }

    override fun getTile(tile: Tile, direction: MoveDirection, times: Int): Tile
    {
        val times = if (times < 1)
        {
            1
        } else
        {
            times
        }
        val x = when (direction)
        {
            MoveDirection.EAST -> tile.x + times
            MoveDirection.WEST -> tile.x - times
            else -> tile.x
        }
        val y = when (direction)
        {
            MoveDirection.NORTH -> tile.y - times
            MoveDirection.SOUTH -> tile.y + times
            else -> tile.y
        }
        return getTile(x, y)
    }

    override fun getTile(x: Int, y: Int): Tile
    {
        val x = x % tiles.size
        val y = y % tiles[x].size
        return tiles[x][y]
    }


}
