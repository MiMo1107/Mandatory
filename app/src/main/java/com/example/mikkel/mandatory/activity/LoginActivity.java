package com.example.mikkel.mandatory.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.Admin;
import com.example.mikkel.mandatory.model.Customer;
import com.example.mikkel.mandatory.model.Dish;
import com.example.mikkel.mandatory.rest.ApiClient;
import com.example.mikkel.mandatory.rest.ApiInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ApiInterface apiService;
    private List<Admin> admins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();


        admins = new ArrayList<Admin>();
        loadFromFirebase();
        Switch rememberSwitch = (Switch)findViewById(R.id.login_saveInfo);
        rememberSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    findViewById(R.id.login_autoLogin).setClickable(true);
                } else {
                    ((Switch)findViewById(R.id.login_autoLogin)).setChecked(false);
                    findViewById(R.id.login_autoLogin).setClickable(false);
                }
            }
        });

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button anonymousSignInButton = (Button) findViewById(R.id.anonymous_sign_in_button);
        anonymousSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        loadPreferences();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        for (Admin admin: admins){
            if(admin.getPassword().equals(password) && admin.getUsername().equals(email)){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("admin", true);
                startActivity(intent);
                showProgress(false);
                return;
            }
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            userLogin(email,password);
        }
    }

    private boolean isEmailValid(String email) {
        return (email.contains("@") && email.contains("."));
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }
    public void userLogin(final String email, final String password){

        final Call<Customer> call_customer = apiService.getCustomer(email, password);
        call_customer.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer>call_customer, Response<Customer> response) {
                Customer customer = response.body();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Gson gson = new Gson();
                intent.putExtra("Customer", gson.toJson(customer));
                startActivity(intent);
                showProgress(false);

                if(((Switch)findViewById(R.id.login_saveInfo)).isChecked()){
                    savePreferences(email, password);
                }
            }

            @Override
            public void onFailure(Call<Customer>call, Throwable t) {
                Log.e("Fail", t.toString());
                checkConnection(email, password);
            }
        });
    }

    public void checkConnection(final String email, final String password){
        final Call<Void> call_connection = apiService.getConnection();
        call_connection.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void>call_connection, Response<Void> response) {
                if(response.isSuccessful()){
                    checkEmail(email, password);
                } else {
                    endApp();
                }

            }

            @Override
            public void onFailure(Call<Void>call, Throwable t) {
                Log.e("Fail", t.toString());
                endApp();
            }
        });
    }

    public void endApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Closing the app");
        builder.setMessage("No connection to the server");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finishAffinity();
                System.exit(1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkEmail(final String email, final String password){
        final Call<List<Customer>> call_customers = apiService.getAllCustomer();
        call_customers.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>>call_customers, Response<List<Customer>> response) {
                List<Customer> customers = response.body();
                boolean found = false;
                for(Customer c: customers){
                    if(c.getEmail().equals(email)){
                        found = true;
                    }
                }
                if(found){
                    wrongPassword();
                } else {
                    createNewCustomer(email, password);
                }
            }

            @Override
            public void onFailure(Call<List<Customer>>call, Throwable t) {
                Log.e("Fail", t.toString());
            }
        });
    }

    public void wrongPassword(){
        showProgress(false);
        Toast.makeText(this, "Email and password does not match",Toast.LENGTH_LONG).show();
    }

    public void createNewCustomer(String email, String password){
        Intent intent = new Intent(LoginActivity.this,CreateNewCustomerActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        showProgress(false);
        return;
    }

    public void savePreferences(String email, String password){
        Log.e("SavePref", "starting");
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("auto", ((Switch)findViewById(R.id.login_autoLogin)).isChecked());
        editor.commit();
    }

    public void loadPreferences(){
        SharedPreferences settings;
        settings = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE); //1
        String email = settings.getString("email", null);
        String password = settings.getString("password", null);
        Boolean autoLogin = settings.getBoolean("auto", false);
        if(autoLogin){
            showProgress(true);
            ((Switch)findViewById(R.id.login_saveInfo)).setChecked(true);
            ((Switch)findViewById(R.id.login_autoLogin)).setChecked(true);
            findViewById(R.id.login_autoLogin).setClickable(true);
            ((AutoCompleteTextView)findViewById(R.id.email)).setText(email);
            ((TextView)findViewById(R.id.password)).setText(password);
            userLogin(email, password);
        } else if(email != null && password != null){
            ((Switch)findViewById(R.id.login_saveInfo)).setChecked(true);
            findViewById(R.id.login_autoLogin).setClickable(true);
            ((AutoCompleteTextView)findViewById(R.id.email)).setText(email);
            ((TextView)findViewById(R.id.password)).setText(password);;
        }
    }

    public void loadFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String, HashMap<String, String>> values = (HashMap)dataSnapshot.getValue();
                Iterator myVeryOwnIterator = values.keySet().iterator();
                while(myVeryOwnIterator.hasNext()) {
                    String key=(String)myVeryOwnIterator.next();
                    HashMap<String, String> value= values.get(key);
                    Iterator myVeryOwnIterator2 = value.keySet().iterator();
                    Admin admin = new Admin();
                    while(myVeryOwnIterator2.hasNext()) {
                        String key2=(String)myVeryOwnIterator2.next();
                        String value2=(String) value.get(key2);
                        if(key2.equals("username")){
                            admin.setUsername(value2);
                        } else if (key2.equals("password")){
                            admin.setPassword(value2);
                        }
                        admins.add(admin);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });

    }
}

