package fr.library.animaliomobile;

public class User {
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
	public Boolean onNewsletter;
	public Boolean onMobile;
	public Boolean isLoggedFacebook;
	public Boolean isBlacklist;
	public String createdAt;
	public String updatedAt;
	
	public User(String lastname, String firstname, String nickname,
			String email, String password, String createdAt, String updatedAt) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.email = email;
		this.avatar_name = "";
		this.password = "";
		this.birthday = "";
		this.phone = "";
		this.phoneMobile = "";
		this.zipCode = "";
		this.city = "";
		this.onNewsletter = false;
		this.onMobile = true;
		this.isLoggedFacebook = false;
		this.isBlacklist = false;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
