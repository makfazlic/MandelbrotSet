import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class DirectDrawDemo extends JPanel{
    static double re_init = -2;
    static double re_fin = 2;
    static double im_init = -2;
    static double im_fin = 2;
    static int times_zoomed = 1;
    private BufferedImage canvas;

    public DirectDrawDemo(int width,
     int height,
     double re_init,
     double re_fin ,
     double im_init,
     double im_fin
     ) {
         
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(new Color( 0, 0, 0));
        drawRect(width, height, re_init,re_fin, im_init, im_fin);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }
    public static double[] zoomIn(){
        double[] coords = new double[4];
        coords[0] = re_init + 0.05;
        coords[1] = re_fin - 0.05;
        coords[2] = im_init + 0.05;
        coords[3] = im_fin - 0.05;
        re_init = coords[0];
        re_fin = coords[1];
        im_init = coords[2];
        im_fin = coords[3];
        return coords;
    }
    public static double[] zoomOut(){
        double[] coords = new double[4];
        coords[0] = re_init - 0.1;
        coords[1] = re_fin + 0.1;
        coords[2] = im_init - 0.1;
        coords[3] = im_fin + 0.1;
        re_init = coords[0];
        re_fin = coords[1];
        im_init = coords[2];
        im_fin = coords[3];
        return coords;
    }
    public static double[] goUp(){
        double[] coords = new double[4];
        coords[0] = re_init;
        coords[1] = re_fin;
        coords[2] = im_init + 0.01;
        coords[3] = im_fin + 0.01;
        re_init = coords[0];
        re_fin = coords[1];
        im_init = coords[2];
        im_fin = coords[3];
        return coords;
    }
    public static double[] goDown(){
        double[] coords = new double[4];
        coords[0] = re_init;
        coords[1] = re_fin;
        coords[2] = im_init - 0.01;
        coords[3] = im_fin - 0.01;
        re_init = coords[0];
        re_fin = coords[1];
        im_init = coords[2];
        im_fin = coords[3];
        return coords;
    }
    public void drawRect(
        int width, 
        int height,
        double re_init,
        double re_fin ,
        double im_init,
        double im_fin
        ) {

        int x1 = width/10;
        int y1 = height/10;
        int local_width = width - x1;
        int local_height = height - y1;
        //double im_fin = im_init + (re_fin-re_init);
        double re_factor = (re_fin-re_init)/(local_width);
        double im_factor = (im_fin-im_init)/(local_height);

        //RGB Conctrol
        //Position = 0.0     Color = (  0,   0,   0)
        //Position = 0.16    Color = ( 32, 107, 203)
        //Position = 0.42    Color = (237, 255, 255)
        //Position = 0.6425  Color = (255, 170,   0)
        //Position = 0.8575  Color = ( 24,  25,  26)
        // Implement rectangle drawing
        for (int x = x1; x < local_width; x++) {
            for (int y = y1; y < local_height; y++) {

                
                double re = re_init + x*re_factor;
                double im =  im_fin - y*im_factor;

                ComplexNumber pixel = new ComplexNumber(
                    re,
                    im
                );
                double mandel = new Mandelbrot(pixel,2,120).calculate();
                double color_value = (255 - mandel*(255/120));


                if (color_value/255 > 0.8575) {
                    Color color = new Color(24,25,26); // Color white
                    canvas.setRGB(x, y, color.getRGB());
                } else if (color_value/255 > 0.6425){
                    Color color = new Color(255, 170,   0); // Color white
                    canvas.setRGB(x, y, color.getRGB());
                } else if (color_value/255 > 0.42){
                    Color color = new Color(237, 255, 255); // Color white
                    canvas.setRGB(x, y, color.getRGB());
                } else if (color_value/255 > 0.16){
                    Color color = new Color( 32, 107, 203); // Color white
                    canvas.setRGB(x, y, color.getRGB());
                } else{
                    Color color = new Color(  0,   0, 0); // Color white
                    canvas.setRGB(x, y, color.getRGB());
                }

                //int color_value = (int) (255 - mandel*(255/120));
                //Color color = new Color(color_value, color_value, color_value); // Color white
                //canvas.setRGB(x, y, color.getRGB());
            }
        }
        canvas.setRGB(x1, y1, Color.RED.getRGB());
        canvas.setRGB(local_width, local_height, Color.RED.getRGB());

        repaint();
    }

    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;

        JFrame frame = new JFrame("Direct draw demo");
        JButton zoomin = new JButton("Zoom in");
        JButton zoomout = new JButton("Zoom out");

        JButton up = new JButton("Up");
        JButton down = new JButton("Down");


        zoomin.setBounds(100, 25, 150,50);
        zoomout.setBounds(320, 25, 150,50);
        up.setBounds(520, 25, 150,50);
        down.setBounds(750, 25, 150,50);

        DirectDrawDemo panel = new DirectDrawDemo(width, height, re_init,re_fin, im_init, im_fin);
        frame.add(zoomin);
        frame.add(zoomout);
        frame.add(up);
        frame.add(down);
        frame.add(panel, BorderLayout.CENTER);
        zoomin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double[] coords = zoomIn();
            DirectDrawDemo panel = new DirectDrawDemo(width, height,
            coords[0], 
            coords[1], 
            coords[2], 
            coords[3]             
            );
            frame.add(panel, BorderLayout.CENTER);  
            frame.pack();
            frame.setVisible(true);

            System.out.println(re_init + " " + re_fin + " " + im_init + " " + im_fin);
            }
        });
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double[] coords = goUp();
            DirectDrawDemo panel = new DirectDrawDemo(width, height,
            coords[0], 
            coords[1], 
            coords[2], 
            coords[3]             
            );
            frame.add(panel, BorderLayout.CENTER);  
            frame.pack();
            frame.setVisible(true);

            System.out.println(re_init + " " + re_fin + " " + im_init + " " + im_fin);
            }
        });
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double[] coords = goDown();
            DirectDrawDemo panel = new DirectDrawDemo(width, height,
            coords[0], 
            coords[1], 
            coords[2], 
            coords[3]             
            );
            frame.add(panel, BorderLayout.CENTER);  
            frame.pack();
            frame.setVisible(true);

            System.out.println(re_init + " " + re_fin + " " + im_init + " " + im_fin);
            }
        });
        zoomout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double[] coords = zoomOut();
            DirectDrawDemo panel = new DirectDrawDemo(width, height,
            coords[0], 
            coords[1], 
            coords[2], 
            coords[3]             
            );
            frame.add(panel, BorderLayout.CENTER);  
            frame.pack();
            frame.setVisible(true);

            System.out.println(re_init + " " + re_fin + " " + im_init + " " + im_fin);
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}