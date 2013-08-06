package fr.library.animaliomobile;

public class Notification {

	// paramètres
	public int id;
	public String who;
	public String toWhom;
	public int type;
	public int nbLike;
	public int nbComm;
	public String date;
	public String hour;
	public String event;
	public String detail_event;

	// Constructeur
	public Notification(int _id, String _who, String _toWhom, int _type,
			int _nbLike, int _nbComm, String _date, String _hour) {
		this.id = _id;
		this.who = _who;
		this.toWhom = _toWhom;
		this.type = _type;
		this.nbLike = _nbLike;
		this.nbComm = _nbComm;
		this.date = _date;
		this.hour = _hour;
	}

	public Notification(int _id, String _who, String _toWhom, int _type,
			int _nbLike, int _nbComm, String _date, String _hour,
			String _event, String _detail_event) {
		this.id = _id;
		this.who = _who;
		this.toWhom = _toWhom;
		this.type = _type;
		this.nbLike = _nbLike;
		this.nbComm = _nbComm;
		this.date = _date;
		this.hour = _hour;
		this.event = _event;
		this.detail_event = _detail_event;
	}

}
