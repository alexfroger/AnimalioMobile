package fr.library.animaliomobile;

public class Message {
	// Paramètre
	public int id;
	public String name;
	public String content;
	public String avatar_name;

	// Constructeur
	public Message(int _id, String _name, String _content, String _avatar_name) {
		this.id = _id;
		this.name = _name;
		this.content = _content;
		this.avatar_name = _avatar_name;
	}

	public int getId() {
		return this.id;
	};
}
