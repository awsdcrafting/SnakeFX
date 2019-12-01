package eu.scisneromam.game

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
    override fun toString(): String
    {
        return "Tile(x=$x, y=$y, state=$state, direction=$direction, game=$game)"
    }
}