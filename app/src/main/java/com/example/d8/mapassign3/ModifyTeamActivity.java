package com.example.d8.mapassign3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class ModifyTeamActivity extends AppCompatActivity {

    Intent intent;
    DatabaseHandler db;
    SportTeam team;
    int teamId;
    String teamCity;
    String teamName;
    String teamSport;
    String teamMVP;
    Uri teamLogoUri;
    EditText editCity;
    EditText editName;
    Spinner spinSport;
    EditText editMVP;
    ImageView imgLogo;
    TextView txtUpload;
    TextView txtUpdate;
    TextView txtDelete;
    TextView txtExit;
    ArrayAdapter<CharSequence> sportSpinnerAdapter; //This is here because it is needed for 2 class functions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_team);
        intent  = getIntent();
        teamId  = intent.getIntExtra("lookUpID", 0);
        db      = new DatabaseHandler(getApplicationContext());

        editCity    = findViewById(R.id.editCity);
        editName    = findViewById(R.id.editName);
        editMVP     = findViewById(R.id.editMVP);
        imgLogo     = findViewById(R.id.imgLogo);

        spinSport   = findViewById(R.id.spinSport);
        sportSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.sports, R.layout.adapter_sport_spinner);
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

        setEditTextWithTeamDetails(teamId);

        txtUpload = findViewById(R.id.txtUpload);
        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        //Update team with new details
        txtUpdate = findViewById(R.id.txtUpdate);
        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamCity    = editCity.getText().toString();
                teamName    = editName.getText().toString();
                teamMVP     = editMVP.getText().toString();

                //If teamCity and teamName are blank, do not update. Display Toast.
                if(teamCity.matches("") || teamName.matches("")){
                    Toast.makeText(getApplicationContext(), "City and Name are required!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    db.editTeam(teamId, teamName, teamSport, teamCity, teamMVP, teamLogoUri);
                    setEditTextWithTeamDetails(teamId);
                    Toast.makeText(getApplicationContext(), "Team details updated!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Delete the team and return to previous activity
        txtDelete = findViewById(R.id.txtDelete);
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTeam(teamId);
                Toast.makeText(getApplicationContext(), "Team deleted!", Toast.LENGTH_SHORT)
                        .show();
                setResult(RESULT_OK, null);
                finish();
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

    //Sets ExitText, Spinner, and imgLogo with team information. Used when page is loaded and after details updated.
    public void setEditTextWithTeamDetails(int teamId) {
        db      = new DatabaseHandler(getApplicationContext());
        team    = db.getTeam(teamId);

        editCity.setText(team.getCity());
        editName.setText(team.getName());
        int spinPosition = sportSpinnerAdapter.getPosition(team.getSport());
        spinSport.setSelection(spinPosition);
        editMVP.setText(team.getMVP());
        teamLogoUri = team.getLogoUri();

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(team.getLogoUri()));
            imgLogo.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
