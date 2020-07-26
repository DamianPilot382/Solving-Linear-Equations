import java.io.IOException;
import java.util.Scanner;

public class Main{

    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException{

        LinearSystem system = getSystem();

        double[] x = chooseSolveMethod(system);

        System.out.println("\n\nThe Solution is:");
        for(double i : x){
            System.out.println(i + " ");
        }

    }

    public static LinearSystem getSystem(){

        do{
            System.out.println("Would you like to enter your own input, random or use a file?");
            System.out.println("Type 'input' to type a matrix.");
            System.out.println("Type 'random' to get a random matrix.");
            System.out.println("Type 'file' to use a file.");
            System.out.print("Your choice: ");

            String input = scanner.nextLine().toLowerCase();

            if(input.equals("input")){
                return getSystemFromInput();
            }else if(input.equals("random")){
                return getRandomMatrix();
            }else if(input.equals("file")){
                return getSystemFromFile();
            }
            System.out.println("That wasn't quite right. Let's try again.\n");
        }while(true);
    }

    public static LinearSystem getSystemFromInput(){
        System.out.print("Enter the number of equations/variables: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        double[][] coefficients = new double[n][n];
        double[] b = new double[n];

        for(int i = 0; i < n; i++){
            System.out.println("Please enter equation #"+(i+1)+", separating each term with a space.");
            try{
                String[] equation = scanner.nextLine().split("\\s");

                if(equation.length != n+1)
                    throw new Exception();

                for(int j = 0; j < equation.length-1; j++){
                    coefficients[i][j] = Double.parseDouble(equation[j]);
                }
                b[i] = Double.parseDouble(equation[equation.length - 1]);
            }catch(Exception e){
                System.out.println("That wasn't recognized. Try again.");
                i--;
            }
        }

        LinearSystem system = new LinearSystem(coefficients, b);

        System.out.println("The Linear System is:");
        System.out.println(system);

        return system;
    }

    public static LinearSystem getRandomMatrix(){
        do{
            try{
                System.out.println("Enter the matrix size that you want to use as an integer");

                int n = scanner.nextInt();
                scanner.nextLine();

                LinearSystem system = LinearSystem.randomSystem(n);

                System.out.println("The Linear System is:");
                System.out.println(system);

                return system;

            }catch(Exception e){
                System.out.println("That wasn't right. Try again.\n");
            }

        }while(true);
    }

    public static LinearSystem getSystemFromFile(){
        do{
            System.out.println("What is the name of the file you want to use? Don't forget to type the file extension!");
            System.out.print("Your file name: ");

            String fileName = scanner.nextLine();

            System.out.println("Opening " + fileName);

            try{
                LinearSystem system = LinearSystem.createMatrixFromFile(fileName);
                System.out.println("File opened, input found as:");
                System.out.print(system);

                return system;

            }catch(Exception e){
                System.out.println("That file wasn't found or is in the wrong format. Make sure the file is in the same directory as the application and you spelled it corectly.");
            }
        }while(true);

    }

    public static double[] getInitialX(int n){

        do{
            System.out.println("What would you like to do for the starting value of x?");
            System.out.println("Type '0' to start with zeroes.");
            System.out.println("Type 'random' to start with random values.");
            System.out.println("Type 'input' to provide the starting values.");

            String choice = scanner.nextLine().toLowerCase();

            if(choice.equals("0")){
                System.out.println("Starting with initial guess at 0.");
                return new double[n];
            }else if(choice.equals("random")){
                System.out.println("Starting with random initial guess.");
                return getRandomGuessX(n);
            }else if(choice.equals("input")){
                System.out.println("Starting with user guess.");
                return getGuessX(n);
            }

            System.out.println("That wasn't right. Try again.\n");

        }while(true);
    }

    public static double[] getRandomGuessX(int n){

        do{
            try{
                System.out.print("Enter a minimum value: ");

                double min = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("enter a maximum value: ");

                double max = scanner.nextDouble();
                scanner.nextLine();

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

    public static double[] getGuessX(int n){
        double[] x = new double[n];
        
        do{
            
            System.out.println("Enter the initial guess matrix with size " + n + ", separating each term with a space.");

            String[] input = scanner.nextLine().split("\\s");

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

    public static double[] chooseSolveMethod(LinearSystem system){

        do{
            System.out.println("What method would you like to use?");
            System.out.println("Type 'pivoting' for scaled partial pivoting for Gaussian elimination method.");
            System.out.println("Type 'jacobi' for Jacobi iterative method.");
            System.out.println("Type 'gauss' for the Gauss-Seidel method.");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine().toLowerCase();

            if(choice.equals("pivoting")){
                System.out.println("Solving with Scaled Partial Pivoting method for Gaussian Elimination.");
                return solveWithPivoting(system);
            }else if(choice.equals("jacobi")){
                System.out.println("Solving with Jacobi Iterative Method.");
                return solveWithJacobi(system);
            }else if(choice.equals("gauss")){
                System.out.println("Solving with Gauss-Seidl Method.");
                return solveWithGauss(system);
            }

            System.out.println("That wasn't right. Let's try again.\n");
        }while(true);

    }

    public static double[] solveWithPivoting(LinearSystem system){
        return Gauss.solve(system);
    }

    public static double[] solveWithJacobi(LinearSystem system){
        double error = getError();

        double[] x = getInitialX(system.n);

        return Jacobi.solve(system, x, error);

    }

    public static double[] solveWithGauss(LinearSystem system){

        double error = getError();

        double[] x = getInitialX(system.n);

        return GaussSeidel.solve(system, x, error);

    }

    public static double getError(){
        do{

            try{

                System.out.println("What is the maximum error?");

                double error = scanner.nextDouble();
                scanner.nextLine();

                return error;
            
            }catch(Exception e){
                System.out.println("That wasn't right. Try again.\n");
            }

        }while(true);
    }


}