import java.util.Random;
import java.swing.*;

public class plot{

    public static void main(String[] args) {
        Random rand = new Random();
        double max = 10;
        double min = -10;
        for (double i = min; i < max; i = i + 5) {
            for (double j = min; j < max; j = j + 5) {
                double minimise_i = i/10;
                double minimise_j = j/10;
                //System.out.println(minimise_i+" "+minimise_j);
                ComplexNumber test = new ComplexNumber(minimise_i,minimise_j);
                //System.out.println(test);
                System.out.println(test + " " +new Mandelbrot(test,2,80).calculate());
                
            }
        }
    
        int width = 640;
        int height = 480;
        JFrame frame = new JFrame("Direct draw demo");

        DirectDrawDemo panel = new DirectDrawDemo(width, height);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}