package com.example.vedant.lekede;

public class ProductInfoFashion
{
    private String sub_category,rent_price,deposit_price,durationTime,description,uri,user_name,email,phone_no;

    public ProductInfoFashion() {
    }

    public ProductInfoFashion(String sub_category, String rent_price, String deposit_price, String durationTime, String description, String uri,String user_name,String phone_no,String email)
    {
        this.sub_category=sub_category;
        this.rent_price=rent_price;
        this.deposit_price=deposit_price;
        this.durationTime=durationTime;
        this.description=description;
        this.uri = uri;
        this.user_name=user_name;
        this.phone_no=phone_no;
        this.email=email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getRent_price() {
        return rent_price;
    }

    public void setRent_price(String rent_price) {
        this.rent_price = rent_price;
    }

    public String getDeposit_price() {
        return deposit_price;
    }

    public void setDeposit_price(String depostit_price) {
        this.deposit_price = depostit_price;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
