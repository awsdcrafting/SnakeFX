package eu.scisneromam.game

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
enum class MoveDirection
{
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NONE;

    fun opposite(): MoveDirection
    {
        return when (this)
        {

            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
            else -> NONE
        }
    }

    fun toTileDirection(): TileDirection
    {
        return when(this)
        {
            NORTH -> TileDirection.SOUTH_NORTH
            EAST -> TileDirection.WEST_EAST
            SOUTH -> TileDirection.NORTH_SOUTH
            WEST -> TileDirection.EAST_WEST
            else -> TileDirection.NONE
        }
    }
}