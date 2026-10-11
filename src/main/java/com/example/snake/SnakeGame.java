package com.example.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int DEFAULT_TILE_SIZE = 20;

    private final int boardWidth;
    private final int boardHeight;
    private final int tileSize;
    private final Random random;
    private final Timer gameLoop;

    private Tile food;
    private Tile snakeHead;
    private ArrayList<Tile> snakeBody;
    private int velocityX;
    private int velocityY;
    private boolean gameOver;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = DEFAULT_TILE_SIZE;
        this.random = new Random();
        this.velocityX = 1;
        this.velocityY = 0;
        this.gameOver = false;

        this.snakeBody = new ArrayList<>();
        this.snakeHead = new Tile(5, 5);
        this.snakeBody.add(new Tile(4, 5));
        this.snakeBody.add(new Tile(3, 5));
        this.snakeBody.add(new Tile(2, 5));

        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(this);

        placeFood();
        gameLoop = new Timer(120, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(26, 26, 26));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                g.setColor(new Color(50, 50, 50));
                g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        for (Tile snakePart : snakeBody) {
            g.setColor(new Color(34, 197, 94));
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        if (gameOver) {
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRect(0, 0, boardWidth * tileSize, boardHeight * tileSize);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Game Over", 110, 185);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Press any arrow key to restart", 60, 215);
        }
    }

    public void placeFood() {
        int randomX;
        int randomY;

        do {
            randomX = random.nextInt(boardWidth);
            randomY = random.nextInt(boardHeight);
        } while (containsSnake(randomX, randomY));

        food = new Tile(randomX, randomY);
    }

    public boolean collision(Tile t1, Tile t2) {
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void move() {
        if (gameOver) {
            return;
        }

        Tile nextHead = new Tile(snakeHead.x + velocityX, snakeHead.y + velocityY);

        if (nextHead.x < 0 || nextHead.x >= boardWidth || nextHead.y < 0 || nextHead.y >= boardHeight) {
            gameOver = true;
            return;
        }

        for (Tile snakePart : snakeBody) {
            if (collision(nextHead, snakePart)) {
                gameOver = true;
                return;
            }
        }

        snakeBody.add(0, new Tile(snakeHead.x, snakeHead.y));
        snakeHead = nextHead;

        if (collision(snakeHead, food)) {
            placeFood();
        } else {
            snakeBody.remove(snakeBody.size() - 1);
        }

        if (snakeBody.size() > 0 && collision(snakeHead, snakeBody.get(0))) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameOver) {
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                resetGame();
            }
            return;
        }

        if (key == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (key == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (key == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (key == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    private boolean containsSnake(int x, int y) {
        if (collision(new Tile(x, y), snakeHead)) {
            return true;
        }

        for (Tile snakePart : snakeBody) {
            if (collision(new Tile(x, y), snakePart)) {
                return true;
            }
        }

        return false;
    }

    private void resetGame() {
        snakeBody = new ArrayList<>();
        snakeHead = new Tile(5, 5);
        snakeBody.add(new Tile(4, 5));
        snakeBody.add(new Tile(3, 5));
        snakeBody.add(new Tile(2, 5));
        velocityX = 1;
        velocityY = 0;
        gameOver = false;
        placeFood();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private static class Tile {
        private final int x;
        private final int y;

        private Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
