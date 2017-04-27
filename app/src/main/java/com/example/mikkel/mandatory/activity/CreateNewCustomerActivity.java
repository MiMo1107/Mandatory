package com.example.mikkel.mandatory.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.Customer;
import com.example.mikkel.mandatory.rest.ApiClient;
import com.example.mikkel.mandatory.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mikkel.mandatory.R.id.email;

public class CreateNewCustomerActivity extends AppCompatActivity {


    private ApiInterface apiService;
    private List<Customer> customers;
    private Boolean firstName, lastName, mail, firstPassword, secondPassword, photoUrl;
    private EditText emailField, firstNameField, lastNameField, firstPasswordField, secondPasswordField, photoUrlField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_customer);
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getEmailList();

        firstName = false;
        lastName = false;
        mail = true;
        firstPassword = true;
        secondPassword = false;
        photoUrl = false;

        emailField = (EditText) findViewById(R.id.new_email);
        emailField.setText(email);
        emailField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().contains("@") && s.toString().contains(".")){
                    boolean found = false;
                    for(Customer c: customers){
                        if(c.getEmail().equals(s.toString())){
                            found = true;
                        }
                    }
                    if(found){
                        emailField.setBackground(getDrawable(R.drawable.input_denied));
                        mail = false;
                        Toast.makeText(getApplicationContext(), "Email allready in use",Toast.LENGTH_SHORT).show();
                    } else {
                        mail = true;
                        emailField.setBackground(getDrawable(R.drawable.input_allowed));
                    }
                } else {
                    mail = false;
                    emailField.setBackground(getDrawable(R.drawable.input_denied));
                }
            }
        });

        firstNameField = (EditText)  findViewById(R.id.new_firstName);
        firstNameField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    firstName = true;
                    firstNameField.setBackground(getDrawable(R.drawable.input_allowed));
                } else {
                    firstName = false;
                    firstNameField.setBackground(getDrawable(R.drawable.input_denied));
                }
            }
        });

        lastNameField = (EditText)  findViewById(R.id.new_lastName);
        lastNameField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    lastName = true;
                    lastNameField.setBackground(getDrawable(R.drawable.input_allowed));
                } else {
                    lastName = false;
                    lastNameField.setBackground(getDrawable(R.drawable.input_denied));
                }
            }
        });

        firstPasswordField = (EditText)  findViewById(R.id.new_password_first);
        firstPasswordField.setText(password);
        firstPasswordField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 4){
                    firstPassword = true;
                    firstPasswordField.setBackground(getDrawable(R.drawable.input_allowed));
                } else {
                    firstPassword = false;
                    firstPasswordField.setBackground(getDrawable(R.drawable.input_denied));
                }
            }
        });

        secondPasswordField = (EditText)  findViewById(R.id.new_password_second);
        secondPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(firstPasswordField.getText().toString().equals(s.toString()) && s.toString().length() > 4){
                    secondPassword = true;
                    secondPasswordField.setBackground(getDrawable(R.drawable.input_allowed));
                } else {
                    secondPassword = false;
                    secondPasswordField.setBackground(getDrawable(R.drawable.input_denied));
                }
            }
        });

        photoUrlField = (EditText)  findViewById(R.id.new_photo_url);
        photoUrlField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(URLUtil.isValidUrl(s.toString())){
                    ImageView image = (ImageView)findViewById(R.id.new_image);
                    Picasso.with(getApplicationContext()).load(s.toString()).into(image);
                    if(image.getDrawable() == null){
                        photoUrl = false;
                        photoUrlField.setBackground(getDrawable(R.drawable.input_denied));
                    } else {
                        photoUrl = true;
                        photoUrlField.setBackground(getDrawable(R.drawable.input_allowed));
                    }
                } else {
                    photoUrl = false;
                    photoUrlField.setBackground(getDrawable(R.drawable.input_denied));

                }
            }
        });
    }

    public void getEmailList(){
        customers = null;
        final Call<List<Customer>> call_customers = apiService.getAllCustomer();
        call_customers.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>>call_customers, Response<List<Customer>> response) {
                customers = response.body();
            }

            @Override
            public void onFailure(Call<List<Customer>>call, Throwable t) {
                Log.e("Fail", t.toString());
            }
        });
    }

    public void checkCustomer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

        if(firstName && lastName && mail && firstPassword && secondPassword){
            if(photoUrl){
                Customer customer = new Customer();
                customer.setEmail(emailField.getText().toString());
                customer.setFirstname(firstNameField.getText().toString());
                customer.setLastname(lastNameField.getText().toString());
                customer.setPassword(firstPasswordField.getText().toString());
                customer.setPictureUrl(photoUrlField.getText().toString());
                creatCustomer(customer);

            } else {
                builder.setTitle(R.string.user_noURL);
                builder.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Customer customer = new Customer();
                                customer.setEmail(emailField.getText().toString());
                                customer.setFirstname(firstNameField.getText().toString());
                                customer.setLastname(lastNameField.getText().toString());
                                customer.setPassword(firstPasswordField.getText().toString());
                                creatCustomer(customer);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else {
            builder.setTitle(R.string.user_checkInput);
            builder.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void creatCustomer(Customer customer){
        final Call<Void> create_customer = apiService.createCustomer(customer);
        create_customer.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void>create_customer, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User created",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Could not create the user",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void>call, Throwable t) {
                Log.e("Fail", t.toString());
            }
        });
    }
}
