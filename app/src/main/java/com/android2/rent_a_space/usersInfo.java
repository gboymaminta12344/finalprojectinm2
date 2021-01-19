package com.android2.rent_a_space;

public class usersInfo {


    private String FullName;
    private String UserEmail;
    private String UserPhone;
    private String UserId;
    private String Address;
    private String UserImageUri;
    private String isUser;

    public usersInfo(){

        // no args constructor
    }

    public usersInfo(String FullName,String UserEmail,String UserPhone,String UserId,String Address,String UserImageUri,String isUser){
        this.FullName = FullName;
        this.UserEmail = UserEmail;
        this.UserPhone = UserPhone;
        this.UserId = UserId;
        this.isUser = isUser;
        this.Address = Address;
        this.UserImageUri = UserImageUri;

    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUserImageUri() {
        return UserImageUri;
    }

    public void setUserImageUri(String userImageUri) {
        UserImageUri = userImageUri;
    }
}


