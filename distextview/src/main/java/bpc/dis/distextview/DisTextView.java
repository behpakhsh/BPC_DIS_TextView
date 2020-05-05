package bpc.dis.distextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

public class DisTextView extends FrameLayout {

    private LinearLayout clMain;
    private AppCompatTextView txtText;
    private AppCompatImageButton btnToggle;

    private String text;
    private boolean isShowingText = true;
    private String passwordChar = "*";

    public DisTextView(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public DisTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DisTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.dis_text_view, this);
        clMain = view.findViewById(R.id.cl_main);
        txtText = view.findViewById(R.id.txt_text);
        btnToggle = view.findViewById(R.id.btn_toggle);
        setupView(context, attrs);
    }

    private void setupView(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DisTextView);
        int backgroundColor = styledAttributes.getColor(R.styleable.DisTextView_dtvBackground, context.getResources().getColor(R.color.defaultBackgroundColor));
        setBackground(backgroundColor);
        String text = styledAttributes.getString(R.styleable.DisTextView_dtvText);
        setText(text);
        int textColor = styledAttributes.getColor(R.styleable.DisTextView_dtvTextColor, context.getResources().getColor(R.color.defaultTextColor));
        setTextColor(textColor);
        float textSize = styledAttributes.getDimension(R.styleable.DisTextView_dtvTextSize, context.getResources().getDimension(R.dimen.defaultTextSize));
        setTextSize(textSize);
        int textStyle = styledAttributes.getInteger(R.styleable.DisTextView_dtvTextStyle, 0);
        setTextStyle(textStyle);
        int gravity = styledAttributes.getInteger(R.styleable.DisTextView_dtvGravity, 17);
        setGravity(gravity);
        int direction = styledAttributes.getInteger(R.styleable.DisTextView_dtvDirection, 1);
        setDirection(direction);


        String passwordChar = styledAttributes.getString(R.styleable.DisTextView_dtvPasswordChar);
        setPasswordChar(passwordChar);
        boolean passwordToggleEnable = styledAttributes.getBoolean(R.styleable.DisTextView_dtvPasswordToggleEnable, false);
        setPasswordToggleEnable(passwordToggleEnable);

        styledAttributes.recycle();
    }

    public void setPasswordChar(String passwordChar) {
        this.passwordChar = passwordChar;
        setText(text);
    }

    public void setDirection(int direction) {
        clMain.setLayoutDirection(direction);
    }

    public void setGravity(int gravity) {
        txtText.setGravity(gravity);
    }

    public void setTextStyle(int textStyle) {
        txtText.setTypeface(txtText.getTypeface(), textStyle);
    }

    public void setPasswordToggleEnable(boolean passwordToggleEnable) {
        if (passwordToggleEnable) {
            isShowingText = false;
            btnToggle.setVisibility(VISIBLE);
            btnToggle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowingText = !isShowingText;
                    checkToggleStatus();
                    setText(text);
                }
            });
        } else {
            isShowingText = true;
            btnToggle.setVisibility(GONE);
            btnToggle.setOnClickListener(null);
        }
        checkToggleStatus();
        setText(text);
    }

    public void checkToggleStatus() {
        if (isShowingText) {
            btnToggle.setImageResource(R.drawable.ic_hide);
        } else {
            btnToggle.setImageResource(R.drawable.ic_show);
        }
    }

    public void setTextSize(float textSize) {
        textSize = textSize / getResources().getDisplayMetrics().density;
        txtText.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        txtText.setTextColor(textColor);
    }

    public void setBackground(int backgroundColor) {
        clMain.setBackgroundColor(backgroundColor);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        StringBuilder stringBuilder = new StringBuilder();
        if (passwordChar != null) {
            for (int i = 0; i < this.text.length(); i++) {
                stringBuilder.append(passwordChar);
            }
        } else {
            stringBuilder.append(text);
        }
        txtText.setText(stringBuilder.toString());
    }

}