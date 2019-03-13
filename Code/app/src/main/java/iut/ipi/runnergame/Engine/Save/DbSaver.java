package iut.ipi.runnergame.Engine.Save;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DbSaver implements Saver{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void save(User u) {
        Map<String,String> scores = new HashMap<>();
        scores.put("Nom","Test");
        scores.put("Score","25");

        db.collection("Score")
                .add(scores)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("clem","CA MARCHE "+documentReference.getId());
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("clem", "Error adding document", e);
                    }
                });
    }
}
