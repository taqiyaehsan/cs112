import java.util.ArrayList;


/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 */
	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) {
		// WRITE YOUR CODE HERE	
	
		TNode locationHead = new TNode(0); 
		TNode o = locationHead;

		for(int i = 0; i < locations.length; i++){
			TNode locationNode = new TNode(locations[i]); 
			o.next = locationNode;
			o = o.next;  

		}

		TNode trainZero = new TNode(0);
		TNode p = trainZero;

		for(int j = 0; j < trainStations.length; j++){
			TNode trainNode = new TNode(trainStations[j]);
			p.next = trainNode; 
			p = p.next; 

		}

		TNode busHead = new TNode(0);
		TNode q = busHead;

		for(int k = 0; k < busStops.length; k++){
			TNode busNode = new TNode(busStops[k]); 
			q.next = busNode;
			q = q.next;   
		} 

		TNode ptrT = trainZero;
		TNode ptrB = busHead;
		TNode t = ptrT;
		TNode b = ptrB;  

		while(ptrT != null && ptrB != null){

			if(ptrT.location == ptrB.location){
				t.down = b; 
		
				ptrT = ptrT.next; 
				ptrB = ptrB.next; 

				t = ptrT; 
				b = ptrB; 

			}

			else {
				ptrB = ptrB.next;
				b = ptrB; 
			}
		}
		
		TNode ptrb = busHead;
		TNode ptrL = locationHead;
		TNode B = ptrb;
		TNode l = ptrL;  

		while(ptrb != null && ptrL != null){

			if(ptrb.location == ptrL.location){
				B.down = l; 

				ptrL = ptrL.next; 
				ptrb = ptrb.next; 
				
				l = ptrL; 
				B = ptrb; 
			}

			else {
				ptrL = ptrL.next;
				l = ptrL; 
			}
		}

		return trainZero; 
	}
	
	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) {
		// WRITE YOUR CODE HERE

		TNode ptr = trainZero;

		while(ptr != null) {

			if(ptr.next == null) return;
			
			if(ptr.next.location == station){ 

				if(ptr.next.down != null){
					ptr.next.down = null;
				}
				
				ptr.next = ptr.next.next;
			}

			ptr = ptr.next;
		
		}

		return;		

	}


	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) {
		// WRITE YOUR CODE HERE
		TNode ptr = trainZero.down; 

		while(ptr != null){
			if(busStop == ptr.location) return;

			if(ptr.location < busStop){
				if(ptr.next == null){
					TNode ptrWalk = ptr.down;
					while(ptrWalk != null){
						if(ptrWalk.location == busStop){
							TNode b = new TNode(busStop);
							b.down = ptrWalk;
							ptr.next = b;
							b.next = null; 
					} 
						ptrWalk = ptrWalk.next; 
				}
			}
			
			else {
				if(ptr.location < busStop && ptr.next.location > busStop){
				
					TNode b = new TNode(busStop);
					b.next = ptr.next;
					b.down = ptr.down.next; 
					ptr.next = b;
			}
		}
			ptr = ptr.next; 
		}

			}	
			
		return; 

	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {
			// WRITE YOUR CODE HERE
	
		ArrayList<TNode> bestPath = new ArrayList<>();
		TNode ptr = trainZero;
	
		bestPath.add(trainZero); 
		while(ptr.next != null && ptr.next.location <= destination){ 
			bestPath.add(ptr.next); 
			ptr = ptr.next;
	
				/* if(ptr.next.location > destination){
					bestPath.add(ptr);
					break;
				} */
		}
	
		bestPath.add(ptr.down);
		ptr = ptr.down;
		while(ptr.next != null && ptr.next.location <= destination){
			bestPath.add(ptr.next);
			ptr = ptr.next;
	
				/* if(b.next.location > destination){
					bestPath.add(b);
					break; 
				} */
		}
	
		bestPath.add(ptr.down);
		ptr = ptr.down;
		while(ptr.next != null && ptr.next.location <= destination){
			bestPath.add(ptr.next);
			ptr = ptr.next;  
	
			}
	
		return bestPath; 
} 

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {

		// WRITE YOUR CODE HERE
 
		TNode headT = new TNode(0);  
		TNode trainPtr = headT;
		
		for(TNode ptr = trainZero.next; ptr != null; ptr = ptr.next){
			TNode newTrains = new TNode(ptr.location); 
			trainPtr.next = newTrains;  
			newTrains = newTrains.next; 
			trainPtr = trainPtr.next; 

		}
 
		TNode headB = new TNode(); 
		TNode busPtr = headB; 

		for(TNode ptr2 = trainZero.down.next; ptr2 != null; ptr2 = ptr2.next){
			TNode newBus = new TNode(ptr2.location); 
			busPtr.next = newBus;  
			newBus = newBus.next;
			busPtr = busPtr.next;

		}
		
		TNode headL = new TNode(0); 
		TNode WalkPtr = headL; 

		for(TNode ptr3 = trainZero.down.down.next; ptr3 != null; ptr3 = ptr3.next){
			TNode newWalk = new TNode(ptr3.location); 
			WalkPtr.next = newWalk;  
			newWalk = newWalk.next; 
			WalkPtr = WalkPtr.next; 

		}

		TNode ptrT = headT;
		TNode ptrB = headB;
		TNode t = ptrT;
		TNode b = ptrB;  

		while(ptrT != null && ptrB != null){

			if(ptrT.location == ptrB.location){
				t.down = b; 
		
				ptrT = ptrT.next; 
				ptrB = ptrB.next; 

				t = ptrT; 
				b = ptrB; 

			}

			else {
				ptrB = ptrB.next;
				b = ptrB; 
			}
		}
		
		TNode ptrb = headB;
		TNode ptrL = headL;
		TNode B = ptrb;
		TNode l = ptrL;  

		while(ptrb != null && ptrL != null){

			if(ptrb.location == ptrL.location){
				B.down = l; 

				ptrL = ptrL.next; 
				ptrb = ptrb.next; 
				
				l = ptrL; 
				B = ptrb; 
			}

			else {
				ptrL = ptrL.next;
				l = ptrL; 
			}
		}


		return headT;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {
		// WRITE YOUR CODE HERE
		TNode scooterHead = new TNode(0); 
		TNode r = scooterHead;

		// linked list for scooter stops only
		for(int i = 0; i < scooterStops.length; i++){
			TNode scooterNode = new TNode(scooterStops[i]); 
			r.next = scooterNode;
			r = r.next;  

		}

		TNode bus = trainZero.down; 		//bus head
		TNode ptrW = trainZero.down.down;	//walking head

		TNode busPtr = bus;			
		TNode scoot = scooterHead;
		TNode b = busPtr; 			

		while(busPtr != null){

			if(b.location == scoot.location){
				b.down = scoot;
				// StdOut.println(b.down.location);  

				scoot = scoot.next; 
				busPtr = busPtr.next; 
				
				b = busPtr; 
			}

			else {
				scoot = scoot.next;
			}
		}

		TNode ptrS = scooterHead;
		TNode sc = ptrS;  
		TNode w = ptrW; 

		while(ptrS != null){

			if(w.location == ptrS.location){
				sc.down = w; 
				// StdOut.println(sc.down.location); 
		
				w = w.next; 
				ptrS = ptrS.next; 

				sc = ptrS; 

			}

			else {
				w = w.next;
			}
		
		}

		return; 
	}

}