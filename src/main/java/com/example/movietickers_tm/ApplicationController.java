package com.example.movietickers_tm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ApplicationController
{


    @FXML
    private Button minimize_btn;
    private static Admin currentUser;

    // region Navigation
    public static void setUser(Admin admin) throws SQLException
    {
        currentUser = admin;
        DButils.getAllInfo(admin);
    }

    public void closeButton(ActionEvent e)
    {
        System.exit(0);
    }

    public void minimize(ActionEvent e)
    {
        Stage stage = (Stage)minimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }
    //endregion

    // region Dashboard

    //endregion

    // region Add Movies

    //endregion

    // region Available Movies

    //endregion

    // region Edit Screening

    //endregion

    //region Customers

    //endregion
}
