package models;
import utils.EasyDate;

public class Simulation implements Identificable {

	public enum StateSimulation {PREPARED, RUNNING, FINISHED};
	public enum GridType {EDGES, CYCLIC, UNLIMITED};

	private User user;
	private EasyDate date;
	private World world;
	private StateSimulation state;

	public Simulation(User user, EasyDate date) {
		this.setUser(user);
		this.setDate(date);
		this.state = StateSimulation.PREPARED;
	}

	public Simulation() {
		this(new User(), 
				EasyDate.now());
	}

	public Simulation(Simulation simulation) {
		assert simulation != null;
		
		this.user = simulation.user;
		this.date =	EasyDate.today();
		this.state = simulation.state;
	}

	@Override
	public String getId() { 
		return this.user.getId() + ":" + this.date.toStringTimeStamp();
	}
	
	public User getUser() {
		return this.user;
	}

	public EasyDate getDate() {
		return this.date;
	}

	public World getWorld() {
		return world;
	}
	
	public StateSimulation getState() {
		return this.state;
	}

	public void setUser(User user) {
		assert user != null;
		this.user = user;
	}

	public void setDate(EasyDate date) {
		assert date != null;	
		if (isValidDate(date)) {
			this.date = date;
		}
	}

	private boolean isValidDate(EasyDate date) {	
		return !date.isAfter(EasyDate.now());
	}

	@Override
	public String toString() {
		return String.format(			
				"%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n",
						"user:", this.user.getName(), 
						"date:", this.date, 
						"world:", this.world, 
						"state:", this.state
				);
	}

	@Override
	public Simulation clone() {
		return new Simulation(this);
	}
} 
