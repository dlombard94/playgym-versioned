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

    public boolean madeProgress(Assessment assessment){
        boolean trend = false;
        //if only 1 assessment done it checks that against the startingweight
        if (assessments.size() == 1 ){
            if (this.startingweight<=assessments.get(0).weight){
                trend = false;
            }else{
                trend = true;
            }
        }

        int lastIndex = assessments.size()-1;
        if (assessments.size() > 1) {
            //checks the very first assessment(last in the list due to reverse()) against the startingweight
            if (assessments.indexOf(assessment)==lastIndex){
                if (this.startingweight<=assessment.weight){
                    trend = false;
                }else{
                    trend = true;
                }
            }else if (assessments.indexOf(assessment)!=lastIndex) {
                //for every remaining assessment - it checks it against the previous one
                if (assessment.weight < (assessments.get(assessments.indexOf(assessment) + 1).weight)) {
                    trend = true;
                } else {
                    trend = false;
                }
            }
        }

        return trend;
    }

    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

}
