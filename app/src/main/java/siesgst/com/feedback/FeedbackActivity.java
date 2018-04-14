package siesgst.com.feedback;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

public class FeedbackActivity extends AppCompatActivity {

    String courseName,teacherName,sid;

    RatingBar bar;
    EditText prn;
    EditText review;
    TextView courseHeading,teacherHeading;
    Button submit;

    AlertDialog.Builder builder;

    public static AVLoadingIndicatorView avl;

    String prnFilter;
    int temp;
    SubmitFeedback submitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        courseName = getIntent().getStringExtra("courseName");
        teacherName = getIntent().getStringExtra("teacherName");
        sid = getIntent().getStringExtra("sid");

        courseHeading = findViewById(R.id.course_heading);
        teacherHeading = findViewById(R.id.teacher_heading);

        courseHeading.setText(courseName);
        teacherHeading.setText("By Prof. "+teacherName);

        bar = findViewById(R.id.ratingBar);
        prn = findViewById(R.id.enterPrn);
        review = findViewById(R.id.giveReview);
        avl = findViewById(R.id.loader);
        submit = findViewById(R.id.submitBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prnFilter = prn.getText().toString();
                if(prnFilter.length()==8 ){
                    temp = 0;
                    if(prnFilter.charAt(0) == '1' && prnFilter.charAt(1) == '1' && prnFilter.charAt(2) == '5' && ( prnFilter.charAt(3) == 'a' || prnFilter.charAt(3) == 'A' ) && prnFilter.charAt(4) == '1' && prnFilter.charAt(5) == '0' && ( prnFilter.charAt(6) == '4' || prnFilter.charAt(6) == '5' || prnFilter.charAt(6) == '9' ) && (prnFilter.charAt(7) >= '0' || prnFilter.charAt(7) <= '9') ) {
                        temp = 0;
                        prnFilter = prnFilter.replace('a','A');
                    }
                    else {
                        temp = 1;
                        Log.d("ONE","here");
                    }
                }
                else
                {
                    temp = 1;
                    Log.d("TWO","here"+prnFilter.length());
                }
                if(temp == 0) {
                    builder = new AlertDialog.Builder(FeedbackActivity.this,R.style.MyDialogTheme)
                            .setTitle("Submit Feedback")
                            .setMessage(Html.fromHtml("<font color='#FFFFFF'>Are you sure that you want to submit feedback?</font>"))
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    submitFeedback = new SubmitFeedback(FeedbackActivity.this);
                                    submitFeedback.execute(String.valueOf(bar.getRating()),review.getText().toString(),prnFilter,sid);
                                }
                            })
                            .setNegativeButton("NO",null)
                            .setIcon(R.drawable.discuss);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    builder = new AlertDialog.Builder(FeedbackActivity.this,R.style.MyDialogTheme)
                            .setTitle("Submit Feedback")
                            .setMessage(Html.fromHtml("<font color='#FFFFFF'>Please check the entered PRN as it doesn't match the format..</font>"))
                            .setPositiveButton("OK", null)
                            .setIcon(R.drawable.discuss);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }
}
