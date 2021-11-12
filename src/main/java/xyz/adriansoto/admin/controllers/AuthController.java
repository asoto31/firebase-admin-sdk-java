package xyz.adriansoto.admin.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("getCustomTokenByUID")
    @ResponseBody
    public String CreateCustomToken(@RequestParam String uid) throws ExecutionException, InterruptedException {
        String customToken = FirebaseAuth.getInstance().createCustomTokenAsync(uid).get();
        return customToken;
    }
    @GetMapping("getUserByUID")
    public String GetUserByUID(@RequestParam String uid) throws ExecutionException, InterruptedException, FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        return "Successfully fetched user data: " + userRecord.getUid();
    }
    @GetMapping("getUserByEmail")
    public String GetUserByEmail(@RequestParam String email) throws ExecutionException, InterruptedException, FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        return "Successfully fetched user data: " + userRecord.getEmail();
    }
}
