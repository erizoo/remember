package com.remember.app.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.AutoCompleteTextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.remember.app.R;
import com.remember.app.data.models.ResponseAuth;
import com.remember.app.ui.cabinet.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AuthActivity extends MvpAppCompatActivity implements AuthView {

    @InjectPresenter
    AuthPresenter presenter;

    @BindView(R.id.login_value)
    AutoCompleteTextView login;
    @BindView(R.id.password_value)
    AutoCompleteTextView password;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_btn)
    public void signIn() {
        if (login.getText().toString().equals("")){
            Snackbar.make(password, "Введите e-mail", Snackbar.LENGTH_LONG).show();
        } else if (password.getText().toString().equals("")){
            Snackbar.make(password, "Введите пароль", Snackbar.LENGTH_LONG).show();
        } else {
            presenter.singInAuth(login.getText().toString(), password.getText().toString());
        }
    }

    @OnClick(R.id.register)
    public void moveRegisterPage() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onLogged(ResponseAuth responseAuth) {
        if (responseAuth != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void error(Throwable throwable) {
        Snackbar.make(password, "Неправильный логин или пароль", Snackbar.LENGTH_LONG).show();
    }
}
