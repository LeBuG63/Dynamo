package iut.ipi.runnergame.Engine.Save;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;


public class DbLoader implements Loader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();
    private Map<String,Object> scoreMap = new HashMap<>();


    Map<String, Object> getScoreMap() {
        return scoreMap;
    }

    public LinkedList<User> load() {
        firestore.collection("Score")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("clem", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("clem", "Error getting documents.", task.getException());
                        }
                    }
                });
        return null;

    }

    public void loadOne(DocumentReference docRef){
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists() && document.getData() != null) {
                        scoreMap.putAll(document.getData());
                        Log.d("clem", "DocumentSnapshot data: " + scoreMap);

                    } else {
                        Log.d("clem", "No such document");
                        scoreMap =null;
                    }
                }
                else {
                    Log.d("clem", "get failed with ", task.getException());
                    scoreMap =null;
                }
            }
        });
    }
}
