package xyz.adriansoto.admin.controllers;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {
    @GetMapping("hola")
    @ResponseBody
    public String Saluda(@RequestParam String uid) throws ExecutionException, InterruptedException {
        String customToken = FirebaseAuth.getInstance().createCustomTokenAsync(uid).get();
        return customToken;
    }
}
