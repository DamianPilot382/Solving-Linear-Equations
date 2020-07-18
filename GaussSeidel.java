import java.util.Arrays;
public class GaussSeidel {
        
    
    public static void solve(double[][] a, double[] b, double[] x){

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
                    return;
                }

                for(j = 0; j < i-1; j++){
                    sum = sum - a[i][j]*x[j];
                }

                for(j = i + 1; j < n; j++){
                    sum = sum - a[i][j]*x[j];
                }
                x[i] = sum/diag;
            }
        }

        System.out.println(k);

        for(int t = 0; t < n; t++){
            System.out.println(x[t]);
        }

    }
    

}