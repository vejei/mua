package io.github.zeleven.mua;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import butterknife.BindView;

public class FileListFragment extends BaseFragment {
    private FrameLayout fabMenuContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filelist;
    }

    @Override
    public int getTitleResId() {
        return R.string.app_name;
    }

    @Override
    public void initView() {
        setDisplayHomeAsUpEnabled = false;
        super.initView();
        setFABMenu();
        setHasOptionsMenu(true);
        setDrawerToggle();
        setNavigationViewItemListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        fabMenuContainer.setVisibility(View.GONE);
    }

    /**
     * Set visibility to VISIBLE for floating action button menu, and set listener for the menu.
     * If the button menu expanded, change background color for menu container.
     * Otherwise, change background to transparent.
     */
    public void setFABMenu() {
        fabMenuContainer = (FrameLayout) context
                .findViewById(R.id.fab_menu_container);
        fabMenuContainer.setVisibility(View.VISIBLE);
        FloatingActionsMenu fabMenu = (FloatingActionsMenu) context
                .findViewById(R.id.fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(
                new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fabMenuContainer.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.white));
            }

            @Override
            public void onMenuCollapsed() {
                fabMenuContainer.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }

    /**
     * Set toggle for drawer in toolbar.
     */
    public void setDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                context, drawerLayout, toolbar, R.string.toggle_drawer_open,
                R.string.toggle_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Set item listener for navigation view.Open new fragment for each click action.
     */
    public void setNavigationViewItemListener() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.sync:
                        selectedFragment = new SyncFragment();
                        break;
                    case R.id.word_cloud:
                        selectedFragment = new WordCloudFragment();
                        break;
                    case R.id.theme_switch:
                        selectedFragment = new ThemeFragment();
                        break;
                    case R.id.help:
                        selectedFragment = new HelpFragment();
                        break;
                    case R.id.settings:
                        context.getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new SettingsFragment())
                                .commit();
                        break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                navigationView.setCheckedItem(item.getItemId());
                if (selectedFragment != null) {
                    context.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null).commit();
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(context.getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        switch (item.getItemId()) {
            case R.id.sort:
                // open sort options dialog
                builder.setTitle(R.string.toolbar_menu_sort);
                builder.setSingleChoiceItems(R.array.sort_options, 0,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(context, "" + 0, Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(context, "" + 1, Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(context, "" + 2, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
            case R.id.show_options:
                // open show options settings dialog
                final ArrayList selectedItems = new ArrayList();
                builder.setTitle(R.string.toolbar_menu_show_option);
                boolean[] checkedItems = {true, true};
                builder.setMultiChoiceItems(R.array.show_options, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    selectedItems.remove(which);
                                }
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something here
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
