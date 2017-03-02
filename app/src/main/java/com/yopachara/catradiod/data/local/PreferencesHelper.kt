package com.yopachara.catradiod.data.local

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.injection.ApplicationContext

import java.util.Calendar
import java.util.GregorianCalendar
import java.util.HashMap
import java.util.HashSet

import javax.inject.Inject
import javax.inject.Singleton

import rx.Observable
import rx.Subscriber
import rx.lang.kotlin.subscriber
import timber.log.Timber
import java.lang.reflect.Type


@Singleton
class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {
    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

    fun saveDjSchedules(djSchedule: DjSchedule): Observable<DjSchedule> {
        return Observable.create(Observable.OnSubscribe<DjSchedule> { subscriber ->
            try {
                editor.putString("dj", convertToGson(djSchedule))
                editor.apply()
                subscriber.onNext(djSchedule)
                subscriber.onCompleted()
            } catch (e: Exception) {
                Timber.e(e)
                subscriber.onError(e)
            }

        })

    }

    fun getDjSchedules(): Observable<DjSchedule> {
        return Observable.create(Observable.OnSubscribe { subscriber ->
            try {
                val dj: DjSchedule = getDjSchedule()
                subscriber.onNext(dj)
                subscriber.onCompleted()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        })
    }

    fun getDjSchedule(): DjSchedule {
        val defValue: String = Gson().toJson(DjSchedule());
        val storedHashMapString: String = sp.getString("dj", defValue);
        val turnsType = genericType<DjSchedule>()
        val dj: DjSchedule = Gson().fromJson<DjSchedule>(storedHashMapString, turnsType)
        return dj
    }

    //    public Observable<Settings> saveSettingsAsObserve(Settings setting) {
//        return Observable.create(new Observable.OnSubscribe<Settings>() {
//            @Override
//            public void call(Subscriber<? super Settings> subscriber) {
//                if (subscriber.isUnsubscribed()) return;
//                try {
//                    if (setting != null) {
//                        editor.putInt(Constant.STANDARD_OT, setting.getStandardOt());
//                        editor.putInt(Constant.MEAL_BREAK, setting.getMealBreak());
//                        editor.putInt(Constant.STANDARD_HRS, setting.getStandardHrs());
//                        editor.putInt(Constant.STANDARD_HRS_PER_WEEK, setting.getStandardHrsPerWeek());
//                        editor.putInt(Constant.WEEK_ENDS, setting.getWeekEnds());
//                        editor.putInt(Constant.WEEK_STARTS, setting.getWeekStarts());
//                        editor.putString(Constant.PUBLIC_HOLIDAYS, convertToGson(setting.getPublicHolidayList()));
//                        editor.apply();
//                        subscriber.onNext(setting);
//                        subscriber.onCompleted();
//                    }
//                } catch (Exception e) {
//                    Timber.e(e);
//                }
//            }
//        });
//
//    }
    fun convertToGson(djSchedule: DjSchedule): String {
        return Gson().toJson(djSchedule)
    }
//    public boolean saveUser(User user) {
//        try {
//            if (user != null) {
//                editor.putLong(Constant.CREATE_AT, user.getCreated_at());
//                editor.putString(Constant.DIVISION_ID, user.getDivision_id());
//                editor.putString(Constant.EMAIL, user.getEmail());
//                editor.putString(Constant.FIRST_NAME, user.getFirst_name());
//                editor.putString(Constant.ID_NUMBER, user.getId_number());
//                editor.putBoolean(Constant.IS_ACTIVE, user.isIsActive());
//                editor.putString(Constant.LAST_NAME, user.getLast_name());
//                editor.putString(Constant.PHONE, user.getPhone());
//                editor.putString(Constant.ROLE_ID, user.getRole_id());
//                editor.putString(Constant.UID, user.getUid());
//                editor.putLong(Constant.UPDATED_AT, user.getUpdated_at());
//                editor.putString(Constant.USER_TYPE, user.getUser_type());
//                if (user.getProfile_img() != null) {
//                    ProfileImage profileImage = new ProfileImage();
//                    profileImage.setName(user.getProfile_img().getName());
//                    profileImage.setPath(user.getProfile_img().getPath());
//                    profileImage.setSrc(user.getProfile_img().getSrc());
//                    Gson gson = new Gson();
//                    String json = gson.toJson(profileImage);
//                    editor.putString("ProfileImage", json);
//                    editor.commit();
//                }
//                editor.apply();
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public Observable<User> saveUserObserve(User user) {
//        return Observable.create(new Observable.OnSubscribe<User>() {
//            @Override
//            public void call(Subscriber<? super User> subscriber) {
//                if (subscriber.isUnsubscribed()) return;
//                try {
//                    if (user != null) {
//                        editor.putLong(Constant.CREATE_AT, user.getCreated_at());
//                        editor.putString(Constant.DIVISION_ID, user.getDivision_id());
//                        editor.putString(Constant.EMAIL, user.getEmail());
//                        editor.putString(Constant.FIRST_NAME, user.getFirst_name());
//                        editor.putString(Constant.ID_NUMBER, user.getId_number());
//                        editor.putBoolean(Constant.IS_ACTIVE, user.isIsActive());
//                        editor.putString(Constant.LAST_NAME, user.getLast_name());
//                        editor.putString(Constant.PHONE, user.getPhone());
//                        editor.putString(Constant.ROLE_ID, user.getRole_id());
//                        editor.putString(Constant.UID, user.getUid());
//                        editor.putLong(Constant.UPDATED_AT, user.getUpdated_at());
//                        editor.putString(Constant.USER_TYPE, user.getUser_type());
//                        if (user.getProfile_img() != null) {
//                            ProfileImage profileImage = new ProfileImage();
//                            profileImage.setName(user.getProfile_img().getName());
//                            profileImage.setPath(user.getProfile_img().getPath());
//                            profileImage.setSrc(user.getProfile_img().getSrc());
//                            Gson gson = new Gson();
//                            String json = gson.toJson(profileImage);
//                            editor.putString("ProfileImage", json);
//                        }
//                        editor.apply();
//                        subscriber.onNext(user);
//                        subscriber.onCompleted();
//                    }
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                    Timber.e(e);
//                }
//            }
//        });
//
//    }
//
//    public User getUser() {
//        return new User(
//                getString(Constant.UID),
//                getString(Constant.DIVISION_ID),
//                getString(Constant.EMAIL),
//                getString(Constant.FIRST_NAME),
//                getString(Constant.LAST_NAME),
//                getString(Constant.PHONE),
//                getProfileImage(),
//                getBoolean(Constant.IS_ACTIVE),
//                getString(Constant.ROLE_ID),
//                getLong(Constant.UPDATED_AT),
//                getLong(Constant.CREATE_AT),
//                getString(Constant.USER_TYPE),
//                getString(Constant.ID_NUMBER)
//        );
//    }
//
//    public ProfileImage getProfileImage() {
//        if (getString("ProfileImage", "") != null) {
//            Gson gson = new Gson();
//            String json = getString("ProfileImage", "");
//            return gson.fromJson(json, ProfileImage.class);
//        } else {
//            return null;
//        }
//    }
//
//    public Settings getSettings() {
//        return new Settings(
//                getInt(Constant.STANDARD_OT),
//                getInt(Constant.MEAL_BREAK),
//                getInt(Constant.STANDARD_HRS),
//                getInt(Constant.STANDARD_HRS_PER_WEEK),
//                getInt(Constant.WEEK_ENDS),
//                getInt(Constant.WEEK_STARTS),
//                getPublicHolidays()
//        );
//    }
//
//    public Observable<User> getUserAsObserve() {
//        return Observable.create(subscriber -> {
//            User user = new User(
//                    getString(Constant.UID),
//                    getString(Constant.DIVISION_ID),
//                    getString(Constant.EMAIL),
//                    getString(Constant.FIRST_NAME),
//                    getString(Constant.LAST_NAME),
//                    getString(Constant.PHONE),
//                    getProfileImage(),
//                    getBoolean(Constant.IS_ACTIVE),
//                    getString(Constant.ROLE_ID),
//                    getLong(Constant.UPDATED_AT),
//                    getLong(Constant.CREATE_AT),
//                    getString(Constant.USER_TYPE),
//                    getString(Constant.ID_NUMBER)
//            );
//            subscriber.onNext(user);
//            subscriber.onCompleted();
//        });
//    }
//
//    public Observable<Settings> getSettingsAsObserve() {
//        return Observable.create(subscriber -> {
//            Settings settings = new Settings(
//                    getInt(Constant.STANDARD_OT),
//                    getInt(Constant.MEAL_BREAK),
//                    getInt(Constant.STANDARD_HRS),
//                    getInt(Constant.STANDARD_HRS_PER_WEEK),
//                    getInt(Constant.WEEK_ENDS),
//                    getInt(Constant.WEEK_STARTS),
//                    getPublicHolidays()
//            );
//            subscriber.onNext(settings);
//            subscriber.onCompleted();
//        });
//
//    }
//
//
//    public Observable<Settings> saveSettingsAsObserve(Settings setting) {
//        return Observable.create(new Observable.OnSubscribe<Settings>() {
//            @Override
//            public void call(Subscriber<? super Settings> subscriber) {
//                if (subscriber.isUnsubscribed()) return;
//                try {
//                    if (setting != null) {
//                        editor.putInt(Constant.STANDARD_OT, setting.getStandardOt());
//                        editor.putInt(Constant.MEAL_BREAK, setting.getMealBreak());
//                        editor.putInt(Constant.STANDARD_HRS, setting.getStandardHrs());
//                        editor.putInt(Constant.STANDARD_HRS_PER_WEEK, setting.getStandardHrsPerWeek());
//                        editor.putInt(Constant.WEEK_ENDS, setting.getWeekEnds());
//                        editor.putInt(Constant.WEEK_STARTS, setting.getWeekStarts());
//                        editor.putString(Constant.PUBLIC_HOLIDAYS, convertToGson(setting.getPublicHolidayList()));
//                        editor.apply();
//                        subscriber.onNext(setting);
//                        subscriber.onCompleted();
//                    }
//                } catch (Exception e) {
//                    Timber.e(e);
//                }
//            }
//        });
//
//    }
//
//    public boolean saveSettings(Settings setting) {
//        try {
//            if (setting != null) {
//                editor.putInt(Constant.STANDARD_OT, setting.getStandardOt());
//                editor.putInt(Constant.MEAL_BREAK, setting.getMealBreak());
//                editor.putInt(Constant.STANDARD_HRS, setting.getStandardHrs());
//                editor.putInt(Constant.STANDARD_HRS_PER_WEEK, setting.getStandardHrsPerWeek());
//                editor.putInt(Constant.WEEK_ENDS, setting.getWeekEnds());
//                editor.putInt(Constant.WEEK_STARTS, setting.getWeekStarts());
//                editor.putString(Constant.PUBLIC_HOLIDAYS, convertToGson(setting.getPublicHolidayList()));
//                editor.apply();
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            Timber.e(e);
//            return false;
//        }
//    }
//
//

//
//    public HashMap<String, List<PublicHoliday>> getPublicHolidays() {
//        String defValue = new Gson().toJson(new HashMap<String, List<PublicHoliday>>());
//        String storedHashMapString = mPref.getString(Constant.PUBLIC_HOLIDAYS, defValue);
//        java.lang.reflect.Type type = new TypeToken<HashMap<String, List<PublicHoliday>>>() {
//        }.getType();
//        return new Gson().fromJson(storedHashMapString, type);
//    }
//
//    public boolean isPublicHoliday(long date) {
//        Calendar cal = GregorianCalendar.getInstance();
//        cal.setTimeInMillis(date);
//        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//            return true;
//        }
//        List<PublicHoliday> list = getPublicHolidays().get(getYear());
//        Timber.d(list.toString());
//        return list.contains(new PublicHoliday(getToday(date), ""));
//    }


    val sp: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sp = context.getSharedPreferences(USER, Context.MODE_PRIVATE)
        editor = sp.edit()
        editor.apply()
    }

    fun clear() {
        sp.edit().clear().apply()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    @JvmOverloads fun getString(key: String, defaultValue: String? = null): String {
        return sp.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun putStringSet(key: String, value: Set<String>) {
        editor.putStringSet(key, value).apply()
    }

    @JvmOverloads fun getStringSet(key: String, defValues: Set<String> = HashSet<String>()): Set<String> {
        return sp.getStringSet(key, defValues)
    }

    @JvmOverloads fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    @JvmOverloads fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp.getLong(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    @JvmOverloads fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp.getFloat(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    @JvmOverloads fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    val all: Map<String, *>
        get() = sp.all

    fun remove(key: String) {
        editor.remove(key).apply()
    }

    operator fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    companion object {

        //    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";
        val USER = "user_jkeart_profile"
    }

}
