package com.tutorial.datastore;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;
import org.reactivestreams.Subscription;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/*
    dependencies {
        ...
        // DataStore
        implementation 'androidx.datastore:datastore-preferences-rxjava2:1.0.0'
        implementation "androidx.datastore:datastore-rxjava2:1.0.0"
    }
 */

public class DataStoreManager {
    private final RxDataStore<Preferences> dataStore;

    public DataStoreManager(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "NAME").build();
    }

    public  <T> void putData(String KEY, T VALUE) {
        Preferences.Key<T> key = new Preferences.Key<>(KEY);
        Single<Preferences> updateData =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            // 第一次建立KEY，一定會回傳NULL
            if (prefsIn.get(key) == null)
                putData(KEY, VALUE);
            mutablePreferences.set(key, VALUE);
            return Single.just(mutablePreferences);
        });
        updateData.subscribe();
    }

    public <T> T getData(String KEY, T defaultValue) {
        Preferences.Key<T> key = new Preferences.Key<>(KEY);
        Flowable<T> flowable = dataStore.data().map(preferences ->
                preferences.get(key) != null ? preferences.get(key) : defaultValue);
        return flowable.blockingFirst();
    }

    public void clear() {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.clear();
            return Single.just(mutablePreferences);
        }).subscribe();
    }
}
