package dataAccess;

import java.util.ArrayList;
import java.util.HashMap;

import models.Identificable;
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

	private ArrayList<Identificable> usersData;
	private ArrayList<Identificable> sessionsData;
	private ArrayList<Identificable> simulationsData;
	private HashMap<String,String> idEquivalence;
	
	public Data() {		
		this.usersData = new ArrayList<Identificable>();
		this.sessionsData = new ArrayList<Identificable>();
		this.simulationsData = new ArrayList<Identificable>();
		this.idEquivalence = new HashMap<String,String>();
		loadIntegratedUsers();

	}

	private void loadIntegratedUsers() {
		this.createUser(new User(new Nif("00000000T"),
				"Admin",
				"Admin Admin",
				"La Iglesia, 0, 30012, Pati�o",
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

	private int indexSort(ArrayList<Identificable> data,String id) {
		int start = 0;
		int end = data.size() - 1;
		int pos;

		while (start <= end) {
			pos = (start + end) / 2;
			if ((data.get(pos)).getId().equals(id)) {
				return pos;
			}
			else { 
				if (data.get(pos).getId().compareTo(id) < 0) {
					start = pos + 1;
				} 
				else {
					end = pos - 1;
				}
			}
		}
		return -(end+2);
	}

	// Users

	public User findUser(String id) {
		id = this.idEquivalence.get(id);
		int pos = this.indexSort(this.usersData, id);
		if (pos >= 0) {
			return (User) this.usersData.get(pos);
		}
		return null;
	}

	public void createUser(User user) {
		int index = this.indexSort(this.usersData, user.getId());
		if(index < 0) {
			this.usersData.add(-(index+1), user);
			this.idEquivalence.put(user.getNif().getText(), user.getId());
			this.idEquivalence.put(user.getMail().getText(), user.getId());
			}
	}

	public void updateUser(User user) {
		User userOld = findUser(user.getNif().getText());
		if (userOld != null) {
			this.usersData.set(this.indexSort(usersData, userOld.getId()), user);
			this.idEquivalence.replace(userOld.getNif().getText(), user.getId());
			this.idEquivalence.replace(userOld.getMail().getText(), user.getId());
		}
	}

	public void deleteUser(String id) {
		User user = findUser(id);

		if (user != null) {
			this.usersData.remove(this.indexSort(usersData, user.getId()));
			this.idEquivalence.remove(user.getNif().getText());
			this.idEquivalence.remove(user.getMail().getText());
		}
	}

	// Sessions

	public Session findSession(String id) {

		int pos = this.indexSort(this.sessionsData,id);
		if (pos >= 0) {
			return (Session) this.sessionsData.get(pos);
		}
		return null;

	}

	public void createSession(Session session) {
		this.sessionsData.add(session);
	}

	public void updateSession(Session session) {
		Session sessionOld = this.findSession(session.getId());
		if (sessionOld != null) {
			this.sessionsData.set(this.indexSort(sessionsData, sessionOld.getId()), session);
		}
	}

	// Simulations

	public Simulation findSimulation(String id) {

		int pos = this.indexSort(this.simulationsData,id);
		if (pos >= 0) {
			return (Simulation) this.simulationsData.get(pos);
		}
		return null;

	}

	public void createSimulation(Simulation simulation) {
		if (findUser(simulation.getId()) == null) {
			this.simulationsData.add(simulation);
		}
	}

	public void updateSimulation(Simulation simulation) {
		Simulation simulationOld = this.findSimulation(simulation.getId());
		if (simulationOld != null) {
			this.simulationsData.set(this.indexSort(simulationsData, simulationOld.getId()), simulation);
		}
	}
}
