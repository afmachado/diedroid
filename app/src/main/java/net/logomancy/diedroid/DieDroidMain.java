//DieDroid -- An Android dice-roller app
//Copyright (C) 2010  Andrew Mike <logomancer@gmail.com>
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//For a copy of the GNU GPLv3, see http://www.gnu.org/licenses/gpl-3.0-standalone.html
//or http://www.gnu.org/licenses/translations.html for a translation into your local language.

package net.logomancy.diedroid;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DieDroidMain extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView actList = (ListView) findViewById(R.id.activityList);

        String[] activities = getResources().getStringArray(R.array.activityListChoices);
        actList.setAdapter(new ArrayAdapter<String>(this, R.layout.approw, activities));
        actList.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView a, View v, int position, long id) {
        Intent newActivity = new Intent();

        // here we open activities based on what item was selected
        switch (position) {
            case 0: // Dice Roller
                newActivity.setClassName(this, "net.logomancy.diedroid.RollActivity");
                startActivity(newActivity);
                break;
            case 1: // Ability Score Roller
                newActivity.setClassName(this, "net.logomancy.diedroid.StatsActivity");
                startActivity(newActivity);
                break;
            case 2: // Dice Pool Roller
                newActivity.setClassName(this, "net.logomancy.diedroid.PoolActivity");
                startActivity(newActivity);
                break;
            case 3: // Pass It On
                newActivity.setClassName(this, "net.logomancy.diedroid.PIOActivity");
                startActivity(newActivity);
                break;
            default: //not implemented yet
                Toast.makeText(this, R.string.errorNotImplemented, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuAbout:
                Intent about = new Intent("org.openintents.action.SHOW_ABOUT_DIALOG");
                try {
                    startActivityForResult(about, 0);
                } catch (ActivityNotFoundException e) {
                    AlertDialog.Builder notFoundBuilder = new AlertDialog.Builder(this);
                    notFoundBuilder.setMessage(R.string.aboutNotFoundText)
                            .setTitle(R.string.aboutNotFoundTitle)
                            .setPositiveButton(R.string.commonYes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        Intent getApp = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutMarket)));
                                        startActivity(getApp);
                                    } catch (ActivityNotFoundException e) {
                                        Intent getAppAlt = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutWeb)));
                                        startActivity(getAppAlt);
                                    }
                                }
                            })
                            .setNegativeButton(R.string.commonNo, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog notFound = notFoundBuilder.create();
                    notFound.show();
                }
                return true;
            case R.id.menuHelp:
                Toast.makeText(this, R.string.mainHelpTxt, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}