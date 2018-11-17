package com.example.d8.mapassign3;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterTeamList extends ArrayAdapter<SportTeam> {
    Activity context;
    List<SportTeam> teams;
    TextView txtCity;
    TextView txtName;

    public AdapterTeamList(@NonNull Activity context, List<SportTeam> teams) {
        super(context, R.layout.activity_adapter_team_list);
        this.context = context;
        this.teams = teams;
    }

    //Because listview uses a arraylist of SportTeams, need custom getCount.
    @Override
    public int getCount(){
        return teams.size();
    }

    public @NonNull
    View getView(int position, View view, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_adapter_team_list, null, true);
        txtCity = rowView.findViewById(R.id.txtCity);
        txtName = rowView.findViewById(R.id.txtName);
        txtCity.setText(teams.get(position).getCity());
        txtName.setText(teams.get(position).getName());
        return rowView;
    }
}
