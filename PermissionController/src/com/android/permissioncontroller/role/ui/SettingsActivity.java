/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.permissioncontroller.role.ui;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.android.permissioncontroller.DeviceUtils;
import com.android.settingslib.collapsingtoolbar.SettingsTransitionActivity;
import android.database.ContentObserver;
import android.os.Handler;
import android.net.Uri;
import android.util.Log;

/**
 * Base class for settings activities.
 */
// Made public for com.android.permissioncontroller.role.ui.specialappaccess
public class SettingsActivity extends SettingsTransitionActivity {
     private boolean isTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addSystemFlags(
                WindowManager.LayoutParams.SYSTEM_FLAG_HIDE_NON_SYSTEM_OVERLAY_WINDOWS);

        Log.d("bella", "SettingsActivity role onCreate......");
        getContentResolver().registerContentObserver(
                android.provider.Settings.System.getUriFor("KEY_TIME"),
                true, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange, Uri uri) {
                        String keyTime =   android.provider.Settings.System.getString(
                                getContentResolver(),
                                "KEY_TIME");
                        String pkgName = getPackageName() ;    
                        Log.d("bella", "SettingsActivity role onChange...... isTop "+isTop + ",keyTime "+keyTime + ",pkgName "+pkgName);    
                        if(keyTime != null && keyTime.contains(pkgName) ){
                              onBackPressed();
                        }
                    }
            });        
    }

    @Override
    protected boolean isSettingsTransitionEnabled() {
        return super.isSettingsTransitionEnabled() && !(DeviceUtils.isAuto(this)
                || DeviceUtils.isTelevision(this) || DeviceUtils.isWear(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }
}
