package eu.scisneromam.game

import eu.scisneromam.game.snake.SnakeType
import javafx.scene.paint.Color

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class GameInfo
{
    var autoScroll: Boolean = true

    var snakeType: SnakeType = SnakeType.PLAYER

    var snakeHeadColor: Color = Color.BLUE
    var snakeBodyColor: Color = Color.WHITE
    var snakeTailColor: Color = Color.WHITE

    var deadHeadColor: Color = Color.BLACK

    var grassColor: Color = Color.GRAY
    var wallColor: Color = Color.ORANGE
    var appleColor: Color = Color.RED

    var walls: Boolean = true

    var rainbowSnake: Boolean = true
    var rainbowLength: Int = 720
    var fixRainbowLengthToSnakeLength: Boolean = false

    var gameWidth: Int = 20
    var gameHeight: Int = 20

    var startingSnakeLength: Int = 5
    var appleValue: Int = 1

    var tileWidth: Int = 10
    var tileHeight: Int = 10

    var loopTime: Long = 250 // 1000/loopTime loops per second
}