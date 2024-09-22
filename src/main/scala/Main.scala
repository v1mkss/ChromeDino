package dev.v1mkss.chrome.dino

import java.awt.*
import java.awt.event.*
import javax.swing.*

object Main {

  val WIDTH: Int = 800
  val HEIGHT: Int = 500

  private def createAndShowGUI(): Unit = {
    val mainWindow = new JFrame("T-Rex Run")
    mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val container: Container = mainWindow.getContentPane
    val gamePanel = new GamePanel with KeyListener
    gamePanel.addKeyListener(gamePanel)
    gamePanel.setFocusable(true)

    container.setLayout(new BorderLayout)
    container.add(gamePanel, BorderLayout.CENTER)

    mainWindow.setSize(WIDTH, HEIGHT)
    mainWindow.setResizable(false)
    mainWindow.setVisible(true)
  }

  def main(args: Array[String]): Unit = SwingUtilities.invokeLater(() => createAndShowGUI())

}