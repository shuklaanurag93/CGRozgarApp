package com.cgemployment.cge_rozgarapp;




import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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


public class MainActivity extends AppCompatActivity {

    Uri downloadUri;
    String downloadUserAgent;
    String downloadMimeType;
    Dialog dialog;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         webView = findViewById(R.id.web);
       //  AllCertificatesAndHostsTruster.apply();

        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        ///////////////////////////////////////////////////////////////

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "wellcome");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "9098988002");//"No name defined" is the default value.

        Get_profile(mobileno);




    }
    private void download() {
        String[] path = downloadUri.getPath().split("/");
        String fileName = path[path.length - 1];
        if(!fileName.contains(".")) {
            fileName += MimeTypeMap.getSingleton().getExtensionFromMimeType(downloadMimeType);
        }

        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.addRequestHeader("User-Agent", downloadUserAgent);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                download();
            } else if (!shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("You permanently declined the storage permission witch is required to download this file. If you want to download it go to the settings and allow the storage permission.")
                        .setPositiveButton("Settings", (DialogInterface p0, int p1) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri intentUri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(intentUri);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", (DialogInterface p0, int p1) -> {})
                        .show();
            }
        }
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
                                String url = Constants_URL.FrmUserCardForApp + mobileno;
                                String xtenURL = url;


                                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(xtenURL));
                                startActivity(urlIntent);


                          /*   webView.loadUrl(xtenURL);

                                // this will enable the javascript.
                               webView.getSettings().setJavaScriptEnabled(true);

                                // WebViewClient allows you to handle
                                // onPageFinished and override Url loading.
                              webView.setWebViewClient(new WebViewClient());





                          /*      webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
                                    Uri uri = Uri.parse(url);

                                    String[] path = uri.getPath().split("/");
                                    String fileName = path[path.length - 1];
                                    if (!fileName.contains(".")) {
                                        fileName += MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                                    }

                                    DownloadManager.Request request = new DownloadManager.Request(uri);

                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.addRequestHeader("User-Agent", userAgent);
                                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                    downloadManager.enqueue(request);


                                });
                                webView.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> {
                                    downloadUri = Uri.parse(url);
                                    downloadUserAgent = userAgent;
                                    downloadMimeType = mimeType;

                                    // Permission is only required for API level <= 28 && Permissions are granted at runtime only for API level 23 and above
                                    if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        boolean hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                                        if(!hasWriteExternalStoragePermission) {
                                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                        } else {
                                            download();
                                        }
                                    } else {
                                        download();
                                    }
                                }); */

                            }else if(result.equals("404")){
                                dialog.dismiss();

                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (MainActivity.this);
                                View view = LayoutInflater.from(MainActivity.this).inflate(
                                        R.layout.layout_error_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                                );
                                builder.setCancelable(false);
                                builder.setView(view);
                                ((TextView) view.findViewById(R.id.textTitle))
                                        .setText("असफल");

                                ((TextView) view.findViewById(R.id.textMessage))
                                        .setText("पंजीयन/नवीनीकरण नहीं हुआ है, कृपया पंजीयन/नवीनीकरण पूर्ण करें |" );
                                ((Button) view.findViewById(R.id.buttonAction))
                                        .setText("बंद करें");
                                ((ImageView) view.findViewById(R.id.imageIcon))
                                        .setImageResource(R.drawable.file);

                                final AlertDialog alertDialog = builder.create();
                                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getApplicationContext(), dashboard.class));
                                        finish();
                                        alertDialog.dismiss();

                                    }
                                });
                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }
                                alertDialog.show();

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
                                        MainActivity.this);
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
                                    MainActivity.this);
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

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        MainActivity.this);
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