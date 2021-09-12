import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.util.Random;


public class Game extends JPanel implements ActionListener{
    Timer timer;
    char direction;
    private static int snakeSize = 3;//Set starting size of the snake
    private static int snakeX[] = new int[50];
    private static int snakeY[] = new int[50];
    private final static int ITEM_SIZE = 20;
    private final static int SCREEN_HEIGHT = 600;
    private final static int SCREEN_WIDTH = 600;
    private static boolean gameOver = true;
    Random random = new Random();
    private int apple[] = new int[2];
    
    Game(){   
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
        this.setVisible(true);

        //Add button to start game
        JButton myButton = new JButton("Start Game");
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked");
                gameOver = false;//Temporary implementation to prevent drawing until game is started
                myButton.setVisible(false);
                init();
            }
        });
        //myButton.setBounds(100, 100, 50, 100); does not work . .
        this.add(myButton);
    }

    public void init(){

        //Set inital random start values for the food
        apple[0] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);
        apple[1] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);

        //Initialize the snake starting position
        for(int i = 0; i < snakeSize; i++){
            //starting from top left
            snakeX[i] = ITEM_SIZE * 4 -  i * ITEM_SIZE; // snake head is at the right the rest behind it to the left
            snakeY[i] = 0; // starting out in a horizontal position
        }

        direction = 'X'; // Make sure there is no movement before first input
        timer = new Timer(200, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //bad implementation
        if(!gameOver){
            drawBoard(g);
        }
        
    }
    
    //Do all the drawing here
    public void drawBoard(Graphics g){
        setBackground(Color.black);
        //Draw all the snake squares at their respective positions
        for(int i = 0; i < snakeSize; i++){
            g.setColor(Color.green);
            //Use a different color for snake head
            if(i == 0){
                g.setColor(Color.RED);
            }
            g.fillRect(snakeX[i], snakeY[i],ITEM_SIZE, ITEM_SIZE);
        }
        //Draw the food
        g.setColor(Color.BLUE);
        g.fillRect(apple[0], apple[1], ITEM_SIZE, ITEM_SIZE);
    }

    public void move(){
        checkFood();
        //Move the snake according to the input
        //Temporary fix to prevent snake from moving at the start
        if(direction != 'X'){
            //Each snake item moves to the position of the item before it
            for(int i = snakeSize - 1; i > 0; i--){
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
        }
        
        switch(direction){
            case 'R':
                snakeX[0] += ITEM_SIZE;
                break;
            case 'L':
                snakeX[0] -= ITEM_SIZE;
                break;
            case 'U':
                snakeY[0] -= ITEM_SIZE;
                break;
            case 'D':
                snakeY[0] += ITEM_SIZE;
                break;
        }
    }

    public void checkFood(){
        //check after each move if snake head is at food
        if(apple[0] == snakeX[0] && apple[1] == snakeY[1]){   
            snakeSize++; //increase snake size

            //spawn another apple
            apple[0] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);
            apple[1] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);
        }       
    }

    public void validateMov(){
        //game logic implemented here

        //Check bounds first
        if(snakeX[0] == 0 || snakeX[0] == SCREEN_WIDTH - ITEM_SIZE){
            gameOver = true;//game is over if snake head touches the screen borders
        }
        //Perform same checks along the Y-axis
        if(snakeY[0] < 0 || snakeY[0] == SCREEN_HEIGHT - ITEM_SIZE){
            gameOver = true;//game is over if snake head touches the screen borders
        }
        
        if(gameOver){
            //Stop the game if game ends according to the rules
            timer.stop();
        }
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        //called repeatedly by the timer
        move();
        validateMov();
        checkFood();
        repaint();
       // System.out.println("I am here");
        
    }
    public class MyKeyAdapter extends KeyAdapter{
        
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 'D';
                    break;
            }
        }
    }
}