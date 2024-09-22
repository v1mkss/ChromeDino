package dev.v1mkss.chrome.dino
package components

import utils.Utils.getResource

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics, Rectangle}
import scala.collection.mutable.ArrayBuffer

class Obstacles(firstPos: Int) {
  import Obstacles.*

  private val firstX: Int = firstPos
  private val obstacleInterval: Int = 200
  private val movementSpeed: Int = 11

  private val imageList: ArrayBuffer[BufferedImage] = ArrayBuffer(
    getResource("/images/Cactus-1.png"),
    getResource("/images/Cactus-2.png"),
    getResource("/images/Cactus-2.png"),
    getResource("/images/Cactus-1.png"),
    getResource("/images/Cactus-5.png")
  )

  private val obList: ArrayBuffer[Obstacle] = ArrayBuffer()

  private var blockedAt: Option[Obstacle] = None

  init()

  private def init(): Unit = {
    var x = firstX
    for (bi <- imageList) {
      val ob = new Obstacle(bi, x, Ground.GROUND_Y(GamePanel.HEIGHT) - bi.getHeight)
      x += obstacleInterval
      obList += ob
    }
  }

  def update(): Unit = {
    val looper = obList.iterator
    val firstOb = looper.next()
    firstOb.x -= movementSpeed

    while (looper.hasNext) {
      val ob = looper.next()
      ob.x -= movementSpeed
    }

    val lastOb = obList.last

    if (firstOb.x < -firstOb.image.getWidth) {
      obList -= firstOb
      firstOb.x = obList.last.x + obstacleInterval
      obList += firstOb
    }
  }

  def create(g: Graphics): Unit = {
    for (ob <- obList) {
      g.setColor(Color.black)
      // g.drawRect(ob.getObstacle.x, ob.getObstacle.y, ob.getObstacle.width, ob.getObstacle.height)
      g.drawImage(ob.image, ob.x, ob.y, null)
    }
  }

  def hasCollided: Boolean = {
    for (ob <- obList) {
      if (Dino.getDino.intersects(ob.getObstacle)) {
        println(s"Dino = ${Dino.getDino}\nObstacle = ${ob.getObstacle}\n\n")
        blockedAt = Some(ob)
        return true
      }
    }
    false
  }

  def resume(): Unit = {
    var x = firstX / 2
    obList.clear()
    for (bi <- imageList) {
      val ob = new Obstacle(bi, x, Ground.GROUND_Y(GamePanel.HEIGHT) - bi.getHeight)

      x += obstacleInterval
      obList += ob
    }
  }
}

object Obstacles {
  private class Obstacle(val image: BufferedImage, var x: Int, var y: Int) {
    def getObstacle: Rectangle = {
      val obstacle = new Rectangle()
      obstacle.x = x
      obstacle.y = y
      obstacle.width = image.getWidth
      obstacle.height = image.getHeight
      obstacle
    }
  }
}
