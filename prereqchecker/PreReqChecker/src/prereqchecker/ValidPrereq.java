package prereqchecker;

import java.util.ArrayList;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a course ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }

        Digraph G = new Digraph(args[0]);
        ArrayList<ArrayList<String>> adjList = G.getAdjList();

        StdIn.setFile(args[1]);
        String course1 = StdIn.readLine();
        String course2 = StdIn.readLine();
        G.addEdge(adjList, course1, course2);

        /*int idx = 0;
        String[] c = G.getCourseCatalog();
        for(int i = 0; i < c.length; i++) if(c[i].equals(lowerLevelCourse)) idx = i;

        ArrayList<String> a = adjList.get(idx); 
        
        for(int j = 0; j < a.size(); j++) System.out.println(a.get(j)); */

        StdOut.setFile(args[2]);
        
        if (G.isCyclic()) StdOut.println("NO");
        else StdOut.println("YES"); 

        // System.out.println(G.isCyclic());
    }
}