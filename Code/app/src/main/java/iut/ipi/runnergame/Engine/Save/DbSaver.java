package iut.ipi.runnergame.Engine.Save;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DbSaver implements Saver{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void saveByUser (User u) {
        DocumentReference docRef = db.collection("Score").document(u.getId());
        String scoreUser;
        DbLoader l = new DbLoader();
        l.loadOne(docRef);
        Map<String,Object> scores = l.getScoreMap();
        /*if ( scoreMap != null && scoreMap.exists()) {
            scoreUser = scoreMap.getString("Score");
            Log.scoreMap("titi", "test :" + scoreUser);
        }*/
        if(scores.isEmpty()){
            Log.d("titi","Je suis vide "+ scores);
        }
        else{
            Log.d("titi","voici le score "+ scores);
        }
        /*else {
            Map<String, String> scores = new HashMap<>();
            scores.put("Nom", u.getPseudo());
            scores.put("Score", u.getScore());

            db.collection("Score").document(u.getId())
                    .set(scores)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.scoreMap("clem", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("clem", "Error writing document", e);
                        }
                    });
        }*/
    }
}
