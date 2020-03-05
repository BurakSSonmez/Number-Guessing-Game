package com.example.istbr.dagitikdersproje;

public class EntityUser {
    private String username;
    private String email;
    private String password;
    private boolean permission;
    private int puan;

    public EntityUser() {
    }

    //puan eklemek için bu constructra veriler gönderilir.
    public EntityUser(String username, String email, String password, boolean permission, int puan) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.permission = permission;
        this.puan = puan;
    }

    /*public EntityUser(String username, String email, String password, boolean permission) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }
}
