package models;

import utils.EasyDate;

public abstract class Person {
	protected Nif nif;
	protected String name;
	protected String surnames;
	protected String address;
	protected Mail mail;
	protected EasyDate birthDate;
	
	public Person(Nif nif, String name, String surnames, String address, Mail mail, EasyDate birthDate) {

		this.nif = nif;
		this.name = name;
		this.surnames = surnames;
		this.address = address;
		this.mail = mail;
		this.birthDate = birthDate;
		
		this.setNif(nif);
		this.setName(name);
		this.setSurnames(surnames);
		this.setAddress(address);
		this.setMail(mail);
		this.setBirthDate(birthDate);
	}
	
	public Person() {
		this.nif = new Nif();
		this.name = new String();
		this.surnames = new String();
		this.address = new String();
		this.mail = new Mail();	
		this.birthDate = EasyDate.today();
	}

	public Nif getNif() {
		return this.nif;
	}

	public String getName() {
		return this.name;
	}

	public String getSurnames() {
		return this.surnames;
	}

	public String getAddress() {
		return this.address;
	}

	public Mail getMail() {
		return this.mail;
	}

	public EasyDate getBirthDate() {
		return this.birthDate;
	}
	public void setNif(Nif nif) {	
		assert nif != null;
		this.nif = nif;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setBirthDate(EasyDate birthDate) {
		assert birthDate != null;

		if (isValidBirthDate(birthDate)) {
			this.birthDate = birthDate;
		}
	}

	private boolean isValidBirthDate(EasyDate birthDate) {	
		return birthDate.isBefore(EasyDate.now().minusYears(16));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((surnames == null) ? 0 : surnames.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (surnames == null) {
			if (other.surnames != null)
				return false;
		} else if (!surnames.equals(other.surnames))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format(
				"%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s\n"
						+ "%15s %-15s",
						"nif:", nif, 
						"name:", name, 
						"surnames:", surnames, 
						"address:", address, 
						"mail:", mail, 
						"birthDate:", birthDate 
				);
	}
	
}
