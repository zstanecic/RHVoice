/* Copyright (C) 2017, 2019  Olga Yakovleva <yakovleva.o.v@gmail.com> */

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

interface IDataSyncCallback {
    public boolean isConnected();

    public void onLanguageDownloadStart(LanguagePack language);

    public void onLanguageDownloadDone(LanguagePack language);

    public void onLanguageInstallation(LanguagePack language);

    public void onLanguageRemoval(LanguagePack language);

    public void onVoiceDownloadStart(VoicePack voice);

    public void onVoiceDownloadDone(VoicePack voice);

    public void onVoiceInstallation(VoicePack voice);

    public void onVoiceRemoval(VoicePack voice);

    public boolean isTaskStopped();
}
