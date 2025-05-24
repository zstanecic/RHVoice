/* Copyright (C) 2014  Olga Yakovleva <yakovleva.o.v@gmail.com> */

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

import java.io.File;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public final class Config {
    private static final String TAG = "RHVoiceConfig";
    private static final String CONFIG_FILE_NAME = "RHVoice.conf";

    public static File getDir(Context context, boolean requireDeviceProtected) {
        Context ctx = context;
        if (requireDeviceProtected && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ctx = context.createDeviceProtectedStorageContext();
        }
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Requesting path to the private external storage directory");
        File dir = ctx.getExternalFilesDir(null);
        if (dir == null) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "The private external storage directory does not exist");
            return ctx.getDir("config", 0);
        }
        if (BuildConfig.DEBUG)
            Log.d(TAG, "The path to the private external storage directory is " + dir.getAbsolutePath());
        return dir;
    }

    public static File getDictsRootDir(Context ctx, boolean requireDeviceProtected) {
        return new File(getDir(ctx, requireDeviceProtected), "dicts");
    }

    public static File getLangDictsDir(Context ctx, String langName, boolean requireDeviceProtected) {
        return new File(getDictsRootDir(ctx, requireDeviceProtected), langName);
    }

    public static File getConfigFile(Context ctx, boolean requireDeviceProtected) {
        return new File(getDir(ctx, requireDeviceProtected), CONFIG_FILE_NAME);
    }
}