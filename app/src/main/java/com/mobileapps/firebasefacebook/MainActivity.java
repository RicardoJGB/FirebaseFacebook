package com.mobileapps.firebasefacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements UserAuthenticator.Callback{


    private EditText etUserEmail;
    private EditText etPassword;
    private UserAuthenticator userAuthenticator;
    public static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAuthenticator = new UserAuthenticator(this,new CompleteListener(this));

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        callbackManager = CallbackManager.Factory.create();



        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();



        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }


      @Override
    public void onUserValidated(FirebaseUser user) {

        Toast.makeText(this,"Welcome"+user.getEmail(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onUserInvalidated() {
        Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show();

    }

    public void onSignIn(View view) {

        userAuthenticator.signIn(etUserEmail.getText().toString(),
                etPassword.getText().toString());

    }

    public void onSignUp(View view) {

        userAuthenticator.signUp(etUserEmail.getText().toString(),
                etPassword.getText().toString());

    }
}
