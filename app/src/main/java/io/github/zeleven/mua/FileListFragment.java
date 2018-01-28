package io.github.zeleven.mua;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class FileListFragment extends BaseFragment {
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.file_list) RecyclerView fileListRecyclerView;
    @BindView(R.id.create_markdown_btn) FloatingActionButton createMarkdownBtn;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.empty_list) RelativeLayout emptyList;

    @BindString(R.string.app_name) String appName;
    private String root = Environment.getExternalStorageDirectory().toString();
    private String rootPath;

    private FilesAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filelist;
    }

    @Override
    public void initView() {
        toolbarTitle = appName;
        setDisplayHomeAsUpEnabled = false;
        super.initView();
        initVar(); // init variable
        setFab(); // set floating action button
        setHasOptionsMenu(true); // set has options menu
        setDrawerToggle(); // set toggle for drawerlayout
        setNavigationViewItemListener(); // set navigation view item listener
        setRecyclerView(); // set recyclerview
        setSwipeRefreshLayout(); // set swipe refresh layout
    }

    public void initVar() {
        rootPath = root + "/" + appName + "/";
    }

    /**
     *  Set visibility to VISIBLE for floating action button menu, and set listener for the menu.
     *  If the button menu expanded, change background color for menu container.
     *  Otherwise, change background to transparent.
     */
    public void setFab() {
        final Fragment fragment = new EditorFragment();
        Bundle args = new Bundle();
        args.putBoolean("FROM_FILE", false);
        fragment.setArguments(args);
        createMarkdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    /**
     * Set toggle for drawer in toolbar.
     */
    public void setDrawerToggle() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(context, drawerLayout,
                toolbar, R.string.toggle_drawer_open, R.string.toggle_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    /**
     * Set item listener for navigation view.Open new fragment for each click action.
     */
    public void setNavigationViewItemListener() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.sync:
                        selectedFragment = new SyncFragment();
                        break;
                    case R.id.word_cloud:
                        selectedFragment = new WordCloudFragment();
                        break;
                    case R.id.help:
                        selectedFragment = new HelpFragment();
                        break;
                    case R.id.settings:
                        selectedFragment = new SettingsFragment();
                        break;
                }
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

    public void setRecyclerView() {
        List<File> fileList = null;
        if (StorageHelper.isExternalStorageReadable()) {
            fileList = FileUtils.listFiles(rootPath);
            if (fileList.isEmpty()) {
                emptyList.setVisibility(View.VISIBLE);
            } else {
                emptyList.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(context, R.string.toast_message_sdcard_unavailable,
                    Toast.LENGTH_SHORT).show();
        }
        adapter = new FilesAdapter(fileList);
        fileListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        fileListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fileListRecyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        fileListRecyclerView.setAdapter(adapter);
    }

    public void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                adapter = new FilesAdapter(FileUtils.listFiles(rootPath));
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.filelist_fragment_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) context.getSystemService
                (Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(context.getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                AlertDialog.Builder sortDialog = new AlertDialog.Builder(context);
                sortDialog.setTitle(R.string.menu_item_sort);
                sortDialog.setSingleChoiceItems(R.array.sort_options, 0,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                sortDialog.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                sortDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // sort here
                    }
                });
                sortDialog.show();
                break;
            case R.id.display_options:
                AlertDialog.Builder displayOptions = new AlertDialog.Builder(context);
                displayOptions.setTitle(R.string.menu_item_show_option);
                boolean checkedItems[] = {true, false};
                final ArrayList<Integer> selected = new ArrayList<>();
                displayOptions.setMultiChoiceItems(R.array.show_options, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selected.add(which);
                        } else if (selected.contains(which)) {
                            selected.remove(which);
                        }
                    }
                });
                displayOptions.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                displayOptions.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        for (int i = 0; i < selected.size(); i++) {
//
//                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                displayOptions.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
