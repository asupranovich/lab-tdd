package itechart.tdd.user.model;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User was not found");
	}

}
