package prereqchecker;

import java.util.*;

/**
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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

        Digraph G = new Digraph(args[0]);

        StdIn.setFile(args[1]);
        String target = StdIn.readLine();
        // System.out.println("target: " + target); 
        int t = StdIn.readInt();
        String[] taken = new String[t];
        StdIn.readLine();
        for (int i = 0; i < t; i++) taken[i] = StdIn.readLine(); 

        // for (int i = 0; i < t; i++) System.out.println(taken[i]); 

        ArrayList<String> coursesTaken = new ArrayList<String>();
        for (int j = 0; j < t; j++) G.DFS(taken[j]);

        coursesTaken = G.getAllCoursesTaken(); 

        ArrayList<String> targetPrereqs = new ArrayList<>();
        targetPrereqs = G.prereqDFS(targetPrereqs, target); 

        // System.out.println(targetPrereqs.get(0));

        ArrayList<String> needToTake = new ArrayList<>(); 
        for (int k = 1; k < targetPrereqs.size(); k++) {
            if (!coursesTaken.contains(targetPrereqs.get(k))) needToTake.add(targetPrereqs.get(k)); 
        }

        StdOut.setFile(args[2]);
        for (String p : needToTake) StdOut.println(p); 

    }
}
