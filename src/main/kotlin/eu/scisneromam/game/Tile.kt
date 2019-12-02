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
