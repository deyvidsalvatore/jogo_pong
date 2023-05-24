package com.deyvidsalvatore.pong;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class PongGame extends JFrame implements KeyListener, Runnable {

    private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    private int ballX = WIDTH / 2;
    private int ballY = HEIGHT / 2;
    private int ballXSpeed = -2;
    private int ballYSpeed = 2;
    private int paddle1Y = HEIGHT / 2 - 40;
    private int paddle2Y = HEIGHT / 2 - 40;

    private int player1Score = 0;
    private int player2Score = 0;

    private BufferedImage buffer;
    private Graphics bufferGraphics;

    private boolean gameRunning = true;

    public PongGame() {
        setTitle("Pong Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setLocationRelativeTo(null);

        addKeyListener(this);
        setFocusable(true);

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.getGraphics();

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (gameRunning) {
            updateGame();
            renderGame();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        if (!gameRunning) {
            return;
        }

        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballY <= 0 || ballY >= HEIGHT - 20) {
            ballYSpeed *= -1;
        }

        if (ballX <= 60 && ballY >= paddle1Y && ballY <= paddle1Y + 80) {
            ballXSpeed *= -1;
        }

        if (ballX >= WIDTH - 70 && ballY >= paddle2Y && ballY <= paddle2Y + 80) {
            ballXSpeed *= -1;
        }

        if (ballX < 0) {
            player2Score++;
            resetGame();
        }

        if (ballX > WIDTH) {
            player1Score++;
            resetGame();
        }
    }

    private void renderGame() {
        bufferGraphics.setColor(Color.WHITE);
        bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(ballX - 10, ballY - 10, 20, 20);

        bufferGraphics.fillRect(50, paddle1Y, 10, 80);
        bufferGraphics.fillRect(WIDTH - 60, paddle2Y, 10, 80);

        bufferGraphics.setFont(new Font("Arial", Font.BOLD, 24));
        bufferGraphics.drawString("Player 1: " + player1Score, 20, 30);
        bufferGraphics.drawString("Player 2: " + player2Score, WIDTH - 150, 30);

        Graphics g = getGraphics();
        g.drawImage(buffer, 0, 0, null);
        g.dispose();
    }

    private void resetGame() {
        ballX = WIDTH / 2;
        ballY = HEIGHT / 2;
        ballXSpeed *= -1;
        ballYSpeed = 2;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddle1Y -= 5;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            paddle1Y += 5;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddle2Y -= 5;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2Y += 5;
        }
    }

    public static void main(String[] args) {
        new PongGame();
    }
    
    // Outros métodos da interface KeyListener (keyTyped e keyReleased) omitidos
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
