package com.rorrim.mang.smartmirror.Model;

public class User {
    private String id;
    private String pw;
    private int money;

    public User(String id, String pw, int money){
        this.id = id;
        this.pw = pw;
        this.money = money;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", pw =" + pw + ", money =" + money;
    }

}
