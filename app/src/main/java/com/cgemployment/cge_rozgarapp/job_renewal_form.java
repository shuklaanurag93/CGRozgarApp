package com.cgemployment.cge_rozgarapp;

import static android.Manifest.permission.CAMERA;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraCharacteristics;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class job_renewal_form extends AppCompatActivity implements SurfaceHolder.Callback, CameraSource.PictureCallback{


    LinearLayout back_ren, btn_check_reg;

    String _input_type = "";
    RadioGroup input_group;
    TextInputEditText input_tv;
    Dialog dialog;

    LinearLayout adhll;
    LinearLayout btn_aadhar_otp;

    TextInputEditText adhar_tv;

    TextInputLayout aadhar_tv_layout;

    LinearLayout Aadhar_Otp_ll, Aadhar_Otp_submit, reg_form_ll;

    PinView PinViewAadharOTP;

    TextInputEditText username_tv;
    CheckBox Aadhardeclaration_checkbox;

    LinearLayout input_ll, input;

    String token;

    TextInputEditText mobile_tv, fathersname_tv;
    RadioButton gender_M, gender_F, gender_T, married, unmarried, caste_Gen, caste_OBC, caste_SC, caste_ST, disability_Yes, disability_NO;

    TextInputEditText DOB_picker_actions;
    LinearLayout next_btn, reg_form_llA, reg_form_llB;
    RadioGroup disability_group;
    RadioButton disability_radio_btn;
    RadioButton rural, urban;
    LinearLayout back_Reg;
    LinearLayout disability_ll;
    LinearLayout urban_ll, rural_ll;
    LinearLayout click_photo_btn, submit_btn;
    private Uri picUri;
    LinearLayout image_ll;
    ImageView img;
    String encodedimage;
    CheckBox declaration_checkbox;
    Spinner Highest_Qualification_sp, Subject_sp, StatusOf_job_Sp, state_Sp, Dist_Sp, block_Sp, nagrinikay_Sp, ward_ll_sp, panchayt_Sp, gram_Sp, disability_sp;
    int MY_SOCKET_TIMEOUT_MS = 500000;

    String HighestQualificationCode, StateCode;

    List<HighestQualificationModelClass> HighestQualificationModelClassList = new ArrayList<>();
    List<SubjectModelClass> SubjectModelClassClassList = new ArrayList<>();
    List<StateMasterModelClass> StateMasterModelClassList = new ArrayList<>();
    List<DistrictMasterModelClass> DistrictMasterModelClassList = new ArrayList<>();
    List<BlockMasterModelClass> BlockMasterModelClassList = new ArrayList<>();
    List<NagriyNikayModelClass> NagriyNikayModelClasssList = new ArrayList<>();
    List<WardMasterModelClass> WardMasterModelClasssList = new ArrayList<>();
    List<GramPanchaytMasterModelClass> GramPanchaytMasterModelClassList = new ArrayList<>();
    List<GramModelClass> GramModelClassList = new ArrayList<>();
    List<StatusOfjobModelClass> StatusOf_jobModelClassList = new ArrayList<>();
    List<DisabilityspModelClass> disabilityspModelClassList = new ArrayList<>();

    List<SubCategoryMasterModelClass> SubCategoryMasterModelClassList = new ArrayList<>();


    String defaultTextForSpinner = "चयन करें";

    LinearLayout Subject_ll;

    TextInputLayout fathers_layout, datepicker_layout, username_layout, mobile_tv_layout;


    RadioGroup gender_group, married_group, caste_group;

    LinearLayout urbanrul_ll, Dist_ll;

    RadioGroup ruralUrban_group;


    String Gender = "";
    String MaritalStatus = "";
    String Category = "";
    String Disability = "";
    String DisabilityTypeCode = "";
    String subjectCode = "";
    String EmployementStatus = "";
    String villageCode = "";
    String wardCode = "";
    String NagriCode = "";
    String BLockCode = "";
    String panchaytCode = "";
    String DistCode = "";
    String Oversease = "";

    TextInputEditText Address_tv, pincode_tv, email_tv;

    String rurarban = "";
    Bitmap mainbitmap;

    RadioGroup oversease_group;

    String _oldRegno="";
     String _exchangeId="";
    String AadharAuth;

    TextInputEditText income_tv;
    TextInputLayout income_tv_layout;

    String _oldName="";
    String _regdate="";
    String _name="";

    RadioButton pvtg_yes,pvtg_no;

    RadioGroup pvtg_group;
    LinearLayout pvtg_ll;
    String _specialCategory="";
    LinearLayout pvtg_cata_ll;

    Spinner pvtgsp_sp;
    String cataCode="";
    LinearLayout rotate,reclick;


    public static final int CAMERA_REQUEST = 101;
    public static Bitmap bitmap;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private String[] neededPermissions = new String[]{CAMERA};
    private FaceDetector detector;
    private CameraSource cameraSource;
    TextView tv_capture;

    FrameLayout frame_layout;
    TextView blinkCount, notemsg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_renewal_form);

        surfaceView = findViewById(R.id.surfaceView);
        tv_capture = findViewById(R.id.tv_capture);
        frame_layout = findViewById(R.id.frame_layout);
        blinkCount = findViewById(R.id.blinkCount);
        notemsg = findViewById(R.id.notemsg);


        back_ren = findViewById(R.id.back_ren);
        btn_check_reg = findViewById(R.id.btn_check_reg);
        input_group = findViewById(R.id.input_group);
        input_tv = findViewById(R.id.input_tv);
        adhll = findViewById(R.id.adhll);
        btn_aadhar_otp = findViewById(R.id.btn_aadhar_otp);
        aadhar_tv_layout = findViewById(R.id.aadhar_tv_layout);
        adhar_tv = findViewById(R.id.adhar_tv);
        Aadhar_Otp_ll = findViewById(R.id.Aadhar_Otp_ll);
        Aadhar_Otp_submit = findViewById(R.id.Aadhar_Otp_submit);
        reg_form_ll = findViewById(R.id.reg_form_ll);
        PinViewAadharOTP = findViewById(R.id.PinViewAadharOTP);
        username_tv = findViewById(R.id.username_tv);
        Aadhardeclaration_checkbox = findViewById(R.id.Aadhardeclaration_checkbox);
        input_ll = findViewById(R.id.input_ll);
        input = findViewById(R.id.input);
        mobile_tv = findViewById(R.id.mobile_tv);
        gender_T = findViewById(R.id.gender_T);
        gender_F = findViewById(R.id.gender_F);
        gender_M = findViewById(R.id.gender_M);
        unmarried = findViewById(R.id.unmarried);
        married = findViewById(R.id.married);
        fathersname_tv = findViewById(R.id.fathersname_tv);
        DOB_picker_actions = findViewById(R.id.DOB_picker_actions);
        caste_Gen = findViewById(R.id.caste_Gen);
        caste_OBC = findViewById(R.id.caste_OBC);
        caste_SC = findViewById(R.id.caste_SC);
        caste_ST = findViewById(R.id.caste_ST);
        next_btn = findViewById(R.id.next_btn);
        reg_form_llA = findViewById(R.id.reg_form_llA);
        reg_form_llB = findViewById(R.id.reg_form_llB);
        disability_Yes = findViewById(R.id.disability_Yes);
        disability_NO = findViewById(R.id.disability_NO);


        disability_group = findViewById(R.id.disability_group);
        disability_ll = findViewById(R.id.disability_ll);
        urban = findViewById(R.id.urban);
        rural = findViewById(R.id.rural);
        urban_ll = findViewById(R.id.urban_ll);
        rural_ll = findViewById(R.id.rural_ll);
        click_photo_btn = findViewById(R.id.click_photo_btn);
        image_ll = findViewById(R.id.image_ll);
        img = findViewById(R.id.img);
        declaration_checkbox = findViewById(R.id.declaration_checkbox);
        submit_btn = findViewById(R.id.submit_btn);
        Highest_Qualification_sp = findViewById(R.id.Highest_Qualification_sp);
        Subject_sp = findViewById(R.id.Subject_sp);
        StatusOf_job_Sp = findViewById(R.id.StatusOf_job_Sp);
        state_Sp = findViewById(R.id.state_Sp);
        Dist_Sp = findViewById(R.id.Dist_Sp);
        block_Sp = findViewById(R.id.block_Sp);
        nagrinikay_Sp = findViewById(R.id.nagrinikay_Sp);
        ward_ll_sp = findViewById(R.id.ward_ll_sp);
        panchayt_Sp = findViewById(R.id.panchayt_Sp);
        gram_Sp = findViewById(R.id.gram_Sp);
        aadhar_tv_layout = findViewById(R.id.aadhar_tv_layout);
        Subject_ll = findViewById(R.id.Subject_ll);
        disability_sp = findViewById(R.id.disability_sp);
        fathers_layout = findViewById(R.id.fathers_layout);
        datepicker_layout = findViewById(R.id.datepicker_layout);
        gender_group = findViewById(R.id.gender_group);
        married_group = findViewById(R.id.married_group);
        caste_group = findViewById(R.id.caste_group);
        username_layout = findViewById(R.id.username_layout);
        mobile_tv_layout = findViewById(R.id.mobile_tv_layout);
        adhll = findViewById(R.id.adhll);
        urbanrul_ll = findViewById(R.id.urbanrul_ll);
        Dist_ll = findViewById(R.id.Dist_ll);
        ruralUrban_group = findViewById(R.id.ruralUrban_group);
        Address_tv = findViewById(R.id.Address_tv);
        pincode_tv = findViewById(R.id.pincode_tv);
        email_tv = findViewById(R.id.email_tv);
        Aadhardeclaration_checkbox = findViewById(R.id.Aadhardeclaration_checkbox);
        oversease_group = findViewById(R.id.oversease_group);

        income_tv = findViewById(R.id.income_tv);
        income_tv_layout = findViewById(R.id.income_tv_layout);

        pvtg_no = findViewById(R.id.pvtg_no);
        pvtg_yes = findViewById(R.id.pvtg_yes);
        pvtg_group = findViewById(R.id.pvtg_group);
        pvtg_ll = findViewById(R.id.pvtg_ll);
        pvtg_cata_ll = findViewById(R.id.pvtg_cata_ll);
        pvtgsp_sp = findViewById(R.id.pvtgsp_sp);

        rotate = findViewById(R.id.rotate);
        reclick = findViewById(R.id.reclick);


        adhll.setVisibility(View.GONE);
        Aadhar_Otp_ll.setVisibility(View.GONE);
        reg_form_ll.setVisibility(View.GONE);
        btn_aadhar_otp.setVisibility(View.GONE);
        btn_check_reg.setVisibility(View.VISIBLE);
        reg_form_llB.setVisibility(View.GONE);
        disability_ll.setVisibility(View.GONE);
        urban_ll.setVisibility(View.GONE);
        rural_ll.setVisibility(View.GONE);
        click_photo_btn.setVisibility(View.GONE);
        image_ll.setVisibility(View.GONE);
        submit_btn.setVisibility(View.GONE);
        Aadhardeclaration_checkbox.setVisibility(View.VISIBLE);

        income_tv_layout.setVisibility(View.GONE);


        pvtg_ll.setVisibility(View.GONE);
        pvtg_cata_ll.setVisibility(View.GONE);
        reclick.setVisibility(View.GONE);


        //////////////////////////////////////////////////////////////////////
        dialog = new Dialog(job_renewal_form.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        ///////////////////////////////////////////////////////////////

      // AllCertificatesAndHostsTruster.apply();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.


        get_SubCategoryMaster();

        back_ren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), dashboard.class));
                finish();
            }
        });
        btn_check_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatesectionnew()) {
                    return;
                }
                get_data();

            }
        });

        btn_aadhar_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateAadharNumberA()) {
                    return;
                } else if (!validateAadharNumber(adhar_tv.getText().toString().trim())) {


                    showErrorDialog("आपके द्वारा दर्ज आधार नंबर गलत है,कृपया सही आधार नंबर दर्ज करें");
                    Aadhardeclaration_checkbox.setChecked(false);
                    adhar_tv.setText("");
                    return;

                }

                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (job_renewal_form.this);
                View view1 = LayoutInflater.from(job_renewal_form.this).inflate(
                        R.layout.layout_warning_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view1);
                ((TextView) view1.findViewById(R.id.textTitle))
                        .setText("चेतावनी");
                ((TextView) view1.findViewById(R.id.textMessage))
                        .setText("आधार में लिंक मोबाइल नंबर में ओटीपी प्राप्त होगी,क्या आप ओटीपी भेजना चाहते हैं?");
                ((Button) view1.findViewById(R.id.buttonYes))
                        .setText("हाँ");
                ((Button) view1.findViewById(R.id.buttonNo))
                        .setText("नहीं");
                ((ImageView) view1.findViewById(R.id.imageIcon))
                        .setImageResource(R.drawable.warning);
                final AlertDialog alertDialog = builder.create();
                view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();


                  SendAdharOTP(adhar_tv.getText().toString().trim());

               /*  Aadhar_Otp_ll.setVisibility(View.VISIBLE);
                      btn_aadhar_otp.setVisibility(View.GONE);
                      reg_form_ll.setVisibility(View.GONE);
                      input.setVisibility(View.GONE);
                     btn_check_reg.setVisibility(View.GONE);
                     adhar_tv.setEnabled(false);
                      Aadhardeclaration_checkbox.setEnabled(false); */

                    }
                });
                view1.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adhar_tv.setText("");
                        Aadhardeclaration_checkbox.setChecked(false);
                        btn_aadhar_otp.setVisibility(View.GONE);
                        alertDialog.dismiss();

                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();


            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mainbitmap = RotateBitmap_img(mainbitmap, 90);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                mainbitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                byte[] byteofimages = byteArrayOutputStream.toByteArray();
                encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);


                byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                img.setImageBitmap(decodedByte);


            }
        });

        reclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facedetectorCamera();
            }
        });

        Aadhar_Otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateAadharOtpTxt()) {
                    return;
                }

         ValidateAdharOTP(PinViewAadharOTP.getText().toString().trim());

        /*  Aadhar_Otp_ll.setVisibility(View.GONE);
              btn_aadhar_otp.setVisibility(View.GONE);
               Aadhardeclaration_checkbox.setVisibility(View.GONE);
               reg_form_ll.setVisibility(View.VISIBLE); */
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatesectiona()) {
                    return;
                }


                reg_form_llA.setVisibility(View.GONE);
                reg_form_llB.setVisibility(View.VISIBLE);
                get_Highest_Qualification();
                get_disability();
                get_StateMaster();
                get_StatusOf_job_Sp();


            }
        });
        click_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateSpinner()) {
                    return;
                }

                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (job_renewal_form.this);
                View view1 = LayoutInflater.from(job_renewal_form.this).inflate(
                        R.layout.layout_warning_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view1);
                ((TextView) view1.findViewById(R.id.textTitle))
                        .setText("चेतावनी");
                ((TextView) view1.findViewById(R.id.textMessage))
                        .setText("फोटो हेतु स्क्रीन को देखते हुये 4 बार पलके झपकाये तथा आँख खोल कर रुकें|");
                ((Button) view1.findViewById(R.id.buttonYes))
                        .setText("हाँ");
                ((Button) view1.findViewById(R.id.buttonNo))
                        .setText("नहीं");
                ((ImageView) view1.findViewById(R.id.imageIcon))
                        .setImageResource(R.drawable.warning);
                final AlertDialog alertDialog = builder.create();
                view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){


                            facedetectorCamera();

                         /*   Dexter.withContext(getApplicationContext())
                                    .withPermission(Manifest.permission.CAMERA)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                            OpenCamera();
                                        }
                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                            Toast.makeText(getApplicationContext(), "कैमरा उपयोग की सहमती दीजिये..!", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                            permissionToken.continuePermissionRequest();
                                        }
                                    }).check(); */

                        }else {

                            facedetectorCamera();
                            /*

                            Dexter.withContext(getApplicationContext())
                                    .withPermission(Manifest.permission.CAMERA)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                            Dexter.withContext(getApplicationContext())
                                                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                    .withListener(new PermissionListener() {
                                                        @Override
                                                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                                            ////////////////////////////////////////////
                                                            OpenCamera();



                                                        }

                                                        @Override
                                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                                                    job_renewal_form.this);
                                                            alert .setCancelable(false);
                                                            alert.setTitle("Message");
                                                            alert.setMessage("Please Give WRITE_EXTERNAL_STORAGE Permission!" );
                                                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    finish();
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                            alert.show();


                                                        }

                                                        @Override
                                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                                            permissionToken.continuePermissionRequest();
                                                        }
                                                    }).check();



                                        }


                                        @Override
                                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                            Toast.makeText(getApplicationContext(), "please give permission!", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                            permissionToken.continuePermissionRequest();
                                        }
                                    }).check(); */

                        }

                        alertDialog.dismiss();

                    }
                });
                view1.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();


            }


        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getAuthorizationtoken();

               // RenewalCandidateRegistration(encodedimage);
            }
        });

        Highest_Qualification_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                SubjectModelClassClassList.clear();

                HighestQualificationCode = HighestQualificationModelClassList.get(position).getCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                //////////////////////////////////////////////////////////////////////////////


                Get_SelectSubject(HighestQualificationCode);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pvtgsp_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {




                cataCode = SubCategoryMasterModelClassList.get(position).getCategoryCode();

                //  Toast.makeText(registration_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        state_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                DistrictMasterModelClassList.clear();
                StateCode = StateMasterModelClassList.get(position).getStateCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                //////////////////////////////////////////////////////////////////////////////
                if (StateCode.equals("22")) {
                    Dist_ll.setVisibility(View.VISIBLE);
                    urbanrul_ll.setVisibility(View.VISIBLE);
                    ruralUrban_group.clearCheck();


                    get_DistrictMaster(StateCode);

                } else {


                    Dist_ll.setVisibility(View.GONE);
                    urbanrul_ll.setVisibility(View.GONE);
                    urban_ll.setVisibility(View.GONE);
                    rural_ll.setVisibility(View.GONE);
                    DistCode = "";
                    BLockCode = "";
                    panchaytCode = "";
                    villageCode = "";
                    NagriCode = "";
                    wardCode = "";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Dist_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                BlockMasterModelClassList.clear();
                NagriyNikayModelClasssList.clear();
                DistCode = DistrictMasterModelClassList.get(position).getDistrictCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                //////////////////////////////////////////////////////////////////////////////

                get_BlockMaster(DistCode);

                get_Nagriynikay(DistCode);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        block_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                GramPanchaytMasterModelClassList.clear();
                BLockCode = BlockMasterModelClassList.get(position).getBlockCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                //////////////////////////////////////////////////////////////////////////////

                get_GramPanchayatMaster(BLockCode);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        panchayt_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                GramModelClassList.clear();
                panchaytCode = GramPanchaytMasterModelClassList.get(position).getGramPanchayatLocalCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                get_VillageMaster(panchaytCode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nagrinikay_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                WardMasterModelClasssList.clear();

                NagriCode = NagriyNikayModelClasssList.get(position).getNagarPanchayatCode();

                //  Toast.makeText(job_renewal_form.this, HighestQualificationCode, Toast.LENGTH_SHORT).show();


                //////////////////////////////////////////////////////////////////////////////

                get_Ward(NagriCode);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        disability_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                DisabilityTypeCode = disabilityspModelClassList.get(position).getCode();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Subject_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                subjectCode = SubjectModelClassClassList.get(position).getCode();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        StatusOf_job_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                EmployementStatus = StatusOf_jobModelClassList.get(position).getId();
                if (EmployementStatus.equals("1") || EmployementStatus.equals("10")) {
                    income_tv_layout.setVisibility(View.GONE);
                } else {

                    income_tv_layout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gram_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                villageCode = GramModelClassList.get(position).getVillageLocalBodyCode();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ward_ll_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                wardCode = WardMasterModelClasssList.get(position).getWardCode();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean validatesectiona() {

        String val = fathers_layout.getEditText().getText().toString().trim();
        String val2 = datepicker_layout.getEditText().getText().toString().trim();
        String val3 = username_layout.getEditText().getText().toString().trim();
        String val4 = mobile_tv_layout.getEditText().getText().toString().trim();
        String val5 = aadhar_tv_layout.getEditText().getText().toString().trim();

        if (gender_group.getCheckedRadioButtonId() == -1
        ) {
            // no radio buttons are checked

            showErrorDialog("लिंग चयन करें..!");

            Vibrator();
            return false;

        }  else if (married_group.getCheckedRadioButtonId() == -1) {

            showErrorDialog("वैवाहिक स्थिति चयन करें..!");

            Vibrator();
            return false;

        } else if (caste_group.getCheckedRadioButtonId() == -1) {

            showErrorDialog("वर्ग चयन करें..!");

            Vibrator();
            return false;

        }else if(Category.equals("ST")&&pvtg_group.getCheckedRadioButtonId() == -1){
            showErrorDialog("PVTG वर्ग में हाँ/ नहीं चुने ");
            Vibrator();
            return false;
        } else if (pvtgsp_sp.getSelectedItem().toString().equals("चयन करें") &&_specialCategory.equals("Y") ) {
            // no radio buttons are checked
            showErrorDialog("PVTG के प्रकार का चयन करें..!");
            Vibrator();
            return false;

        }else if (oversease_group.getCheckedRadioButtonId() == -1) {
            Vibrator();
            showErrorDialog("क्या प्रवासी नौकरी में रूचि हेतु हाँ/नहीं का चयन करें..!");
            return false;
        }
        if (val.equals("") || val.length() < 2) {
            Vibrator();
            showErrorDialog("कृपया पिता का वैध नाम दर्ज करें ");
            fathersname_tv.setText("");
            return false;
        } else if (val2.equals("")) {
            Vibrator();
            showErrorDialog("कृपया जन्म दिनांक दर्ज करें");
            DOB_picker_actions.setText("");
            return false;
        } else if (val3.equals("")) {
            Vibrator();
            showErrorDialog("कृपया उपयोगकर्ता का नाम दर्ज करें");

            return false;
        } else if (val4.equals("")) {
            Vibrator();
            showErrorDialog("कृपया मोबाइल दर्ज करें");
            return false;
        } else if (val5.equals("")) {
            Vibrator();
            showErrorDialog("कृपया आधार नंबर दर्ज करें");
            return false;
        } else {
            return true;

        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.aadhar_radio:
                if (checked)

                    _input_type = "A";
                break;

            case R.id.reg_radio:
                if (checked)
                    _input_type = "R";

                break;

            case R.id.mobile_radio:
                if (checked)
                    _input_type = "M";

                break;

            case R.id.disability_NO:
                if (checked)
                    disability_ll.setVisibility(View.GONE);
                Disability = "N";
                DisabilityTypeCode = "";

                break;

            case R.id.disability_Yes:
                if (checked)
                    disability_ll.setVisibility(View.VISIBLE);
                Disability = "Y";

                break;
            case R.id.urban:
                if (checked)

                    rurarban = "U";
                urban_ll.setVisibility(View.VISIBLE);
                rural_ll.setVisibility(View.GONE);

                break;
            case R.id.rural:
                if (checked)
                    rurarban = "R";
                urban_ll.setVisibility(View.GONE);
                rural_ll.setVisibility(View.VISIBLE);

                break;


            case R.id.gender_M:
                if (checked)
                    Gender = "M";
                break;
            case R.id.gender_F:
                if (checked)
                    Gender = "F";
                break;
            case R.id.gender_T:
                if (checked)
                    Gender = "T";
                break;

            case R.id.married:
                if (checked)
                    MaritalStatus = "M";
                break;
            case R.id.unmarried:
                if (checked)
                    MaritalStatus = "U";
                break;

            case R.id.caste_Gen:
                if (checked)
                    Category = "GN";
                pvtg_ll.setVisibility(View.GONE);
                break;
            case R.id.caste_OBC:
                if (checked)
                    Category = "OBC";
                pvtg_ll.setVisibility(View.GONE);
                break;

            case R.id.caste_SC:
                if (checked)
                    Category = "SC";
                pvtg_ll.setVisibility(View.GONE);
                break;

            case R.id.caste_ST:
                if (checked)
                    Category = "ST";
                if(caste_ST.isChecked()){
                    pvtg_ll.setVisibility(View.VISIBLE);
                }else{
                    pvtg_ll.setVisibility(View.GONE);

                }
                break;
            case R.id.oversease_Yes:
                if (checked)
                    Oversease = "Y";
                break;

            case R.id.oversease_NO:
                if (checked)
                    Oversease = "N";
                break;


            case R.id.pvtg_yes:
                if (checked)
                    _specialCategory="Y";
                pvtg_cata_ll.setVisibility(View.VISIBLE);
                break;

            case R.id.pvtg_no:
                if (checked)
                    _specialCategory="N";
                pvtg_cata_ll.setVisibility(View.GONE);
                break;
        }
    }

    private boolean validateAadharOtpTxt() {

        String val = PinViewAadharOTP.getText().toString().trim();
        if (val.equals("") || val.length() != 6) {
            Vibrator();

            showErrorDialog("कृपया 6 अंको का ओटीपी दर्ज करें |");
            return false;
        } else {
            PinViewAadharOTP.setError(null);


            return true;

        }
    }

    private boolean validatesectionnew() {
        String val = input_tv.getText().toString().trim();
        if (input_group.getCheckedRadioButtonId() == -1
        ) {
            showErrorDialog("आधार नंबर/पंजीयन क्रमांक/मोबाइल नंबर का चयन करें");

            Vibrator();
            return false;

        } else if (val.equals("")) {
            showErrorDialog("कृपया इनपुट दर्ज करें |");
            return false;

        } else if (!val.equals("") && _input_type.equals("M") && val.length() != 10) {
            showErrorDialog("कृपया 10 अंको का मोबाइल नंबर दर्ज करें");
            return false;

        } else if (!val.equals("") && _input_type.equals("A") && val.length() != 12) {
            showErrorDialog("कृपया 12 अंको का आधार नंबर दर्ज करें");
            return false;

        } else if (!val.equals("") && _input_type.equals("A") && !validateAadharNumber(input_tv.getText().toString().trim())) {
            showErrorDialog("आपके द्वारा दर्ज आधार नंबर गलत है,कृपया सही आधार नंबर दर्ज करें");
            input_tv.setText("");
            return false;

        } else {
            return true;
        }
    }

    public static boolean validateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if (isValidAadhar) {
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }

    public void Vibrator() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(60);

    }

    private void showErrorDialog(String msg) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (job_renewal_form.this);
        View view = LayoutInflater.from(job_renewal_form.this).inflate(
                R.layout.layout_error_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setCancelable(false);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle))
                .setText("असफल");

        ((TextView) view.findViewById(R.id.textMessage))
                .setText(msg);
        ((Button) view.findViewById(R.id.buttonAction))
                .setText("बंद करें");
        ((ImageView) view.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.file);

        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private boolean validateAadharNumberA() {

        String val = aadhar_tv_layout.getEditText().getText().toString().trim();
        if (val.equals("") || val.length() != 12) {
            Vibrator();

            showErrorDialog("कृपया 12 अंको का आधार नंबर दर्ज करें |");
            Aadhardeclaration_checkbox.setChecked(false);

            adhar_tv.setText("");
            return false;
        } else {
            aadhar_tv_layout.setError(null);
            return true;

        }
    }

    public void Check_checkBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case R.id.declaration_checkbox:
                if (checked) {

                    click_photo_btn.setVisibility(View.VISIBLE);
                    break;
                } else {

                    click_photo_btn.setVisibility(View.GONE);
                }

            case R.id.Aadhardeclaration_checkbox:
                if (checked) {

                    if (!validateAadharNumberA()) {
                        return;
                    } else if (!validateAadharNumber(adhar_tv.getText().toString().trim())) {


                        showErrorDialog("आपके द्वारा दर्ज आधार नंबर गलत है,कृपया सही आधार नंबर दर्ज करें");
                        adhar_tv.setText("");
                        Aadhardeclaration_checkbox.setChecked(false);
                        return;

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(job_renewal_form.this);
                    View layout_dialog = LayoutInflater.from(job_renewal_form.this).inflate(R.layout.aadhar_dailog, null);
                    builder.setView(layout_dialog);
                    AppCompatButton retrybtn = layout_dialog.findViewById(R.id.retry_btn);
                    /////////show dialog//////////
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCancelable(false);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    /// go to offline page//////
                    retrybtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            btn_aadhar_otp.setVisibility(View.VISIBLE);

                        }
                    });

                    //btn_aadhar_otp.setVisibility(View.VISIBLE);
                    break;
                } else {

                    btn_aadhar_otp.setVisibility(View.GONE);
                }

        }
    }

    public void get_data() {
        dialog.show();

        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("candidateData", input_tv.getText().toString().trim());
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GetCandidateDetailsForRenewal, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = null;
                        try {
                            result = response.getString("statusCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result.equals("200")) {
                            dialog.dismiss();
                            btn_check_reg.setVisibility(View.GONE);

                            try {
                                input.setVisibility(View.GONE);
                                AadharAuth = response.getString("isAdhar");
                                if (AadharAuth.equals("Y")) {

                                    Aadhardeclaration_checkbox.setVisibility(View.GONE);
                                    btn_aadhar_otp.setVisibility(View.GONE);
                                    Aadhar_Otp_ll.setVisibility(View.GONE);
                                    reg_form_llA.setVisibility(View.VISIBLE);
                                    adhar_tv.setVisibility(View.VISIBLE);

                                } else if (AadharAuth.equals("N")) {

                                    if (_input_type.equals("A")) {
                                        input_tv.setEnabled(false);
                                        adhll.setVisibility(View.VISIBLE);

                                    } else if (_input_type.equals("R")) {
                                        input_tv.setEnabled(false);
                                        adhll.setVisibility(View.VISIBLE);

                                    } else if (_input_type.equals("M")) {
                                        input_tv.setEnabled(false);
                                        adhll.setVisibility(View.VISIBLE);
                                    }


                                }
                                JSONObject mainrResult = response.getJSONObject("data");
                                _name = mainrResult.getString("name").trim();
                                String _mobile = mainrResult.getString("mobNo").trim();
                                String _gender = mainrResult.getString("gender").trim();
                                String _maritalStatus = mainrResult.getString("maritalStatus").trim();
                                String _fathername = mainrResult.getString("fathername").trim();


                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                String inputDateStr = mainrResult.getString("dob").trim();
                                Date date = inputFormat.parse(inputDateStr);
                                String CDOB = outputFormat.format(date);

                                String _category = mainrResult.getString("category").trim();
                                String _disability = mainrResult.getString("disability").trim();
                                _oldRegno = mainrResult.getString("oldRegno").trim();
                                _exchangeId = mainrResult.getString("exchangeId").trim();
                                String _address = mainrResult.getString("address").trim();
                                String _adharRefNo = mainrResult.getString("adharRefNo").trim();
                                _oldName = mainrResult.getString("oldName").trim();
                                _regdate = mainrResult.getString("regdate").trim();

                                username_tv.setText(_name);

                                mobile_tv.setText(mobileno);
                                if (_gender.equals("M")) {
                                    gender_M.setChecked(true);

                                } else if (_gender.equals("F")) {
                                    gender_F.setChecked(true);

                                } else if (_gender.equals("T")) {
                                    gender_T.setChecked(true);

                                } else {
                                    gender_M.setChecked(true);
                                }

                                if (_maritalStatus.equals("U")) {
                                    unmarried.setChecked(true);
                                } else if (_maritalStatus.equals("M")) {
                                    married.setChecked(true);
                                } else {
                                    unmarried.setChecked(true);
                                }
                                fathersname_tv.setText(_fathername);
                                DOB_picker_actions.setText(CDOB);

                                if (_category.equals("GN")) {
                                    caste_Gen.setChecked(true);

                                } else if (_category.equals("GN")) {
                                    caste_OBC.setChecked(true);
                                } else if (_category.equals("SC")) {
                                    caste_SC.setChecked(true);
                                } else if (_category.equals("ST")) {
                                    caste_ST.setChecked(true);
                                }
                                if (_disability.equals("Y")) {
                                    disability_Yes.setChecked(true);
                                    disability_ll.setVisibility(View.VISIBLE);

                                } else if (_disability.equals("N")) {
                                    disability_NO.setChecked(true);
                                    disability_ll.setVisibility(View.GONE);
                                }
                                Address_tv.setText(_address);

                                if (AadharAuth.equals("Y")) {
                                    adhar_tv.setText(_adharRefNo);
                                }
                                if(caste_ST.isChecked()){
                                    pvtg_ll.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
                            dialog.dismiss();
                            input_tv.setText("");
                            adhll.setVisibility(View.GONE);
                            Aadhar_Otp_ll.setVisibility(View.GONE);
                            reg_form_ll.setVisibility(View.GONE);
                            String MSG = null;
                            try {
                                MSG = response.getString("msg");
                                ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(MSG);
                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("बंद करें");
                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.file);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();
                adhll.setVisibility(View.GONE);
                Aadhar_Otp_ll.setVisibility(View.GONE);
                reg_form_ll.setVisibility(View.GONE);


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        job_renewal_form.this);
                alert.setTitle("सन्देश !");
                alert.setCancelable(false);
                alert.setMessage(error.toString());
                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    public void SendAdharOTP(String AadharNomber) {
        dialog.show();


        SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
        String userName = sp.getString("userName", "");//"No name defined" is the default value.
        String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("adharNo", AadharNomber);
            jsonObject.put("mobilNo", mobileno);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.SendAdharOTP, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("statusCode");

                            if (result.equals("204")) {
                                dialog.dismiss();
                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (job_renewal_form.this);
                                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                        R.layout.layout_error_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                                );
                                builder.setCancelable(false);
                                builder.setView(view);
                                ((TextView) view.findViewById(R.id.textTitle))
                                        .setText("असफल");

                                ((TextView) view.findViewById(R.id.textMessage))
                                        .setText(MSG);
                                ((Button) view.findViewById(R.id.buttonAction))
                                        .setText("बंद करें");
                                ((ImageView) view.findViewById(R.id.imageIcon))
                                        .setImageResource(R.drawable.file);

                                final AlertDialog alertDialog = builder.create();
                                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adhar_tv.setText("");
                                        Aadhardeclaration_checkbox.setChecked(false);
                                        Aadhardeclaration_checkbox.setEnabled(true);
                                        btn_aadhar_otp.setVisibility(View.GONE);
                                        alertDialog.dismiss();

                                    }
                                });
                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }
                                alertDialog.show();


                            } else if (result.equals("200")) {
                                dialog.dismiss();

                                JSONObject output = response.getJSONObject("data");
                                token = output.getString("token");

                                SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
                                SharedPreferences.Editor editoremp = sp.edit();
                                editoremp.putString("token", token);
                                editoremp.commit();
                                editoremp.apply();

                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (job_renewal_form.this);
                                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                        R.layout.layout_success_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                                );
                                builder.setCancelable(false);
                                builder.setView(view);
                                ((TextView) view.findViewById(R.id.textTitle))
                                        .setText("सफल");


                                ((TextView) view.findViewById(R.id.textMessage))
                                        .setText(MSG);

                                ((Button) view.findViewById(R.id.buttonAction))
                                        .setText("ओके");

                                ((ImageView) view.findViewById(R.id.imageIcon))
                                        .setImageResource(R.drawable.correct);

                                final AlertDialog alertDialog = builder.create();
                                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        adhar_tv.setEnabled(false);
                                        Aadhardeclaration_checkbox.setEnabled(false);
                                        Aadhardeclaration_checkbox.setVisibility(View.GONE);
                                        btn_aadhar_otp.setVisibility(View.GONE);
                                        Aadhar_Otp_ll.setVisibility(View.VISIBLE);
                                        input.setVisibility(View.GONE);
                                        btn_check_reg.setVisibility(View.GONE);


                                        alertDialog.dismiss();

                                    }
                                });
                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }
                                alertDialog.show();


                            } else {

                                dialog.dismiss();

                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (job_renewal_form.this);
                                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                        R.layout.layout_error_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                                );
                                builder.setCancelable(false);
                                builder.setView(view);
                                ((TextView) view.findViewById(R.id.textTitle))
                                        .setText("असफल");

                                ((TextView) view.findViewById(R.id.textMessage))
                                        .setText(MSG);
                                ((Button) view.findViewById(R.id.buttonAction))
                                        .setText("बंद करें");
                                ((ImageView) view.findViewById(R.id.imageIcon))
                                        .setImageResource(R.drawable.file);

                                final AlertDialog alertDialog = builder.create();
                                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adhar_tv.setText("");
                                        Aadhardeclaration_checkbox.setChecked(false);
                                        Aadhardeclaration_checkbox.setEnabled(true);
                                        btn_aadhar_otp.setVisibility(View.GONE);
                                        alertDialog.dismiss();

                                    }
                                });
                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }
                                alertDialog.show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            dialog.dismiss();

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(e.toString());
                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("बंद करें");
                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.file);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    adhar_tv.setText("");
                                    Aadhardeclaration_checkbox.setChecked(false);
                                    Aadhardeclaration_checkbox.setEnabled(true);
                                    btn_aadhar_otp.setVisibility(View.GONE);
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (job_renewal_form.this);
                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                        R.layout.layout_error_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle))
                        .setText("असफल");

                ((TextView) view.findViewById(R.id.textMessage))
                        .setText(error.toString());
                ((Button) view.findViewById(R.id.buttonAction))
                        .setText("बंद करें");
                ((ImageView) view.findViewById(R.id.imageIcon))
                        .setImageResource(R.drawable.file);

                final AlertDialog alertDialog = builder.create();
                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adhar_tv.setText("");
                        adhar_tv.setText("");
                        Aadhardeclaration_checkbox.setChecked(false);
                        Aadhardeclaration_checkbox.setEnabled(true);
                        btn_aadhar_otp.setVisibility(View.GONE);
                        alertDialog.dismiss();

                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();

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

    public void ValidateAdharOTP(String otp) {
        dialog.show();

        Aadhardeclaration_checkbox.setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("adharNo", adhar_tv.getText().toString().trim());
            jsonObject.put("otp", otp);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.ValidateAdharOTP, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            String result = response.getString("statusCode");


                            if (result.equals("200")) {
                                dialog.dismiss();

                                Aadhar_Otp_ll.setVisibility(View.GONE);
                                btn_aadhar_otp.setVisibility(View.GONE);
                                input.setVisibility(View.GONE);
                                btn_check_reg.setVisibility(View.GONE);
                                reg_form_ll.setVisibility(View.VISIBLE);



                              /*  String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        job_renewal_form.this);
                                alert.setTitle("सन्देश !");
                                alert.setCancelable(false);
                                alert.setMessage(MSG);
                                alert.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                           Aadhar_Otp_ll.setVisibility(View.GONE);
                                           btn_aadhar_otp.setVisibility(View.GONE);
                                           reg_form_ll.setVisibility(View.VISIBLE);
                                        dialog.dismiss();
                                    }
                                });
                                alert.show(); */


                            } else {

                                dialog.dismiss();

                                String MSG = null;
                                try {
                                    MSG = response.getString("msg");
                                    ;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder
                                                (job_renewal_form.this);
                                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                        R.layout.layout_error_dialog,
                                        findViewById(R.id.layoutDialogContainer)
                                );
                                builder.setCancelable(false);
                                builder.setView(view);
                                ((TextView) view.findViewById(R.id.textTitle))
                                        .setText("असफल");

                                ((TextView) view.findViewById(R.id.textMessage))
                                        .setText(MSG);
                                ((Button) view.findViewById(R.id.buttonAction))
                                        .setText("बंद करें");
                                ((ImageView) view.findViewById(R.id.imageIcon))
                                        .setImageResource(R.drawable.file);

                                final AlertDialog alertDialog = builder.create();
                                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adhar_tv.setEnabled(true);
                                        Aadhar_Otp_ll.setVisibility(View.GONE);
                                        reg_form_ll.setVisibility(View.GONE);
                                        //   btn_aadhar_otp.setVisibility(View.VISIBLE);
                                        Aadhardeclaration_checkbox.setVisibility(View.VISIBLE);
                                        Aadhardeclaration_checkbox.setChecked(false);
                                        Aadhardeclaration_checkbox.setEnabled(true);
                                        adhll.setVisibility(View.VISIBLE);
                                        adhar_tv.setText("");
                                        PinViewAadharOTP.setText("");
                                        alertDialog.dismiss();

                                    }
                                });
                                if (alertDialog.getWindow() != null) {
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }
                                alertDialog.show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            dialog.dismiss();

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(e.toString());
                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("बंद करें");
                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.file);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    adhar_tv.setEnabled(true);
                                    Aadhar_Otp_ll.setVisibility(View.GONE);
                                    reg_form_ll.setVisibility(View.GONE);
                                    //   btn_aadhar_otp.setVisibility(View.VISIBLE);
                                    Aadhardeclaration_checkbox.setVisibility(View.VISIBLE);
                                    Aadhardeclaration_checkbox.setChecked(false);
                                    Aadhardeclaration_checkbox.setEnabled(true);
                                    adhll.setVisibility(View.VISIBLE);
                                    adhar_tv.setText("");
                                    PinViewAadharOTP.setText("");
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();

                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (job_renewal_form.this);
                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                        R.layout.layout_error_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle))
                        .setText("असफल");

                ((TextView) view.findViewById(R.id.textMessage))
                        .setText(error.toString());
                ((Button) view.findViewById(R.id.buttonAction))
                        .setText("बंद करें");
                ((ImageView) view.findViewById(R.id.imageIcon))
                        .setImageResource(R.drawable.file);

                final AlertDialog alertDialog = builder.create();
                view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adhar_tv.setEnabled(true);
                        Aadhar_Otp_ll.setVisibility(View.GONE);
                        reg_form_ll.setVisibility(View.GONE);
                        //   btn_aadhar_otp.setVisibility(View.VISIBLE);
                        Aadhardeclaration_checkbox.setVisibility(View.VISIBLE);
                        Aadhardeclaration_checkbox.setChecked(false);
                        Aadhardeclaration_checkbox.setEnabled(true);
                        adhll.setVisibility(View.VISIBLE);
                        adhar_tv.setText("");
                        PinViewAadharOTP.setText("");
                        alertDialog.dismiss();

                    }
                });
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();

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

    private void OpenCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            picUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT);
            captureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            captureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            //  startActivityForResult(captureIntent,CAMERA_PIC_REQUEST);
            someActivityLauncher.launch(captureIntent);
        } else {

            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT);
            captureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            captureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
            picUri = Uri.fromFile(file);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            // startActivityForResult(captureIntent,CAMERA_PIC_REQUEST);
            someActivityLauncher.launch(captureIntent);
        }






    }

    ActivityResultLauncher<Intent> someActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        String ImagePath = RealPathUtil.getRealPath(job_renewal_form.this, picUri);
                        File image = new File(ImagePath);
                         bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){

                            mainbitmap = RotateBitmap(bitmap, 270);
                            mainbitmap = flip(mainbitmap);


                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            mainbitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                            byte[] byteofimages = byteArrayOutputStream.toByteArray();
                            encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);

                            image_ll.setVisibility(View.VISIBLE);


                            byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            img.setImageBitmap(decodedByte);

                            click_photo_btn.setVisibility(View.GONE);
                            declaration_checkbox.setVisibility(View.GONE);
                            submit_btn.setVisibility(View.VISIBLE);
                        }else{


                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
                            byte[] byteofimages = byteArrayOutputStream.toByteArray();
                            encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);

                            image_ll.setVisibility(View.VISIBLE);


                            byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            img.setImageBitmap(decodedByte);

                            click_photo_btn.setVisibility(View.GONE);
                            declaration_checkbox.setVisibility(View.GONE);
                            submit_btn.setVisibility(View.VISIBLE);

                        }

                    }

                }


            });





    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    Bitmap flip(Bitmap d) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = d;
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    public void get_Highest_Qualification() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.get_Highest_Qualification, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                HighestQualificationModelClass
                                        highestQualificationModelClass = new HighestQualificationModelClass(
                                        output.getString("code"),/////web_service/////////////////
                                        output.getString("qualification")
                                );

                                HighestQualificationModelClassList.add(highestQualificationModelClass);
                            }

                            String[] HighestQualificatioNameArray = new String[HighestQualificationModelClassList.size()];
                            for (int i = 0; i < HighestQualificationModelClassList.size(); i++) {
                                HighestQualificatioNameArray[i] = HighestQualificationModelClassList.get(i).getQualification();////////from_model_class
                            }

                     /*   ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                android.R.layout.simple_spinner_item, HighestQualificatioNameArray);
                        Highest_Qualification_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                            Highest_Qualification_sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, HighestQualificatioNameArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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


    public void Get_SelectSubject(String highestQualificationCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("qualificationCode", highestQualificationCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.SelectSubject, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            String result = response.getString("statusCode");


                            if (result.equals("404")) {

                                subjectCode = "";
                                Subject_ll.setVisibility(View.GONE);
                                dialog.dismiss();

                            } else if (result.equals("200")) {


                                Subject_ll.setVisibility(View.VISIBLE);

                                JSONArray result_data = response.getJSONArray("listData");
                                for (int i = 0; i < result_data.length(); i++) {
                                    JSONObject output = result_data.getJSONObject(i);
                                    SubjectModelClass
                                            subjectModelClass = new SubjectModelClass(
                                            output.getString("code"),/////web_service/////////////////
                                            output.getString("subject")
                                    );

                                    SubjectModelClassClassList.add(subjectModelClass);
                                }

                                String[] SubjectNameArray = new String[SubjectModelClassClassList.size()];
                                for (int i = 0; i < SubjectModelClassClassList.size(); i++) {
                                    SubjectNameArray[i] = SubjectModelClassClassList.get(i).getSubject();////////from_model_class
                                }

                         /*   ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, SubjectNameArray);
                            Subject_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                                Subject_sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, SubjectNameArray, defaultTextForSpinner));
                                dialog.dismiss();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    public void get_StateMaster() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.StateMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                StateMasterModelClass
                                        stateMasterModelClass = new StateMasterModelClass(
                                        output.getString("stateCode"),/////web_service/////////////////
                                        output.getString("stateName")
                                );

                                StateMasterModelClassList.add(stateMasterModelClass);
                            }

                            String[] StateMasterNameArray = new String[StateMasterModelClassList.size()];
                            for (int i = 0; i < StateMasterModelClassList.size(); i++) {
                                StateMasterNameArray[i] = StateMasterModelClassList.get(i).getStateName();////////from_model_class
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, StateMasterNameArray);
                            state_Sp.setAdapter(arrayAdapter);
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    public void get_DistrictMaster(String stateCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("stateCode", stateCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.DistrictMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                DistrictMasterModelClass
                                        districtMasterModelClass = new DistrictMasterModelClass(
                                        output.getString("districtCode"),/////web_service/////////////////
                                        output.getString("districtName")
                                );

                                DistrictMasterModelClassList.add(districtMasterModelClass);
                            }

                            String[] DistrictMasterArray = new String[DistrictMasterModelClassList.size()];
                            for (int i = 0; i < DistrictMasterModelClassList.size(); i++) {
                                DistrictMasterArray[i] = DistrictMasterModelClassList.get(i).getDistrictName();////////from_model_class
                            }

                    /*        ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, DistrictMasterArray);
                         Dist_Sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */

                            Dist_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, DistrictMasterArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    /////////////////block/////////////////////////
    public void get_BlockMaster(String distCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("districtId", distCode);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.BlockMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                BlockMasterModelClass
                                        blockMasterModelClass = new BlockMasterModelClass(
                                        output.getString("blockCode"),/////web_service/////////////////
                                        output.getString("blockName")
                                );

                                BlockMasterModelClassList.add(blockMasterModelClass);
                            }

                            String[] BlockMasterArray = new String[BlockMasterModelClassList.size()];
                            for (int i = 0; i < BlockMasterModelClassList.size(); i++) {
                                BlockMasterArray[i] = BlockMasterModelClassList.get(i).getBlockName();////////from_model_class
                            }

                      /*     ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, BlockMasterArray);
                            block_Sp.setAdapter(arrayAdapter);

                            dialog.dismiss(); */

                            block_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, BlockMasterArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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
