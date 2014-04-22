package com.team19.cs2340;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.team19.cs2340.user.IUser;
import com.team19.cs2340.user.IUserAccountService;
import com.team19.cs2340.user.UserAccountException;
import com.team19.cs2340.user.UserAccountServiceFactory;

public class AdminScreenActivity extends Activity {
    IUserAccountService uas;
    IUser admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);
        // Show the Up button in the action bar.
        setupActionBar();
        
        admin = (IUser)getIntent().getSerializableExtra("admin");
        uas = UserAccountServiceFactory.createUserAccountService(this);
        
        try {
            final List<IUser> users = uas.getUsers(admin);

            List<String> usernames = new ArrayList<String>();
            for (IUser u : users) usernames.add(u.getUsername());

            ListView userList = (ListView)findViewById(R.id.user_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
            
            userList.setAdapter(adapter);
            
            userList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    Intent intent = new Intent(AdminScreenActivity.this, PasswordChangeActivity.class);
                    intent.putExtra("admin", admin);
                    intent.putExtra("user", users.get(position));
                    startActivity(intent);
                }
                
            });
        } catch (UserAccountException e) {
            e.printStackTrace();
        }
        
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
//        getMenuInflater().inflate(R.menu.admin_screen, menu);
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
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
