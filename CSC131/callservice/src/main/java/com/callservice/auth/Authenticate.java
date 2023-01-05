package com.callservice.auth;

//Use in conjunction w/thymeleaf to inform client of bad/denied requests
public class Authenticate 
{
    private String message;

    public Authenticate()
    {
        this.message = "";
    }

    public void setMessage(String m)
    {
        this.message = m;
    }

    public String getMessage()
    {
        return this.message;
    }
}
