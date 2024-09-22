package dev.v1mkss.chrome.dino
package utils

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

object Utils {
  def getResource(path: String): BufferedImage = {
    var img: BufferedImage = null
    try {
      val inputStream = getClass.getResource(path)
      if (inputStream == null) {
        throw new IllegalArgumentException(s"Resource not found: $path")
      }
      img = ImageIO.read(inputStream)
      if (img == null) {
        throw new IOException(s"Failed to read image: $path")
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
    img
  }
}