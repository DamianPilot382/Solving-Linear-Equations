import java.io.IOException;
import java.util.Scanner;

public class Main{
    
    public static void main(String[] args) throws IOException{

        Scanner in = new Scanner(System.in);

        LinearSystem system = getSystem(in);

        // LinearSystem system = LinearSystem.createMatrixFromFile("testMatrix.txt");

        // Gauss.solve(3, system.coefficients, system.b);
        // GaussSeidel.solve(system.coefficients, system.b, new double[]{0, 0, 0});

        // System.out.println(system);





    }

    public static LinearSystem getSystem(Scanner in){

        do{
            System.out.println("Would you like to enter your own input or use a file?");
            System.out.println("Type 'input' to type a matrix.");
            System.out.println("Type 'file' to use a file.");
            System.out.print("Your choice: ");

            String input = in.nextLine();

            if(input.equals("input")){
                return getSystemFromInput(in);
            }else if(input.equals("file")){
                return getSystemFromFile(in);
            }
            System.out.println("That wasn't quite right. Let's try again.");
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
                    System.out.println("That wasn't right, let's try again.");
                    break;
                }
            }

            if(i==n){
                return x;
            }

        }while(true);

    }

    public static double get


}