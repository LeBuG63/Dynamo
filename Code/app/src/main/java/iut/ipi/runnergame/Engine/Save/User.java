package iut.ipi.runnergame.Engine.Save;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable, Parcelable {
    private String id;
    private String pseudo;
    private String score;


    public User(String pseudo, String score, String id) {
        this.pseudo = pseudo;
        this.score = score;
        this.id=id;
    }
    public User(String pseudo) {
        this.pseudo = pseudo;
        this.score = "-1";
        this.id=genererId();
    }

    protected User(Parcel in) {
        id = in.readString();
        pseudo = in.readString();
        score = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private String genererId (){
        Date d = new Date();
        return Long.toString(d.getTime());
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pseudo);
        dest.writeString(score);

    }
}
