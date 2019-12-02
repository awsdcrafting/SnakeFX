package eu.scisneromam.game

import eu.scisneromam.game.snake.Snake
import kotlin.math.sqrt

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class Tile(
    val x: Int,
    val y: Int,
    var state: TileState = TileState.GRASS,
    var direction: TileDirection = TileDirection.NONE,
    val game: IGame
)
{
    var snake : Snake? = null

    fun distTo(other: Tile): Double
    {
        if (this.game != other.game)
        {
            return -1.0
        }
        val xDelta = x - other.x
        val yDelta = y - other.y
        return sqrt((xDelta * xDelta + yDelta * yDelta).toDouble())
    }

    override fun toString(): String
    {
        return "Tile(x=$x, y=$y, state=$state, direction=$direction, game=$game)"
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tile

        if (x != other.x) return false
        if (y != other.y) return false
        if (game != other.game) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = x
        result = 31 * result + y
        result = 31 * result + game.hashCode()
        return result
    }


}
