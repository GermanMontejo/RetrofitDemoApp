package com.retrofitdemo.sampleclientapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String BASE_ENDPOINT = "http://192.168.1.59:9321";
    private TextView mTextViewData;
    private Button mButtonSendRequest;
    private Button mButtonSendRequestAll;
    private EditText mEditTextUserName;
    private PersonApiService personApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewData = (TextView) findViewById(R.id.text_view_data);
        mEditTextUserName = (EditText) findViewById(R.id.edit_text_user_name);
        mButtonSendRequest = (Button) findViewById(R.id.button_send_request);
        mButtonSendRequestAll = (Button) findViewById(R.id.button_send_request_all);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        personApiService = restAdapter.create(PersonApiService.class);
        mButtonSendRequest.setOnClickListener(this);
        mButtonSendRequestAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_request:
                String userName = mEditTextUserName.getText().toString();
                personApiService.retrieveSpecific(userName, new Callback<Person>() {

                    @Override
                    public void success(Person person, Response response) {
                        if (person != null) {
                            mTextViewData.setText("Name: " + person.getName() + "\nAge: " + person.getAge() + "\nMarital Status: " + person.getMaritalStatus() + "\nOccupation: " + person.getOccupation());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;
            case R.id.button_send_request_all:
                personApiService.retrieveAll(new Callback<Persons>() {

                    @Override
                    public void success(Persons persons, Response response) {
                        List<Person> personList = persons.getPersons();
                        String personDetails = "";
                        for (Person person : personList) {
                            personDetails += "\nName: " + person.getName() + "\nAge: " + person.getAge() + "\nMarital Status: " + person.getMaritalStatus() + "\nOccupation: " + person.getOccupation();
                        }
                        mTextViewData.setText(personDetails);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
    }
}
