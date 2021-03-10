package dataAccess;

import models.Mail;
import models.Nif;
import models.Password;
import models.Session;
import models.Simulation;
import models.User;
import models.User.RoleUser;
import utils.EasyDate;

public class Data {

	public final static int MAX_DATA = 10;

	private User[] usersData;
	private Session[] sessionsData;
	private Simulation[] simulationsData;
	private int registerdUser;
	private int registerdSessions;
	private int registerdSimulations;

	public Data() {		
		this.usersData = new User[MAX_DATA];
		this.sessionsData = new Session[MAX_DATA];
		this.simulationsData = new Simulation[MAX_DATA];
		this.registerdUser = 0;
		this.registerdSessions = 0;
		this.registerdSessions = 0;
		loadIntegratedUsers();
	}

	private void loadIntegratedUsers() {
		this.createUser(new User(new Nif("00000000T"),
				"Admin",
				"Admin Admin",
				"La Iglesia, 0, 30012, Patiño",
				new Mail("admin@gmail.com"),
				new EasyDate(2000, 1, 14),
				new EasyDate(2021, 1, 14),
				new Password("Miau#00"), 
				RoleUser.REGISTERED
				));
		this.createUser(new User(new Nif("00000001R"),
				"Guest",
				"Guest Guest",
				"La Iglesia, 0, 30012, Patiño",
				new Mail("guest@gmail.com"),
				new EasyDate(2000, 1, 14),
				new EasyDate(2021, 1, 14),
				new Password("Miau#00"), 
				RoleUser.REGISTERED
				));
	}

	// Users

	public User findUser(String id) {
		for (User user : this.usersData) {
			if (user != null && user.getNif().getText().equals(id)) {
				return user;
			}
		}
		return null;
	}

	public void createUser(User user) {
		if (findUser(user.getNif().getText()) == null) {
			this.usersData[this.registerdUser] = user;
			this.registerdUser++;
			return;
		}
	}

	public void updateUser(User user) {
		User userOld = findUser(user.getNif().getText());
		if (userOld != null) {
			this.usersData[this.indexOfUser(userOld)] = user;
			return;
		}
	}

	public void deleteUser(String id) {
		User user = findUser(id);

		if (user != null) {
			this.usersData[this.indexOfUser(user)] = null;
			this.registerdUser--;
			return;
		}
	}

	private int indexOfUser(User user) {
		for (int i=0; i < this.usersData.length; i++) {
			if (user.equals(this.usersData[i])) {
				return i;
			}
		}
		return -1;
	}

	// Sessions

	public Session findSession(String id) {
		for (Session session : this.sessionsData) {
			if (session != null && session.getId().equals(id)) {
				return session;
			}
		}
		return null;
	}

	public void createSession(Session session) {
		this.sessionsData[this.registerdSessions] = session;
		this.registerdSessions++;
		return;
	}

	public void updateSession(Session session) {
		Session sessionOld = this.findSession(session.getId());
		if (sessionOld != null) {
			this.sessionsData[this.indexOfSession(sessionOld)] = session;
			return;
		}
	}

	private int indexOfSession(Session session) {
		for (int i=0; i < this.sessionsData.length; i++) {
			if (session.equals(this.sessionsData[i])) {
				return i;
			}
		}
		return -1;
	}
	
	// Simulations

	public Simulation findSimulation(String id) {
		for (Simulation simulation : this.simulationsData) {
			if (simulation != null && simulation.getId().equals(id)) {
				return simulation;
			}
		}
		return null;
	}

	public void createSimulation(Simulation simulation) {
		if (findUser(simulation.getId()) == null) {
			this.simulationsData[this.registerdSimulations] = simulation;
			this.registerdSimulations++;
			return;
		}
	}
	
	public void updateSimulation(Simulation simulation) {
		Simulation simulationOld = this.findSimulation(simulation.getId());
		if (simulationOld != null) {
			this.simulationsData[this.indexOfSimulation(simulationOld)] = simulation;
			return;
		}
	}

	private int indexOfSimulation(Simulation simulation) {
		for (int i=0; i < this.simulationsData.length; i++) {
			if (simulation.equals(this.simulationsData[i])) {
				return i;
			}
		}
		return -1;
	}

}
