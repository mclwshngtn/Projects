import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // initialize necessary variables
    static final int SCREEN_HEIGHT = 600, SCREEN_WIDTH = 600, UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 70;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 5, applesEaten, appleX, appleY;
    char direction = 'R';
    boolean running = false, gameOver = false;
    Timer timer;
    Random random;
    JButton restartButton;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // generates gridlines

            for (int i = 0; i < SCREEN_HEIGHT; i++) {
                // g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                // g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                Color color = new Color(28, 205, 105);
                for (int j = 0; j < SCREEN_WIDTH; j++) {
                    color = (i + j) % 2 == 0 ? new Color(28, 205, 105)
                            : new Color(96, 240, 159);
                    g.setColor(color);
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                    g.fillRect(j * UNIT_SIZE, i * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }

            // generates apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // generates snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(111, 33, 228));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(99, 28, 205));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // Score text
            g.setColor(Color.black);
            g.setFont(new Font(Font.SERIF, Font.PLAIN, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }

    // creates an apple at a random index
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        // check if head collides with apple
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        // check if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        // check if head collides with a border
        // Left
        if (x[0] < 0) {
            running = false;
        }
        // Right
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // Top
        if (y[0] < 0) {
            running = false;
        }
        // Bottom
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten,
                (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());

        g.setFont(new Font(Font.SERIF, Font.PLAIN, 75));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}