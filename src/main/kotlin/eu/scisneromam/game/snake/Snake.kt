package eu.scisneromam.game.snake

import eu.scisneromam.game.IGame
import eu.scisneromam.game.MoveDirection
import eu.scisneromam.game.Tile
import eu.scisneromam.util.RainbowColor

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
abstract class Snake(val game: IGame, val player: Boolean)
{
    lateinit var headTile: Tile
    lateinit var tailTile: Tile
    var length = game.gameInfo.startingSnakeLength
        set(value)
        {
            field = value
            if (game.gameInfo.fixRainbowLengthToSnakeLength)
            {
                rainbow.amount = value
            }
        }
    val rainbow = RainbowColor(
        amount = if (game.gameInfo.fixRainbowLengthToSnakeLength)
        {
            length
        } else
        {
            game.gameInfo.rainbowLength
        }
    )
    /**
     * Amount of apples (length increases) remaining
     */
    var apples = 0


    abstract fun getMoveDirection(): MoveDirection

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (other !is Snake) return false

        if (game != other.game) return false
        if (player != other.player) return false
        if (headTile != other.headTile) return false
        if (tailTile != other.tailTile) return false
        if (length != other.length) return false
        if (apples != other.apples) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = game.hashCode()
        result = 31 * result + player.hashCode()
        result = 31 * result + headTile.hashCode()
        result = 31 * result + tailTile.hashCode()
        result = 31 * result + length
        result = 31 * result + apples
        return result
    }


}