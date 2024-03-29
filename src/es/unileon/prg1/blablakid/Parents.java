package es.unileon.prg1.blablakid;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Class that manages an array of parents
 * 
 * @author PabloJ
 *
 **/

public class Parents {
	
	 static final Logger logger = LogManager.getLogger(Parents.class.getName());

	/**
	 * 
	 * Array composed of parents
	 * 
	 */
	
	public Parent[] parents;
	
	/**
	 * 
	 * Integer with the valor of the first free position in the array
	 * 
	 */
	
	private int next;
	
	/**
	 * 
	 * Create the array which will be managed by Parents
	 * 
	 * @param numberOfParents the maximum number of parents
	 * 
	 */
	
	public Parents(int numberOfParents) {
		this.parents = new Parent[numberOfParents];
		this.next = 0;
		
	}

	/**
	 * 
	 * This method add a parent to a array of Parents
	 * 
	 * @param parent that want to be added
	 * 
	 * @throws ParentException if the Parent is already included or the array is full
	 * 
	 */
	
	public void add(Parent parent)throws ParentException{
		//If the parent is not included add the parent into the first space that has no parent in it
		if (isIncluded(parent)) {
			logger.error("Parent "+ parent.getName() +" is already included");
			throw new ParentException("Error, parent "+ parent.getName() +" is already included");
		}
		else if (this.next >= this.parents.length) {
				
				logger.error("The array of parents is full");
				//If next is equal or bigger than the size of the array , the list of parents is full
				throw new ParentException("Error, the list of parents is full!");
			
		}
		else {
				this.parents[this.next] = parent;
				logger.info("The parent " + this.parents[this.next].getName() + " has been added successfully");
				this.next++;
			}	
		}	
				
	
	/**
	 * 
	 * Method that removes a parents from the arrays of Parents
	 * 
	 * @param parent that want to be deleted
	 * 
	 * @throws ParentException if the parent is not in the array
	 * 
	 */
	
	public boolean remove(Parent parent) throws ParentException{
		boolean removed = false;
		//If the parent is included take the position and removes the parent of the array
		if (isIncluded(parent)) {
			//Also compacts the array replacing the next parent
			int position = getPos(parent);
			logger.info("The parent "+ this.parents[position].getName() + "has been removed");
			compact(position);
			this.next--;
			
			removed = true;
		}
		else {
			
			logger.error("The parent is not founded in the array, so can't be removed");
			//If parent is not founded, throws an exception
			throw new ParentException("Error, the parent is not in the list");
		}
		return removed;
	}
	/**
	 * 
	 * Method that removes a Ride from a Day
	 * 
	 * @param ride
	 * @param day
	 * @throws RideException
	 */
	public void remove(Ride ride, Day day) throws RideException{
		boolean removed = false;
		int i = 0;
		while(!(removed) && (i < this.next)) {
			removed = this.parents[i].remove(ride , day);
			if(!(removed)) {
				i++;
			}
		}
	}
	
	
	/**
	 * 
	 * Method that removes a Kid from the array of kids of the parent
	 * 
	 * @param kid
	 * @throws KidException
	 */
	
	public void remove(Kid kid ) throws KidException{
		
		boolean removed = false;
		int i = 0;
		while (!(removed) && (i< this.next)) {
			removed = this.parents[i].remove(kid);
			
		}
	}
	/**
	 * 
	 * Getter of the parent at a specific position
	 * 
	 * @param index
	 * 
	 * @return Parent in the position of the index 
	 * 
	 */
	public Parent get(int index) {
		return this.parents[index];
	}
	
	
	/**
	 * 
	 * Method that returns the length of the array	
	 * 
	 * @return length of the array
	 */
	
	public int getLength() {
		return this.parents.length;
	}
	

	/**
	 * 
	 * Method that shows in which position of the array is the parent
	 * 
	 * @param parent that wants to get his/her position
	 * 
	 * @return Integer with the position of the array 
	 * 
	 */
	
	private int getPos(Parent parent){
		int count = 0;
		boolean end = false;
		while(count < this.next && !end) {
			if(this.parents[count].isSame(parent)) {
				end = true;
			}
			else {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 
	 *  Method that returns if the parent is included
	 *  
	 *  @param parent that wants to check if is included
	 *  
	 *  @return boolean true if it is included or false if not
	 *  
	 */
	
	private boolean isIncluded(Parent parent) {
		boolean resultado = false;
		//Check in the loop if the parent is in the array, returning true if the parent is included in the array
		for (int i = 0; i < this.next; i++) {
			if (this.parents[i].isSame(parent)) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	/**
	 * 
	 *  Method that search a parent in the array of parents
	 *  
	 *  @param name of the parent
	 *  
	 *  @return the parent or null if the parent is not on the array
	 *  
	 */
	
	public Parent search(String name) {
		int count = 0;
		boolean found = false;
		Parent parentOut = null;
		/*The loop check in all the spaces of the array if the parent is included (through his name) */
		while ((count < this.next) && (!found)) {
			if (this.parents[count].getName().equals(name)) {
				/* When the parent has been founded the method return the parent */
				parentOut = this.parents[count];
				found = true;
			}
			else {
				count++;
			}
		}	
		/* If the parent is not founded the method returns null */
		return parentOut;		
	}
	
	/**
	 * 
	 * Method that remove and compact
	 *  
	 * @param position of the deleted parent
	 * 
	 */
	
	private void compact(int position){
		int i;
		//I use a loop to travel the array until next
		for (i = position; i < (this.next-1) ; i++ ){ 
			//I make the compaction
			this.parents[i] = this.parents[i+1];
		}
		//Equals to null the last position of the loop
		this.parents[this.next-1]=null; 
	
	}
	
	/**
	 * 
	 * Method toString to print the status of the full array
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder salida = new StringBuilder();
		for (int i = 0; i < this.next; i++) {
			salida.append(this.parents[i].toString()).append("\n");
		}

		return salida.toString();
	}

}
