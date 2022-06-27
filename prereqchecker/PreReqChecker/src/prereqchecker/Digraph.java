package prereqchecker;

import java.util.ArrayList;

class Digraph {

    private int V;
    private int E;
    public String[] catalog;
    public ArrayList<String> AllCoursesTaken;
    private ArrayList<ArrayList<String>> adj;
    
    public Digraph(String inputFile) {

      StdIn.setFile(inputFile);
      this.V = StdIn.readInt();

      catalog = new String[V]; 
      StdIn.readLine();
      for(int i = 0; i < V; i++) {
        catalog[i] = StdIn.readLine();
      }

      this.E = StdIn.readInt();

      this.adj = new ArrayList<ArrayList<String>>(V); 
      for (int i = 0; i < V; i++) adj.add(new ArrayList<String>());

      // for (int k = 0; k < V; k++) System.out.println(catalog[k]);
      
      int j = 0;
      while (j <= E) {
        String s = StdIn.readLine();
        if(!s.equals("")) {
          String[] temp = s.split(" ");
          addEdge(adj, temp[0], temp[1]);
        }

        j++; 
      }

      this.AllCoursesTaken = new ArrayList<>(); 
    
    }

    public ArrayList<ArrayList<String>> getAdjList() {
      return adj; 
    }

    public int getV() { 
      return V; 
    }
  
    public int getE() { 
      return E; 
    }

    public String[] getCourseCatalog() {
      return catalog;
    }
  
    public void addEdge(ArrayList<ArrayList<String>> adj, String c1, String c2) {
      
      int v = 0;
      for (int i = 0; i < catalog.length; i++) {
        if (catalog[i].equals(c1)) v = i;
      }

      adj.get(v).add(c2);
  }

  public boolean isCyclicUtil(String course, boolean[] visited, boolean[] recStack) {
    
    int idx = 0;
    for (int i = 0; i < catalog.length; i++) {
      if(catalog[i].equals(course)) idx = i; 
    }
    
    if (recStack[idx]) return true;
    if (visited[idx]) return false; 

    visited[idx] = true;
    recStack[idx] = true;

    ArrayList<String> prereqs = adj.get(idx); 

    for (String c : prereqs) { 
      if (isCyclicUtil(c, visited, recStack)) return true; 
    }

    recStack[idx] = false;

    return false;
  }

  public boolean isCyclic() {

    boolean[] recStack = new boolean[V];
    boolean[] visited = new boolean[V]; 

    for (int i = 0; i < V; i++) {
      if(isCyclicUtil(catalog[i], visited, recStack)) return true;
    }

    return false; 

  }

  public void DFSUtils(int v, boolean[] visited) {
    
    visited[v] = true; 
    
    if(!AllCoursesTaken.contains(catalog[v])) AllCoursesTaken.add(catalog[v]); 
    System.out.println(v + ": " + catalog[v]); 

    ArrayList<String> prereqs = adj.get(v);

    if (prereqs.size() == 0) return;
    
    for(int m = 0; m < prereqs.size(); m++) {
      String c = prereqs.get(m);  
      for (int j = 0; j < catalog.length; j++) {
        if (catalog[j].equals(c)) {
          if(!visited[j]) DFSUtils(j, visited); 
          
        }
      }
    }
  }

  public void DFS(String c) {
    
    // System.out.println("c:" + c); 
    
    int v = 0;

    for(int i = 0; i < catalog.length; i++) {
      System.out.println(i);  
      if (catalog[i].equals(c)) v = i; 
    }
 
    boolean[] visited = new boolean[V];

    //System.out.println(v + ": " + visited[v]); 
    DFSUtils(v, visited);
  }

  public ArrayList<String> prereqDFSutils(ArrayList<String> a, int v, boolean[] visited) {
    visited[v] = true; 
    
    if(!a.contains(catalog[v])) a.add(catalog[v]); 
    // System.out.println(v + ": " + catalog[v]); 

    ArrayList<String> prereqs = adj.get(v);

    if (prereqs.size() == 0) return a;
    
    for(int m = 0; m < prereqs.size(); m++) {
      String c = prereqs.get(m);  
      for (int j = 0; j < catalog.length; j++) {
        if (catalog[j].equals(c)) {
          if(!visited[j]) prereqDFSutils(a, j, visited); 
        }
      }
    }
    return a; 
  }

  public ArrayList<String> prereqDFS(ArrayList<String> a, String c) {
    
    int v = 0;

    for(int i = 0; i < catalog.length; i++) {
      // System.out.println(i);  
      if (catalog[i].equals(c)) v = i; 
    }
 
    boolean[] visited = new boolean[V];

    //System.out.println(v + ": " + visited[v]); 
    a = prereqDFSutils(a, v, visited);

    return a;
  }

  public ArrayList<String> getAllCoursesTaken() {
    return AllCoursesTaken; 
  }


  
}

