package es.unileon.prg1.blablakid;

import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;

/**
 * 
 * Class that manages an array of objects of the type activity
 * 
 * @author Pablo Bayon
 *
 */
public class Activities {

	static final Logger logger = LogManager.getLogger(Activities.class.getName());

	/**
	 * 
	 * Array of objects of the type Activity
	 * 
	 */
	private Activity[] activities;

	/**
	 * 
	 * Integer used in order to control the next position that is going to be filled
	 * in the array
	 * 
	 */
	private int next;

	/**
	 * 
	 * Constant that establish the maximum length of the array
	 * 
	 */
	private final int MAXACTIVITIES = 3;

	/**
	 * 
	 * Constructor of the class, inicializates the array and set the next at 0
	 * 
	 */
	public Activities() {
		this.next = 0;
		this.activities = new Activity[MAXACTIVITIES];
	}

	/**
	 * 
	 * Method that returns an object activity using its position in the array
	 * 
	 * @param pos of the array that you want to get the kid
	 * 
	 * @return the activity located in the position specified by the parameter
	 * 
	 */
	public Activity get(int pos) {
		return this.activities[pos];
	}

	/**
	 * 
	 * Getter of the length of the array
	 * 
	 * @return Integer with the number of elements in the array
	 * 
	 */
	public int getLength() {
		return this.activities.length;
	}

	/**
	 * 
	 * Method that returns the position of an activity in its array
	 * 
	 * @param activity
	 * 
	 * @return The position of the object inside the array
	 * 
	 */
	private int getPos(Activity activity) {
		int count = 0;
		boolean end = false;
		while (count < this.next && !end) {
			if (activities[count].isSame(activity)) {
				end = true;
			} else {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * Method that add an object activity to an array of activities if it is
	 * possible
	 * 
	 * @param activity to add
	 * 
	 * @throws ActivityException if the activity was also included or the activity
	 *                           list is full
	 * 
	 */
	public void add(Activity activity) throws ActivityException {
		if (this.isIncluded(activity)) {
			logger.error("Activity " + activity.getName() + " is already included");
			throw new ActivityException("Error: Activity already included");
		} else if (this.next >= this.MAXACTIVITIES) {
			logger.error("Activity list is full");
			throw new ActivityException("Error: Activity list is full");
		} else {
			activities[this.next] = activity;
			logger.info("The activity " + activity.getName() + " has been successfully added");
			this.next++;
		}
	}

	/**
	 * 
	 * Method that checks if an activity is included in the array
	 * 
	 * @param activity to search
	 * 
	 * @return true if the activity is included in the array, false if not
	 * 
	 */
	private boolean isIncluded(Activity activity) {
		boolean end = false;
		int i = 0;
		while (i < this.next && !end) {
			if (this.activities[i].isSame(activity)) {
				end = true;
			} else {
				i++;
			}
		}
		return end;
	}

	/**
	 * 
	 * Method that removes an object of the array in which it is included
	 * 
	 * @param activity to remove
	 * 
	 * @throws ActivityException if the activity was not included in the array
	 * 
	 */
	public void remove(Activity activity) throws ActivityException {
		if (this.isIncluded(activity)) {
			int pos = this.getPos(activity);
			this.compact(pos);
			logger.info("The activity " + activity.getName() + " has been successfully removed");
			this.next--;
		} else {
			logger.error("The activity " + activity.getName() + " wasn't included");
			throw new ActivityException("Error: Activity wasn't included");
		}
	}

	/**
	 * 
	 * Private method to compact the array after deleting a activity
	 * 
	 * @param pos of the activity to be deleted
	 * 
	 */
	private void compact(int pos) {
		for (int i = 0; i < (this.next - 1); i++) {
			activities[i] = activities[i + 1];
		}
		activities[this.next - 1] = null;
	}

	/**
	 * 
	 * Method to search if there is a activity like the parameter in the array
	 * 
	 * @param name to search in the array
	 * 
	 * @param day  when the activity will take place
	 * 
	 * @return The activity if it exists or null
	 * 
	 */
	public Activity search(String name, int day) {
		Activity found = null;
		boolean end = false;
		int i = 0;
		while ((i < this.next) && (!end)) {

			if ((this.activities[i].getName().equals(name)) && (this.activities[i].getDay().getNumDay() == day)) {
				end = true;
				found = activities[i];
			} else {
				i++;
			}
		}
		return found;
	}

	/**
	 * Method that decides if afterRide, beforeRide or neither of them has to be
	 * removed
	 * 
	 * @param ride
	 * @return true if the ride is removed, false if not
	 */
	public boolean remove(Ride ride) {
		boolean removed = false;
		int i = 0;

		while (!(removed) && (i < this.next)) {
			if (this.activities[i].getAfterRide() != null) {
				if (this.activities[i].getAfterRide().isSame(ride)) {
					removed = true;
					this.activities[i].removeAfterRide();
				}
			}

			if (this.activities[i].getBeforeRide() != null) {
				if (this.activities[i].getBeforeRide().isSame(ride)) {
					removed = true;
					this.activities[i].removeBeforeRide();
				}
			}
			if (!(removed)) {
				i++;
			}
		}
		return removed;
	}

	/**
	 * Method that checks if an activity hasn't got rides assigned to it
	 * 
	 * @return the rides that are missing
	 */
	public String checkStatus() {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < this.next; i++) {
			out.append(this.activities[i].checkStatus());
		}
		return out.toString();

	}

	/**
	 * 
	 * toString version according to the show summary option in textUI
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < this.next; i++) {
			out.append(this.get(i).toString());
		}
		return out.toString();
	}
}
