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

package com.android.permissioncontroller.permission.ui;

import com.android.permissioncontroller.DeviceUtils;
import com.android.settingslib.collapsingtoolbar.SettingsTransitionActivity;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;


/**
 * Parent activity that supports transitions
 */
public class SettingsActivity extends SettingsTransitionActivity {
     private boolean isTop;

    @Override
    protected boolean isSettingsTransitionEnabled() {
        return super.isSettingsTransitionEnabled() && !(DeviceUtils.isAuto(this)
                || DeviceUtils.isTelevision(this) || DeviceUtils.isWear(this));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("bella", "SettingsActivity onCreate......");
        getContentResolver().registerContentObserver(
                Settings.System.getUriFor("BACK_KEY_TIME"),
                true, new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange, Uri uri) {
                        Log.w("SettingsActivity", "BACK_KEY_TIME changed, isTop: " + isTop);
                        if(isTop){
                            onBackPressed();
                        }
                    }
                });

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
