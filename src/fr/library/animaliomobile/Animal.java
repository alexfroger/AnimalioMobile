package fr.library.animaliomobile;


public class Animal {

	// Paramètre
	public int id;
	public int userId = 0;
	public int raceId = 0;
	public String name;
	public String avatarName;
	public String description = "";
	public String birthday = "";
	public String death = "";
	public String createdAt = "";
	public String updatedAt = "";
	public String photo_url = "";

	// Constructeur
	public Animal(int id, int userId, int raceId, String name, String avatarName,
			String description, String birthday, String death,
			String createdAt, String updatedAt) {
		this.id = id;
		this.userId = userId;
		this.raceId = raceId;
		this.name = name;
		this.avatarName = avatarName;
		this.description = description;
		this.birthday = birthday;
		this.death = death;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Animal(int _id, String _name, String _avatarName) {
		this.id = _id;
		this.name = _name;
		this.avatarName = _avatarName;
	}
}
