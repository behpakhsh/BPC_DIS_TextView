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

    private View view;

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
        view = inflate(context, R.layout.dis_text_view, this);
        setupView(context, attrs, defStyleAttr);
    }

    private void setupView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DisTextView);
        setBackground(styledAttributes.getColor(R.styleable.DisTextView_dtvBackground, context.getResources().getColor(R.color.defaultBackgroundColor)));
        setText(styledAttributes.getString(R.styleable.DisTextView_dtvText));
        setTextColor(styledAttributes.getColor(R.styleable.DisTextView_dtvTextColor, context.getResources().getColor(R.color.defaultTextColor)));
        setTextSize(styledAttributes.getDimension(R.styleable.DisTextView_dtvTextSize, context.getResources().getDimension(R.dimen.defaultTextSize)));
        setTextStyle(styledAttributes.getInteger(R.styleable.DisTextView_dtvTextStyle, 0));
        setGravity(styledAttributes.getInteger(R.styleable.DisTextView_dtvGravity, 17));
        setDirection(styledAttributes.getInteger(R.styleable.DisTextView_dtvDirection, 1));
        setPasswordChar(styledAttributes.getString(R.styleable.DisTextView_dtvPasswordChar));
        setPasswordToggleEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvPasswordToggleEnable, false));
        setLineEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvLineEnable, false));
        setLineColor(styledAttributes.getColor(R.styleable.DisTextView_dtvLineColor, context.getResources().getColor(R.color.defaultLineColor)));
        setUnderlineEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvUnderlineEnable, false));
        setUnderlineColor(styledAttributes.getColor(R.styleable.DisTextView_dtvUnderlineColor, context.getResources().getColor(R.color.defaultUnderlineColor)));
        styledAttributes.recycle();
    }

    private void setLineEnable(boolean enable) {
        View line = view.findViewById(R.id.view_line);
        if (enable) {
            line.setVisibility(VISIBLE);
        } else {
            line.setVisibility(GONE);
        }
    }

    private void setLineColor(int color) {
        View line = view.findViewById(R.id.view_line);
        line.setBackgroundColor(color);
    }

    private void setUnderlineEnable(boolean enable) {
        View line = view.findViewById(R.id.view_underline);
        if (enable) {
            line.setVisibility(VISIBLE);
        } else {
            line.setVisibility(GONE);
        }
    }

    private void setUnderlineColor(int color) {
        View line = view.findViewById(R.id.view_underline);
        line.setBackgroundColor(color);
    }

    public void setPasswordChar(String passwordChar) {
        this.passwordChar = passwordChar;
        setText(text);
    }

    public void setDirection(int direction) {
        LinearLayout llMain = view.findViewById(R.id.ll_dtv_main);
        llMain.setLayoutDirection(direction);
    }

    public void setGravity(int gravity) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_text);
        txtText.setGravity(gravity);
    }

    public void setTextStyle(int textStyle) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_text);
        txtText.setTypeface(txtText.getTypeface(), textStyle);
    }

    public void setPasswordToggleEnable(boolean passwordToggleEnable) {
        AppCompatImageButton btnToggle = view.findViewById(R.id.btn_toggle);
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
        AppCompatImageButton btnToggle = view.findViewById(R.id.btn_toggle);
        if (isShowingText) {
            btnToggle.setImageResource(R.drawable.ic_hide);
        } else {
            btnToggle.setImageResource(R.drawable.ic_show);
        }
    }

    public void setTextSize(float textSize) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_text);
        textSize = textSize / getResources().getDisplayMetrics().density;
        txtText.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_text);
        txtText.setTextColor(textColor);
    }

    public void setBackground(int backgroundColor) {
        LinearLayout llMain = view.findViewById(R.id.ll_dtv_main);
        llMain.setBackgroundColor(backgroundColor);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_text);
        if (text == null) {
            text = "";
        }
        this.text = text;
        StringBuilder stringBuilder = new StringBuilder();
        if (passwordChar != null) {
            if (isShowingText) {
                stringBuilder.append(text);
            } else {
                for (int i = 0; i < this.text.length(); i++) {
                    stringBuilder.append(passwordChar);
                }
            }
        } else {
            stringBuilder.append(text);
        }
        txtText.setText(stringBuilder.toString());
    }

}