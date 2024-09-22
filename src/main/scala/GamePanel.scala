package dev.v1mkss.chrome.dino

import components.{Dino, Ground, Obstacles}

import java.awt.*
import java.awt.event.*
import javax.swing.*
import scala.compiletime.uninitialized

class GamePanel extends JPanel with KeyListener with Runnable {

  import GamePanel.*

  private var animator: Thread = uninitialized
  private var running = false
  private var gameOver = false

  private var ground: Ground = uninitialized
  private var dino: Dino = uninitialized
  private var obstacles: Obstacles = uninitialized

  private var score = 0

  init()

  private def init(): Unit = {
    WIDTH = Main.WIDTH
    HEIGHT = Main.HEIGHT

    ground = new Ground(HEIGHT)
    dino = new Dino()
    obstacles = new Obstacles((WIDTH * 1.5).toInt)

    score = 0

    setSize(WIDTH, HEIGHT)
    setVisible(true)
  }

  override def paint(g: Graphics): Unit = {
    super.paint(g)
    g.setFont(new Font("Courier New", Font.BOLD, 25))
    g.drawString(score.toString, getWidth / 2 - 5, 100)
    ground.create(g)
    obstacles.create(g)
    dino.create(g)
  }

  override def run(): Unit = {
    running = true

    while (running) {
      updateGame()
      repaint()
      try {
        Thread.sleep(50)
      } catch {
        case e: InterruptedException => e.printStackTrace()
      }
    }
  }

  private def updateGame(): Unit = {
    score += 1

    ground.update()
    obstacles.update()

    if (obstacles.hasCollided) {
      dino.die()
      repaint()
      running = false
      gameOver = true
      println("collide")
    }
  }


  private def reset(): Unit = {
    score = 0
    println("reset")
    obstacles.resume()
    gameOver = false
  }

  override def keyTyped(e: KeyEvent): Unit = {
    if (e.getKeyChar == ' ') {
      if (gameOver) reset()
      if (animator == null || !running) {
        println("Game starts")
        animator = new Thread(this)
        animator.start()
        dino.startRunning()
      } else {
        dino.jump()

      }
    }
  }

  override def keyPressed(e: KeyEvent): Unit = {
    // println("keyPressed: " + e)
  }

  override def keyReleased(e: KeyEvent): Unit = {
    // println("keyReleased: " + e)
  }
}

object GamePanel {
  var WIDTH: Int = uninitialized
  var HEIGHT: Int = uninitialized
}
