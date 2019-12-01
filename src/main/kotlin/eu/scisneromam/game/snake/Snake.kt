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

}