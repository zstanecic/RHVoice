/* Copyright (C) 2021  Olga Yakovleva <olga@rhvoice.org> */

/* This program is free software: you can redistribute it and/or modify */
/* it under the terms of the GNU Lesser General Public License as published by */
/* the Free Software Foundation, either version 3 of the License, or */
/* (at your option) any later version. */

/* This program is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the */
/* GNU Lesser General Public License for more details. */

/* You should have received a copy of the GNU Lesser General Public License */
/* along with this program.  If not, see <https://www.gnu.org/licenses/>. */


package com.github.olga_yakovleva.rhvoice.android;

import android.content.Context;

import java.io.File;

final class PackageClient {
    // Credential protected storage (default, kao do sada)
    public static String getPath(Context context) {
        File dir = context.getDir("packages", 0);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static String getDeviceProtectedPath(Context context) {
        Context deviceContext = context.createDeviceProtectedStorageContext();
        File dir = deviceContext.getDir("packages", 0);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
}