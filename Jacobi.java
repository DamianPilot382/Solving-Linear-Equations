import java.util.Arrays;

public class Jacobi {

    public static double[] solve(LinearSystem system, double[] x, double error){

        double[][] a = system.coefficients;
        double[] b = system.b;

        double kmax = 100;
        double delta = Math.pow(10, -10);
        double epsilon = .5 * Math.pow(10, -4);

        int i = 0;
        int j = 0;
        int k = 0;
        int n = a.length;

        double diag = 0;
        double sum = 0;

        double[] y = new double[n];

        for(k = 0; k < kmax; k++){
            y = Arrays.copyOf(x, x.length);
            for(i = 0; i < n; i++){
                sum = b[i];
                diag = a[i][i];

                if(Math.abs(diag) < delta){
                    System.out.println("Diagonal element too small");
                    return x;
                }

                for(j = 0; j < n; j++){
                    if(j != i){
                        sum = sum - a[i][j]*y[j];
                    }
                }
                x[i] = sum/diag;
            }
        }

        //System.out.println(k);

        // for(int t = 0; t < n; t++){
        //     System.out.println(x[t]);
        // }

        return x;

    }
    
    public static double vectorMagnitude(double[] x){

        double sum = 0;

        for(double next : x){
            sum += next * next;
        }

        return Math.sqrt(sum);

    }

}