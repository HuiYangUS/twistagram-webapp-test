package pojos.ui;

public class Profile {

	public String name = "";
	public String username = "";
	public String bio = "";

	public Profile() {
		// Basic constructor
	}

	public Profile(String name, String username, String bio) {
		if (name != null)
			this.name = name;
		if (username != null)
			this.username = username;
		if (bio != null)
			this.bio = bio;
	}

	public String getDisplayUsername() {
		if (username != null && !username.contains("@"))
			return "@" + username;
		else
			return username;
	}

}
