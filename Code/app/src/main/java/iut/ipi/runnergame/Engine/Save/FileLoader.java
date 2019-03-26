package iut.ipi.runnergame.Engine.Save;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class FileLoader implements Loader {

    public LinkedList<User> load(){
        LinkedList<User> userList = new LinkedList<>();
        try{
            InputStream fin = new FileInputStream("res/score.bin");
            User u;
            do {
                ObjectInputStream reader = new ObjectInputStream(fin);
                u = (User)reader.readObject();
                userList.add(u);

            }while (u.getPseudo() != null);
        }
        catch (EOFException ignored){
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return userList;
    }



}
