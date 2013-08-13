package fr.library.animaliomobile;

public class Friend {
	// Paramètre
	public int id;
	public String lastname;
	public String firstname;
	public String nickname;
	public String email;
	public String avatar_name;
	public String password;
	public String birthday;
	public String phone;
	public String phoneMobile;
	public String zipCode;
	public String city;
	public int onNewsletter;
	public int onMobile;
	public int isLoggedFacebook;
	public int isBlacklist;
	public String createdAt;
	public String updatedAt;

	public Friend(int id, String lastname, String firstname, String nickname,
			String email, String avatar_name, String password, String birthday,
			String phone, String phoneMobile, String zipCode, String city,
			int onNewsletter, int onMobile, int isLoggedFacebook,
			int isBlacklist, String createdAt, String updatedAt) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.email = email;
		this.avatar_name = avatar_name;
		this.password = password;
		this.birthday = birthday;
		this.phone = phone;
		this.phoneMobile = phoneMobile;
		this.zipCode = zipCode;
		this.city = city;
		this.onNewsletter = onNewsletter;
		this.onMobile = onMobile;
		this.isLoggedFacebook = isLoggedFacebook;
		this.isBlacklist = isBlacklist;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Friend(int id, String nickname, String avatarNname, int onMobile) {
		this.id = id;
		this.nickname = nickname;
		this.avatar_name = avatarNname;
		this.onMobile = onMobile;
	}
}
