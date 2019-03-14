package iut.ipi.runnergame.Engine.Save;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String pseudo;
    private String score;


    public User(String pseudo, String score, String id) {
        this.pseudo = pseudo;
        this.score = score;
        this.id=id;
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


}
