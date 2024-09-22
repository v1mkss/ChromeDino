package dev.v1mkss.chrome.dino
package components

import components.Dino.*
import utils.Utils.getResource

import java.awt.image.BufferedImage
import java.awt.{Graphics, Rectangle}
import scala.compiletime.uninitialized

class Dino {

  private var foot: Int = NO_FOOT

  private var leftFootDino: BufferedImage = uninitialized
  private var rightFootDino: BufferedImage = uninitialized
  private var deadDino: BufferedImage = uninitialized

  init()

  private def init(): Unit = {
    Dino.image = getResource("/images/Dino-stand.png")
    leftFootDino = getResource("/images/Dino-left-up.png")
    rightFootDino = getResource("/images/Dino-right-up.png")
    deadDino = getResource("/images/Dino-big-eyes.png")

    Dino.dinoBaseY = Ground.GROUND_Y(GamePanel.HEIGHT)
    Dino.dinoTopY = Dino.dinoBaseY - Dino.image.getHeight
    Dino.dinoStartX = 100
    Dino.dinoEndX = Dino.dinoStartX + Dino.image.getWidth
    Dino.topPoint = Dino.dinoTopY - 140

    Dino.state = STAND_STILL
    foot = NO_FOOT
  }

  def create(g: Graphics): Unit = {
    Dino.dinoBottom = Dino.dinoTop + Dino.image.getHeight

    Dino.state match {
      case STAND_STILL =>
        g.drawImage(Dino.image, Dino.dinoStartX, Dino.dinoTopY, null)
      case RUNNING =>
        foot match {
          case NO_FOOT =>
            foot = LEFT_FOOT
            g.drawImage(leftFootDino, Dino.dinoStartX, Dino.dinoTopY, null)
          case LEFT_FOOT =>
            foot = RIGHT_FOOT
            g.drawImage(rightFootDino, Dino.dinoStartX, Dino.dinoTopY, null)
          case _ =>
            foot = LEFT_FOOT
            g.drawImage(leftFootDino, Dino.dinoStartX, Dino.dinoTopY, null)
        }
      case JUMPING =>
        if (Dino.dinoTop > Dino.topPoint && !Dino.topPointReached) {
          g.drawImage(Dino.image, Dino.dinoStartX, Dino.dinoTop - Dino.jumpFactor, null)
        } else if (Dino.dinoTop >= Dino.topPoint && !Dino.topPointReached) {
          Dino.topPointReached = true
          g.drawImage(Dino.image, Dino.dinoStartX, Dino.dinoTop + Dino.jumpFactor, null)
        } else if (Dino.dinoTop > Dino.topPoint && Dino.topPointReached) {
          if (Dino.dinoTopY == Dino.dinoTop && Dino.topPointReached) {
            Dino.state = RUNNING
            Dino.topPointReached = false
          } else {
            g.drawImage(Dino.image, Dino.dinoStartX, Dino.dinoTop + Dino.jumpFactor, null)
          }
        }
      case DIE =>
        g.drawImage(deadDino, Dino.dinoStartX, Dino.dinoTop, null)
    }
  }

  def die(): Unit = {
    Dino.state = DIE
  }

  def startRunning(): Unit = {
    Dino.dinoTop = Dino.dinoTopY
    Dino.state = RUNNING
  }

  def jump(): Unit = {
    Dino.dinoTop = Dino.dinoTopY
    Dino.topPointReached = false
    Dino.state = JUMPING
  }
}

object Dino {
  private var dinoBaseY, dinoTopY, dinoStartX, dinoEndX = 0
  private var dinoTop, dinoBottom, topPoint = 0

  private var topPointReached = false
  private val jumpFactor = 30

  private val STAND_STILL = 1
  private val RUNNING = 2
  private val JUMPING = 3
  private val DIE = 4
  private val LEFT_FOOT = 1
  private val RIGHT_FOOT = 2
  private val NO_FOOT = 3

  private var state: Int = STAND_STILL

  private var image: BufferedImage = uninitialized

  def getDino: Rectangle = {
    val dino = new Rectangle()
    dino.x = dinoStartX

    if (state == JUMPING && !topPointReached) dino.y = dinoTop - jumpFactor
    else if (state == JUMPING && topPointReached) dino.y = dinoTop + jumpFactor
    else if (state != JUMPING) dino.y = dinoTop

    dino.width = image.getWidth
    dino.height = image.getHeight

    dino
  }
}
