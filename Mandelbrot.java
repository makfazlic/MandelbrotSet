import java.lang.Math;

public class Mandelbrot {
    
    ComplexNumber c;
    int threshold;
    int max_iteration;


    public Mandelbrot(ComplexNumber c, int threshold, int max_iteration){
        this.c = c;
        this.threshold = threshold;
        this.max_iteration = max_iteration;
    }

    public int calculate() {
        // Z_(n) = (Z_(n-1))^2 + c
        // Z_(0) = c
        ComplexNumber z = new ComplexNumber();
        int i = 0;
        while (i < max_iteration & Math.sqrt(ComplexNumber.multiply(z, z.conjugate()).getRe()) < threshold) {
            ComplexNumber z_squared = ComplexNumber.multiply(z, z);
            ComplexNumber z_squared_with_c = ComplexNumber.add(z_squared, c);
            z = z_squared_with_c;

            i++;
        }
        return i;
    }
}
