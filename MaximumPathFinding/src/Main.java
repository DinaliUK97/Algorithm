//Student-ID-w1742096, IIT ID-2018361
//Student Name: Dinali Uththama Kumarasiri

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static int[][] Graph;   // 2D array for storing the flow network
    public static int Nodes;

    public static void main(String[] args) {
        loop: while (true){
            Scanner sc = new Scanner(System.in);  // printing the option menu
            System.out.println("\n" +
                    "Hi... Choose the option\n" +
                    "1) Input a graph\n" +
                    "2) Get the maximum flow\n" +
                    "3) Delete a link\n" +
                    "4) Change a capacity\n" +
                    "5) Enter a graph from a text file\n" +
                    "6) Close the program");
            String option = sc.nextLine();

            switch (option){
                case "1":
                    getGraph();
                    break;
                case "2":
                    if (graphNotExist())break;
                    calculateFlow();
                    break;
                case "3":
                    if (graphNotExist())break;
                    deleteOrEditLink(0);
                    break;
                case "4":
                    if (graphNotExist())break;
                    deleteOrEditLink(1);
                    break;
                case "5":
                    getGraphFromFile();
                    break;
                case "6":
                    break loop;
                default:
                    System.out.println("Wrong Input!...");
            }
        }
    }

    public static void getGraph(){      // method for getting flow network by the user input
        while (true){
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the number of nodes : ");
            int nodes = 0;
            try {
                nodes = sc.nextInt();
                if (nodes < 6){
                    System.out.println("Number of nodes must be 6 or larger!");
                    continue;
                }
            } catch (InputMismatchException e){
                System.out.println("Wrong input. Enter again...");
                continue;
            }

            int[][] graph = new int[nodes][nodes];
            int i =0;
            while (i < nodes){    // getting the input as line by line for the matrix
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Enter the "+nodes+" space separated inputs of line "+i);
                String[] input = sc1.nextLine().split(" ");

                if (input.length != nodes){
                    System.out.println("Wrong Input Format! Enter again...");
                    continue;
                }

                try {
                    for (int j = 0; j < nodes; j++) {
                        graph[i][j] = Integer.parseInt(input[j]);
                    }
                } catch (Exception e){
                    System.out.println("Wrong Input Format! Enter again...");
                    continue;
                }

                i++;
            }

            Graph = graph;
            Nodes = nodes;
            printGraph();  // print the flow network entered by the user
            break;
        }

    }

    public static void calculateFlow(){    // method for calculating and printing the maximum flow
        int source = 0;
        int sink = 0;

        while (true) {    // getting the source and sink by the user and validating it
            Scanner sc = new Scanner(System.in);
            try {
                System.out.println("Enter the source : ");
                source = sc.nextInt();
                System.out.println("Enter the sink : ");
                sink = sc.nextInt();

                if (!(0 <= source && source < Nodes && 0 <= sink && sink < Nodes )){
                    System.out.println("Wrong Input! Enter again...");
                    continue;
                }
                if (source == sink){
                    System.out.println("Source and sink is equal!");
                    continue;
                }

                break;
            } catch (InputMismatchException e){
                System.out.println("Wrong Input! Enter again...");
                continue;
            }
        }

        MaxFlow maxFlow = new MaxFlow(Nodes);
        maxFlow.fordFulkerson(Graph,source,sink);    // calling the ford fulkerson algorithm for getting the maximum flow
    }

    public static void deleteOrEditLink(int option){    // method for deleting and editing a link
        String[] node = new String[2];
        int start = 0;
        int end = 0;

        while (true){
            Scanner sc = new Scanner(System.in);

            try {
                System.out.println("Enter the link (Start_node,End_node)  : ");

                node = sc.nextLine().split(",");  // getting the start and end vertices with comma separated

                start = Integer.parseInt(node[0]);
                end = Integer.parseInt(node[1]);

                int edit = 0;

                if (option == 1){   // getting the value for edit the value of a link
                    System.out.println("The current value of the link is "+Graph[start][end]);
                    System.out.println("Enter the new value to change :");
                    edit = sc.nextInt();

                    if (edit<0){
                        System.out.println("You can't put a negative value!");
                        continue;
                    }
                }

                Graph[start][end] = edit;    // updating the value of the link

                break;
            } catch (Exception e){
                System.out.println("Wrong Input! Enter again...");
                continue;
            }
        }

        printGraph();
    }

    public static void printGraph(){  // method for printing the flow network
        System.out.println("\nThe graph is :");
        for (int a = 0; a < Nodes; a++){
            for (int b = 0; b < Nodes; b++){
                System.out.print(Graph[a][b]+" ");
            }
            System.out.println("");
        }
    }

    public static boolean graphNotExist(){    // method for checking if a flow network is entered or not
        boolean out = false;
        if (Graph == null || Nodes == 0){
            System.out.println("You have to enter a graph first!");
            out = true;
        }
        return out;
    }

    public static void getGraphFromFile(){    // method for getting a flow network from a text file
        System.out.println("Enter the file name : ");   // the user only have to enter the file name
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName+".txt"));
            String line = reader.readLine();
            int nodes = Integer.parseInt(line);    // get the first line reading as the number of vertices of the flow network
            int graph[][] = new int[nodes][nodes];
            int i = 0;

            while (i < nodes) {    // read the file line by line and get the flow network
                line = reader.readLine();
                String[] input = line.split(" ");
                for (int j = 0; j < nodes; j++) {
                    graph[i][j] = Integer.parseInt(input[j]);
                }
                i++;

            }
            Nodes = nodes;
            Graph = graph;
            printGraph();
            reader.close();
        } catch (IOException e) {
            System.out.println("Something went Wrong! Check again");
        }
    }
}
