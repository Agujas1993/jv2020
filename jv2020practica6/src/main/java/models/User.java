package models;

import utils.EasyDate;

public class User extends Person implements Identificable {

	public enum RoleUser { GUEST, REGISTERED, ADMIN };

	private EasyDate registeredDate;
	private Password password;
	private RoleUser role;

	public User(Nif nif, String name, String surnames,
			String address, Mail mail, EasyDate birthDate,
			EasyDate registeredDate, Password password, RoleUser role) {
		
		super(nif, name, surnames, address, mail, birthDate);
		this.setRegisteredDate(registeredDate);
		this.setPassword(password);
		this.setRole(role);
	}

	public User() {
		super();			
		this.registeredDate = EasyDate.today();	
		this.password = new Password("Miau#00");
		this.role = RoleUser.REGISTERED;
	}

	public User(User user) {
		assert user != null;
		this.nif = new Nif(user.nif);
		this.name = new String(user.name);
		this.surnames = new String(user.surnames);
		this.address = new String(user.address);
		this.mail = new Mail(user.mail);	
		this.birthDate = user.birthDate.clone();
		this.registeredDate = user.registeredDate.clone();
		this.password = new Password(user.password);
		this.role = RoleUser.REGISTERED;	
	}

	@Override
	public String getId() {
		return this.nif.getText();
	}

	public EasyDate getRegisteredDate() {
		return this.registeredDate;
	}

	public Password getPassword() {
		return this.password;
	}

	public RoleUser getRole() {
		return this.role;
	}

	
	public void setRegisteredDate(EasyDate registeredDate) {
		assert registeredDate != null;

		if (isValidRegisteredDate(registeredDate)) {
			this.registeredDate = registeredDate;
		}
	}

	private boolean isValidRegisteredDate(EasyDate registeredDate) {	
		return !registeredDate.isAfter(EasyDate.now());
	}

	public void setPassword(Password password) {
		assert password != null;
		this.password = password;

	}

	public void setRole(RoleUser role) {
		this.role = role;
	}

	@Override
	public User clone() {
		return  new User(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registeredDate == null) ? 0 : registeredDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (registeredDate == null) {
			if (other.registeredDate != null)
				return false;
		} else if (!registeredDate.equals(other.registeredDate))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s", 
						"registeredDate:", registeredDate, 
						"password:", password, 
						"role:", role
				);
	}

	public int compareTo(User user) {
		
		return this.getId().compareTo(user.getId());
	}


}