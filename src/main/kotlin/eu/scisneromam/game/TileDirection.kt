package eu.scisneromam.game

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
enum class TileDirection
{
    //used by snake
    //from_to
    NORTH_SOUTH,
    NORTH_EAST,
    NORTH_WEST,
    EAST_WEST,
    EAST_NORTH,
    EAST_SOUTH,
    SOUTH_NORTH,
    SOUTH_EAST,
    SOUTH_WEST,
    WEST_EAST,
    WEST_NORTH,
    WEST_SOUTH,

    //used by anything that is not a snake
    NONE;

    fun toMoveDirection(): MoveDirection
    {
        return when (this)
        {
            NORTH_SOUTH, EAST_SOUTH, WEST_SOUTH -> MoveDirection.SOUTH
            NORTH_EAST, SOUTH_EAST, WEST_EAST -> MoveDirection.EAST
            NORTH_WEST, EAST_WEST, SOUTH_WEST -> MoveDirection.WEST
            EAST_NORTH, SOUTH_NORTH, WEST_NORTH -> MoveDirection.NORTH
            else -> MoveDirection.NONE
        }
    }

    fun getOpposite(): TileDirection
    {
        return when (this)
        {
            NORTH_SOUTH -> SOUTH_NORTH
            NORTH_EAST -> EAST_NORTH
            NORTH_WEST -> WEST_NORTH
            EAST_WEST -> WEST_EAST
            EAST_NORTH -> NORTH_EAST
            EAST_SOUTH -> SOUTH_EAST
            SOUTH_NORTH -> NORTH_SOUTH
            SOUTH_EAST -> EAST_SOUTH
            SOUTH_WEST -> WEST_SOUTH
            WEST_EAST -> EAST_WEST
            WEST_NORTH -> NORTH_WEST
            WEST_SOUTH -> SOUTH_WEST
            else -> NONE
        }
    }

    fun from(): Array<TileDirection>
    {
        return when (this)
        {
            //coming from north -> going to south
            NORTH_SOUTH, NORTH_EAST, NORTH_WEST -> arrayOf(NORTH_SOUTH, EAST_SOUTH, WEST_SOUTH)
            //coming from east -> going to west
            EAST_WEST, EAST_NORTH, EAST_SOUTH -> arrayOf(NORTH_WEST, EAST_WEST, SOUTH_WEST)
            //coming from south -> going to north
            SOUTH_NORTH, SOUTH_EAST, SOUTH_WEST -> arrayOf(SOUTH_NORTH, EAST_NORTH, WEST_NORTH)
            //coming from west -> going to east
            WEST_EAST, WEST_NORTH, WEST_SOUTH -> arrayOf(WEST_EAST, SOUTH_EAST, NORTH_EAST)
            else -> arrayOf(NONE)
        }
    }

    fun to(): Array<TileDirection>
    {
        return when (this)
        {
            //going to south -> coming from north
            NORTH_SOUTH, EAST_SOUTH, WEST_SOUTH -> arrayOf(NORTH_SOUTH, NORTH_EAST, NORTH_WEST)
            //going to east -> coming from west
            NORTH_EAST, SOUTH_EAST, WEST_EAST -> arrayOf(WEST_EAST, WEST_NORTH, WEST_SOUTH)
            //going to west -> coming from east
            NORTH_WEST, EAST_WEST, SOUTH_WEST -> arrayOf(EAST_NORTH, EAST_WEST, EAST_SOUTH)
            //going to north -> coming from south
            EAST_NORTH, SOUTH_NORTH, WEST_NORTH -> arrayOf(SOUTH_EAST, SOUTH_NORTH, SOUTH_WEST)
            else -> arrayOf(NONE)
        }
    }

    fun to(direction: MoveDirection): TileDirection
    {
        return when (this)
        {
            NORTH_SOUTH, EAST_SOUTH, WEST_SOUTH -> when (direction)
            {
                MoveDirection.EAST -> NORTH_EAST
                MoveDirection.SOUTH -> NORTH_SOUTH
                MoveDirection.WEST -> NORTH_WEST
                else -> NONE
            }
            NORTH_EAST, SOUTH_EAST, WEST_EAST -> when (direction)
            {
                MoveDirection.EAST -> WEST_EAST
                MoveDirection.SOUTH -> WEST_SOUTH
                MoveDirection.NORTH -> WEST_NORTH
                else -> NONE
            }
            NORTH_WEST, EAST_WEST, SOUTH_WEST -> when (direction)
            {
                MoveDirection.WEST -> EAST_WEST
                MoveDirection.SOUTH -> EAST_SOUTH
                MoveDirection.NORTH -> EAST_NORTH
                else -> NONE
            }
            EAST_NORTH, SOUTH_NORTH, WEST_NORTH -> when (direction)
            {
                MoveDirection.EAST -> SOUTH_EAST
                MoveDirection.NORTH -> SOUTH_NORTH
                MoveDirection.WEST -> SOUTH_WEST
                else -> NONE
            }
            else -> NONE
        }
    }

}