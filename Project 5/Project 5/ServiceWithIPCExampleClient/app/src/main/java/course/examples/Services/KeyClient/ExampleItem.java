package course.examples.Services.KeyClient;

import android.graphics.Bitmap;

//Defining all the objects get methods, data fetched from Second activiy

public class ExampleItem {
    private Bitmap mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(Bitmap imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
    }

    public Bitmap getImageResource() {
        return mImageResource;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }
}
