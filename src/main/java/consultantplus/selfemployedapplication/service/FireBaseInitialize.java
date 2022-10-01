package consultantplus.selfemployedapplication.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FireBaseInitialize {

    @PostConstruct
    public void initialize() throws IOException {

        FileInputStream serviceAccount = new FileInputStream(
                        "C:/Users/solos/IdeaProjects/SelfEmployedApplication/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

    }
}
