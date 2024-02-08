package com.example.movietickers_tm;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable
{
    @FXML
    private TableColumn<Movie, String> add_movie_title, add_movie_genre, add_movie_duration, add_date_published;

    @FXML
    private AnchorPane addMovieAp, availableMoviesAp, customersAp, dashboardAp, editScreeningAp;

    @FXML
    private TableView<Movie> add_table_view;

    @FXML
    private Button minimize_btn, sign_out_btn;

    @FXML
    private TextField add_movie_name_tf, add_movie_genre_tf, add_duration_tf;

    @FXML
    private DatePicker add_date_picker;

    @FXML
    private Label tab_name_lbl, available_movies_lbl, earns_today_lbl, tickets_sold_lbl, welcome_lbl;
    private static Admin currentUser;

    Movie currentMovie;
    ObservableList<Movie> addMovieList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addMoviesTable();
        makeWelcomeText();
    }

    public static void onLoad(Label welcome_lbl)
    {
        welcome_lbl.setText("Welcome \n" + currentUser.getName());
    }

    public void makeWelcomeText()
    {
        onLoad(welcome_lbl);
    }

    // region Navigation
    public static void setUser(Admin user) throws SQLException
    {
        currentUser = user;
        System.out.println(currentUser);
        DButils.getAllUserInfo(user);
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

    public void clearEverything()
    {
        // clear all text fields and other places with user input
        clearAllFields();

        // hide all the anhcor panes
        addMovieAp.visibleProperty().set(false);
        availableMoviesAp.visibleProperty().set(false);
        customersAp.visibleProperty().set(false);
        dashboardAp.visibleProperty().set(false);
        editScreeningAp.visibleProperty().set(false);
    }
    public void showDashboard()
    {
        clearEverything();
        dashboardAp.visibleProperty().set(true);
        tab_name_lbl.setText("Dashboard");
    }

    public void showAvailableMovies()
    {
        clearEverything();
        availableMoviesAp.visibleProperty().set(true);
        tab_name_lbl.setText("Now Showing");
    }

    public void showCustomers()
    {
        clearEverything();
        customersAp.visibleProperty().set(true);
        tab_name_lbl.setText("Customers");
    }

    public void showEditShowing()
    {
        clearEverything();
        editScreeningAp.visibleProperty().set(true);
        tab_name_lbl.setText("Edit Showing");
    }

    public void showAddMovies()
    {
        clearEverything();
        addMovieAp.visibleProperty().set(true);
        tab_name_lbl.setText("Add Movies");
    }

    public void clearAllFields()
    {
        // TODO clear the info of every textfield
        add_movie_name_tf.setText("");
        add_movie_genre_tf.setText("");
        add_duration_tf.setText("");
        add_date_picker.getEditor().clear();

    }

    public void signOut(ActionEvent e) throws SQLException, IOException
    {
        currentUser = null;
        DButils.switchScene(null, sign_out_btn, "login.fxml");
    }




    //endregion

    // region Dashboard

    //endregion

    // region Add Movies
    public void addMoviesTable()
    {
        addMovieList = DButils.getMoviesFromDatabase();

        add_movie_title.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        add_movie_genre.setCellValueFactory(new PropertyValueFactory<>("movieGenre"));
        add_movie_duration.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        add_date_published.setCellValueFactory(new PropertyValueFactory<>("moviePublishDate"));

        add_table_view.setItems(addMovieList);
    }

    public void addInsertMovie(ActionEvent e) throws SQLException
    {
        String title = add_movie_name_tf.getText();
        String duration = add_duration_tf.getText();
        String genre = add_movie_genre_tf.getText();
        LocalDate date = add_date_picker.getValue();
        DButils.addMovieToDataBase(title, duration, genre, date);
        addMoviesTable();
        clearAllFields();
    }

    public void addMovieSelectMovie()
    {
        currentMovie = add_table_view.getSelectionModel().getSelectedItem();
        int num = add_table_view.getSelectionModel().getSelectedIndex();

        if((num-1 < -1)) { return; }

        add_movie_name_tf.setText(currentMovie.getMovieName());
        add_duration_tf.setText(String.valueOf(currentMovie.getMovieDuration()));
        add_movie_genre_tf.setText(currentMovie.getMovieGenre());
        add_date_picker.setValue((currentMovie.getMoviePublishDate().toLocalDate()));
    }

    public void addMovieUpdate(ActionEvent e) throws SQLException
    {
        int selectedIndex = add_table_view.getSelectionModel().getSelectedIndex();
        String title = add_movie_name_tf.getText();
        String duration = add_duration_tf.getText();
        String genre = add_movie_genre_tf.getText();
        LocalDate date = add_date_picker.getValue();
        DButils.updateMovieInDataBase(title, duration, genre, date, selectedIndex+1);
        addMoviesTable();
        clearAllFields();
    }

    public void addDeleteMovie(ActionEvent e) throws SQLException
    {
        int selectedIndex = add_table_view.getSelectionModel().getSelectedIndex();
        DButils.deleteMovieFromDataBase(selectedIndex);
        addMoviesTable();
        clearAllFields();
    }

    //endregion

    // region Available Movies

    //endregion

    // region Edit Screening

    //endregion

    //region Customers

    //endregion
}
