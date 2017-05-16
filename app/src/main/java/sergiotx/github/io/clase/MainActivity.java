package sergiotx.github.io.clase;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import sergiotx.github.io.clase.Fragments.Fragment_Assistance;
import sergiotx.github.io.clase.Fragments.Fragment_Calendar;
import sergiotx.github.io.clase.Fragments.Fragment_Exams;
import sergiotx.github.io.clase.Fragments.Fragment_Subjects;
import sergiotx.github.io.clase.Fragments.Fragment_Tasks;
import sergiotx.github.io.clase.Fragments.Fragment_Timetable;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburguer_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default fragment (probably not the best way) //FIXME
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new Fragment_Timetable())
                .commit();

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        boolean setchecked = false;
                        switch (menuItem.getItemId()) {
                            /*case R.id.navigation_assistance:
                                fragment = new Fragment_Assistance();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;*/
                            case R.id.navigation_tasks:
                                fragment = new Fragment_Tasks();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;
                            case R.id.navigation_timetable:
                                fragment = new Fragment_Timetable();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;
                            /*case R.id.navigation_exams:
                                fragment = new Fragment_Exams();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;
                            case R.id.navigation_calendar:
                                fragment = new Fragment_Calendar();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;*/
                            case R.id.navigation_subjects:
                                fragment = new Fragment_Subjects();
                                fragmentTransaction = true;
                                setchecked = true;
                                break;
                            /*case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");//TODO
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");//TODO
                                break;*/
                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack("tag") //to handle back button http://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
                                    .commit();

                            menuItem.setChecked(setchecked);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
