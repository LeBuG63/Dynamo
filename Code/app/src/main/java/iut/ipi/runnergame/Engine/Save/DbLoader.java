package iut.ipi.runnergame.Engine.Save;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;


public class DbLoader implements Loader {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build();

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

    public DocumentSnapshot loadOne(DocumentReference docRef){
        final DocumentSnapshot[] d = new DocumentSnapshot[1];
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d("clem", "DocumentSnapshot data: " + document.getData());
                        d[0] = document;
                    } else {
                        Log.d("clem", "No such document");
                        d[0]=null;
                    }
                } else {
                    Log.d("clem", "get failed with ", task.getException());
                    d[0]=null;
                }
            }
        });
        return d[0];
    }
}