/////////////////////////Nagriynikay/////////////////////

    public void get_Nagriynikay(String distCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("districtCode", distCode);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.Nagriynikay, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                NagriyNikayModelClass
                                        nagriyNikayModelClass = new NagriyNikayModelClass(
                                        output.getString("nagarPanchayatCode"),/////web_service/////////////////
                                        output.getString("nagarPanchayatName")
                                );

                                NagriyNikayModelClasssList.add(nagriyNikayModelClass);
                            }

                            String[] nagarMasterArray = new String[NagriyNikayModelClasssList.size()];
                            for (int i = 0; i < NagriyNikayModelClasssList.size(); i++) {
                                nagarMasterArray[i] = NagriyNikayModelClasssList.get(i).getNagarPanchayatName();////////from_model_class
                            }

                     /*      ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, nagarMasterArray);
                            nagrinikay_Sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */

                            nagrinikay_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, nagarMasterArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    ///////////////////////WardMaster/////////////////////
    public void get_Ward(String nagriCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("nagarPanchayatCode", nagriCode);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.WardMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                WardMasterModelClass
                                        wardMasterModelClass = new WardMasterModelClass(
                                        output.getString("wardCode"),/////web_service/////////////////
                                        output.getString("wardName"),
                                        output.getString("wardNumber")
                                );

                                WardMasterModelClasssList.add(wardMasterModelClass);
                            }

                            String[] WardMasterArray = new String[WardMasterModelClasssList.size()];
                            for (int i = 0; i < WardMasterModelClasssList.size(); i++) {
                                WardMasterArray[i] = WardMasterModelClasssList.get(i).getWardName();////////from_model_class
                            }

                     /*     ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, WardMasterArray);
                            ward_ll_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */

                            ward_ll_sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, WardMasterArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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
    ////////////////////////////////GrampanchaytMaster////////////////

    public void get_GramPanchayatMaster(String BLockCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("blockId", BLockCode);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GramPanchayatMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                GramPanchaytMasterModelClass
                                        gramPanchaytMasterModelClass = new GramPanchaytMasterModelClass(
                                        output.getString("gramPanchayatName"),
                                        output.getString("gramPanchayatLocalCode")
                                );

                                GramPanchaytMasterModelClassList.add(gramPanchaytMasterModelClass);
                            }

                            String[] GramPanchaytArray = new String[GramPanchaytMasterModelClassList.size()];
                            for (int i = 0; i < GramPanchaytMasterModelClassList.size(); i++) {
                                GramPanchaytArray[i] = GramPanchaytMasterModelClassList.get(i).getGramPanchayatName();////////from_model_class
                            }

                     /*    ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, GramPanchaytArray);
                            panchayt_Sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */

                            panchayt_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, GramPanchaytArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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


    public void get_VillageMaster(String panchaytCode) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        // Toast.makeText(this, panchaytCode, Toast.LENGTH_SHORT).show();

        try {
            jsonObject.put("gramPanchayatCode", panchaytCode);
            jsonObject.put("key", "ERojgargdvd");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.VillageMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                GramModelClass
                                        gramModelClass = new GramModelClass(
                                        /////web_service/////////////////
                                        output.getString("villageName"),
                                        output.getString("villageLocalBodyCode")
                                );

                                GramModelClassList.add(gramModelClass);
                            }

                            String[] GramArray = new String[GramModelClassList.size()];
                            for (int i = 0; i < GramModelClassList.size(); i++) {
                                GramArray[i] = GramModelClassList.get(i).getVillageName();////////from_model_class
                            }

                     /*  ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                    android.R.layout.simple_spinner_item, GramArray);
                            gram_Sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                            gram_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, GramArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    public void get_StatusOf_job_Sp() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.EmployeementStatus, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                StatusOfjobModelClass
                                        statusOfjobModelClass = new StatusOfjobModelClass(
                                        output.getString("id"),/////web_service/////////////////
                                        output.getString("status")
                                );

                                StatusOf_jobModelClassList.add(statusOfjobModelClass);
                            }

                            String[] StatusOf_jobNameArray = new String[StatusOf_jobModelClassList.size()];
                            for (int i = 0; i < StatusOf_jobModelClassList.size(); i++) {
                                StatusOf_jobNameArray[i] = StatusOf_jobModelClassList.get(i).getStatus();////////from_model_class
                            }

                     /*   ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                android.R.layout.simple_spinner_item, HighestQualificatioNameArray);
                        Highest_Qualification_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                            StatusOf_job_Sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, StatusOf_jobNameArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    public void get_disability() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.HandicapType, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                DisabilityspModelClass
                                        disabilityspModelClass = new DisabilityspModelClass(
                                        output.getString("code"),/////web_service/////////////////
                                        output.getString("handicapType")
                                );

                                disabilityspModelClassList.add(disabilityspModelClass);
                            }

                            String[] disabilityNameArray = new String[disabilityspModelClassList.size()];
                            for (int i = 0; i < disabilityspModelClassList.size(); i++) {
                                disabilityNameArray[i] = disabilityspModelClassList.get(i).getHandicapType();////////from_model_class
                            }

                     /*   ArrayAdapter arrayAdapter = new ArrayAdapter(job_renewal_form.this,
                                android.R.layout.simple_spinner_item, HighestQualificatioNameArray);
                        Highest_Qualification_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                            disability_sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, disabilityNameArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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

    private boolean validateSpinner() {

        if (disability_group.getCheckedRadioButtonId() == -1
        ) {
            // no radio buttons are checked
            Vibrator();
            showErrorDialog("कृपया दिव्यांग हाँ/नहीं का चयन करें..!");
            return false;

        } else if (Disability.equals("Y") && disability_sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया दिव्यांगता के प्रकार का चयन करें..!");
            Vibrator();
            return false;

        } else if (Highest_Qualification_sp.getSelectedItem().toString().equals("चयन करें")) {
            // no radio buttons are checked
            showErrorDialog("कृपया उच्चतम योग्यता का चयन करें..!");
            Vibrator();
            return false;

        } else if (subjectCode != "" && Subject_sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया विषय चयन करें..!");
            Vibrator();
            return false;

        } else if (StatusOf_job_Sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया रोज़गार की स्थिति चयन करें..!");
            Vibrator();
            return false;

        } else if (( EmployementStatus.equals("2") || EmployementStatus.equals("3") ||EmployementStatus.equals("4")
                ||EmployementStatus.equals("5") ||EmployementStatus.equals("6") ||EmployementStatus.equals("7")
                ||EmployementStatus.equals("8")
                ||  EmployementStatus.equals("9")) && income_tv.getText().toString().trim().equals("")  ) {

            showErrorDialog("कृपया मासिक आय दर्ज करें..!");
            Vibrator();
            return false;

        } else if (StateCode.equals("22") && Dist_Sp.getSelectedItem().toString().equals("चयन करें")) {


            showErrorDialog("कृपया जिले का चयन करें..!");
            Vibrator();
            return false;

        } else if (StateCode.equals("22") && ruralUrban_group.getCheckedRadioButtonId() == -1) {

            showErrorDialog("कृपया ग्रामीण/शहरी का चयन करें..!");

            Vibrator();
            return false;
        } else if (StateCode.equals("22") && rurarban.equals("R") && block_Sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया ब्लाक चयन करें..!");
            Vibrator();
            return false;
        } else if (StateCode.equals("22") && rurarban.equals("R") && panchayt_Sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया ग्राम पंचायत चयन करें..!");

            Vibrator();
            return false;
        } else if (StateCode.equals("22") && rurarban.equals("R") && gram_Sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया ग्राम चयन करें..!");

            Vibrator();
            return false;
        } else if (StateCode.equals("22") && rurarban.equals("U") && nagrinikay_Sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया नगरीय निकाय चयन करें..!");

            Vibrator();
            return false;
        } else if (StateCode.equals("22") && rurarban.equals("U") && ward_ll_sp.getSelectedItem().toString().equals("चयन करें")) {

            showErrorDialog("कृपया वार्ड चयन करें..!");

            Vibrator();
            return false;
        } else if (Address_tv.getText().toString().trim().equals("")) {

            showErrorDialog("पूर्ण पता दर्ज करें..!");

            Vibrator();
            return false;
        } else if (pincode_tv.getText().toString().trim().equals("")) {

            showErrorDialog("पिन कोड दर्ज करें..!");

            Vibrator();
            return false;
        } else if (pincode_tv.getText().toString().trim().length() != 6) {
            showErrorDialog("6 अंको का पिन कोड दर्ज करें..!");
            pincode_tv.setText("");
            Vibrator();
            return false;
        } else {
            // no radio buttons are checked

            return true;

        }


    }

    public void RenewalCandidateRegistration(String image,String accesstoken ) {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        int _income;

        if (rurarban.equals("U") && StateCode.equals("22")) {
            BLockCode = "";
            panchaytCode = "";
            villageCode = "";

        } else if (rurarban.equals("R") && StateCode.equals("22")) {
            NagriCode = "";
            wardCode = "";
        } else {
            BLockCode = "";
            panchaytCode = "";
            villageCode = "";
            NagriCode = "";
            wardCode = "";
            rurarban = "";
        }
        if (EmployementStatus.equals("1") || EmployementStatus.equals("10")) {

            _income = 0;
        } else {

            String income = income_tv.getText().toString().trim();
            _income = Integer.parseInt(income);
        }
        if(gender_M.isChecked()){
            Gender="M";
        } else if (gender_F.isChecked()) {
            Gender="F";
        }else if (gender_T.isChecked()){
            Gender="T";
        }
        if(married.isChecked()){
            MaritalStatus="M";
        } else if (unmarried.isChecked()) {
            MaritalStatus="U";
        }
        if(caste_Gen.isChecked()){
            Category="GN";
        } else if (caste_OBC.isChecked()) {
            Category="OBC";
        }else if (caste_ST.isChecked()) {
            Category="ST";
        }else if (caste_SC.isChecked()) {
            Category="SC";
        }
        if(disability_Yes.isChecked()){
            Disability="Y";
        } else if (disability_NO.isChecked()) {
            Disability="N";
        }
        if(_specialCategory.equals("N")){
            cataCode="";
        }

        try {
            jsonObject.put("name", username_tv.getText().toString().trim());
            jsonObject.put("mobNo", mobile_tv.getText().toString().trim());
            jsonObject.put("adharRefNo", adhar_tv.getText().toString().trim());
            jsonObject.put("newRegno", "");
            jsonObject.put("profileImagePath", "");
            jsonObject.put("profileImage", image);
            jsonObject.put("gender", Gender.trim());
            jsonObject.put("maritalStatus", MaritalStatus.trim());
            jsonObject.put("fathername", fathersname_tv.getText().toString().trim());
            jsonObject.put("dob", DOB_picker_actions.getText().toString().trim());
            jsonObject.put("category", Category.trim());
            jsonObject.put("specialCategory", _specialCategory);
            jsonObject.put("specialCategoryType", cataCode);
            jsonObject.put("disability", Disability.trim());
            jsonObject.put("disabilityType", DisabilityTypeCode.trim());
            jsonObject.put("highestQual", HighestQualificationCode.trim());
            jsonObject.put("subject", subjectCode.trim());
            jsonObject.put("ncocode", "");
            jsonObject.put("employementStatus", EmployementStatus.trim());
            jsonObject.put("monthlyIncome", _income);
            jsonObject.put("state", StateCode.trim());
            jsonObject.put("dist", DistCode.trim());
            jsonObject.put("residence", rurarban);
            jsonObject.put("block", BLockCode.trim());
            jsonObject.put("panchayat", panchaytCode.trim());
            jsonObject.put("village", villageCode.trim());
            jsonObject.put("nagarNigam", NagriCode.trim());
            jsonObject.put("ward", wardCode.trim());
            jsonObject.put("address", Address_tv.getText().toString().trim());
            jsonObject.put("pincode", pincode_tv.getText().toString().trim());
            jsonObject.put("email", email_tv.getText().toString().trim());
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("overseaseInterested", Oversease);
            jsonObject.put("oldRegno", _oldRegno);
            jsonObject.put("oldName",_name );
            jsonObject.put("regdate", _regdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.RenewalCandidates, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = null;
                        try {
                            result = response.getString("statusCode");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result.equals("201")) {
                            dialog.dismiss();
                            //////////////////////////////////SharedPreferences/////////////////////////////////////////////////////////
                            String MSG = null;
                            String RegCode = null;
                            try {
                                RegCode = response.getString("data");
                                MSG = response.getString("msg");
                                ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_success_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("सफल");


                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText("पंजीयन क्रमांक :- " + RegCode + " सन्देश :- " + MSG);

                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("ओके");

                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.correct);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showWmsg("क्या आप X-10 फॉर्म डाउनलोड करना चाहते है?");
                                    reg_form_llB.setVisibility(View.GONE);

                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();


                        } else {
                            dialog.dismiss();
                            mobile_tv.setText("");
                            String MSG = null;
                            try {
                                MSG = response.getString("msg");
                                ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText("असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(MSG);
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


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


                AlertDialog.Builder builder =
                        new AlertDialog.Builder
                                (job_renewal_form.this);
                View view = LayoutInflater.from(job_renewal_form.this).inflate(
                        R.layout.layout_error_dialog,
                        findViewById(R.id.layoutDialogContainer)
                );
                builder.setCancelable(false);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle))
                        .setText("असफल");

                ((TextView) view.findViewById(R.id.textMessage))
                        .setText(error.toString());
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
              /*  String credentials = Constants_URL.USERNAME+":"+Constants_URL.PASSWORD;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                params.put("Authorization", auth); */
                params.put("Authorization", "Bearer " + accesstoken);
                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonRequest);


    }

    private void showWmsg(String msg) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder
                        (job_renewal_form.this);
        View view1 = LayoutInflater.from(job_renewal_form.this).inflate(
                R.layout.layout_warning_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setCancelable(false);
        builder.setView(view1);
        ((TextView) view1.findViewById(R.id.textTitle))
                .setText("चेतावनी");
        ((TextView) view1.findViewById(R.id.textMessage))
                .setText(msg);
        ((Button) view1.findViewById(R.id.buttonYes))
                .setText("हाँ");
        ((Button) view1.findViewById(R.id.buttonNo))
                .setText("नहीं");
        ((ImageView) view1.findViewById(R.id.imageIcon))
                .setImageResource(R.drawable.warning);
        final AlertDialog alertDialog = builder.create();
        view1.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            /*    SharedPreferences sp = getSharedPreferences("credentialsuser", MODE_PRIVATE);
                String userName = sp.getString("userName", "");//"No name defined" is the default value.
                String mobileno = sp.getString("mobile", "");//"No name defined" is the default value.
                String url = Constants_URL.FrmUserCardForApp + mobileno;
                String xtenURL = url;

                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(xtenURL));
                startActivity(urlIntent); */
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                alertDialog.dismiss();

            }
        });
        view1.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
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
    public void get_SubCategoryMaster() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "ERojgargdvd");
            jsonObject.put("categoryCode", "ST");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.SubCategoryMaster, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONArray result_data = response.getJSONArray("listData");
                            for (int i = 0; i < result_data.length(); i++) {
                                JSONObject output = result_data.getJSONObject(i);
                                SubCategoryMasterModelClass
                                        subCategoryMasterModelClass = new SubCategoryMasterModelClass(
                                        output.getString("categoryCode"),/////web_service/////////////////
                                        output.getString("categoryName")
                                );

                                SubCategoryMasterModelClassList.add(subCategoryMasterModelClass);
                            }

                            String[] SubcataNameArray = new String[SubCategoryMasterModelClassList.size()];
                            for (int i = 0; i < SubCategoryMasterModelClassList.size(); i++) {
                                SubcataNameArray[i] = SubCategoryMasterModelClassList.get(i).getCategoryName();////////from_model_class
                            }

                     /*   ArrayAdapter arrayAdapter = new ArrayAdapter(registration_form.this,
                                android.R.layout.simple_spinner_item, HighestQualificatioNameArray);
                        Highest_Qualification_sp.setAdapter(arrayAdapter);
                            dialog.dismiss(); */


                            pvtgsp_sp.setAdapter(new CustomSpinnerAdapter(job_renewal_form.this, R.layout.spinner_row, SubcataNameArray, defaultTextForSpinner));
                            dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder alert = new AlertDialog.Builder(
                                    job_renewal_form.this);
                            alert.setTitle("सन्देश !");
                            alert.setMessage(e.toString());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do your work here
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
                        job_renewal_form.this);
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
    public static Bitmap RotateBitmap_img(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    private boolean checkPermission() {
        ArrayList<String> permissionsNotGranted = new ArrayList<>();
        for (String permission : neededPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNotGranted.add(permission);
            }
        }
        if (!permissionsNotGranted.isEmpty()) {
            boolean shouldShowAlert = false;
            for (String permission : permissionsNotGranted) {
                shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
            }
            if (shouldShowAlert) {
                showPermissionAlert(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
            } else {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]));
            }
            return false;
        }
        return true;
    }

    private void showPermissionAlert(final String[] permissions) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.permission_required);
        alertBuilder.setMessage(R.string.permission_message);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(permissions);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(job_renewal_form.this, permissions, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(job_renewal_form.this, R.string.permission_warning, Toast.LENGTH_LONG).show();
                    // setViewVisibility(R.id.showPermissionMsg);
                    checkPermission();
                    return;
                }
            }
            frame_layout.setVisibility(View.VISIBLE);
            setViewVisibility(R.id.tv_capture);
            setViewVisibility(R.id.surfaceView);
            blinkCount.setVisibility(View.VISIBLE);
            notemsg.setVisibility(View.VISIBLE);
            setupSurfaceHolder();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setViewVisibility(int id) {
        View view = findViewById(id);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setupSurfaceHolder() {
        cameraSource = new CameraSource.Builder(this, detector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build();

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    public void captureImage() {
        // We add a delay of 200ms so that image captured is stable.
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clickImage();
                    }
                });
            }
        }, 220);
    }

    public void blinkCountfn(int bCount) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        blinkCount.setText("पलके झपकने की संख्या: - " + bCount);
                    }
                });
            }
        }, 0);
    }

    private void clickImage() {
        if (cameraSource != null) {
            cameraSource.takePicture(null, this);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startCamera();
    }

    private void startCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraSource.start(surfaceHolder);
            detector.setProcessor(new LargestFaceFocusingProcessor(detector,
                    new GraphicFaceTracker2(job_renewal_form.this)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        cameraSource.stop();
    }

    @Override
    public void onPictureTaken(byte[] bytes) {
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // Save or Display image as per your requirements. Here we display the image.
        // Intent intent = new Intent(this, picture.class);
        // startActivity(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frame_layout.setVisibility(View.GONE);
                tv_capture.setVisibility(View.GONE);
                surfaceView.setVisibility(View.GONE);
                //    blinkCount.setVisibility(View.GONE);
                //   notemsg.setVisibility(View.GONE);
            }
        });



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            mainbitmap = RotateBitmap(bitmap, 270);
            mainbitmap = flip(mainbitmap);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mainbitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] byteofimages = byteArrayOutputStream.toByteArray();
            encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);

            image_ll.setVisibility(View.VISIBLE);


            byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);

            click_photo_btn.setVisibility(View.GONE);
            declaration_checkbox.setVisibility(View.GONE);
            submit_btn.setVisibility(View.VISIBLE);

        } else {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] byteofimages = byteArrayOutputStream.toByteArray();
            encodedimage = android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);

            image_ll.setVisibility(View.VISIBLE);


            byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img.setImageBitmap(decodedByte);

            click_photo_btn.setVisibility(View.GONE);
            declaration_checkbox.setVisibility(View.GONE);
            submit_btn.setVisibility(View.VISIBLE);
        }
    }


    private void facedetectorCamera() {

        detector = new FaceDetector.Builder(this)
                .setProminentFaceOnly(true) // optimize for single, relatively large face
                .setTrackingEnabled(true) // enable face tracking
                .setClassificationType(/* eyes open and smile */ FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE) // for one face this is OK
                .build();

        if (!detector.isOperational()) {
            // Log.w("MainActivity", "Detector Dependencies are not yet available");
        } else {
            //  Log.w("MainActivity", "Detector Dependencies are available");
            if (surfaceView != null) {
                boolean result = checkPermission();
                if (result) {
                    frame_layout.setVisibility(View.VISIBLE);
                    setViewVisibility(R.id.tv_capture);
                    setViewVisibility(R.id.surfaceView);
                    blinkCount.setVisibility(View.VISIBLE);
                    notemsg.setVisibility(View.VISIBLE);
                    setupSurfaceHolder();
                }
            }
        }
    }

    public void getAuthorizationtoken() {
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userName", Constants_URL.USERNAME);
            jsonObject.put("password", Constants_URL.PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constants_URL.GenerateToken, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        String result = null;
                        try {
                            result = response.getString("statusCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result.equals("200")) {


                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String _token = MSG;
                            RenewalCandidateRegistration(encodedimage,_token);

                        }

                        else {
                            dialog.dismiss();

                            String MSG = null;
                            try {
                                MSG = response.getString("msg");;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder
                                            (job_renewal_form.this);
                            View view = LayoutInflater.from(job_renewal_form.this).inflate(
                                    R.layout.layout_error_dialog,
                                    findViewById(R.id.layoutDialogContainer)
                            );
                            builder.setCancelable(false);
                            builder.setView(view);
                            ((TextView) view.findViewById(R.id.textTitle))
                                    .setText( "असफल");

                            ((TextView) view.findViewById(R.id.textMessage))
                                    .setText(MSG);
                            ((Button) view.findViewById(R.id.buttonAction))
                                    .setText("बंद करें");
                            ((ImageView) view.findViewById(R.id.imageIcon))
                                    .setImageResource(R.drawable.file);

                            final AlertDialog alertDialog = builder.create();
                            view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();

                                }
                            });
                            if (alertDialog.getWindow() != null){
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                            }
                            alertDialog.show();


                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                dialog.dismiss();


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        job_renewal_form.this);
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
                params.put("Content-Type", "application/json; charset=utf-8");
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

