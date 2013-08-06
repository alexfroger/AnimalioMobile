package fr.library.animaliomobile;

public class Message {
	// Paramètre
	public int id;
	public String name;
	public String content;

	// Constructeur
	public Message(int _id, String _name, String _content) {
		this.id = _id;
		this.name = _name;
		this.content = _content;
	}

	public int getId() {
		return this.id;
	};
}
