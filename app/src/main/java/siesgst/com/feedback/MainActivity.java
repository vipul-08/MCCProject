package siesgst.com.feedback;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<DataModel> list = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_teachers);
        list.add(0,new DataModel("DDB Lecture","Aparna Bannore"));
        list.add(1,new DataModel("SPCC Lecture","Prachi Shahane"));
        list.add(2,new DataModel("MCC Lecture","Suvarna Chaure"));
        list.add(3,new DataModel("SE Lecture","Sunil Punjabi"));
        list.add(4,new DataModel("OR Lecture","Bhavna R"));
        list.add(5,new DataModel("DDB Practicals","Ujwala Ravale"));
        list.add(6,new DataModel("SPCC Practicals","Anindita Khade"));
        list.add(7,new DataModel("MCC Practicals","Suvarna Chaure"));
        list.add(8,new DataModel("SE Practicals","Suneha Patil"));
        list.add(9,new DataModel("OR Practicals","Bhavna R"));
        list.add(10,new DataModel("NPL Practicals","Roopal Mamtora"));

        customAdapter = new CustomAdapter(this,list);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
                intent.putExtra("courseName",list.get(position).getCourseName());
                intent.putExtra("teacherName",list.get(position).getTeacherName());
                intent.putExtra("sid",String.valueOf(position+1));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
