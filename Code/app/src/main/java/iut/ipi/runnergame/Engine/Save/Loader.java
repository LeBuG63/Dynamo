package iut.ipi.runnergame.Engine.Save;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.LinkedList;

public interface Loader {

    LinkedList<User> load();
    //void loadOne(DocumentReference docRef);
}
