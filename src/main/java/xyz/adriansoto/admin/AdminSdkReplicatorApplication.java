package xyz.adriansoto.admin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class AdminSdkReplicatorApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AdminSdkReplicatorApplication.class, args);

		FileInputStream serviceAccount = new FileInputStream("sa.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://replicator-37607.firebaseio.com/")
				.build();
		FirebaseApp.initializeApp(options);
	}
}
