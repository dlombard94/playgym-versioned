package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Member extends Model
{
    public String name;
    public String gender;
    public String email;
    public String password;
    public String address;
    public double height;
    public double startingweight;

    //1 member has many assessments
    @OneToMany(cascade=CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();

    public Member(String name, String gender, String email, String password, String address, double height, double startingweight)
    {
        this.name = name;
        //gender validation taken form programming assignment
        if (gender.charAt(0) == 'M' || gender.charAt(0) =='m'){
            this.gender = "M";
        }else if (gender.charAt(0) == 'F' || gender.charAt(0) == 'f'){
            this.gender = "F";
        }else {
            this.gender = "Unspecified";
        }
        this.email = email;
        this.password = password;
        this.address = address;
        this.height = height;
        this.startingweight = startingweight;
    }


}
