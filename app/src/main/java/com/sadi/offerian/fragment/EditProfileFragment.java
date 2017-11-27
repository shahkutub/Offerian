package com.sadi.offerian.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sadi.offerian.R;
import com.sadi.offerian.utils.AppConstant;
import com.sadi.offerian.utils.BitmapUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditProfileFragment extends Fragment {
    private Context con;
    private TextView tvName, tvId, tvDesignation, tvPhone, tvAddress, tvBloodGroup, tvDOB, tvEmail;
    //private ImageView imgEditProfile;
    private Button btnDateOfInterView;
    private Spinner spinnerBloodgroup;

    private TextInputLayout input_layout_name, input_layout_designation, input_layout_phone, input_layout_address,  input_layout_blood_group, input_layout_email, input_layout_id;

    private EditText input_name, input_id, input_Designation, input_phone, input_address, input_blood_group, input_email;

    String name, designation, phone, address, bloodGroup, email, officerId, dob;
    String employeeDate;
    private ImageView imgUserPicture, imvTakePic,imgEditProfile;
    private File file;
    String picture = "";
    private static File dir = null;
    String imageLocal = "";
    public final int imagecaptureid = 0;
    public final int galarytakid = 1;

    Dialog dialog;
    //private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_fragement,null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        con = getActivity();
        initUI(view);
        name = "Here is azhar";
        designation = "Android Developer";
        phone = "01717121839";
        address = "Mymensingh";
        email = "azadbasis@gmail.com";
        officerId = "0947328974";
        bloodGroup = "A+";
        dob = "1-1-1990";
        tvName.setText(name);
        tvId.setText(officerId);
        tvDesignation.setText(designation);
        tvBloodGroup.setText(bloodGroup);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvDOB.setText(dob);
        btnDateOfInterView.setText(dob);
    }

    public void initUI(View v) {


        input_layout_name = (TextInputLayout) v.findViewById(R.id.input_layout_name);
        input_name = (EditText) v.findViewById(R.id.input_name);
        tvName = (TextView) v.findViewById(R.id.tvName);

        input_layout_id = (TextInputLayout) v.findViewById(R.id.input_layout_id);
        input_id = (EditText) v.findViewById(R.id.input_id);
        tvId = (TextView) v.findViewById(R.id.tvId);

        input_layout_designation = (TextInputLayout) v.findViewById(R.id.input_layout_designation);
        input_Designation = (EditText) v.findViewById(R.id.input_Designation);
        tvDesignation = (TextView) v.findViewById(R.id.tvDesignation);

        input_layout_phone = (TextInputLayout) v.findViewById(R.id.input_layout_phone);
        input_phone = (EditText) v.findViewById(R.id.input_phone);
        tvPhone = (TextView) v.findViewById(R.id.tvPhone);

        input_layout_address = (TextInputLayout) v.findViewById(R.id.input_layout_address);
        input_address = (EditText) v.findViewById(R.id.input_address);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);

        input_layout_blood_group = (TextInputLayout) v.findViewById(R.id.input_layout_blood_group);
        input_blood_group = (EditText) v.findViewById(R.id.input_blood_group);
        tvBloodGroup = (TextView) v.findViewById(R.id.tvBloodGroup);
        spinnerBloodgroup = (Spinner)v.findViewById(R.id.spinnerBloodgroup);

        tvDOB = (TextView) v.findViewById(R.id.tvDOB);
        btnDateOfInterView = (Button) v.findViewById(R.id.btnDateOfInterView);


        input_layout_email = (TextInputLayout) v.findViewById(R.id.input_layout_email);
        input_email = (EditText) v.findViewById(R.id.input_email);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);



        imgEditProfile = (ImageView) v.findViewById(R.id.imgEditProfile);
        imvTakePic = (ImageView) v.findViewById(R.id.imvTakePic);
        imgUserPicture = (ImageView) v.findViewById(R.id.imgUserPicture);

        imvTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCaptureDialogue();
            }
        });

        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvName.setVisibility(View.GONE);
                input_layout_name.setVisibility(View.VISIBLE);
                input_name.setText(name);


                tvId.setVisibility(View.GONE);
                input_layout_id.setVisibility(View.VISIBLE);
                input_id.setText(officerId);


                tvDesignation.setVisibility(View.GONE);
                input_layout_designation.setVisibility(View.VISIBLE);
                input_Designation.setText(designation);

                tvPhone.setVisibility(View.GONE);
                input_layout_phone.setVisibility(View.VISIBLE);
                input_phone.setText(phone);

                tvAddress.setVisibility(View.GONE);
                input_layout_address.setVisibility(View.VISIBLE);
                input_address.setText(address);

                tvBloodGroup.setVisibility(View.GONE);
                //input_layout_blood_group.setVisibility(View.VISIBLE);
                //input_blood_group.setText(bloodGroup);
                spinnerBloodgroup.setVisibility(View.VISIBLE);


                tvDOB.setVisibility(View.GONE);
                //  input_layout_dob.setVisibility(View.VISIBLE);
                //   input_dob.setText(dob);
                btnDateOfInterView.setVisibility(View.VISIBLE);

                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        DateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
                        employeeDate = dateFormat.format(myCalendar.getTime());
                        btnDateOfInterView.setText(employeeDate);
                    }
                };
                final DatePickerDialog d = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

                btnDateOfInterView.setText(dob);
                btnDateOfInterView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.show();
                    }
                });

                tvEmail.setVisibility(View.GONE);
                input_layout_email.setVisibility(View.VISIBLE);
                input_email.setText(email);

            }
        });
    }




    private void imageCaptureDialogue() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chang_photo_dialogue);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout tvUseCam = (LinearLayout) dialog
                .findViewById(R.id.tvUseCam);
        LinearLayout tvRoll = (LinearLayout) dialog
                .findViewById(R.id.tvRoll);
        LinearLayout tvCance = (LinearLayout) dialog
                .findViewById(R.id.tvCance);


        tvRoll.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                AppConstant.isGallery = true;
                if (ActivityCompat.checkSelfPermission(con, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.WRITEEXTERNAL_PERMISSION_RUNTIME);
                    dialog.dismiss();
                } else {
                    final Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), galarytakid);
                    dialog.dismiss();
                }
            }




        });

        tvUseCam.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getActivity(),
                            new String[]{Manifest.permission.CAMERA}, AppConstant.CAMERA_RUNTIME_PERMISSION);
                    dialog.dismiss();
                } else {
                    AppConstant.isGallery = false;
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.WRITEEXTERNAL_PERMISSION_RUNTIME);
                        dialog.dismiss();
                    } else {
                        final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, imagecaptureid);
                        dialog.dismiss();
                    }
                }
            }


