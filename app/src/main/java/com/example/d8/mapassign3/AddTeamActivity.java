package com.example.d8.mapassign3;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTeamActivity extends AppCompatActivity {

    DatabaseHandler db;
    String teamCity;
    String teamName;
    String teamSport;
    String teamStadium;
    String teamMVP;
    EditText editCity;
    EditText editName;
    EditText editSport;
    EditText editStadium;
    EditText editMVP;
    TextView txtSubmit;
    TextView txtExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        db          = new DatabaseHandler(getApplicationContext());
        editCity    = findViewById(R.id.editCity);
        editName    = findViewById(R.id.editName);
        editSport   = findViewById(R.id.editSport);
        editStadium = findViewById(R.id.editStadium);
        editMVP     = findViewById(R.id.editMVP);

        //Submit form, adding team to database
        txtSubmit = findViewById(R.id.txtSubmit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamCity    = editCity.getText().toString();
                teamName    = editName.getText().toString();
                teamSport   = editSport.getText().toString();
                teamStadium = editStadium.getText().toString();
                teamMVP     = editMVP.getText().toString();

                //If teamCity and teamName are blank, do not add. Display toast
                if(teamCity.matches("") || teamName.matches("")){
                    Toast.makeText(getApplicationContext(), "City and Name are required!", Toast.LENGTH_SHORT).show();
                }else {
                    db.addTeam(teamName, teamSport, teamCity, teamStadium, teamMVP);
                    editCity.setText("");
                    editName.setText("");
                    editSport.setText("");
                    editStadium.setText("");
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
}
