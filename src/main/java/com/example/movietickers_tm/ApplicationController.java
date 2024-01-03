package com.example.movietickers_tm;

import javafx.event.ActionEvent;

import java.sql.SQLException;

public class ApplicationController
{


    private static Admin currentUser;

    // region Navigation
    public static void setUser(Admin admin) throws SQLException
    {
        currentUser = admin;
        DButils.getAllInfo(admin);
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
