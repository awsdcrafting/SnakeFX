package eu.scisneromam.gui

import eu.scisneromam.game.GameEngine
import javafx.event.EventHandler
import javafx.stage.Stage
import tornadofx.App

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class MainGuiApp : App()
{
    override val primaryView = MainGui::class
    override fun start(stage: Stage)
    {
        super.start(stage)
        workspace.primaryStage.onCloseRequest = EventHandler {
            GameEngine.running = false
        }
    }

}