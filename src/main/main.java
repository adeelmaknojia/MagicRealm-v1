package main;

import javax.swing.SwingUtilities;



public class main
{
    public static void main(String[] args) {
    	 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {                                           
                Player model = new Player();
                PlayerGUI view = new PlayerGUI();
                PlayerController controller = new PlayerController(model,view);
                controller.control();
            }
        });  
    }
}