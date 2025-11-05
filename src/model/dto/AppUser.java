package model.dto;

public class AppUser {

    private int userId;
    private String name;
    private Credential loginCredential;
    private String mobileNumber;

    public AppUser() {
    }

    public AppUser(int userId, String name, Credential loginCredential, String mobileNumber) {
        this.userId = userId;
        this.name = name;
        this.loginCredential = loginCredential;
        this.mobileNumber = mobileNumber;
    }

    public AppUser(String name, Credential loginCredential, String mobileNumber) {
        this.name = name;
        this.loginCredential = loginCredential;
        this.mobileNumber = mobileNumber;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "AppUser [name=" + name + ", mobileNumber=" + mobileNumber + ", email=" + loginCredential.getEmail()
                + "]";
    }

}
