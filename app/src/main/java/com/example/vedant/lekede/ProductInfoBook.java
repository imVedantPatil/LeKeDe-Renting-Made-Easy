package com.example.vedant.lekede;

public class ProductInfoBook
{
    public String book_name,rent_price,deposit_ammount,duration,description,image_uri,user_name,phone_no,email_id;

    public ProductInfoBook(String book_name,String rent_price,String deposit_ammount,String duration,String description,String image_uri,String user_name,String phone_no,String email_id)
    {
        this.book_name=book_name;
        this.rent_price=rent_price;
        this.deposit_ammount=deposit_ammount;
        this.duration=duration;
        this.description=description;
        this.image_uri=image_uri;
        this.user_name=user_name;
        this.phone_no=phone_no;
        this.email_id=email_id;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setRent_price(String rent_price) {
        this.rent_price = rent_price;
    }

    public void setDeposit_ammount(String deposit_ammount) {
        this.deposit_ammount = deposit_ammount;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
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

    public ProductInfoBook()
    {

    }
    public String getBook_name() {
        return book_name;
    }

    public String getRent_price() {
        return rent_price;
    }

    public String getDeposit_ammount() {
        return deposit_ammount;
    }

    public String getDuration() {
        return duration;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public String getDescription() {
        return description;

    }

}
