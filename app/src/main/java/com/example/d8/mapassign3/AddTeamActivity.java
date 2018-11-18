package com.example.d8.mapassign3;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class AddTeamActivity extends AppCompatActivity {

    DatabaseHandler db;
    String teamCity;
    String teamName;
    String teamSport;
    String teamMVP;
    Uri teamLogoUri = Uri.parse("drawable/image_not_found");
    EditText editCity;
    EditText editName;
    Spinner spinSport;
    EditText editMVP;
    ImageView imgLogo;
    TextView txtUpload;
    TextView txtSubmit;
    TextView txtExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        db          = new DatabaseHandler(getApplicationContext());
        editCity    = findViewById(R.id.editCity);
        editName    = findViewById(R.id.editName);
        editMVP     = findViewById(R.id.editMVP);
        imgLogo     = findViewById(R.id.imgLogo);

        txtUpload = findViewById(R.id.txtUpload);
        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        spinSport = findViewById(R.id.spinSport);
        ArrayAdapter<CharSequence> sportSpinnerAdapter = ArrayAdapter.createFromResource(
                getApplicationContext(), R.array.sports, R.layout.adapter_sport_spinner);
        sportSpinnerAdapter.setDropDownViewResource(R.layout.adapter_sport_spinner_item);
        spinSport.setAdapter(sportSpinnerAdapter);
        spinSport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamSport = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Submit form, adding team to database
        txtSubmit = findViewById(R.id.txtSubmit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamCity    = editCity.getText().toString();
                teamName    = editName.getText().toString();
                teamMVP     = editMVP.getText().toString();

                //If teamCity and teamName are blank, do not add. Display toast
                if(teamCity.matches("") || teamName.matches("")){
                    Toast.makeText(getApplicationContext(), "City and Name are required!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    db.addTeam(teamName, teamSport, teamCity, teamMVP, teamLogoUri);
                    editCity.setText("");
                    editName.setText("");
                    editMVP.setText("");
                }
            }
        });

        //Return to previous activity
        txtExit = findViewById(R.id.txtExit);
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            teamLogoUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(teamLogoUri));
                imgLogo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
