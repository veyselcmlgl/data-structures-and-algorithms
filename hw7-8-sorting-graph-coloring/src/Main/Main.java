package Main;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import DSA.Graphs.GTUGraph;
import DSA.Graphs.GCA.GCASolution;
import DSA.Graphs.GCA.GreedyGCA;
import DSA.Graphs.MatrixGraph.MatrixGraph;
import DSA.Sorting.GTUSorter;
import DSA.Sorting.GTUInsertSort;
import DSA.Sorting.GTUQuickSort;
import DSA.Sorting.GTUSelectSort;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Too few arguments. (Expected: <int:input_file> <String:output_path>)");
            return;
        }

        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);
    
            Integer graph_size = Integer.parseInt(scanner.nextLine().strip());
            ArrayList<Integer> arr = new ArrayList<>();
    
            while (scanner.hasNextLine()) {
                String line[] = scanner.nextLine().strip().split(" ");
                arr.add(Integer.parseInt(line[0]) + Integer.parseInt(line[1]));
            }
    
            scanner.close();
    
            ArrayList<GTUSorter> sorters = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
    
            // Uncomment the sorting algorithms you have implemented
            sorters.add(new GTUInsertSort());                         names.add("MyInsertSort");
            sorters.add(new GTUSelectSort());                         names.add("MySelectSort");
            sorters.add(new GTUQuickSort());                          names.add("MyQuickSort");
            sorters.add(new GTUQuickSort(new GTUInsertSort(), 10));    names.add("MyQuickSort_MyInsertSort");
            sorters.add(new GTUQuickSort(new GTUSelectSort(), 10));    names.add("MyQuickSort_MySelectSort");
    
            Integer[] tempArr = new Integer[arr.size()];
            for (int i = 0; i < sorters.size(); i++) {
                GTUSorter sorter = sorters.get(i);
                String name = names.get(i);
    
                arr.toArray(tempArr);
                sorter.sort(tempArr, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer arg0, Integer arg1) {
                        return arg1.compareTo(arg0);
                    }
                });
                
                File result = new File(args[1] + name + ".txt");
                FileWriter writer = new FileWriter(result);
                for (Integer integer : tempArr) {
                    writer.write(integer.toString() + "\n");
                }
                writer.close();
            }

            GTUGraph graph = new MatrixGraph();
            GTUGraph.readGraph(args[0], graph);
            GTUGraph.writeGraph(args[1] + "graph.txt", graph);
    
            for (int i = 0; i < sorters.size(); i++) {
                GTUSorter sorter = sorters.get(i);
                String name = names.get(i);
                
                GCASolution solution = GreedyGCA.solve(graph, sorter);
    
                solution.writeSolution(args[1] + name + "_color.txt");
            }

        } catch (Exception e) {
            System.err.printf("Error: %s\n", e.getMessage());
            return;
        }
    }
}