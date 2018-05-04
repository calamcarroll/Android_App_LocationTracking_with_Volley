package ie.app.volley_test_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static ie.app.volley_test_app.R.layout.my_custom_row;

class CustomAdaptor extends ArrayAdapter<String> {

    ArrayList<String> ProgramName;
    ArrayList<String> ProgramDate;

    public CustomAdaptor(Context context, ArrayList<String> ProgramName,ArrayList<String> ProgramDate) {
        super(context, R.layout.my_custom_row , ProgramName);
        this.ProgramName = ProgramName;
        this.ProgramDate = ProgramDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(my_custom_row,parent,false);

        String singleProgramName = getItem(position);
        String singleProgramDate = ProgramDate.get(position);
        TextView programName = (TextView) customView.findViewById(R.id.programName);
        TextView programDate = (TextView) customView.findViewById(R.id.date);
//        ImageView myImage = (ImageView) customView.findViewById(R.id.myImage);

        programName.setText(singleProgramName);
//        myImage.setImageResource(R.drawable.dumbbell);
        programDate.setText(singleProgramDate);
        return customView;
    }
}
