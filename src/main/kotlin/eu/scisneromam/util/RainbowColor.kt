package eu.scisneromam.util

import javafx.scene.paint.Color

/**
 * Project: SnakeFX
 * Initially created by scisneromam on 30.11.2019.
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2019 | scisneromam | All rights reserved.
 */
class RainbowColor(minColor: Int = 0, maxColor: Int = 360, amount: Int)
{

    var pos = 0

    var maxColor = maxColor
        set(value)
        {
            field = when
            {
                value < 0 -> 0
                else -> value
            }
            calculateStep()
        }
    var minColor = minColor
        set(value)
        {
            field = when
            {
                value < 0 -> 0
                else -> value
            }
            calculateStep()
        }
    var amount: Int = amount
        set(value)
        {
            field = if (value < 1)
            {
                1
            } else
            {
                value
            }
            calculateStep()
        }


    init
    {
        if (minColor < 0)
        {
            this.minColor = 0
        } else if (minColor > 360)
        {
            this.minColor = 360
        }
        if (maxColor < 0)
        {
            this.maxColor = 0
        } else if (maxColor > 360)
        {
            this.maxColor = 360
        }
        if (this.minColor > this.maxColor)
        {
            val tmp = this.maxColor
            this.maxColor = this.minColor
            this.minColor = tmp
        }
        calculateStep()
    }


    private fun calculateStep(): Double
    {
        step = (maxColor - minColor) / (amount * 1.0)
        return step
    }

    var step = calculateStep()

    fun getNext(saturation: Double = 1.0, brightness: Double = 1.0, opacity: Double = 1.0): Color
    {
        val color = getColor(pos++, saturation, brightness, opacity)
        if (pos == amount)
        {
            pos = 0
        }
        return color
    }


    fun getColor(pos: Int, saturation: Double = 1.0, brightness: Double = 1.0, opacity: Double = 1.0): Color
    {
        val pos = pos % amount
        val saturation: Double = when
        {
            saturation < 0 -> 0.0
            saturation > 1 -> 1.0
            else -> saturation
        }
        val brightness: Double = when
        {
            brightness < 0 -> 0.0
            brightness > 1 -> 1.0
            else -> brightness
        }
        val opacity: Double = when
        {
            opacity < 0 -> 0.0
            opacity > 1 -> 1.0
            else -> opacity
        }

        return Color.hsb((minColor + (step * pos)) % 360.0, saturation, brightness, opacity)
    }

}