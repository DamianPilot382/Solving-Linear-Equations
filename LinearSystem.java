import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;

public class LinearSystem{

    protected double[][] coefficients;
    protected double[] b;

    public LinearSystem(double[][] coefficients, double[] b){
        this.coefficients = coefficients;
        this.b = b;
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