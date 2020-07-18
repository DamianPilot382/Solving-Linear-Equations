import java.io.IOException;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) throws IOException{
        LinearSystem system = LinearSystem.createMatrixFromFile("testMatrix.txt");

        //Gauss.solve(3, system.coefficients, system.b);
        GaussSeidel.solve(system.coefficients, system.b, new double[]{0, 0, 0});

        System.out.println(system);





    }


}