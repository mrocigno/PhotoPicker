package lib.rocigno.photopicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class TesteView extends FrameLayout {
    public TesteView(@NonNull Context context) {
        super(context);
        initComponents();
    }

    public TesteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initComponents();
    }

    public TesteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents();
    }

    public TesteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initComponents();
    }

    private void initComponents(){
        inflate(getContext(), R.layout.view_uploader, this);
    }
}
