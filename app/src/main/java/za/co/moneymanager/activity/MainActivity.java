package za.co.moneymanager.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import za.co.moneymanager.R;
import za.co.moneymanager.model.SmsDao;
import za.co.moneymanager.service.SmsMessagingService;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    ProgressDialog progressDialog;
    View.OnClickListener linearLoutClickListener =
    new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView textView1 = (TextView) v.findViewById(R.id.new_sms_description);
            Toast.makeText(v.getContext(), textView1.getText(), Toast.LENGTH_LONG).show();
        }
    };

    LinearLayout mainContentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.main_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mainContentLayout = (LinearLayout) findViewById(R.id.content_body);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        final TextView textView = new TextView(this);

        if (menuItem.getItemId() == R.id.navigation_item_home){
            drawerLayout.closeDrawer(GravityCompat.START);
            textView.setText("HOME");
            mainContentLayout.addView(textView);
        }

        if (menuItem.getItemId() == R.id.navigation_item_expense){
            drawerLayout.closeDrawer(GravityCompat.START);
            textView.setText("EXPENSE");
            mainContentLayout.addView(textView);
        }

        if (menuItem.getItemId() == R.id.navigation_item_income){
            drawerLayout.closeDrawer(GravityCompat.START);
            textView.setText("INCOME");
            mainContentLayout.addView(textView);
        }

        if (menuItem.getItemId() == R.id.navigation_item_budget){
            drawerLayout.closeDrawer(GravityCompat.START);
            textView.setText("BUDGET");
            mainContentLayout.addView(textView);
        }

        if (menuItem.getItemId() == R.id.navigation_item_sync_inbox) {
            drawerLayout.closeDrawer(GravityCompat.START);
            MyTask myTask = new MyTask();
            myTask.execute();
        }
        if (menuItem.getItemId() == R.id.navigation_item_logout){
            drawerLayout.closeDrawer(GravityCompat.START);
            textView.setText("LOGOUT");
            mainContentLayout.addView(textView);

        }

        return true;
    }

    class MyTask extends AsyncTask<Void, SmsDao, Void> {

//        LinearLayout frameLayout = (LinearLayout) findViewById(R.id.content_body);
        Collection<SmsDao> smsDaoCollection = new ArrayList<SmsDao>();
        int maxCount = 0;
        int currentCount = 0;

        @Override
        protected void onPreExecute() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle(R.string.import_dialog_title);
            progressDialog.setMessage(getString(R.string.import_dialog_message));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            smsDaoCollection = SmsMessagingService.readSmsInbox(MainActivity.this);
            maxCount = smsDaoCollection.size();
        }

        @Override
        protected Void doInBackground(Void[] params) {

            for (SmsDao smsDao : smsDaoCollection) {
                currentCount++;
                publishProgress(smsDao);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(SmsDao... values) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            progressDialog.setMessage(getString(R.string.import_dialog_message) + currentCount + " of " + maxCount);
            RelativeLayout linearLayout = (RelativeLayout) MainActivity.this.getLayoutInflater().inflate(R.layout.new_sms_layout, null);
            linearLayout.setId(Integer.parseInt(values[0].getId()));
            linearLayout.setOnClickListener(linearLoutClickListener);
            TextView textViewDate = (TextView) linearLayout.findViewById(R.id.new_sms_date);
            TextView textViewDescription = (TextView) linearLayout.findViewById(R.id.new_sms_description);
            TextView textViewVendor = (TextView) linearLayout.findViewById(R.id.new_sms_vendor);
            TextView textViewAmount = (TextView) linearLayout.findViewById(R.id.new_sms_amount);
            textViewDate.setText(values[0].getId() + "  ==  " + values[0].getId());
            textViewDescription.setText(values[0].getId() + "  ==  " + values[0].getBody());
            textViewVendor.setText("Vendor Name");
            textViewAmount.setText("R1000");

            mainContentLayout.addView(linearLayout);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }
}
