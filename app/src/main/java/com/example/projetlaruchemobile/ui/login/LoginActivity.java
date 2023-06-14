package com.example.projetlaruchemobile.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetlaruchemobile.ApiService;
import com.example.projetlaruchemobile.LoginRequest;
import com.example.projetlaruchemobile.R;
import com.example.projetlaruchemobile.SalesActivity;
import com.example.projetlaruchemobile.TokenResponse;
import com.example.projetlaruchemobile.User;
import com.example.projetlaruchemobile.ui.login.LoginViewModel;
import com.example.projetlaruchemobile.ui.login.LoginViewModelFactory;
import com.example.projetlaruchemobile.databinding.ActivityLoginBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private ApiService apiService;
    private SharedPreferences sharedPref;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("fesfesf");

        //setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //usernameEditText = findViewById(R.id.username);
        //passwordEditText = findViewById(R.id.password);
        //loginButton = findViewById(R.id.login);

        usernameEditText = binding.username;
        passwordEditText = binding.password;
        loginButton = binding.login;

        // Initialize Retrofit and ApiService

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Initialize SharedPreferences
        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            Log.d("LoginActivity", "test");
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            LoginRequest request = new LoginRequest(username, password);

            Call<TokenResponse> call = apiService.login(request);
            call.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if (response.isSuccessful()) {
                        String token = response.body().getToken();

                        // Store the token locally
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("token", token);
                        editor.apply();

                        // Proceed to fetch user info
                        fetchUserInfo(username, token);
                    } else {
                        // Handle login failure
                        System.out.println("fail");
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    // Handle network or API call failure
                    System.out.println("fail");
                }
            });
        });
    }

    private void fetchUserInfo(String username, String token) {
        Call<User> call = apiService.getUser(username, "Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    // Store user info locally
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("userId", user.getUserId());
                    editor.putString("username", user.getUsername());
                    editor.putString("firstName", user.getFirstName());
                    editor.putString("lastName", user.getLastName());
                    editor.putInt("privileges", user.getPrivileges());
                    editor.putInt("locationId", user.getLocationId());
                    editor.apply();

                    // Proceed to next activity
                    startNextActivity();
                } else {
                    // Handle user info retrieval failure
                    System.out.println("fail");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle network or API call failure
                System.out.println("fail");
            }
        });
    }

    private void startNextActivity() {
        Intent myIntent = new Intent(LoginActivity.this, SalesActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            //finish();
            Intent myIntent = new Intent(LoginActivity.this, SalesActivity.class);
            LoginActivity.this.startActivity(myIntent);
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }
     */

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}