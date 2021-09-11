import javax.swing.*;

public class Window extends JFrame{
   Window(){
       this.add(new Game());
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setTitle("Snake Game");
       this.setLocationRelativeTo(null);
       this.setSize(600, 600);
       this.setVisible(true);

   }

}
