# PhotoPicker
A great and easy to use PhotoPicker for Android projects

# How to use
First you need to implement the library in your project:
In File>New>Import module..., then a window will open, in this window choose in the browser where you downloaded de library. 
And finally, click in finish.
After this in your Gradle app implement the library:
```gradle 
implementation project(':photopicker') 
```
or
[![](https://jitpack.io/v/mrocigno/PhotoPicker.svg)](https://jitpack.io/#mrocigno/PhotoPicker)

That is it, the Library is implemented in your project.

To use you need add the view in you XML file (somthing like this):
```XML
<!-- result_camera and result_galery is for the activity result -->
<!-- knows who calls the function -->
<lib.rocigno.photopicker.PhotoPicker
    android:id="@+id/photo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:title="Photos" 
    app:result_camera="1"
    app:result_galery="2"
    app:limit="3"/>
```
And in the Java you just need set the view and Override the "onActivityResult", like this:
```Java
    PhotoPicker photo;
    [...]
    //inside onCreate
    photo = findViewById(R.id.photo);
    [...]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photo.result(requestCode,resultCode,data);
    }
```
