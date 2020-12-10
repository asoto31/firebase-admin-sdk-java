package xyz.adriansoto.admin.controllers;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/firestore")
public class FirestoreController {
    @GetMapping("getCollection")
    @ResponseBody
    public Object GetCollection(@RequestParam String colID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(colID).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Object> result = new ArrayList<Object>();
        for (DocumentSnapshot document : documents) {
            result.add(document.getData());
        }
        return result;
    }

    @GetMapping("getDocument")
    @ResponseBody
    public Object GetDocument(@RequestParam String colID, @RequestParam String docID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(colID).document(docID);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.getData();
        } else {
            return"No such document!";
        }
    }

    @PostMapping(path = "setDocument", consumes = "application/json", produces = "application/json")
    public String SetDocument(@RequestBody Map<String,Object> payload) throws ExecutionException, InterruptedException {
        if(!payload.containsKey("collection")) {
            return "Collection ID is required in payload";
        }
        if(!payload.containsKey("document")) {
            return "Document ID is required in payload";
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection((String) payload.get("collection")).document((String) payload.get("document")).set(payload.get("payload"));
        return "Document: " + payload.get("collection") + "/" + payload.get("document") + ", deleted";
    }

    @DeleteMapping("deleteDocument/{col}/{doc}")
    public String deleteDocument(@PathVariable String col, @PathVariable String doc) {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(col).document(doc).delete();
        return "Document: " + col + "/" + doc + ", deleted";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return name + " parameter is required";
    }
}
