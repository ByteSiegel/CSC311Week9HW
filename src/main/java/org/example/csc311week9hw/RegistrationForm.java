package org.example.csc311week9hw;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.regex.Pattern;

/**
 * RegistrationForm is a JavaFX application that allows users to input and validate their registration information.
 * The form includes fields for First Name, Last Name, Email, Date of Birth, and Zip Code, with appropriate validations.
 *
 * Author: Benjamin Siegel
 */
public class RegistrationForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Form");

        // Creating the form layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-background-radius: 10;");

        // First Name Label and Input
        Label firstNameLabel = createStyledLabel("First Name:");
        TextField firstNameInput = createStyledTextField();
        Label firstNameValidation = createStyledValidationLabel();
        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameInput, 1, 0);
        grid.add(firstNameValidation, 2, 0);

        // Last Name Label and Input
        Label lastNameLabel = createStyledLabel("Last Name:");
        TextField lastNameInput = createStyledTextField();
        Label lastNameValidation = createStyledValidationLabel();
        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameInput, 1, 1);
        grid.add(lastNameValidation, 2, 1);

        // Email Label and Input
        Label emailLabel = createStyledLabel("Email:");
        TextField emailInput = createStyledTextField();
        Label emailValidation = createStyledValidationLabel();
        grid.add(emailLabel, 0, 2);
        grid.add(emailInput, 1, 2);
        grid.add(emailValidation, 2, 2);

        // Date of Birth Label and Input
        Label dobLabel = createStyledLabel("Date of Birth (MM/DD/YYYY):");
        TextField dobInput = createStyledTextField();
        Label dobValidation = createStyledValidationLabel();
        grid.add(dobLabel, 0, 3);
        grid.add(dobInput, 1, 3);
        grid.add(dobValidation, 2, 3);

        // Zip Code Label and Input
        Label zipLabel = createStyledLabel("Zip Code:");
        TextField zipInput = createStyledTextField();
        Label zipValidation = createStyledValidationLabel();
        grid.add(zipLabel, 0, 4);
        grid.add(zipInput, 1, 4);
        grid.add(zipValidation, 2, 4);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setDisable(true);
        submitButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;");
        grid.add(submitButton, 1, 5);

        // Result Label
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
        grid.add(resultLabel, 1, 6);

        // Focus Change Listeners for Validation
        addValidationOnFocusLost(firstNameInput, firstNameValidation, this::isValidName, "Invalid First Name");
        addValidationOnFocusLost(lastNameInput, lastNameValidation, this::isValidName, "Invalid Last Name");
        addValidationOnFocusLost(emailInput, emailValidation, this::isValidEmail, "Invalid Email");
        addValidationOnFocusLost(dobInput, dobValidation, this::isValidDateOfBirth, "Invalid Date of Birth");
        addValidationOnFocusLost(zipInput, zipValidation, this::isValidZipCode, "Invalid Zip Code");

        // Disable or Enable Submit Button based on Validation
        ChangeListener<String> submitButtonListener = (observable, oldValue, newValue) -> {
            boolean isFirstNameValid = isValidName(firstNameInput.getText());
            boolean isLastNameValid = isValidName(lastNameInput.getText());
            boolean isEmailValid = isValidEmail(emailInput.getText());
            boolean isDobValid = isValidDateOfBirth(dobInput.getText());
            boolean isZipValid = isValidZipCode(zipInput.getText());

            submitButton.setDisable(!(isFirstNameValid && isLastNameValid && isEmailValid && isDobValid && isZipValid));
        };

        // Listen to text changes to enable/disable submit button
        firstNameInput.textProperty().addListener(submitButtonListener);
        lastNameInput.textProperty().addListener(submitButtonListener);
        emailInput.textProperty().addListener(submitButtonListener);
        dobInput.textProperty().addListener(submitButtonListener);
        zipInput.textProperty().addListener(submitButtonListener);

        // Submit Button Action Handler
        submitButton.setOnAction(event -> {
            resultLabel.setText("Registration Successful!");
            showNextUI(primaryStage);
        });

        // Setting the scene
        VBox vbox = new VBox(grid);
        vbox.setPadding(new Insets(20));
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a styled label for form fields.
     *
     * @param text The label text.
     * @return A styled Label.
     */
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        return label;
    }

    /**
     * Creates a styled TextField for form input.
     *
     * @return A styled TextField.
     */
    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdbdbd; -fx-border-radius: 5; -fx-background-radius: 5;");
        return textField;
    }

    /**
     * Creates a styled validation label for displaying validation messages.
     *
     * @return A styled Label for validation.
     */
    private Label createStyledValidationLabel() {
        Label label = new Label();
        label.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        return label;
    }

    /**
     * Adds a focus listener to a TextField to perform validation when focus is lost.
     *
     * @param textField         The TextField to add the listener to.
     * @param validationLabel   The Label to display validation messages.
     * @param validationMethod  The method to validate the input.
     * @param errorMessage      The error message to display if validation fails.
     */
    private void addValidationOnFocusLost(TextField textField, Label validationLabel,
                                          ValidationFunction validationMethod, String errorMessage) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Focus lost
                if (!validationMethod.validate(textField.getText())) {
                    validationLabel.setText(errorMessage);
                    textField.setStyle("-fx-border-color: red; -fx-background-color: #ffe6e6; -fx-border-radius: 5; -fx-background-radius: 5;");
                } else {
                    validationLabel.setText("");
                    textField.setStyle("-fx-border-color: #bdbdbd; -fx-background-color: #ffffff; -fx-border-radius: 5; -fx-background-radius: 5;");
                }
            }
        });
    }

    // Functional interface for validation methods
    @FunctionalInterface
    interface ValidationFunction {
        boolean validate(String input);
    }

    // Name Validation Method
    private boolean isValidName(String name) {
        return name.matches("^[A-Za-z]{2,25}$");
    }

    // Email Validation Method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@farmingdale\\.edu$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Date of Birth Validation Method
    private boolean isValidDateOfBirth(String dob) {
        String dobRegex = "^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$";
        return dob.matches(dobRegex);
    }

    // Zip Code Validation Method
    private boolean isValidZipCode(String zip) {
        return zip.matches("\\d{5}");
    }

    /**
     * Navigates to the next user interface after successful registration.
     *
     * @param stage The current stage to display the new UI
     */
    private void showNextUI(Stage stage) {
        Label welcomeLabel = new Label("Welcome to the next UI!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: blue;");
        VBox vbox = new VBox(welcomeLabel);
        vbox.setPadding(new Insets(20));
        Scene nextScene = new Scene(vbox, 400, 200);
        stage.setScene(nextScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
