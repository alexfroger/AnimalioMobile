package fr.library.animaliomobile;

public class Friend {
	// Paramètre
	public int id;
	public String name;
	public int status;

	// Constructeur
	public Friend(int _id, String _name, int _status) {
		this.id = _id;
		this.name = _name;
		this.status = _status;
	}

	public int getFriendId() {
		return this.id;
	}
}
