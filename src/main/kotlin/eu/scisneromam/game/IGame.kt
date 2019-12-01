package eu.scisneromam.game

import eu.scisneromam.game.snake.Snake
import javafx.scene.input.KeyEvent
import java.util.function.Predicate

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
interface IGame
{
    val gameInfo: GameInfo

    /**
     * Returns true if the game died
     * @return true if the snake died
     */
    fun moveSnake(): Boolean

    var gameStatus: GameStatus

    fun moveSnake(times: Int)
    {
        for (i in 1..times)
        {
            moveSnake()
        }
    }

    val tiles: Array<Array<Tile>>
    val changedTiles: MutableList<Tile>

    fun getTile(x: Int, y: Int): Tile

    val snake: Snake
    var paused: Boolean

    fun handleKeyPress(event: KeyEvent)

    /**
     * @return true if the snake died that turn, or won the game
     */
    fun doLoop(): Boolean

    /**
     * Adds walls to the game, eg the state of every tile which is at an edge will be set to [TileState.WALL]
     */
    fun addWalls()
    {
        for (tiles2 in tiles)
        {
            for (tile in tiles2)
            {
                if (tile.x == 0 || tile.y == 0 || tile.x == tiles.size - 1 || tile.y == tiles2.size - 1)
                {
                    tile.state = TileState.WALL
                    tile.direction = TileDirection.NONE
                    changedTiles.add(tile)
                }
            }
        }
    }

    fun placeApple(): Boolean
    fun restart()
    fun init()
    fun getTile(tile: Tile, direction: MoveDirection, times: Int = 1): Tile

    fun tileCheck(tile: Tile, direction: MoveDirection, distance: Int = 1, predicate: Predicate<Tile>): Boolean
    {
        for (dist in 1..distance)
        {
            if (predicate.test(getTile(tile, direction, dist)))
            {
                return true
            }
        }
        return false
    }

    fun getApple(): Tile
}