package com.luxoft.trainings.jva005.domain;

public class User {

    private long userId;
    private String name;
    private String address;
    private String username;
    private String password;
    private int accessLevel;
    private int rating;

    public User(long userId, String name, String address, String username, String password, int accessLevel, int rating) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessLevel=" + accessLevel +
                ", rating=" + rating +
                '}';
    }
}
