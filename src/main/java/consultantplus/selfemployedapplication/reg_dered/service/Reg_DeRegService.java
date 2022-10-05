package consultantplus.selfemployedapplication.reg_dered.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import consultantplus.selfemployedapplication.reg_dered.model.Reg_DeRegModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class Reg_DeRegService {

    public Reg_DeRegModel getInfo (Long Id) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = FirestoreClient
                .getFirestore()
                .collection("registration-deregistration")
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        Reg_DeRegModel reg_deRegModel = null;


        for (DocumentSnapshot document : documents) {

            if (document.getId().equals(Id.toString())) {
                reg_deRegModel = document.toObject(Reg_DeRegModel.class);
                break;
            }
        }

        return reg_deRegModel;
    }
}
