package com.example.vedant.lekede;

public class UserInformation
{
    public String email,name,adhar_number,phone_number,uri;;
    public String sub_category,company,rent_price,deposit_price,durationTime,descriptionText,user_name,phone_no,email_id;
    public UserInformation()
    {

    }
    public UserInformation(String email,String name,String adno,String phno)
    {
        this.email=email;
        this.name = name;
        this.adhar_number=adno;
        this.phone_number = phno;
    }

    public UserInformation(String uri) {
        this.uri = uri;
    }

    public UserInformation(String sub_category, String company, String rent_price, String deposit_price, String durationTime, String descriptionText, String uri,String user_name,String phone_no,String email_id)
    {
        this.sub_category=sub_category;
        this.company=company;
        this.rent_price=rent_price;
        this.deposit_price=deposit_price;
        this.durationTime=durationTime;
        this.descriptionText=descriptionText;
        this.uri=uri;
        this.user_name=user_name;
        this.phone_no=phone_no;
        this.email_id=email_id;
    }
    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPhone()
    {
        return phone_number;
    }
    public String getUri()
    {
        return uri;
    }

    public String getAdhar_number() {
        return adhar_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getCompany() {
        return company;
    }

    public String getRent_price() {
        return rent_price;
    }

    public String getDeposit_price() {
        return deposit_price;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

}
