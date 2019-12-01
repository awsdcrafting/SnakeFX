package eu.scisneromam.gui

import eu.scisneromam.game.GameStatus
import eu.scisneromam.game.IGame
import eu.scisneromam.game.Tile
import eu.scisneromam.game.TileState
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.geometry.Orientation
import javafx.scene.control.ScrollPane
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Priority
import javafx.scene.shape.Rectangle
import tornadofx.*

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class GameGui(private val game: IGame) : View("SnakeFX")
{
    private lateinit var scrollPane: ScrollPane
    private lateinit var vPane: AnchorPane

    private var tileRectangle: MutableMap<Tile, Rectangle> = HashMap()

    val loopTime = SimpleLongProperty()
    val appleValue = SimpleIntegerProperty()
    val scaleProperty = SimpleDoubleProperty(1.0)

    override val root = vbox {
        primaryStage.scene.addEventHandler(KeyEvent.KEY_PRESSED) { game.handleKeyPress(it) }


        scrollPane = scrollpane {
            vPane = anchorpane {
                scaleXProperty().bind(scaleProperty)
                scaleYProperty().bind(scaleProperty)
                vboxConstraints {
                    vGrow = Priority.ALWAYS
                }
                rectangle(
                    0.0,
                    0.0,
                    game.gameInfo.gameWidth * game.gameInfo.tileWidth,
                    game.gameInfo.gameHeight * game.gameInfo.tileHeight
                ) {
                    fill = game.gameInfo.grassColor
                }
            }
        }

        hbox {
            vboxConstraints {
                vGrow = Priority.NEVER
            }
            button("Return to MainMenu")
            {
                action {
                    game.paused = true
                    replaceWith<MainGui>()
                }
            }
            button("Restart")
            {
                action {
                    tileRectangle.forEach { (tile, rectangle) ->
                        rectangle.fill = game.gameInfo.grassColor
                    }
                    game.restart()
                    paint()
                }
            }
            label("To pause / unpause press escape")
            togglebutton("Paused") {
                text = if (isSelected)
                {
                    "Paused"
                } else
                {
                    "Unpaused"
                }
                selectedProperty().onChange { game.paused = it }
            }
            slider(0.0, 1.0, 1.0)
            {
                minorTickCount = 10
                majorTickUnit = 0.1
                isShowTickLabels = true
                isShowTickMarks = true
                valueProperty().bindBidirectional(scaleProperty)
            }

        }
        hbox {
            label("LoopTime")
            textfield(loopTime) {

                text = "${game.gameInfo.loopTime}"
                filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() >= 0 }
            }
            slider(0, 1000, 250, Orientation.HORIZONTAL) {
                minorTickCount = 10
                majorTickUnit = 100.toDouble()
                isShowTickLabels = true
                isShowTickMarks = true
                valueProperty().bindBidirectional(loopTime)
            }
            label("AppleValue")
            textfield(appleValue) {

                text = "1"
                filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() > 0 }
            }


            button("Apply") {

                action {
                    applySettings()
                }

            }
        }
    }

    fun applySettings()
    {
        game.gameInfo.loopTime = loopTime.value
        game.gameInfo.appleValue = appleValue.value
    }

    init
    {
        paint()
    }


    /**
     * Should be called by gameEngine, paints the field
     */
    fun paint()
    {
        if (game.paused)
        {
            this.title = "SnakeFX - Paused"
        } else
        {
            this.title = "SnakeFX"
        }

        val changedTiles = ArrayList(game.changedTiles)
        game.changedTiles.clear()
        for (tile in changedTiles)
        {
            val rectangle = tileRectangle.getOrPut(
                tile, {
                    vPane.rectangle(
                        tile.x * game.gameInfo.tileWidth,
                        tile.y * game.gameInfo.tileHeight,
                        game.gameInfo.tileWidth,
                        game.gameInfo.tileHeight
                    )
                }
            )
            when (tile.state)
            {
                TileState.WALL -> rectangle.fill = game.gameInfo.wallColor

                TileState.SNAKE_HEAD ->
                {
                    rectangle.fill =
                        if (game.gameStatus == GameStatus.DEAD)
                        {
                            game.gameInfo.deadHeadColor
                        } else if (game.gameInfo.rainbowSnake)
                        {
                            game.snake.rainbow.getNext()
                        } else
                        {
                            game.gameInfo.snakeHeadColor
                        }
                    if (game.gameInfo.autoScroll && scaleProperty.value == 1.0)
                    {
                        scrollPane.hvalue = ((tile.x.toDouble() / game.gameInfo.gameWidth))
                        scrollPane.vvalue = ((tile.y.toDouble() / game.gameInfo.gameHeight))
                    }
                }
                TileState.SNAKE_BODY ->
                    rectangle.fill = if (game.gameInfo.rainbowSnake)
                    {
                        rectangle.fill
                        //game.snake.rainbow.getColor(snakePos++)
                    } else
                    {
                        game.gameInfo.snakeBodyColor
                    }


                TileState.SNAKE_TAIL ->
                    rectangle.fill = if (game.gameInfo.rainbowSnake)
                    {
                        rectangle.fill
                        // game.snake.rainbow.getColor(snakePos++)
                    } else
                    {
                        game.gameInfo.snakeTailColor
                    }

                TileState.APPLE -> rectangle.fill = game.gameInfo.appleColor


                TileState.GRASS -> rectangle.fill = game.gameInfo.grassColor

                TileState.BUFF -> TODO()
            }
        }
        game.changedTiles.clear()
    }
}
