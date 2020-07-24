import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;

public class LinearSystem{

    protected double[][] coefficients;
    protected double[] b;
    protected int n;

    public LinearSystem(double[][] coefficients, double[] b){
        this.coefficients = coefficients;
        this.b = b;
        this.n = b.length;
    }

    public static LinearSystem createMatrix(String input) throws InputMismatchException {

        String[] system = input.split("\\n\\s{1,}");
        int varCount = system[0].split("\\s").length-1;

        double[][] coefficients = new double[varCount][varCount];
        double[] b = new double[varCount];

        for(int i = 0; i < system.length; i++){
            String[] vars = system[i].split("\\s");

            for(int j = 0; j < vars.length - 1; j++){
                coefficients[i][j] = Double.parseDouble(vars[j]);
            }

            b[i] = Double.parseDouble(vars[vars.length - 1]);

        }

        return new LinearSystem(coefficients, b);

    }

    public static LinearSystem createMatrixFromFile(String fileName) throws IOException, InputMismatchException {
        String input = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        return createMatrix(input);

    }

    public static LinearSystem randomSystem(int n){
        double[][] a = new double[n][n];
        double[] b = new double[n];

        double min = -5;
        double max = 10;

        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                a[i][j] = (Math.random() * ((max-min)+1) + min);
            }

            b[i] = (Math.random() * ((max-min)+1) + min);
        }

        return new LinearSystem(a, b);

    }

    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < this.coefficients.length; i++){
            for(int j = 0; j < this.coefficients[0].length; j++){
                builder.append(coefficients[i][j]+" ");
            }
            builder.append(b[i] + "\n");
        }

        return builder.toString();
    }
}