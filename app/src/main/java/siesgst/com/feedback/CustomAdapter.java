package siesgst.com.feedback;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private Context mContext;
    private List<DataModel> list;

    public CustomAdapter(@NonNull Context mContext ,@LayoutRes ArrayList<DataModel> list) {

        super(mContext,0,list);
        this.mContext = mContext;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        DataModel currentDataModel = list.get(position);

        TextView tname = listItem.findViewById(R.id.tname);
        tname.setText(currentDataModel.getTeacherName());

        TextView courseName = listItem.findViewById(R.id.subject);
        courseName.setText(currentDataModel.getCourseName());

        return listItem;
    }

}
