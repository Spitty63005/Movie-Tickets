package com.example.movietickers_tm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private AnchorPane login_ap, register_ap;

    @FXML
    private TextField login_userfield;

    @FXML
    private PasswordField login_passfield;


    public void closeButton(ActionEvent e)
    {
        System.exit(0);
    }

    public void minimizeButton(ActionEvent e)
    {
        // TODO ask sean riley
    }

    public void toggleLoginAndRegister()
    {
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

    public void login(ActionEvent e) throws SQLException, IOException
    {
        String user = login_userfield.getText();
        String pass = login_passfield.getText();
        if(!user.isEmpty() && !pass.isEmpty())
        {
            DButils.switchScene(DButils.login(user, pass));
        }
    }
}