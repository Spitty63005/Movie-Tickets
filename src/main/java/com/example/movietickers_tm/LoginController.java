package com.example.movietickers_tm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.*;

public class LoginController
{
    @FXML
    private AnchorPane login_ap, register_ap;

    @FXML
    private TextField login_username_tf, register_email_tf, register_username_tf, register_name_tf;

    @FXML
    private PasswordField login_password_pf, register_password_pf;

    @FXML
    private Button minimize_btn, login_btn, register_btn;


    //region navigation
    public void closeButton(ActionEvent e)
    {
        System.exit(0);
    }

    public void minimize(ActionEvent e)
    {
        Stage stage = (Stage)minimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    public void toggleLoginAndRegister()
    {
        clearFields();
        if(login_ap.visibleProperty().get())
        {
            login_ap.visibleProperty().set(false);
            register_ap.visibleProperty().set(true);
        }
        else
        {
            login_ap.visibleProperty().set(true);
            register_ap.visibleProperty().set(false);
        }
    }

    public void clearFields()
    {
        login_username_tf.setText("");
        register_email_tf.setText("");
        register_name_tf.setText("");
        register_username_tf.setText("");
        login_password_pf.setText("");
        register_password_pf.setText("");
    }

    //endregion
    //region login

    public void login(ActionEvent e) throws SQLException, IOException
    {

        String user = login_username_tf.getText();
        String pass = login_password_pf.getText();
        if(!user.isEmpty() && !pass.isEmpty())
        {
            Admin admin = DButils.login(user, pass);
            if(admin != null)
            {
                DButils.switchScene(admin, login_btn, "application.fxml");
            }
        }
    }
    //endregion

    //region signup
    public void signup(ActionEvent e)throws SQLException, IOException
    {
        String user = register_username_tf.getText();
        String email = register_email_tf.getText();
        String name = register_name_tf.getText();
        String password = register_password_pf.getText();
        boolean emptyTextBoxes = (user.isEmpty() && password.isEmpty() && name.isEmpty() && email.isEmpty());
        if(emptyTextBoxes)
            DButils.createAlert(Alert.AlertType.ERROR, "Please enter all test fields.", "Missing information.");
        else
        {
            System.out.println(":p");
            if(!validateRegisterCredentials(password))
            {
                DButils.createAlert(Alert.AlertType.ERROR, "Please enter a valid email.", "Invalid email.");
                return;
            }
            DButils.switchScene(DButils.signup(user, email, name, password), register_btn, "application.fxml");
        }
    }

    public boolean validateRegisterCredentials(String pass)
    {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(register_email_tf.getText());

        boolean hasCapitalChar = false;
        boolean hasNumberChar = false;
        boolean hasSpecialChar = false;

        for(int i = 0; i < pass.length(); i++)
        {
            if(Character.isDigit(pass.charAt(i)))
            {
                hasNumberChar = true;
                continue;
            }
            if(Character.isUpperCase(pass.charAt(i)))
            {
                hasCapitalChar = true;
                continue;
            }
            if(!Character.isDigit(pass.charAt(i)) && !Character.isLetter(pass.charAt(i)))
            {
                hasSpecialChar = true;
            }

        }



        return (hasCapitalChar && hasNumberChar && hasSpecialChar) && matcher.find();

    }
    //endregion

}