//                if (ContextCompat.checkSelfPermission(con,Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},
//                            1);
//
//                }else if(ContextCompat.checkSelfPermission(con,Manifest.permission.CAMERA)
//                        == PackageManager.PERMISSION_GRANTED){
//                    final Intent i = new Intent(
//                            "android.media.action.IMAGE_CAPTURE");
//                    startActivityForResult(i, imagecaptureid);
//                    dialog.dismiss();
//                }


        });

        tvCance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstant.CAMERA_RUNTIME_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.WRITEEXTERNAL_PERMISSION_RUNTIME);
                } else {
                    final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, imagecaptureid);
                }
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        } else if (requestCode == AppConstant.WRITEEXTERNAL_PERMISSION_RUNTIME) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (AppConstant.isGallery) {
                    final Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), galarytakid);
                } else {
                    final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, imagecaptureid);
                }
            }
        }
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("On Activity Result", "On Activity Result");
        if (requestCode == galarytakid && resultCode == Activity.RESULT_OK) {

            Log.e("In gallelrly", "lllll..........");
            try {

                final Uri selectedImageUri = data.getData();

                final Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity().getContentResolver().openInputStream(
                                selectedImageUri));
                //  final Bitmap d = BitmapFactory.decodeStream(getChildFragmentManager().)


                // final Bundle extras = data.getExtras();
                // final Bitmap b = (Bitmap) extras.get("data");
                final String path = setToImageView(bitmap);
                Log.e("Bitmap >>",
                        "W: " + bitmap.getWidth() + " H: " + bitmap.getHeight());
                Log.e("path", ">>>>>" + path);
                //PersistData.setStringData(con, AppConstant.path, path);
                picture = path;

//                Log.e("path",
//                        ">>>>>"
//                                + PersistData.getStringData(con,
//                                AppConstant.path));
                //Picasso.with(con).load(path).transform(new CircleTransform()).into(imgPicCapture);

                imgUserPicture.setImageBitmap(bitmap);
                // AppConstant.imagebit=bitmap;

                //  AppConstant.imagebit = bitmap;

            } catch (final Exception e) {
                return;
            }

        } else if (requestCode == imagecaptureid
                && resultCode == Activity.RESULT_OK) {

            try {

                final Bundle extras = data.getExtras();
                final Bitmap b = (Bitmap) extras.get("data");

                final String path = setToImageView(b);
                Log.e("Bitmap >>",
                        "W: " + b.getWidth() + " H: " + b.getHeight());
                picture = path;
                Log.e("path", ">>>>>" + path);
//                Log.e("path",
//                        ">>>>>"
//                                + PersistData.getStringData(con,
//                                AppConstant.path));


//                    ImgUserEdit.setImageBitmap(b);
//                    AppConstant.imagebit = b;

                imgUserPicture.setImageBitmap(b);
                //Picasso.with(con).load(path).transform(new CircleTransform()).into(imgPicCapture);
                //AppConstant.imagebit = b;


            } catch (final Exception e) {
                return;
            }

        }

    }

    private String setToImageView(Bitmap bitmap) {

        try {

            // if (isImage) {
            final Bitmap bit = BitmapUtils.getResizedBitmap(bitmap, 300);
            final double time = System.currentTimeMillis();

            imageLocal = saveBitmapIntoSdcard(bit, "offerian" + time + ".png");

            Log.e("camera saved URL :  ", " " + imageLocal);


        } catch (final IOException e) {
            e.printStackTrace();

            imageLocal = "";
            Log.e("camera saved URL :  ", e.toString());

        }

        return imageLocal;

    }

    private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
            throws IOException {
        /*
         *
		 * check the path and create if needed
		 */
        createBaseDirctory();

        try {

            new Date();

            OutputStream out = null;
            file = new File(this.dir, "/" + filename);

            if (file.exists()) {
                file.delete();
            }

            out = new FileOutputStream(file);

            bitmap22.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
            // Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            return file.getAbsolutePath();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createBaseDirctory() {
        final String extStorageDirectory = Environment
                .getExternalStorageDirectory().toString();
        dir = new File(extStorageDirectory + "/Offerian");

        if (this.dir.mkdir()) {
            System.out.println("Directory created");
        } else {
            System.out.println("Directory is not created or exists");
        }
    }



}
