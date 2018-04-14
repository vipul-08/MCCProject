package siesgst.com.feedback;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Html;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SubmitFeedback extends AsyncTask<String,Void,String> {

    AlertDialog.Builder alertDialog;
    Context context;

    String rating;
    String review;
    String prn;
    String sid;

    public SubmitFeedback(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        alertDialog = new AlertDialog.Builder(context,R.style.MyDialogTheme);
        alertDialog.setTitle("Feedback Submit");
        alertDialog.setMessage("");
        FeedbackActivity.avl.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        String feedback_url = "http://feedback-siesgst.stackstaging.com/submitFeedback.php";

        rating = strings[0];
        review = strings[1];
        prn = strings[2];
        sid = strings[3];

        try {
            URL url = new URL(feedback_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            String data = URLEncoder.encode("rating","UTF-8")+
                    "="+
                    URLEncoder.encode(rating,"UTF-8")+
                    "&"+
                    URLEncoder.encode("review","UTF-8")+
                    "="+
                    URLEncoder.encode(review,"UTF-8")+
                    "&"+
                    URLEncoder.encode("prn","UTF-8")+
                    "="+
                    URLEncoder.encode(prn,"UTF-8")+
                    "&"+
                    URLEncoder.encode("sid","UTF-8")+
                    "="+
                    URLEncoder.encode(sid,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine())!=null) {
                response += line;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        FeedbackActivity.avl.setVisibility(View.INVISIBLE);
        if(s.equals("1")) {
            alertDialog.setMessage(Html.fromHtml("<font color='#FFFFFF'>Thank You, for giving your valuable feedback.<br>Your view matters to us.<br>We will consider this.</font>"));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(context,MainActivity.class));
                }
            });
        }
        else if(s.equals("2")) {
            alertDialog.setMessage(Html.fromHtml("<font color='#FFFFFF'>Error submitting feedback<br>You have already given feedback for the course.</font>"));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(context,MainActivity.class));
                }
            });
        }
        else if(s.equals("0")) {
            alertDialog.setMessage(Html.fromHtml("<font color='#FFFFFF'>Error</font>"));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(context,MainActivity.class));
                }
            });
        }
        FeedbackActivity.avl.setVisibility(View.INVISIBLE);
        AlertDialog alert = alertDialog.create();
        alert.setIcon(R.drawable.discuss);
        alert.show();
    }
}