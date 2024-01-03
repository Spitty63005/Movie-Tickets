package com.example.movietickers_tm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class DButils
{
    private static boolean isDashboardShowing = false;

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

    public static void switchScene(Admin admin) throws IOException, SQLException
    {
        if(isDashboardShowing)
        {
            isDashboardShowing = false;
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Hello authenticate to enter!");
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            isDashboardShowing = true;
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("application.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.initStyle(StageStyle.UNDECORATED);
            ApplicationController.setUser(admin);
            stage.setTitle("Welcome to Our Program!");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void getAllInfo(Admin admin) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = conn.prepareStatement("SELECT * FROM ADMIN WHERE username = ?");
        preparedStatement.setString(1, admin.getUsername());
        resultSet = preparedStatement.executeQuery();

        System.out.println(resultSet);
    }

    //endregion

    // region login
    public static Admin login(String user, String pass) throws SQLException
    {
        Connection conn = connectDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        // select everything where the username and password match each other
        preparedStatement = conn.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
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

    //endregion
}
