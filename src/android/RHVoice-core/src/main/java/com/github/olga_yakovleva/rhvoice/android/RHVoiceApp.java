/* Copyright (C) 2025  Darko Milosevic <daremc86@gmail.com> */

/* This program is free software: you can redistribute it and/or modify */
/* it under the terms of the GNU Lesser General Public License as published by */
/* the Free Software Foundation, either version 2.1 of the License, or */
/* (at your option) any later version. */

/* This program is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the */
/* GNU Lesser General Public License for more details. */

/* You should have received a copy of the GNU Lesser General Public License */
/* along with this program.  If not, see <https://www.gnu.org/licenses/>. */


package com.github.olga_yakovleva.rhvoice.android;

import android.app.Application;
import android.content.Context;
import android.os.Build;

public class RHVoiceApp extends Application {

    private static Context storageContext;

    public void onCreate() {
        super.onCreate();
        Context appContext = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            RHVoiceApp.storageContext = appContext.createDeviceProtectedStorageContext();
        }
        else {
            RHVoiceApp.storageContext = appContext;
        }
    }

    public static Context getStorageContext() {
        return RHVoiceApp.storageContext;
    }
}
