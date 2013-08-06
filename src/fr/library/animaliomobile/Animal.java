package fr.library.animaliomobile;


public class Animal {

	// Paramètre
	public int id;
	public int userId = 0;
	public int raceId = 0;
	public String name;
	public String description = "";
	public String birthday = "";
	public String death = "";
	public String createdAt = "";
	public String updatedAt = "";

	// Constructeur
	public Animal(int _id, String _name) {
		this.id = _id;
		this.name = _name;
	}

	public int getAnimalId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int _userId) {
		this.userId = _userId;
	}

	public int getRaceId() {
		return this.raceId;
	}

	public void setRaceId(int _raceId) {
		this.raceId = _raceId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String _description) {
		this.description = _description;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String _birthday) {
		this.birthday = _birthday;
	}

	public String getDeath() {
		return this.death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String _createdAt) {
		this.createdAt = _createdAt;
	}

	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(String _updatedAt) {
		this.updatedAt = _updatedAt;
	}
}
