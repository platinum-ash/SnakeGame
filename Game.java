import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Game extends JPanel implements ActionListener{
    Timer timer;
    char direction;
    private static int snakeSize = 3;
    private static int snakeX[] = new int[50];
    private static int snakeY[] = new int[50];
    private static int ITEM_SIZE = 20;
    private static int SCREEN_HEIGHT = 600;
    private static int SCREEN_WIDTH = 600;
    private static boolean gameOver = false;
    Random random = new Random();
    private int apple[] = {(random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE) , (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE)};
    

    Game(){   
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
        init();
    }
    public void init(){
        //set all dot loc
        for(int i = 0; i < snakeSize; i++){
            //starting from top left
            snakeX[i] = ITEM_SIZE * 4 -  i * ITEM_SIZE; // snake head is at the right the rest behind it to the left
            snakeY[i] = 0; // starting out in a horizontal position
        }
        timer = new Timer(200, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //Do all the drawing here
        drawBoard(g);
    }
    public void drawBoard(Graphics g){

        setBackground(Color.gray);
       
        //Draw all the snake Dots at their respective positions
        g.setColor(Color.green);
        for(int i = 0; i < snakeSize; i++){
            g.fillRect(snakeX[i], snakeY[i],ITEM_SIZE, ITEM_SIZE);
        }
        //Draw the food
        g.setColor(Color.GREEN);
        g.fillRect(apple[0], apple[1], ITEM_SIZE, ITEM_SIZE);
    }

    public void move(){
        checkFood();
        //Move the snake according to the input
        //Each snake item moves to the position of the item before it
        for(int i = snakeSize - 1; i > 0; i--){
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
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
            //increase snake size
            snakeSize++;
            //spawn another apple
            apple[0] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);
            apple[1] = (random.nextInt( (SCREEN_HEIGHT/ITEM_SIZE))* ITEM_SIZE);
            System.out.println("The x coordinate is : " + apple[0] + " the y coordinate is : "+ apple[1]);
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
            gameOver = true;
        }
        //For debugging
        if(gameOver){
            System.out.println("Game over");
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