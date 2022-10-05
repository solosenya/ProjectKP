package consultantplus.selfemployedapplication.selfEmployedTest.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import consultantplus.selfemployedapplication.selfEmployedTest.model.NodeModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FireBaseService {

    public NodeModel getNodeModel (String clientsAnswer, String previousQuestion) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = FirestoreClient
                .getFirestore()
                .collection("chatbot behaviour")
                .whereEqualTo("clientsAnswer", clientsAnswer)
                .whereEqualTo("previousQuestion", previousQuestion)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        NodeModel nodeModel = null;


        for (DocumentSnapshot document : documents) {
            nodeModel = document.toObject(NodeModel.class);
        }

        return nodeModel;
    }
}
