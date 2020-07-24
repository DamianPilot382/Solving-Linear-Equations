public class Gauss {

    public static double[] solve(LinearSystem system){
        double[][] a = system.coefficients;
        double[] b = system.b;
        int n = system.n;
        int[] l = gauss(n, a);
        b(n, a, b, l);
        return solver(n, a, b, l);
    }

    public static int[] gauss(int n, double[][] a) {

        int[] l = new int[n];

        int i = 0;
        int j = 0;
        int k = 0;

        double smax = 0;
        double[] s = new double[n];

        double rmax = 0;
        double r = 0;

        double xmult = 0;

        for(i = 0; i < n; i++){
            l[i] = i;
            smax = 0;
            for(j = 0; j < n; j++){
                smax = Math.max(smax, Math.abs(a[i][j]));
            }
            s[i] = smax;
        }

        for(k = 0; k < n-1; k++){
            rmax = 0;
            for(i = k; i < n; i++){
                r = Math.abs(a[l[i]][k]/s[l[i]]);
                if(r > rmax){
                    rmax = r;
                    j = i;
                }
            }
            int temp = l[j];
            l[j] = l[k];
            l[k] = temp;

            for(i = k+1; i < n; i++){
                xmult = a[l[i]][k]/a[l[k]][k];
                a[l[i]][k] = xmult;
                for(j = k+1; j < n; j++){
                    a[l[i]][j] = a[l[i]][j] - xmult * a[l[k]][j];
                }
            }
        }

        return l;

    }

    public static void b(int n, double[][] a, double[]b, int[] l){
        for(int k = 0; k < n-1; k++){
            for(int i = k+1; i < n; i++){
                b[l[i]] = b[l[i]] - a[l[i]][k]*b[l[k]];
            }
        }
    }

    public static double[] solver(int n, double[][] a, double[] b, int[] l){
        int i = 0;
        int k = 0;
        
        double sum = 0;
        
        double[] x = new double[n];

        for(k = 0; k < n-1; k++){
            for(i = k + 1; i < n; i++){
                b[l[i]] = b[l[i]] - a[l[i]][k] * b[l[k]];
            }
        }

        x[n-1] = b[l[n-1]]/a[l[n-1]][n-1];

        for(i = n-2; i >=0; i--){
            sum = b[l[i]];
            for(int j = i + 1; j < n; j++){
                sum = sum - a[l[i]][j]*x[j];
            }
            x[i] = sum/a[l[i]][i];
        }

        return x;

    }


    
}