package com.team19.cs2340;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team19.cs2340.user.IUser;
import com.team19.cs2340.user.IUserAccountService;
import com.team19.cs2340.user.UserAccountException;
import com.team19.cs2340.user.UserAccountServiceFactory;

public class PasswordChangeActivity extends Activity {
    IUserAccountService uas;
    IUser user;
    IUser admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        // Show the Up button in the action bar.
        setupActionBar();
        
        uas = UserAccountServiceFactory.createUserAccountService(this);
        
        user = (IUser)getIntent().getSerializableExtra("user");
        admin = (IUser)getIntent().getSerializableExtra("admin");
        
        TextView changePasswordText = (TextView)findViewById(R.id.change_password_text);
        changePasswordText.setText(changePasswordText.getText() + user.getUsername() + ":");
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.password_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, TransactionListActivity.class);
            intent.putExtra("admin", admin);
            intent.putExtra("user", user);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void changePassword(View view) {
        EditText passwordInput = (EditText)findViewById(R.id.passwordInput);
        EditText confirmPasswordInput = (EditText)findViewById(R.id.confirmPassword);
        
        TextView errorMessage = (TextView)findViewById(R.id.error_message);
        
        String password = passwordInput.getEditableText().toString();
        String confirmPassword = confirmPasswordInput.getEditableText().toString();

        try {
            if (!password.equals(confirmPassword)) {
                throw new UserAccountException("Password and confirmation must match");
            }
            uas.changePassword(admin, user, password);
            
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AdminScreenActivity.class);
            intent.putExtra("admin", admin);
            intent.putExtra("user", user);
            NavUtils.navigateUpTo(this, intent);
        } catch (UserAccountException e) {
            errorMessage.setText(e.getMessage());
        }
    }
    
    public void cancelChangePassword(View view) {
        Intent intent = new Intent(this, AdminScreenActivity.class);
        intent.putExtra("admin", admin);
        intent.putExtra("user", user);
        NavUtils.navigateUpTo(this, intent);
    }

}
