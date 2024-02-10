package Interface;
import javax.swing.JButton;





/**
 * Interface representing user login functionality.
 */
public interface UserLogin {

    /**
     * Handles the login logic based on the provided user type, username, and password.
     *
     * @param userType the type of user (e.g., Admin, Employee)
     * @param username the username entered by the user
     * @param password the password entered by the user
     */
    void handleLogin(String userType, String username, String password);

    /**
     * Checks the login credentials for an employee.
     *
     * @param username the username entered by the employee
     * @param password the password entered by the employee
     * @return true if the login is successful, false otherwise
     */
    boolean checkEmployeeLogin(String username, String password);

    /**
     * Customizes the appearance of a button.
     *
     * @param button the button to be customized
     */
    void customizeButton(JButton button);
}
