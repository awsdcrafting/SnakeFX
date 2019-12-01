package eu.scisneromam.game.snake

import eu.scisneromam.game.IGame
import eu.scisneromam.game.MoveDirection
import eu.scisneromam.game.Tile
import kotlin.math.abs

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 01.12.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
open class HamiltonianSnake(game: IGame) : Snake(game, false)
{

    val cycleMap: MutableMap<Tile, Tile> = HashMap()

    val dist = if (game.gameInfo.walls)
    {
        1
    } else
    {
        0
    }

    init
    {
        val width = game.gameInfo.gameWidth
        val height = game.gameInfo.gameHeight
        if (width == 1 || height == 1 || (width % 2 != 0 && height % 2 != 0))
        {
            throw IllegalArgumentException("Impossible to generate Hamiltonian Path!")
        }

        var start = game.getTile(dist, dist)
        if (width % 2 == 0)
        {
            var currentTile = start
            var nextTile = game.getTile(currentTile, MoveDirection.SOUTH)
            cycleMap[currentTile] = nextTile
            do
            {
                currentTile = nextTile
                nextTile = when
                {
                    currentTile.y == dist -> game.getTile(currentTile, MoveDirection.WEST)
                    currentTile.x == width - dist - 1 -> game.getTile(currentTile, MoveDirection.NORTH)
                    (currentTile.y == height - dist - 1 && currentTile.x % 2 == dist) || (currentTile.y == dist + 1 && currentTile.x % 2 != dist) ->
                        game.getTile(
                            currentTile,
                            MoveDirection.EAST
                        )
                    currentTile.x % 2 == dist -> game.getTile(currentTile, MoveDirection.SOUTH)
                    currentTile.x % 2 != dist -> game.getTile(currentTile, MoveDirection.NORTH)
                    else -> throw IllegalStateException("Should not happen")
                }
                cycleMap[currentTile] = nextTile
            } while (nextTile != start)
        } else if (height % 2 == 0)
        {
            var currentTile = start
            var nextTile = game.getTile(currentTile, MoveDirection.EAST)
            cycleMap[currentTile] = nextTile
            do
            {
                currentTile = nextTile
                nextTile = when
                {
                    currentTile.x == dist -> game.getTile(currentTile, MoveDirection.NORTH)
                    currentTile.y == height - dist - 1 -> game.getTile(currentTile, MoveDirection.WEST)
                    (currentTile.x == width - dist - 1 && currentTile.y % 2 == dist) || (currentTile.x == dist + 1 && currentTile.y % 2 != dist) -> game.getTile(
                        currentTile,
                        MoveDirection.SOUTH
                    )
                    currentTile.y % 2 == dist -> game.getTile(currentTile, MoveDirection.EAST)
                    currentTile.y % 2 != dist -> game.getTile(currentTile, MoveDirection.WEST)
                    else -> throw IllegalStateException("Should not happen")
                }
                cycleMap[currentTile] = nextTile
            } while (currentTile != start)
        } else
        {
            throw IllegalArgumentException("Impossible to generate Hamiltonian Path!")
        }
    }

    override fun getMoveDirection(): MoveDirection
    {
        var tile: Tile =
            cycleMap[headTile] ?: throw IllegalStateException("Snake doesnt know where to get from $headTile")
        val directions = MoveDirection.values()
        var index = 0
        while (tile.state.deadly)
        {
            if (index >= directions.size)
            {
                println("Nowhere to get from $headTile")
                return MoveDirection.NONE
            }
            tile = game.getTile(headTile, directions[index++])
        }
        return directionToTileFromTile(headTile, tile)
    }

    fun directionToTileFromTile(from: Tile, to: Tile): MoveDirection
    {
        if (from.game != to.game)
        {
            return MoveDirection.NONE
        }
        val deltaX = from.x - to.x
        val deltaY = from.y - to.y

        return when
        {
            deltaX < 0 && (deltaY == 0 || abs(deltaX) >= abs(deltaY)) -> MoveDirection.EAST
            deltaX > 0 && (deltaY == 0 || abs(deltaX) >= abs(deltaY)) -> MoveDirection.WEST
            deltaY < 0 && (deltaX == 0 || abs(deltaY) >= abs(deltaX)) -> MoveDirection.SOUTH
            deltaY > 0 && (deltaX == 0 || abs(deltaY) >= abs(deltaX)) -> MoveDirection.NORTH
            else -> MoveDirection.NONE
        }

    }
}