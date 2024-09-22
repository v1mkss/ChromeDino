package dev.v1mkss.chrome.dino
package components

import utils.Utils.getResource

import java.awt.Graphics
import java.awt.image.BufferedImage
import scala.collection.mutable.ArrayBuffer
import scala.compiletime.uninitialized

object Ground {
  def GROUND_Y(panelHeight: Int): Int = (panelHeight - 0.25 * panelHeight).toInt
}

class Ground(val panelHeight: Int) {

  private class GroundImage {
    var image: BufferedImage = uninitialized
    var x: Int = uninitialized
  }

  private var image: BufferedImage = uninitialized

  private var groundImageSet: ArrayBuffer[GroundImage] = uninitialized

  {
    try {
      image = getResource("/images/Ground.png")
    } catch {
      case e: Exception => e.printStackTrace()
    }

    groundImageSet = new ArrayBuffer[GroundImage]()

    for (_ <- 0 until 3) {
      val obj: GroundImage = new GroundImage()
      obj.image = image
      obj.x = 0
      groundImageSet.append(obj)
    }
  }

  def update(): Unit = {
    val looper: Iterator[GroundImage] = groundImageSet.iterator // Now uses Scala Iterator
    val first: GroundImage = looper.next()

    first.x -= 10

    var previousX: Int = first.x
    while (looper.hasNext) {
      val next: GroundImage = looper.next()
      next.x = previousX + image.getWidth()
      previousX = next.x
    }

    if (first.x < -image.getWidth()) {
      groundImageSet.remove(0) // Remove from the beginning
      first.x = previousX + image.getWidth()
      groundImageSet.append(first) // Add to the end
    }

  }

  def create(g: Graphics): Unit = {
    for (img <- groundImageSet) {
      g.drawImage(img.image, img.x, Ground.GROUND_Y(panelHeight), null)
    }
  }
}