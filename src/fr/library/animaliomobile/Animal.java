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
	public Animal(int id, int userId, int raceId, String name,
			String description, String birthday, String death,
			String createdAt, String updatedAt) {
		this.id = id;
		this.userId = userId;
		this.raceId = raceId;
		this.name = name;
		this.description = description;
		this.birthday = birthday;
		this.death = death;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Animal(int _id, String _name) {
		this.id = _id;
		this.name = _name;
	}
}
