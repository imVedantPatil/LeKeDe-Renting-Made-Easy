package com.example.vedant.lekede;

public class ProductInfoCar
{
    public String sub_category,company,rent_price,deposit_price,durationTime,descriptionText,user_name,phone_no,email_id,uri;

    public ProductInfoCar(String sub_category, String company, String rent_price, String deposit_price, String durationTime, String descriptionText,String uri, String user_name, String phone_no, String email_id) {
        this.sub_category = sub_category;
        this.company = company;
        this.rent_price = rent_price;
        this.deposit_price = deposit_price;
        this.durationTime = durationTime;
        this.descriptionText = descriptionText;
        this.uri=uri;
        this.user_name = user_name;
        this.phone_no = phone_no;
        this.email_id = email_id;
    }
    public ProductInfoCar() {
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public void setDeposit_price(String deposit_price) {
        this.deposit_price = deposit_price;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
