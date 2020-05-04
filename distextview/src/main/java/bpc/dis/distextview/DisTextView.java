package bpc.dis.distextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DisTextView extends FrameLayout {

    private ConstraintLayout clMain;
    private AppCompatTextView txtText;
    private AppCompatImageButton btnToggle;

    private String text;
    private boolean passwordEnable = false;
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
        clMain.setId((int) (System.currentTimeMillis() / 100));
        txtText.setId((int) (System.currentTimeMillis() / 110));
        btnToggle.setId((int) (System.currentTimeMillis() / 120));
        setupView(context, attrs);
    }

    private void setupView(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DisTextView);

        int backgroundColor = styledAttributes.getColor(R.styleable.DisTextView_dtvBackground, context.getResources().getColor(R.color.defaultBackgroundColor));
        setBackground(backgroundColor);

        String passwordChar = styledAttributes.getString(R.styleable.DisTextView_dtvPasswordChar);
        setPasswordChar(passwordChar);

        String text = styledAttributes.getString(R.styleable.DisTextView_dtvText);
        setText(text);

        int textColor = styledAttributes.getColor(R.styleable.DisTextView_dtvTextColor, context.getResources().getColor(R.color.defaultTextColor));
        setTextColor(textColor);

        float textSize = styledAttributes.getDimension(R.styleable.DisTextView_dtvTextSize, context.getResources().getDimension(R.dimen.defaultTextSize));
        setTextSize(textSize);

        boolean passwordEnable = styledAttributes.getBoolean(R.styleable.DisTextView_dtvPasswordToggleEnable, false);
        setPasswordEnable(passwordEnable);

        boolean passwordToggleEnable = styledAttributes.getBoolean(R.styleable.DisTextView_dtvPasswordToggleEnable, false);
        setPasswordToggleEnable(passwordToggleEnable);

        int textStyle = styledAttributes.getInteger(R.styleable.DisTextView_dtvTextStyle, 0);
        setTextStyle(textStyle);

        int gravity = styledAttributes.getInteger(R.styleable.DisTextView_dtvGravity, 17);
        setGravity(gravity);

        int direction = styledAttributes.getInteger(R.styleable.DisTextView_dtvDirection, 1);
        setDirection(direction);

        styledAttributes.recycle();
    }

    private void setPasswordChar(String passwordChar) {
        this.passwordChar = passwordChar;
        setText(text);
    }

    private void setDirection(int direction) {
        clMain.setLayoutDirection(direction);
    }

    private void setGravity(int gravity) {
        txtText.setGravity(gravity);
    }

    private void setTextStyle(int textStyle) {
        txtText.setTypeface(txtText.getTypeface(), textStyle);
    }

    private void setPasswordEnable(boolean passwordEnable) {
        this.passwordEnable = passwordEnable;
        setText(text);
    }

    private void setPasswordToggleEnable(boolean passwordToggleEnable) {
        if (passwordToggleEnable) {
            btnToggle.setVisibility(VISIBLE);
            checkToggleStatus();
            btnToggle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowingText = !isShowingText;
                    checkToggleStatus();
                }
            });
        } else {
            btnToggle.setVisibility(GONE);
            btnToggle.setOnClickListener(null);
        }
    }

    private void checkToggleStatus() {
        if (isShowingText) {
            passwordEnable = false;
            btnToggle.setImageResource(R.drawable.ic_hide);
        } else {
            passwordEnable = true;
            btnToggle.setImageResource(R.drawable.ic_show);
        }
        setText(text);
    }

    private void setTextSize(float textSize) {
        textSize = textSize / getResources().getDisplayMetrics().density;
        txtText.setTextSize(textSize);
    }

    private void setTextColor(int textColor) {
        txtText.setTextColor(textColor);
    }

    private void setBackground(int backgroundColor) {
        clMain.setBackgroundColor(backgroundColor);
    }

    public String getText() {
        return text;
    }

    private void setText(String text) {
        this.text = text;
        StringBuilder stringBuilder = new StringBuilder();
        if (passwordEnable) {
            for (int i = 0; i < this.text.length(); i++) {
                stringBuilder.append(passwordChar);
            }
        } else {
            stringBuilder.append(text);
        }
        txtText.setText(stringBuilder.toString());
    }

}