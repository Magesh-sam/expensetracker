package model.dto;

public class AppUser {
    private int userId;
    private String name;
    private Credential loginCredential;

    public AppUser() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Credential getLoginCredential() {
        return loginCredential;
    }

    public void setLoginCredential(Credential loginCredential) {
        this.loginCredential = loginCredential;
    }

}
