package eu.scisneromam.game.snake

import eu.scisneromam.game.IGame
import eu.scisneromam.game.MoveDirection
import java.util.function.Predicate

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 01.12.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class CuttingHamiltonianSnake(game: IGame) : HamiltonianSnake(game)
{

    override fun getMoveDirection(): MoveDirection
    {

        val moveDir = super.getMoveDirection()
        val apple = game.applesByDistance(this).minBy {
            when
            {
                it.x > headTile.x -> -1
                it.y == dist -> 0
                else -> 1
            }
        } ?: return moveDir
        if (game.gameInfo.gameWidth % 2 == 0)
        {

            if (headTile.y == apple.y && headTile.y > dist && headTile.x < apple.x)
            {
                if (moveDir == MoveDirection.SOUTH)
                {
                    var move = true
                    if (game.tileCheck(headTile, MoveDirection.EAST, 2, Predicate { it.state.deadly }))
                    {
                        move = false
                    } else if (game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.EAST
                            ), MoveDirection.NORTH, headTile.y - dist - 1,
                            Predicate { it.state.deadly }) || game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.EAST, 2
                            ), MoveDirection.SOUTH, game.gameInfo.gameHeight - headTile.y - dist - 1,
                            Predicate { it.state.deadly })
                    )
                    {
                        move = false
                    }
                    if (move)
                    {
                        return MoveDirection.EAST
                    }
                } else
                {
                    if (!game.getTile(headTile, MoveDirection.EAST).state.deadly && !game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.EAST
                            ), MoveDirection.NORTH, headTile.y - dist - 1,
                            Predicate { it.state.deadly }
                        )
                    )
                    {
                        return MoveDirection.EAST
                    }
                }
            } else if (headTile.x > apple.x && headTile.y == dist + 1)
            {
                if (!game.getTile(headTile, MoveDirection.NORTH).state.deadly)
                {
                    return MoveDirection.NORTH
                }
            } else if (headTile.x < game.gameInfo.gameWidth - dist - 1 && apple.y == dist && !game.tileCheck(headTile,
                    MoveDirection.EAST,
                    game.gameInfo.gameWidth - headTile.x - dist - 1,
                    Predicate { it.state.deadly }
                )
            )
            {
                return MoveDirection.EAST
            }

        } else if (game.gameInfo.gameHeight % 2 == 0)
        {
            if (headTile.x == apple.x && headTile.x > dist && headTile.y < apple.y)
            {
                if (moveDir == MoveDirection.WEST)
                {
                    var move = true
                    if (game.tileCheck(headTile, MoveDirection.EAST, 2, Predicate { it.state.deadly }))
                    {
                        move = false
                    } else if (game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.SOUTH
                            ), MoveDirection.EAST, game.gameInfo.gameWidth - headTile.x - dist - 1,
                            Predicate { it.state.deadly }) || game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.SOUTH, 2
                            ), MoveDirection.WEST, headTile.x - dist - 1,
                            Predicate { it.state.deadly })
                    )
                    {
                        move = false
                    }
                    if (move)
                    {
                        return MoveDirection.SOUTH
                    }
                } else
                {
                    if (!game.getTile(headTile, MoveDirection.NORTH).state.deadly && !game.tileCheck(
                            game.getTile(
                                headTile,
                                MoveDirection.SOUTH
                            ), MoveDirection.EAST, game.gameInfo.gameWidth - headTile.x - dist - 1,
                            Predicate { it.state.deadly }
                        )
                    )
                    {
                        return MoveDirection.SOUTH
                    }
                }
                if (!game.getTile(headTile, MoveDirection.NORTH).state.deadly)
                {
                    return MoveDirection.SOUTH
                }
            } else if (headTile.y > apple.y && headTile.x == dist + 1)
            {
                if (!game.getTile(headTile, MoveDirection.WEST).state.deadly)
                {
                    return MoveDirection.WEST
                }
            } else if (headTile.y < game.gameInfo.gameHeight - dist - 1 && apple.x == dist && !game.tileCheck(headTile,
                    MoveDirection.SOUTH,
                    game.gameInfo.gameHeight - headTile.y - dist - 1,
                    Predicate { it.state.deadly }
                )
            )
            {
                return MoveDirection.SOUTH
            }
        }

        return moveDir
    }

}