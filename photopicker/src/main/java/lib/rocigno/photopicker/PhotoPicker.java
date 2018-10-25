package lib.rocigno.photopicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import lib.rocigno.photopicker.Adapters.PhotoRecyclerAdapter;
import lib.rocigno.photopicker.Helpers.CustomBottomSheet;
import lib.rocigno.photopicker.Models.PhotosModel;
import lib.rocigno.photopicker.Utils.ImagesActions;

import static android.app.Activity.RESULT_OK;

public class PhotoPicker extends FrameLayout {



    public PhotoPicker(Context context) {
        super(context);
        initComponents(null);
    }

    public PhotoPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponents(attrs);
    }

    public PhotoPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents(attrs);
    }

    public PhotoPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponents(attrs);
    }

    Activity activity;
    ImageView uploader_btnUp;
    TextView uploader_txtTitle;
//    GridView uploader_gridPhotos;
    RecyclerView uploader_rcyPhotos;
    int limit = -1;
    int result_camera = 1;
    int result_galery = 2;

    private void initComponents(@Nullable AttributeSet attrs){
        inflate(getContext(), R.layout.view_uploader, this);
        uploader_txtTitle = this.findViewById(R.id.uploader_txtTitle);
        uploader_btnUp = this.findViewById(R.id.uploader_btnUp);
        uploader_rcyPhotos = this.findViewById(R.id.uploader_rcyPhotos);
        uploader_btnUp.setOnClickListener(ibtnUpActions);

        if(!isInEditMode()){
            activity = (Activity) getContext();
        }

        if(attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PhotoPicker);
            uploader_txtTitle.setText(ta.getString(R.styleable.PhotoPicker_title));
            limit = ta.getInteger(R.styleable.PhotoPicker_limit, -1);
            result_camera = ta.getInteger(R.styleable.PhotoPicker_result_camera, 1);
            result_galery = ta.getInteger(R.styleable.PhotoPicker_result_galery, 2);
        }
    }

    private OnClickListener ibtnUpActions = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int cFoto = arrayList.size();
            if(cFoto >= limit && limit != -1){
                Toast.makeText(getContext(), "Limite de " + cFoto + " foto" + (limit > 1? "s":""), Toast.LENGTH_LONG).show();
                return;
            }
            new CustomBottomSheet(activity).add(
                    R.drawable.ic_camera,
                    "Camera",
                    new CustomBottomSheet.onClickAction() {
                        @Override
                        public void onItemSelected() {
                            if(verifiePermissions(activity)){
                                ImagesActions.showCamera(activity, result_camera);
                            }
                        }
                    }
            ).add(
                    R.drawable.ic_galery,
                    "Galeria",
                    new CustomBottomSheet.onClickAction() {
                        @Override
                        public void onItemSelected() {
                            if(verifiePermissions(activity)){
                                ImagesActions.showGalery(activity, result_galery);
                            }
                        }
                    }
            ).show();
        }
    };

    public static boolean verifiePermissions(Activity activity) {
        String[] permisions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> denied = new ArrayList<>();

        for (String permision:permisions) {
            if(ContextCompat.checkSelfPermission(activity, permision) == PackageManager.PERMISSION_DENIED){
                denied.add(permision);
            }
        }

        if(denied.size() > 0){
            String[] permisionsDenied = new String[denied.size()];
            permisionsDenied = denied.toArray(permisionsDenied);

            ActivityCompat.requestPermissions(activity, permisionsDenied,0);
            return false;
        }
        return true;
    }

    public void result(int requestCode, int resultCode, Intent data){

        Uri imageUri = ImagesActions.getImageUri();

        if (resultCode == RESULT_OK && requestCode == result_camera) {
            GuardaImagem(imageUri);

        }else if (resultCode == RESULT_OK && requestCode == result_galery) {
            Uri selectedImage = returnGalery(activity, data, 150);
            if(selectedImage != null) {
                GuardaImagem(selectedImage);
            }
        }
    }

    ArrayList<PhotosModel> arrayList = new ArrayList<>();
    private void GuardaImagem(Uri uriBitmap) {
        try {
            int numColunms = arrayList.size();
            GridLayoutManager glm = new GridLayoutManager(activity, 1);
//            uploader_gridPhotos.setNumColumns(++numColunms);
            numColunms++;
            if(numColunms < 3){
                glm.setSpanCount(numColunms);
            }else{
                glm.setSpanCount(3);
            }
            arrayList.add(new PhotosModel("Foto " + numColunms, uriBitmap));
//            uploader_gridPhotos.setAdapter(new GridUploaderAdapter(getContext(), arrayList, activity));
            uploader_rcyPhotos.setLayoutManager(glm);
            uploader_rcyPhotos.setAdapter(new PhotoRecyclerAdapter(arrayList, activity));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri returnGalery(Activity activity, Intent data, int MaxDimension){
        try{
            final Uri imageUri = data.getData();

            assert imageUri != null;
            InputStream inputStream = activity.getContentResolver().openInputStream(imageUri);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageHeight <= MaxDimension || imageWidth <= MaxDimension) {
                Toast.makeText(activity, "Imagem muito pequena, selecione outra.", Toast.LENGTH_SHORT).show();
                return null;
            } else {
                System.gc();
                return imageUri;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(activity, "Você não selecionou uma foto.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

}
