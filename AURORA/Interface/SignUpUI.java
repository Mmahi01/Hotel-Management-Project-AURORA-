package Interface;

import javax.swing.*;

public interface SignUpUI {

    /**
     * Initializes the UI components for sign-up.
     */
    void initializeUI();

    /**
     * Gets the first name entered by the user.
     *
     * @return the first name
     */
    String getFirstName();

    /**
     * Gets the last name entered by the user.
     *
     * @return the last name
     */
    String getLastName();

    /**
     * Gets the email entered by the user.
     *
     * @return the email
     */
    String getEmail();

    /**
     * Gets the password entered by the user.
     *
     * @return the password
     */
    String getPassword();

    /**
     * Clears the input fields.
     */
    void clearFields();

    /**
     * Handles the sign-up action.
     */
    void signUp();

    /**
     * Customizes the appearance of a button.
     *
     * @param button the button to customize
     */
    void customizeButton(JButton button);

    /**
     * Creates a text field.
     *
     * @return a new JTextField
     */
    JTextField createTextField();
}
