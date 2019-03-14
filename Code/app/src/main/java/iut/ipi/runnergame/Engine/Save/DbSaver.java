package iut.ipi.runnergame.Engine.Save;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DbSaver implements Saver{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void saveByUser (User u) {
        DocumentReference docRef = db.collection("Score").document(u.getId());
        String scoreUser;
        Loader l = new DbLoader();
        DocumentSnapshot d = l.loadOne(docRef);
        if (d!= null && d.exists()) {
            scoreUser = d.getString("Score");
            Log.d("clem2", "test :" + scoreUser);
        }
        /*
        Map<String,String> scores = new HashMap<>();
        scores.put("Nom",u.getPseudo());
        scores.put("Score",u.getScore());

        db.collection("Score").document(u.getId())
                .set(scores)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("clem", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("clem", "Error writing document", e);
                    }
                });
    */
    }
}
