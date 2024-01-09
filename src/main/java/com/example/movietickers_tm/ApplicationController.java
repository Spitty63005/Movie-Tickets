package com.example.movietickers_tm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable
{

    @FXML
    private AnchorPane addMovieAp, avaliableMoviesAp, customersAp, dashboardAp, editScreeningAp;

    @FXML
    private Button minimize_btn;

    @FXML
    private Label tab_name_lbl, available_movies_lbl, earns_today_lbl, tickets_sold_lbl;
    @FXML
    private static Label welcome_lbl;

    private static Admin currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public static void onLoad()
    {
        welcome_lbl.setText("Welcome \n" + currentUser.getName());
    }

    // region Navigation
    public static void setUser(Admin user) throws SQLException
    {
        currentUser = user;
        DButils.getAllUserInfo(user);
        onLoad();
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

    public void hideAllAnchorPanes()
    {
        addMovieAp.visibleProperty().set(false);
        avaliableMoviesAp.visibleProperty().set(false);
        customersAp.visibleProperty().set(false);
        dashboardAp.visibleProperty().set(false);
        editScreeningAp.visibleProperty().set(false);
    }
    public void showDashboard()
    {
        hideAllAnchorPanes();
        dashboardAp.visibleProperty().set(true);
        tab_name_lbl.setText("Dashboard");
    }

    public void showAvaliableMovies()
    {
        hideAllAnchorPanes();
        avaliableMoviesAp.visibleProperty().set(true);
        tab_name_lbl.setText("Now Showing");
    }

    public void showCustomers()
    {
        hideAllAnchorPanes();
        customersAp.visibleProperty().set(true);
        tab_name_lbl.setText("Customers");
    }

    public void showEditShowing()
    {
        hideAllAnchorPanes();
        editScreeningAp.visibleProperty().set(true);
        tab_name_lbl.setText("Edit Showing");
    }

    public void showAddMovies()
    {
        hideAllAnchorPanes();
        addMovieAp.visibleProperty().set(true);
        tab_name_lbl.setText("Add Movies");
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
