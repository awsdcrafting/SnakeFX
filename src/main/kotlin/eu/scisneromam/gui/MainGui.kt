package eu.scisneromam.gui

import eu.scisneromam.game.Game
import eu.scisneromam.game.GameEngine
import eu.scisneromam.game.GameInfo
import eu.scisneromam.game.snake.SnakeType
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Orientation
import tornadofx.*


class MainGui : View("Create Game")
{
    val width = SimpleIntegerProperty()
    val height = SimpleIntegerProperty()
    val snakeLength = SimpleIntegerProperty()
    val appleValue = SimpleIntegerProperty()
    val walls = SimpleBooleanProperty()
    val selectedSnakeType = SimpleObjectProperty<SnakeType>()
    val loopTime = SimpleLongProperty()


    override val root = form {
        label("Create a new Game")
        fieldset {
            field("Width") {
                textfield(this@MainGui.width) {
                    text = "20"
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() > 0 }
                }
            }
            field("Height") {
                textfield(this@MainGui.height) {
                    text = "20"
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() > 0 }
                }
            }

            field("SnakeLength")
            {
                textfield(snakeLength) {
                    text = "5"
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() > 0 }
                }
            }

            field("AppleValue")
            {
                textfield(appleValue) {
                    text = "1"
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() > 0 }
                }
            }

            val snakeTypes = FXCollections.observableArrayList<SnakeType>()
            snakeTypes.addAll(SnakeType.values())

            selectedSnakeType.value = SnakeType.PLAYER
            field("SnakeType")
            {
                combobox(selectedSnakeType, snakeTypes)
            }

            checkbox("Walls", walls)
            {
                isSelected = true
            }

            field("LoopTime")
            {
                textfield(loopTime) {
                    text = "250"
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() >= 0 }
                }
                slider(0, 1000, 250, Orientation.HORIZONTAL) {
                    minorTickCount = 10
                    majorTickUnit = 100.toDouble()
                    isShowTickLabels = true
                    isShowTickMarks = true
                    valueProperty().bindBidirectional(loopTime)
                }
            }

            button("Create") {
                action {
                    create()
                }
            }
        }
    }

    fun create()
    {
        val width = this@MainGui.width.value
        val height = this@MainGui.height.value
        println("Creating gui with ${width} width and ${height} height")
        val gameInfo = GameInfo()
        val dist = if (gameInfo.walls)
        {
            2
        } else
        {
            0
        }
        gameInfo.gameWidth = width + dist
        gameInfo.gameHeight = height + dist
        gameInfo.snakeType = selectedSnakeType.value
        gameInfo.startingSnakeLength = snakeLength.value
        gameInfo.loopTime = loopTime.value
        gameInfo.appleValue = appleValue.value
        gameInfo.walls = walls.value
        gameInfo.rainbowLength = (width * height) / 4
        val game = Game(gameInfo)
        println("created game")
        val gui = GameGui(game)
        println("created game gui")
        val gameEngine = GameEngine(gui, game)
        println("starting")
        gameEngine.start()
        replaceWith(gui)
    }
}

