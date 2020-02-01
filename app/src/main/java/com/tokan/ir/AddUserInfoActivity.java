package com.tokan.ir;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
import com.tokan.ir.widget.BEditTextView;
import com.tokan.ir.widget.BTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.img_user)
    AppCompatImageView img_user;

    @BindView(R.id.img_arrow)
    AppCompatImageView img_arrow;

    @BindView(R.id.edt_nameFamily)
    BEditTextView edt_nameFamily;

    @BindView(R.id.edt_title)
    BEditTextView edt_title;

    @BindView(R.id.edt_phoneNumber)
    BEditTextView edt_phoneNumber;

    @BindView(R.id.edt_mobileNumber)
    BEditTextView edt_mobileNumber;

    @BindView(R.id.edt_address)
    BEditTextView edt_address;

    @BindView(R.id.edt_email)
    BEditTextView edt_email;

    @BindView(R.id.edt_site)
    BEditTextView edt_site;

    @BindView(R.id.edt_username)
    BEditTextView edt_username;

    @BindView(R.id.edt_password)
    BEditTextView edt_password;

    @BindView(R.id.edt_confirmPassword)
    BEditTextView edt_confirmPassword;

    @BindView(R.id.txt_title)
    BTextView txt_title;

    private Uri mCapturedImageURI;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_user);

        ButterKnife.bind(this);

        edt_nameFamily.setHint("نام و نانوادگی");
        edt_title.setHint("عنوان مجموعه");
        edt_phoneNumber.setHint("تلفن ثابت مجموعه");
        edt_mobileNumber.setHint("تلفن همراه");
        edt_address.setHint("نشانی");
        edt_email.setHint("ایمیل");
        edt_site.setHint("وب سایت");
        edt_username.setHint("نام کاربری");
        edt_password.setHint("رمز عبور");
        edt_confirmPassword.setHint("تکرار رمز عبور");
        txt_title.setText("مشخصات مالک");
        txt_title.setTextSize(24);
        img_arrow.setVisibility(View.GONE);

        //edt_address.setMax(1000);
        edt_address.setLines(5);
        edt_phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt_mobileNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edt_site.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edt_confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        img_user.setBackgroundDrawable(getResources().getDrawable(R.drawable.click));


    }

    public void action(View view) {
        addUser();
    }


    public void attach(View view) {
        System.out.println("attach==");
        final Dialog dialog = new Dialog(AddUserInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attach);
        BTextView camera = (BTextView) dialog.findViewById(R.id.txt_camera);
        BTextView gallery = (BTextView) dialog.findViewById(R.id.txt_gallery);
        dialog.show();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                        .checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale
                            (AddUserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale
                                    (AddUserInfoActivity.this, Manifest.permission.CAMERA)) {
                        finish();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST);
                        }
                    }

                } else {
                    cameraIntent();
                }
                dialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                        .checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale
                            (AddUserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale
                                    (AddUserInfoActivity.this, Manifest.permission.CAMERA)) {
                        finish();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST);
                        }
                    }

                } else {
                    galleryIntent();
                }

                dialog.dismiss();
            }
        });
    }


    public void addUser() {

        edt_nameFamily.setHint("نام و نانوادگی");
        edt_title.setHint("عنوان مجموعه");
        edt_phoneNumber.setHint("تلفن ثابت مجموعه");
        edt_mobileNumber.setHint("تلفن همراه");
        edt_address.setHint("نشانی");
        edt_email.setHint("ایمیل");
        edt_site.setHint("وب سایت");
        edt_username.setHint("نام کاربری");
        edt_password.setHint("رمز عبور");
        edt_confirmPassword.setHint("تکرار رمز عبور");

        String nameFamily = edt_nameFamily.getText().toString();
        String title = edt_title.getText().toString();
        String phoneNumber = edt_phoneNumber.getText().toString();
        String mobileNumber = edt_mobileNumber.getText().toString();
        String address = edt_address.getText().toString();
        String email = edt_email.getText().toString();
        String site = edt_site.getText().toString();
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        String confirmPassword = edt_confirmPassword.getText().toString();

        if (nameFamily.equals("")) {
            edt_nameFamily.setError("تکمیل شود");
            return;
        }

        if (title.equals("")) {
            edt_title.setError("تکمیل شود");
            return;
        }

        if (phoneNumber.equals("")) {
            edt_phoneNumber.setError("تکمیل شود");
            return;
        }

        if (mobileNumber.equals("")) {
            edt_mobileNumber.setError("تکمیل شود");
            return;
        }

        if (address.equals("")) {
            edt_address.setError("تکمیل شود");
            return;
        }

        if (email.equals("")) {
            edt_email.setError("تکمیل شود");
            return;
        }

        if (site.equals("")) {
            edt_site.setError("تکمیل شود");
            return;
        }

        if (username.equals("")) {
            edt_username.setError("تکمیل شود");
            return;
        }

        if (password.equals("")) {
            edt_password.setError("تکمیل شود");
            return;
        }

        if (!password.equals(confirmPassword)) {
            edt_password.setError("صحیح نمی باشد");
            edt_confirmPassword.setError("صحیح نمی باشد");
            return;
        }


        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                User user = new User();
                user.setNameFamily(nameFamily);
                user.setAddress(address);
                user.setTitle(title);
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);
                user.setPhoneNumber(phoneNumber);
                user.setMobileNumber(mobileNumber);
                user.setSite(site);
                user.setLoginIs(true);
                user.setPath(path);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().insertUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(getApplicationContext(), AddUserInfoActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        new SaveUser().execute();
    }

    //========= cameraIntent for attachment
    public void cameraIntent() {
        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    //========= galleryIntent for attachment
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select image"), REQUEST_GALLERY);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri outputFileUri;
            path = null;
            if (requestCode == REQUEST_CAMERA) {
                path = getPathCamera();
                Bitmap bitmap = resizeBitmap(path, 100, 100);
                img_user.setBackgroundDrawable(null);
                img_user.setImageBitmap(bitmap);

            } else if (requestCode == REQUEST_GALLERY) {
                outputFileUri = data.getData();
                path = getPathFromUri(getApplicationContext(), outputFileUri);
                Bitmap bitmap = resizeBitmap(path, 100, 100);
                img_user.setBackgroundDrawable(null);
                img_user.setImageBitmap(bitmap);

            }
        }
    }


    private Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    /*getPathCamera */
    private String getPathCamera() {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index_data);
    }

    /*//========= getRealPathFromURI
    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] pro = {MediaStore.MediaColumns.DATA};
        try {
            Cursor cursor = getContentResolver().query(contentUri, pro, null, null, null);
            assert cursor != null;
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
            }
            cursor.close();
        } catch (Exception e) {
            System.out.println("Err" + e.getMessage());
        }
        return path;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
