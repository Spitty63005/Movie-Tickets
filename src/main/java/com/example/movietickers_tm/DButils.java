package com.example.movietickers_tm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DButils
{
    // region connections and other reused in methods
    public static Connection connectDB()
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "password");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void createAlert(Alert.AlertType alertType, String context, String title)
    {
        Alert alert = new Alert(alertType);
        alert.setContentText(context);
        alert.setTitle(title);
        alert.show();
    }

    public static void closingUtils(Connection conn, ResultSet rs, PreparedStatement ps) throws SQLException
    {
        conn.close();
        rs.close();
        ps.close();
    }

    public static void switchScene(Admin admin, Button btn, String fxmlFileName) throws IOException, SQLException
    {
        Stage currentStage = (Stage) btn.getScene().getWindow();
        double width = currentStage.getWidth();
        double height = currentStage.getHeight();
        if(width > 600)
        {
            width -= 200;
            height-= 200;
        }
        else
        {
            width += 200;
            height += 200;
        }
        currentStage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
        if (admin != null)
        {
            ApplicationController.setUser(admin);
        }
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void getAllUserInfo(Admin user) throws SQLException
    {
        System.out.println(user);
        Connection conn = connectDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = conn.prepareStatement("SELECT email, name FROM users WHERE username = ?");
        preparedStatement.setString(1, user.getUsername());
        resultSet = preparedStatement.executeQuery();

        if(!resultSet.isBeforeFirst())
        {
            createAlert(Alert.AlertType.ERROR, "Username or Password is incorrect.",
                    "Incorrect login Info");
        }
        while(resultSet.next() && user.getEmail().isEmpty())
        {
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
        }
    }

    //endregion

    // region login
    public static Admin login(String user, String pass) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        // select everything where the username and password match each other
        preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, pass);
        resultSet = preparedStatement.executeQuery();

        if(!resultSet.isBeforeFirst())
        {
            createAlert(Alert.AlertType.ERROR, "Username is incorrect, user not found.",
                    "User does not exist");
            return null;
        }
        else
        {
            while(resultSet.next())
            {
                return new Admin(user, pass, "", "");
            }
            createAlert(Alert.AlertType.ERROR, "Password does not match.", "Incorrect Password.");
        }
        closingUtils(conn, resultSet, preparedStatement);
        return null;

    }

    //endregion

    // region sign up
    public static Admin signup(String user, String email, String name, String password) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement;
        PreparedStatement insertNewUser;
        ResultSet resultSet;

        preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE USERNAME = ?");
        preparedStatement.setString(1, user);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.isBeforeFirst())
        {
            createAlert(Alert.AlertType.ERROR, "Username is already in use please pick another one.", "Username is taken.");
        }
        else
        {
            insertNewUser = conn.prepareStatement("INSERT INTO users (username, password, email, name) VALUES (?, ?, ?, ?)");
            insertNewUser.setString(1, user);
            insertNewUser.setString(2, password);
            insertNewUser.setString(3, email);
            insertNewUser.setString(4, name);
            insertNewUser.executeUpdate();
            insertNewUser.close();
        }
        closingUtils(conn, resultSet, preparedStatement);

        return new Admin(user, password, name, email);
    }
    //endregion

    //region add movies

    public static ObservableList<Movie> getMoviesFromDatabase()
    {
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        Connection connection = connectDB();
        try
        {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies");
            ResultSet rs = ps.executeQuery();
            Movie movie;
            while(rs.next())
            {
                movie = new Movie(rs.getString("movieName"), rs.getString("genre"),
                        rs.getDate("releaseDate"), rs.getString("duration"));

                movieList.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movieList;
    }

    public static void addMovieToDataBase(String title, String duration, String genre, LocalDate date) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO movies (movieName, genre," +
                " duration, releaseDate) VALUES(?, ?, ?, ?);");

        System.out.println(duration);

        Date sqlDate = Date.valueOf(date);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, genre);
        preparedStatement.setString(3, duration);
        preparedStatement.setDate(4,  sqlDate);

        preparedStatement.executeUpdate();
    }

    public static void updateMovieInDataBase(String title, String duration, String genre, LocalDate date, int index) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement(
                "UPDATE movies SET movieName = ?, genre = ?, duration = ?, releaseDate = ?" +
                "WHERE movieId =" + index+";");



        Date sqlDate = Date.valueOf(date);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, genre);
        preparedStatement.setString(3, duration);
        preparedStatement.setDate(4,  sqlDate);

        preparedStatement.executeUpdate();
    }

    public static void deleteMovieFromDataBase(int selectedIndex) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM movies WHERE movieid = "+selectedIndex+";");
        preparedStatement.executeUpdate();
    }

    //endregion
}
