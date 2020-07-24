import java.io.IOException;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) throws IOException{

        Scanner in = new Scanner(System.in);

        LinearSystem system = getSystem(in);

        double[] x = chooseSolveMethod(in, system);

        System.out.println("\n\nThe Solution is:");
        for(double i : x){
            System.out.println(i + " ");
        }

    }

    public static LinearSystem getSystem(Scanner in){

        do{
            System.out.println("Would you like to enter your own input, random or use a file?");
            System.out.println("Type 'input' to type a matrix.");
            System.out.println("Type 'random' to get a random matrix.");
            System.out.println("Type 'file' to use a file.");
            System.out.print("Your choice: ");

            String input = in.nextLine().toLowerCase();

            if(input.equals("input")){
                return getSystemFromInput(in);
            }else if(input.equals("random")){
                return getRandomMatrix(in);
            }else if(input.equals("file")){
                return getSystemFromFile(in);
            }
            System.out.println("That wasn't quite right. Let's try again.\n");
        }while(true);
    }

    public static LinearSystem getSystemFromInput(Scanner in){
        System.out.print("Enter the number of equations/variables: ");
        int n = in.nextInt();
        in.nextLine();

        double[][] coefficients = new double[n][n];
        double[] b = new double[n];

        for(int i = 0; i < n; i++){
            System.out.println("Please enter equation #"+(i+1)+", separating each term with a space.");
            String[] equation = in.nextLine().split("\\s");

            for(int j = 0; j < equation.length-1; j++){
                coefficients[i][j] = Double.parseDouble(equation[j]);
            }
            b[i] = Double.parseDouble(equation[equation.length - 1]);
        }

        LinearSystem system = new LinearSystem(coefficients, b);

        System.out.println("The Linear System is:");
        System.out.println(system);

        return system;
    }

    public static LinearSystem getRandomMatrix(Scanner in){
        do{
            try{
                System.out.println("Enter the matrix size that you want to use as an integer");

                int n = in.nextInt();
                in.nextLine();

                return LinearSystem.randomSystem(n);

            }catch(Exception e){
                System.out.println("That wasn't right. Try again.\n");
            }

        }while(true);
    }

    public static LinearSystem getSystemFromFile(Scanner in){
        do{
            System.out.println("What is the name of the file you want to use? Don't forget to type the file extension!");
            System.out.print("Your file name: ");

            String fileName = in.nextLine();

            System.out.println("Opening " + fileName);

            try{
                LinearSystem system = LinearSystem.createMatrixFromFile(fileName);
                System.out.println("File opened, input found as:");
                System.out.print(system);

                return system;

            }catch(IOException e){
                System.out.println("That file wasn't found. Make sure the file is in the same directory as the application and you spelled it corectly.");
            }
        }while(true);

    }

    public static double[] getInitialX(Scanner in, int n){

        do{
            System.out.println("What would you like to do for the starting value of x?");
            System.out.println("Type '0' to start with zeroes.");
            System.out.println("Type 'random' to start with random values.");
            System.out.println("Type 'input' to provide the starting values.");

            String choice = in.nextLine().toLowerCase();

            if(choice.equals("0")){
                System.out.println("Starting with initial guess at 0.");
                return new double[n];
            }else if(choice.equals("random")){
                System.out.println("Starting with random initial guess.");
                return getRandomGuessX(in, n);
            }else if(choice.equals("input")){
                System.out.println("Starting with user guess.");
                return getGuessX(in, n);
            }

            System.out.println("That wasn't right. Try again.\n");

        }while(true);
    }

    public static double[] getRandomGuessX(Scanner in, int n){

        do{
            try{
                System.out.print("Enter a minimum value: ");

                double min = in.nextDouble();
                in.nextLine();

                System.out.println("enter a maximum value: ");

                double max = in.nextDouble();
                in.nextLine();

                double[] x = new double[n];

                for(int i = 0; i < x.length; i++){
                    x[i] = (Math.random() * ((max-min)+1) + min);
                }

                return x;

            }catch(Exception e){
                System.out.println("That wasn't right. Let's try again.\n");
            }
        }while(true);
    }

    public static double[] getGuessX(Scanner in, int n){
        double[] x = new double[n];
        
        do{
            
            System.out.println("Enter the initial guess matrix with size " + n + ", separating each term with a space.");

            String[] input = in.nextLine().split("\\s");

            int i = 0;
            for( ; i < input.length; i++){
                try{
                    x[i] = Double.parseDouble(input[i]);
                }catch(Exception e){
                    System.out.println("That wasn't right, let's try again.\n");
                    break;
                }
            }

            if(i==n){
                return x;
            }

        }while(true);

    }

    public static double[] chooseSolveMethod(Scanner in, LinearSystem system){

        do{
            System.out.println("What method would you like to use?");
            System.out.println("Type 'pivoting' for scaled partial pivoting for Gaussian elimination method.");
            System.out.println("Type 'jacobi' for Jacobi iterative method.");
            System.out.println("Type 'gauss' for the Gauss-Seidel method.");
            System.out.print("Your choice: ");

            String choice = in.nextLine().toLowerCase();

            if(choice.equals("pivoting")){
                System.out.println("Solving with Scaled Partial Pivoting method for Gaussian Elimination.");
                return solveWithPivoting(system);
            }else if(choice.equals("jacobi")){
                System.out.println("Solving with Jacobi Iterative Method.");
                return solveWithJacobi(in, system);
            }else if(choice.equals("gauss")){
                System.out.println("Solving with Gauss-Seidl Method.");
                return solveWithGauss(in, system);
            }

            System.out.println("That wasn't right. Let's try again.\n");
        }while(true);

    }

    public static double[] solveWithPivoting(LinearSystem system){
        return Gauss.solve(system);
    }

    public static double[] solveWithJacobi(Scanner in, LinearSystem system){
        double error = getError(in);

        double[] x = getInitialX(in, system.n);

        return Jacobi.solve(system, x, error);

    }

    public static double[] solveWithGauss(Scanner in, LinearSystem system){

        double error = getError(in);

        double[] x = getInitialX(in, system.n);

        return GaussSeidel.solve(system, x, error);

    }

    public static double getError(Scanner in){
        do{

            try{

                System.out.println("What is the maximum error?");

                double error = in.nextDouble();
                in.nextLine();

                return error;
            
            }catch(Exception e){
                System.out.println("That wasn't right. Try again.\n");
            }

        }while(true);
    }


}