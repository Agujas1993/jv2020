package dataAccess;

import java.util.ArrayList;

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

	private ArrayList<User> usersData;
	private ArrayList<Session> sessionsData;
	private ArrayList<Simulation> simulationsData;
	private int registerdUser;
	private int registerdSessions;
	private int registerdSimulations;

	public Data() {		
		this.usersData = new ArrayList<User>();
		this.sessionsData = new ArrayList<Session>();
		this.simulationsData = new ArrayList<Simulation>();
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
		int pos = this.binaryfind(id);
		if (pos >= 0) {
			return this.usersData.get(pos);
		}
		return null;
	}
	private int binaryfind(String id) {
		int inicio = 0;
		int fin = usersData.size() - 1;
		int pos;

		while (inicio <= fin) {
			pos = (inicio + fin) / 2;
			if (usersData.get(pos).getId().equals(id)) {
				return pos;
			}
			else { 
				if (usersData.get(pos).getId().compareTo(id) < 0) {
					inicio = pos + 1;
				} 
				else {
					fin = pos - 1;
				}
			}
		}
		return -1;
	}




	public void createUser(User user) {
		if (findUser(user.getNif().getText()) == null) {
			this.usersData.add(user);
			this.registerdUser++;
			return;
		}
	}

	public void updateUser(User user) {
		User userOld = findUser(user.getNif().getText());
		if (userOld != null) {
			this.usersData.add(this.indexOfUser(userOld), user);
			return;
		}
	}

	public void deleteUser(String id) {
		User user = findUser(id);

		if (user != null) {
			this.usersData.remove(this.indexOfUser(user));
			this.registerdUser--;
			return;
		}
	}

	private int indexOfUser(User user) {
		for (int i=0; i < this.usersData.size(); i++) {
			if (user.equals(this.usersData.get(i))) {
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
		this.sessionsData.add(session);
		this.registerdSessions++;
		return;
	}

	public void updateSession(Session session) {
		Session sessionOld = this.findSession(session.getId());
		if (sessionOld != null) {
			this.sessionsData.add(this.indexOfSession(sessionOld), session);
			return;
		}
	}

	private int indexOfSession(Session session) {
		for (int i=0; i < this.sessionsData.size(); i++) {
			if (session.equals(this.sessionsData.get(i))) {
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
			this.simulationsData.add(simulation);
			this.registerdSimulations++;
			return;
		}
	}

	public void updateSimulation(Simulation simulation) {
		Simulation simulationOld = this.findSimulation(simulation.getId());
		if (simulationOld != null) {
			this.simulationsData.add(this.indexOfSimulation(simulationOld), simulation);
			return;
		}
	}

	private int indexOfSimulation(Simulation simulation) {
		for (int i=0; i < this.simulationsData.size(); i++) {
			if (simulation.equals(this.simulationsData.get(i))) {
				return i;
			}
		}
		return -1;
	}

}
