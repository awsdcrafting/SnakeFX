package eu.scisneromam.game

import eu.scisneromam.gui.GameGui
import javafx.application.Platform
import java.util.concurrent.FutureTask

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 01.12.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class GameEngine(val gui: GameGui, val game: IGame) : Thread()
{

    var paused = false

    companion object
    {
        var running = true
    }

    override fun run()
    {
        while (running)
        {
            if (!game.paused)
            {
                paused = false
                val gameOver = game.doLoop()

                val futureTask = FutureTask {
                    gui.paint()
                    true
                }

                Platform.runLater { futureTask.run() }
                futureTask.get()
                if (gameOver)
                {
                    for (snake in game.snakes.keys)
                    {
                        println("The Game ended with the status ${game.gameStatus}, and the snake had a length of ${snake.length}")
                    }
                    game.paused = true
                }
            } else if (!paused)
            {
                paused = true
                Platform.runLater { gui.paint() }
            }
            if (game.gameInfo.loopTime > 0)
            {
                sleep(game.gameInfo.loopTime)
            }
        }

    }

}