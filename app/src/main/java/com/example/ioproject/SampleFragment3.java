package com.example.ioproject;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.ioproject.user.Password;
import com.example.ioproject.user.user;
import com.google.rpc.Code;

/**
 * Fragment dla rejestracji
 */
//TODO ZMIENIĆ NAZWĘ FRAGMENTU
public class SampleFragment3 extends Fragment {

    private EditText EmailField, Password1Field, Password2Field, LoginField, CodeField;
    private TextView passwordValidationText, passwordStrengthText;
    private ProgressBar progressBar;
    private ImageView regImgValid, registrationLock;
    private Password firstPassword, secondPassword;
    private Animation fadeIn, fadeOut;
    private CardView registrationValidation, registrationPasswordValidation;
    private boolean didButtonMove = false;


    public SampleFragment3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sample3, container, false);
        Button registerButton = root.findViewById(R.id.button);

        CodeField = root.findViewById(R.id.editSerialCode);
        LoginField = root.findViewById(R.id.editTextTextPersonName);
        EmailField = root.findViewById(R.id.editTextTextPersonName2);
        Password1Field = root.findViewById(R.id.editTextTextPassword);
        Password2Field = root.findViewById(R.id.editTextTextPassword3);
        progressBar = root.findViewById(R.id.progressBar);
        registrationLock = root.findViewById(R.id.registrationImageLock);
        regImgValid = root.findViewById(R.id.registrationImageValidation);
        registrationValidation = root.findViewById(R.id.registration_passwordStrength);
        registrationPasswordValidation = root.findViewById(R.id.registration_passwordValidation);
        passwordStrengthText = root.findViewById(R.id.registration_passwordStrengthText);
        passwordValidationText = root.findViewById(R.id.registration_passwordValidationText);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.register_info_fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.register_info_fade_out);

        // Metody do sprawdzania wpisywanych haseł przez użytkownika
        TextWatcher passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstPassword = new Password(Password1Field.getText().toString());
                secondPassword = new Password(Password2Field.getText().toString());
                String strength = firstPassword.validatePassword();


                if (!firstPassword.getPassword().equals(secondPassword.getPassword())) {
                    userInfoSetup(passwordValidationText, regImgValid, "hasła nie są identyczne",
                            "registration_icon_wrong_passwords");
                } else if (secondPassword.getPassword().length() == 0) {
                    userInfoSetup(passwordValidationText, regImgValid, "Brak hasła",
                            "registration_icon_wrong_passwords");
                } else {
                    userInfoSetup(passwordValidationText, regImgValid, "hasła są identyczne",
                            "registration_icon_matching_passwords");
                }

                if (firstPassword.getPassword().length() > 0) {
                    switch (strength) {
                        case "słabe": {
                            progressBarSetup(33, R.color.red);
                            passwordStrengthText.setText(strength.toUpperCase());
                            if(!didButtonMove){
                                registerButton.animate().translationXBy(1000f).setDuration(500);
                                didButtonMove = true;
                            }
                            break;
                        }
                        case "średnie": {
                            progressBarSetup(66, R.color.orange);
                            passwordStrengthText.setText(strength.toUpperCase());
                            if(didButtonMove){
                                registerButton.animate().translationXBy(-1000f).setDuration(500);
                                didButtonMove = false;
                            }
                            break;
                        }
                        case "silne": {
                            progressBarSetup(100, R.color.green);
                            passwordStrengthText.setText(strength.toUpperCase());
                            break;
                        }
                    }
                } else {
                    progressBar.setProgress(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        Password2Field.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                animationChange(registrationPasswordValidation, fadeOut, View.GONE);
            } else {
                animationChange(registrationPasswordValidation, fadeIn, View.VISIBLE);
            }
        });

        Password1Field.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                animationChange(registrationValidation, fadeOut, View.GONE);
            } else {
                animationChange(registrationValidation, fadeIn, View.VISIBLE);
            }
        });

        Password1Field.addTextChangedListener(passwordWatcher);
        Password2Field.addTextChangedListener(passwordWatcher);


        registerButton.setOnClickListener(view -> {
            String email = EmailField.getText().toString();
            String password = Password1Field.getText().toString();
            String login = LoginField.getText().toString();
            String code = CodeField.getText().toString();
            user user = new user();
            user.registerUser(email, password,login,code, getActivity());
        });
        return root;
    }

    @SuppressLint("DiscouragedApi")
    private void userInfoSetup(TextView textView, ImageView imageView, String info, String imageName) {
        textView.setText(info.toUpperCase());
        imageView.setImageResource(getResources().getIdentifier(
                imageName, "drawable", requireActivity().getPackageName()));
    }

    /**
     * Metoda do zmiany stanu paska progresu
     * @param progressValue wartość progresu na pasku
     * @param colorResourceID kolor paska progresu
     */
    private void progressBarSetup(int progressValue, int colorResourceID) {
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(colorResourceID));
        progressBar.setProgress(progressValue);
        progressBar.setProgressTintList(colorStateList);
        registrationLock.setImageTintList(colorStateList);
    }

    /**
     * Metoda do zmiany animacji komponentu
     * @param cardView komponent
     * @param animation animacja
     * @param setVisibility ustawienie widzialności
     */
    private void animationChange(CardView cardView, Animation animation, int setVisibility) {
        cardView.startAnimation(animation);
        cardView.setVisibility(setVisibility);
    }

}