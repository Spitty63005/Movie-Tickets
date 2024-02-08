package com.example.movietickers_tm;

public class Admin
{
    private String username, password, name, email;

    public Admin(String username, String password, String name, String email)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // region mutators
    public void setUsername(String newUsername)
    {
        username = newUsername;
    }
    public void setPassword(String newPassword)
    {
        password = newPassword;
    }
    public void setName(String newName)
    {
        name = newName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
    //endregion

    //region accessors

    public String getName()
    {
        return name;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }
    //endregion

}
