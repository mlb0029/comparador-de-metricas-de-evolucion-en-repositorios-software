package repositorydatasource.model;

/**
 * Information of the connected user.
 * 
 * @author Miguel Ángel León Bardavío - mlb0029
 *
 */
public class User {
	/**
	 * URL of the avatar.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private String avatarUrl;
	
	/**
	 * E-mail.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private String email;
	
	/**
	 * Name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private String name;
	
	/**
	 * Username.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 */
	private String username;

	/**
	 * Sets all attributes.
	 *
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param avatarUrl URL of the avatar.
	 * @param email E-mail.
	 * @param name Name.
	 * @param username Username.
	 */
	public User(String avatarUrl, String email, String name, String username) {
		setAvatarUrl(avatarUrl);
		setEmail(email);
		setName(name);
		setUsername(username);
	}

	/**
	 * Gets the avatarUrl.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * Sets the avatarUrl.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param avatarUrl the avatarUrl to set
	 */
	private void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * Gets the email.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param email the email to set
	 */
	private void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the username.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * @author Miguel Ángel León Bardavío - mlb0029
	 * @param username the username to set
	 */
	private void setUsername(String username) {
		this.username = username;
	}
}