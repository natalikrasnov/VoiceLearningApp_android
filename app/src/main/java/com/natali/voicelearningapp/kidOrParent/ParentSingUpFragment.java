package com.natali.voicelearningapp.kidOrParent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.QueryPreferences;
import com.natali.voicelearningapp.parentApp.ParentMainActivity;


public class ParentSingUpFragment extends Fragment {

    private FirebaseAuth mAuth;
    private AuthCredential credential;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        (view.findViewById(R.id.enterPassword)).setOnKeyListener((v1, keyCode, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if(((EditText) v1).getText().length()!=0){
                    String password = ((EditText) v1).getText().toString();
                    String email = password + "@voicelearningapp.com";
                    credential = EmailAuthProvider.getCredential(email, password);
                    singIn((EditText) v1);
                }
            }
            return false;
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.parent_enter_code, container, false);
        return v;
    }


   public void singIn(EditText editText){
       editText.getText().clear();
       editText.setInputType(0);
       mAuth.signInWithCredential(credential)
               .addOnCompleteListener(task -> {
                   if(task.isSuccessful()) {
                       FirebaseUser currentUser = task.getResult().getUser();
                       updateUI(currentUser);
                   }else{
                       Log.i("logInForParrent: ","fail");
                       editText.setHint("סיסמא שגויה נסה שוב");
                       updateUI(null);
                   }
               });
   }


    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Log.i("logInForParrent: ","succssed");
            QueryPreferences.setISThisAKid(getContext(),false);
            new FirebaseData(currentUser.getUid());
            startActivity(new Intent(getContext(), ParentMainActivity.class));
            getActivity().finish();
        }else{
            //start sing up kid and father
            Log.i("logInForParrent: ","fail");
            Toast.makeText(getContext(),"שגיעה, נסה שוב פעם",Toast.LENGTH_LONG).show();
        }

    }
}
