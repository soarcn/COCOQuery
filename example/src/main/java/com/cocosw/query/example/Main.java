package com.cocosw.query.example;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cocosw.query.CocoQuery;
import com.cocosw.query.CocoTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class Main extends Activity {


    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

    @InjectView(R.id.login_status_message)
    TextView mLoginStatusMessage;
    @InjectView(R.id.login_status)
    LinearLayout mLoginStatus;
    @InjectView(R.id.email)
    EditText mEmail;
    @InjectView(R.id.password)
    EditText mPassword;
    @InjectView(R.id.sign_in_button)
    Button mSignInButton;
    @InjectView(R.id.login_form)
    ScrollView mLoginForm;


    private CocoQuery q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        q = new CocoQuery(this);
        ButterKnife.inject(this);
        q.v(mLoginStatus).gone();
        q.v(mEmail).text("Geek");
        q.v(mPassword).imeAction(R.string.action_sign_in_short, mSignInButton);
        q.id(R.id.sign_in_button).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_forgot_password) {
            q.task(new CocoTask<Void>() {
                @Override
                public Void backgroundWork() throws Exception {
                    Thread.sleep(2000);
                    return null;
                }
            }.dialog(R.string.another_task).cancelable());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_field_required));
            return;
        }

        q.task(new CocoTask<String>() {
            @Override
            public String backgroundWork() throws Exception {
                Thread.sleep(2000);
                return "Hello ";
            }

            @Override
            public void callback(String result) {
                q.toast(result+mEmail.getText());
               // q.startActivity(ListAct.class);
            }

            @Override
            public void pre() {
                q.v(mLoginStatusMessage).text(R.string.login_progress_signing_in);
            }
        }.progress(mLoginStatus).view(mLoginForm));
    }
}
