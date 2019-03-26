package iut.ipi.runnergame.Engine.Save;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileSaver implements Saver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveByUser(User u){
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("res/score.bin", true))){
            writer.writeObject(u);
            Log.d("clem","OK");

        } catch (IOException e1) {
            e1.printStackTrace();
            Log.d("clem","PB");
        }
    }
}
