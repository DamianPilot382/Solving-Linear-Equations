public class Gauss {

    public static double[] solve(LinearSystem system){
        int[] l = getIndexArray(system);
        forwardElimination(system, l);
        return backSubstitution(system, l);
    }

    private static int[] getIndexArray(LinearSystem system) {

        int[] l = new int[system.n];

        int i = 0;
        int j = 0;
        int k = 0;

        double smax = 0;
        double[] s = new double[system.n];

        double rmax = 0;
        double r = 0;

        double xmult = 0;

        for(i = 0; i < system.n; i++){
            l[i] = i;
            smax = 0;
            for(j = 0; j < system.n; j++){
                smax = Math.max(smax, Math.abs(system.coefficients[i][j]));
            }
            s[i] = smax;
        }

        for(k = 0; k < system.n-1; k++){
            rmax = 0;
            for(i = k; i < system.n; i++){
                r = Math.abs(system.coefficients[l[i]][k]/s[l[i]]);
                if(r > rmax){
                    rmax = r;
                    j = i;
                }
            }
            int temp = l[j];
            l[j] = l[k];
            l[k] = temp;

            for(i = k+1; i < system.n; i++){
                xmult = system.coefficients[l[i]][k]/system.coefficients[l[k]][k];
                system.coefficients[l[i]][k] = xmult;
                for(j = k+1; j < system.n; j++){
                    system.coefficients[l[i]][j] = system.coefficients[l[i]][j] - xmult * system.coefficients[l[k]][j];
                }
            }

            System.out.println(system);
        }

        return l;

    }

    private static void forwardElimination(LinearSystem system, int[] l){
        for(int k = 0; k < system.n-1; k++){
            for(int i = k+1; i < system.n; i++){
                system.b[l[i]] = system.b[l[i]] - system.coefficients[l[i]][k]*system.b[l[k]];
            }
        }
    }

    private static double[] backSubstitution(LinearSystem system, int[] l){
        int i = 0;
        int k = 0;
        
        double sum = 0;
        
        double[] x = new double[system.n];

        for(k = 0; k < system.n-1; k++){
            for(i = k + 1; i < system.n; i++){
                system.b[l[i]] = system.b[l[i]] - system.coefficients[l[i]][k] * system.b[l[k]];
            }
        }

        x[system.n-1] = system.b[l[system.n-1]]/system.coefficients[l[system.n-1]][system.n-1];

        for(i = system.n-2; i >=0; i--){
            sum = system.b[l[i]];
            for(int j = i + 1; j < system.n; j++){
                sum = sum - system.coefficients[l[i]][j]*x[j];
            }
            x[i] = sum/system.coefficients[l[i]][i];
        }

        return x;

    }


    
}