package com.natali.voicelearningapp.kidOrParent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.natali.voicelearningapp.KidApp.Mainh;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.QueryPreferences;

import java.security.SecureRandom;

public class KidSingUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    private AuthCredential credential;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       mAuth = FirebaseAuth.getInstance();

        String randomPassword = generateAlphaNumericString();
        String email = randomPassword + "@voicelearningapp.com";
        singup(email,randomPassword);
    }

    private String randomString(int len, String mPassword) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(mPassword.charAt(rnd.nextInt(mPassword.length())));
        Log.i("password:", sb.toString());
        return sb.toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.kid_enter_number, container, false);
        view = v;
        return v;
    }

    private String generateAlphaNumericString() {
        String regexDigit = "\\d+";
        String regexAlphabets = "[a-zA-Z]+";
        String randomAlphaNumString;

        do {
            randomAlphaNumString = randomString(6, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        } while ((randomAlphaNumString.matches(regexDigit) || randomAlphaNumString.matches(regexAlphabets)));
        Log.i("new password:", randomAlphaNumString);
        return randomAlphaNumString;
    }

    public void singup(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("singUp", "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user, password);
                credential = EmailAuthProvider.getCredential(email, password);
            } else {
                // If sign in fails, display a message to the user.
                Log.w("singUp", "createUserWithEmail:failure", task.getException());
                updateUI(null,null);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser, String password) {
        if(currentUser != null && password != null){
            ((TextView)view.findViewById(R.id.code)).setText(password);
            (view.findViewById(R.id.buttonEnd)).setOnClickListener((v -> {
                //start kid play normaly ->(registerd)
                QueryPreferences.setISThisAKid(getContext(),true);
                startActivity(new Intent(getContext(), Mainh.class));
                getActivity().finish();
            }));
        }else if (currentUser != null){
            getActivity().finish();
        }
    }
}
