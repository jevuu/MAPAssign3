package com.example.d8.mapassign3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyTeamActivity extends AppCompatActivity {

    Intent intent;
    DatabaseHandler db;
    SportTeam team;
    int teamId;
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
    TextView txtUpdate;
    TextView txtDelete;
    TextView txtExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_team);
        intent  = getIntent();
        teamId  = intent.getIntExtra("lookUpID", 0);
        db      = new DatabaseHandler(getApplicationContext());

        editCity    = findViewById(R.id.editCity);
        editName    = findViewById(R.id.editName);
        editSport   = findViewById(R.id.editSport);
        editStadium = findViewById(R.id.editStadium);
        editMVP     = findViewById(R.id.editMVP);
        setEditTextWithTeamDetails(teamId);

        //Update team with new details
        /*txtUpdate = findViewById(R.id.txtUpdate);
        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamCity    = editCity.getText().toString();
                teamName    = editName.getText().toString();
                teamSport   = editSport.getText().toString();
                teamStadium = editStadium.getText().toString();
                teamMVP     = editMVP.getText().toString();

                //If teamCity and teamName are blank, do not update. Display Toast.
                if(teamCity.matches("") || teamName.matches("")){
                    Toast.makeText(getApplicationContext(), "City and Name are required!", Toast.LENGTH_SHORT).show();
                }else {
                    db.editTeam(teamId, teamName, teamSport, teamCity, teamStadium, teamMVP);
                    setEditTextWithTeamDetails(teamId);
                    Toast.makeText(getApplicationContext(), "Team details updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        //Delete the team and return to previous activity
        /*txtDelete = findViewById(R.id.txtDelete);
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteTeam(teamId);
                Toast.makeText(getApplicationContext(), "Team deleted!", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, null);
                finish();
            }
        });
        */

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

    //Sets edit text with team information. Used when page is loaded and after details updated.
    public void setEditTextWithTeamDetails(int teamId) {
        db      = new DatabaseHandler(getApplicationContext());
        team    = db.getTeam(teamId);

        editCity.setText(team.getCity());
        editName.setText(team.getName());
        editSport.setText(team.getSport());
        editStadium.setText(team.getStadium());
        editMVP.setText(team.getMVP());
    }
}
