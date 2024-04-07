package com.cgemployment.cge_rozgarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    LinearLayout back_profile;
    TextView txtname, txtmobile;

    ImageView image;
    TextView txtemail,DOB,newRegno,category,fathername,regdate,renewalDate,graceperiodInMonths,dateAfterGraceperiod,gender;

    TextView txtname2,txtmobile2;
    CardView cardView,cardView2;
    Dialog dialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back_profile=findViewById(R.id.back_profile);
        txtname = findViewById(R.id.txtname);
        txtmobile = findViewById(R.id.txtmobile);
        image = findViewById(R.id.image);
        txtemail = findViewById(R.id.txtemail);
        txtmobile2 = findViewById(R.id.txtmobile2);
        txtname2 = findViewById(R.id.txtname2);
        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardView2);
        DOB = findViewById(R.id.DOB);
        newRegno = findViewById(R.id.newregno);
        category = findViewById(R.id.category);
        fathername = findViewById(R.id.fathername);
        regdate = findViewById(R.id.regdate);
        renewalDate = findViewById(R.id.renewalDate);
        graceperiodInMonths = findViewById(R.id.graceperiodInMonths);
        dateAfterGraceperiod = findViewById(R.id.dateAfterGraceperiod);
        gender = findViewById(R.id.gender);



     //  AllCertificatesAndHostsTruster.apply();

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(profile.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        ///////////////////////////////////////////////////////////////

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "wellcome");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.


        txtname2.setText(userName);
        txtmobile2.setText(mobileno);


        cardView.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
/////////////////////////////////////get_details//////////////////////
        Get_profile(mobileno);


        back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),dashboard.class));
                finish();
            }
        });


    }

    public void Get_profile(String mobileno) {
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("mobileNo", mobileno);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GetCandidateDetails, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("statusCode");

                            if (result.equals("200")) {
                                dialog.dismiss();
                                cardView.setVisibility(View.VISIBLE);
                                cardView2.setVisibility(View.GONE);

                                JSONObject output = response.getJSONObject("data");
                                String  MSG = output.getString("profileImage");

                                     /////////////////////image_view////////////////////////////////
                                byte[] decodedString = Base64.decode(MSG, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                image.setImageBitmap(decodedByte);

                                txtname.setText("नाम - "+output.getString("name"));
                                txtmobile.setText("मोबाइल न. - "+output.getString("mobNo"));
                                txtemail.setText("ईमेल - "+output.getString("email"));

                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String inputDateStr=output.getString("dob");
                                Date date = inputFormat.parse(inputDateStr);
                                String CDOB = outputFormat.format(date);


                                DOB.setText("जन्म दिनांक - "+CDOB);
                                String _category;
                                if(output.getString("category").equals("GN")){
                                    _category="General";
                                }else{
                                        _category=output.getString("category");
                                }

                                category.setText("वर्ग - "+_category);
                                fathername.setText("पिता का नाम - "+output.getString("fathername"));
                                newRegno.setText("पंजीयन क्र. - "+output.getString("newRegno"));


                                String input=output.getString("regdate");
                                Date date1 = inputFormat.parse(input);
                                String Cregdate = outputFormat.format(date1);

                                regdate.setText("पंजीयन दिनांक - "+Cregdate);

                                String input2=output.getString("renewalDate");
                                Date date2 = inputFormat.parse(input2);
                                String CrenewalDate = outputFormat.format(date2);

                                renewalDate.setText("नवीनीकरण दिनांक - "+CrenewalDate);

                                graceperiodInMonths.setText("अनुग्रह अवधि महीनों में - "+output.getString("graceperiodInMonths"));

                                String input3=output.getString("dateAfterGraceperiod");
                                Date date3 = inputFormat.parse(input3);
                                String CdateAfterGraceperiod = outputFormat.format(date3);

                                dateAfterGraceperiod.setText("अनुग्रह अवधि के बाद की तारीख - "+CdateAfterGraceperiod);
                                    String _gender="";
                                if(output.getString("gender").equals("M")){
                                    _gender="Male";
                                }else if(output.getString("gender").equals("F")){
                                    _gender="Female";
                                }else if (output.getString("gender").equals("T")){
                                    _gender="Transgender";
                                }

                                gender.setText("लिंग - "+ _gender);

                            }else if(result.equals("404")){
                                dialog.dismiss();
                                cardView.setVisibility(View.GONE);
                                cardView2.setVisibility(View.VISIBLE);


                            }

                            else{

                                dialog.dismiss();

                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        profile.this);
                                alert.setTitle("सन्देश !");
                                alert.setCancelable(false);
                                alert.setMessage(MSG);
                                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    profile.this);
                            alert.setTitle("सन्देश !");
                            alert.setCancelable(false);
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            alert.show();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        profile.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alert.show();

                error.printStackTrace();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("X-Content-Type-Options", "nosniff");
                params.put("X-XSS-Protection", "1; mode=block");
                params.put("X-Frame-Options", "deny");
                params.put("Content-Security-Policy", "default-src 'self'");
                params.put("Strict-Transport-Security", "63072000; includeSubDomains; preload");
                String credentials = Constants_URL.USERNAME+":"+Constants_URL.PASSWORD;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonRequest);




    }
}