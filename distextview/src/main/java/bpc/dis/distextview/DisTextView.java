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

import bpc.dis.utilities.StringUtilities.StringUtilities;

public class DisTextView extends FrameLayout {

    private View view;

    private String text;
    private String passwordText = "";
    private boolean isShowingText = false;
    private boolean passwordToggleEnable = false;
    private String passwordChar = "*";
    private int textType = 0;

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
        setTextType(styledAttributes.getInteger(R.styleable.DisTextView_dtvTextType, 0));
        setBackground(styledAttributes.getColor(R.styleable.DisTextView_dtvBackground, context.getResources().getColor(R.color.defaultBackgroundColor)));
        setTextColor(styledAttributes.getColor(R.styleable.DisTextView_dtvTextColor, context.getResources().getColor(R.color.defaultTextColor)));
        setTextSize(styledAttributes.getDimension(R.styleable.DisTextView_dtvTextSize, context.getResources().getDimension(R.dimen.defaultTextSize)));
        setTextStyle(styledAttributes.getInteger(R.styleable.DisTextView_dtvTextStyle, 0));
        setGravity(styledAttributes.getInteger(R.styleable.DisTextView_dtvGravity, 17));
        setDirection(styledAttributes.getInteger(R.styleable.DisTextView_dtvDirection, 1));
        setPasswordChar(styledAttributes.getString(R.styleable.DisTextView_dtvPasswordChar));
        setUnitText(styledAttributes.getString(R.styleable.DisTextView_dtvPriceUnitText));
        setUnitTextColor(styledAttributes.getColor(R.styleable.DisTextView_dtvPriceUnitTextColor, context.getResources().getColor(R.color.defaultTextColor)));
        setUnitTextSize(styledAttributes.getDimension(R.styleable.DisTextView_dtvPriceUnitTextSize, context.getResources().getDimension(R.dimen.defaultTextSize)));
        setAfterUnitText(styledAttributes.getString(R.styleable.DisTextView_dtvAfterUnitText));
        setAfterUnitTextColor(styledAttributes.getColor(R.styleable.DisTextView_dtvAfterUnitTextColor, context.getResources().getColor(R.color.defaultTextColor)));
        setAfterUnitTextSize(styledAttributes.getDimension(R.styleable.DisTextView_dtvAfterUnitTextSize, context.getResources().getDimension(R.dimen.defaultTextSize)));
        setPasswordToggleEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvPasswordToggleEnable, false));
        setLineEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvLineEnable, false));
        setLineColor(styledAttributes.getColor(R.styleable.DisTextView_dtvLineColor, context.getResources().getColor(R.color.defaultLineColor)));
        setUnderlineEnable(styledAttributes.getBoolean(R.styleable.DisTextView_dtvUnderlineEnable, false));
        setUnderlineColor(styledAttributes.getColor(R.styleable.DisTextView_dtvUnderlineColor, context.getResources().getColor(R.color.defaultUnderlineColor)));
        setText(styledAttributes.getString(R.styleable.DisTextView_dtvText));
        styledAttributes.recycle();
    }


    public void setLineEnable(boolean enable) {
        View line = view.findViewById(R.id.view_line);
        if (enable) {
            line.setVisibility(VISIBLE);
        } else {
            line.setVisibility(GONE);
        }
    }

    public void setLineColor(int color) {
        View line = view.findViewById(R.id.view_line);
        line.setBackgroundColor(color);
    }

    public void setUnderlineEnable(boolean enable) {
        View line = view.findViewById(R.id.view_underline);
        if (enable) {
            line.setVisibility(VISIBLE);
        } else {
            line.setVisibility(GONE);
        }
    }

    public void setUnderlineColor(int color) {
        View line = view.findViewById(R.id.view_underline);
        line.setBackgroundColor(color);
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

    public void setTextType(int textType) {
        this.textType = textType;
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
        if (this.textType == 0) {
            manageNormalType(text);
            txtText.setText(this.text);
        } else if (this.textType == 1) {
            manageCurrencyType(text);
            txtText.setText(this.text);
        } else if (this.textType == 2) {
            managePasswordType(text);
            if (isShowingText) {
                txtText.setText(this.text);
            } else {
                txtText.setText(this.passwordText);
            }
        } else {
            this.text = text;
            txtText.setText(this.text);
        }
    }

    public void setUnitText(String priceUnit) {
        AppCompatTextView txtPriceUnit = view.findViewById(R.id.txt_price_unit);
        if (textType == 1) {
            if (priceUnit != null && !priceUnit.isEmpty()) {
                txtPriceUnit.setVisibility(VISIBLE);
                txtPriceUnit.setText(priceUnit);
            } else {
                txtPriceUnit.setVisibility(GONE);
            }
        } else {
            txtPriceUnit.setVisibility(GONE);
        }
    }

    public void setUnitTextColor(int textColor) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_price_unit);
        txtText.setTextColor(textColor);
    }

    public void setUnitTextSize(float textSize) {
        AppCompatTextView txtText = view.findViewById(R.id.txt_price_unit);
        textSize = textSize / getResources().getDisplayMetrics().density;
        txtText.setTextSize(textSize);
    }

    public void setAfterUnitText(String text) {
        AppCompatTextView txtPriceAfterUnit = view.findViewById(R.id.txt_after_unit);
        if (textType == 1) {
            if (text != null && !text.isEmpty()) {
                txtPriceAfterUnit.setVisibility(VISIBLE);
                txtPriceAfterUnit.setText(text);
            } else {
                txtPriceAfterUnit.setVisibility(GONE);
            }
        } else {
            txtPriceAfterUnit.setVisibility(GONE);
        }
    }

    public void setAfterUnitTextColor(int textColor) {
        AppCompatTextView txtPriceAfterUnit = view.findViewById(R.id.txt_after_unit);
        txtPriceAfterUnit.setTextColor(textColor);
    }

    public void setAfterUnitTextSize(float textSize) {
        AppCompatTextView txtPriceAfterUnit = view.findViewById(R.id.txt_after_unit);
        textSize = textSize / getResources().getDisplayMetrics().density;
        txtPriceAfterUnit.setTextSize(textSize);
    }


    private void manageCurrencyType(String text) {
        if (text.isEmpty()) {
            this.text = text;
        } else {
            if (StringUtilities.isNotNumber(text)) {
                this.text = text;
            } else {
                this.text = StringUtilities.getCurrencyFormatter(Double.parseDouble(text));
            }
        }
    }


    private void manageNormalType(String text) {
        this.text = text;
    }


    private void managePasswordType(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            stringBuilder.append(passwordChar);
        }
        this.text = text;
        this.passwordText = stringBuilder.toString();
        manageBtnToggle();
    }

    private void setPasswordToggleEnable(boolean passwordToggleEnable) {
        this.passwordToggleEnable = passwordToggleEnable;
    }

    private void setPasswordChar(String passwordChar) {
        if (passwordChar == null || !passwordChar.isEmpty()) {
            passwordChar = "*";
        }
        this.passwordChar = passwordChar;
    }

    private void manageBtnToggle() {
        AppCompatImageButton btnToggle = view.findViewById(R.id.btn_toggle);
        if (passwordToggleEnable) {
            btnToggle.setVisibility(VISIBLE);
            btnToggle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isShowingText = !isShowingText;
                    checkToggleStatus();
                    setText(DisTextView.this.text);
                }
            });
            checkToggleStatus();
        } else {
            isShowingText = true;
            btnToggle.setVisibility(GONE);
            btnToggle.setOnClickListener(null);
        }
    }

    private void checkToggleStatus() {
        AppCompatImageButton btnToggle = view.findViewById(R.id.btn_toggle);
        if (isShowingText) {
            btnToggle.setImageResource(R.drawable.ic_hide);
        } else {
            btnToggle.setImageResource(R.drawable.ic_show);
        }
    }

}