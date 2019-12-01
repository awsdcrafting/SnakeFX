package eu.scisneromam.game.snake

import eu.scisneromam.game.IGame
import eu.scisneromam.game.MoveDirection

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 01.12.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class PlayerSnake(game: IGame) : Snake(game, true)
{
    override fun getMoveDirection(): MoveDirection
    {
        return MoveDirection.NONE // this is actually handled in [Game]
    }
}