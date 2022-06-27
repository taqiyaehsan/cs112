package prereqchecker;

import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

        ArrayList<String> eligibleCourses = new ArrayList<String>();

        Digraph G = new Digraph(args[0]);
        ArrayList<ArrayList<String>> adjList = G.getAdjList();
        String[] catalog = G.getCourseCatalog();

        StdIn.setFile(args[1]);
        int n = StdIn.readInt();

        ArrayList<String> GivenCourses = new ArrayList<>(); 
        StdIn.readLine(); 
        for(int i = 0; i < n; i++) {
            String s = StdIn.readLine();
            if(!s.equals("")) GivenCourses.add(s); 
        }
        
        // for (int i = 0; i < n; i++) System.out.println(GivenCourses.get(i));
        // System.out.println(); 

        for (int c = 0; c < n; c++) {
            G.DFS(GivenCourses.get(c));
            // System.out.print(c + ": ");
            // System.out.println(GivenCourses.get(c));
        }

        ArrayList<String> TakenCourses = new ArrayList<>(); 
        TakenCourses = G.getAllCoursesTaken(); 
        // for (int i = 0; i < TakenCourses.size(); i++) System.out.println(TakenCourses.get(i));

        // for (int c = 0; c <= n; c++) G.DFS(TakenCourses.get(c));
        for(int i = 0; i < catalog.length; i++) {
            String c = catalog[i];
            ArrayList<String> prereqs = adjList.get(i);
            boolean eligible = true; 

            for (String p : prereqs) {
                if (!TakenCourses.contains(p)) eligible = false;
            }

            if (eligible) {
                if (!TakenCourses.contains(c)) eligibleCourses.add(c); 
            }
        }

        StdOut.setFile(args[2]);
        for(String c : eligibleCourses) StdOut.println(c); 
    }
}
