package eu.scisneromam.game

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
enum class TileState(val deadly: Boolean)
{
    WALL(true),
    SNAKE_HEAD(true),
    SNAKE_BODY(true),
    SNAKE_TAIL(false),
    APPLE(false),
    GRASS(false),
    BUFF(false) //unused atm


}