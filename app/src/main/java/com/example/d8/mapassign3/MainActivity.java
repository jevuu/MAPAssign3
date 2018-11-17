package com.example.d8.mapassign3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    List<SportTeam> sportTeams;
    TextView txtAddTeam;
    TextView txtExit;
    ListView listTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(getApplicationContext());

        //Starts AddTeamActivity
        txtAddTeam = findViewById(R.id.txtAddTeam);
        txtAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Closes the app
        txtExit = findViewById(R.id.txtExit);
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateTeamList();
        listTeam = findViewById(R.id.listTeam);
        listTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ModifyTeamActivity.class);
                intent.putExtra("lookUpID", sportTeams.get(position).getId());
                //Log.d("lookUpID before passing", ""+sportTeams.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            updateTeamList();
        }
    }

    //Display list of teams in database
    //Clicking on a list item opens ModifyTeamActivity
    //Update list of teams. Used when activity created and on result
    protected void updateTeamList(){
        sportTeams = db.getAllAsList();
        listTeam = findViewById(R.id.listTeam);
        ArrayAdapter<SportTeam> teamListAdapter = new AdapterTeamList(this, sportTeams);
        listTeam.setAdapter(teamListAdapter);
    }
}
