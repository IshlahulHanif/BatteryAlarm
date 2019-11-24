package com.izura.batteryalarm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

//TODO: make save type check using overload operator
fun Context.getDefaultPreferences() : SharedPreferences = getCustomSharedPreferences("default_shared_prefs")
fun Context.getCustomSharedPreferences(name : String) : SharedPreferences = getSharedPreferences(name,Context.MODE_PRIVATE)
fun SharedPreferences.setValue(key:String, value : Any?){
    val editor: SharedPreferences.Editor = edit()
    when (value) {
        is Boolean -> editor.putBoolean(key, value)
        is Float -> editor.putFloat(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is String -> editor.putString(key, value)
        is Set<*> -> {
            val iterator = value.iterator()
            val type = iterator.next()
            if (type is String) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    editor.putStringSet(key, value as MutableSet<String>)
                } catch (e: Exception) {
                    error("Failed to set string set: ${e.localizedMessage}")
                    throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float, long dan string set")
                }
            }
        }
        else -> {
            error("Unknown value")
            throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float, long dan string set")
        }
    }
    editor.apply()
}

inline fun <reified T:Any> SharedPreferences.getValue(key:String,defaultValue : T? = null) : T{
    return when(T::class) {
        String::class -> getString(key, defaultValue as? String?:"") as T
        Int::class -> getInt(key, defaultValue as? Int?:-1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean?:false) as T
        Float::class -> getFloat(key, defaultValue as? Float?:-1f) as T
        Long::class -> getLong(key, defaultValue as? Long?:-1) as T
        Set::class -> {
            try {
                @Suppress("UNCHECKED_CAST")
                getStringSet(key, defaultValue as? Set<String>) as T
            } catch (e: Exception) {
                error("Failed to get string set: ${e.localizedMessage}")
                throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float, long dan string set")
            }
        }
        else -> {
            error("Unknown value")
            throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float, long dan string set")
        }
    }
}

fun Any.info(msg: String?, tag: String = javaClass.simpleName) { Log.i(tag, msg ?: "" ) }
fun Any.debug(msg: String?, tag: String = "shlh") { Log.d(tag, msg?: "") }
fun Any.warning(msg: String?, tag: String = javaClass.simpleName) { Log.w(tag, msg?: "") }
fun Any.error(msg: String?, tag: String = javaClass.simpleName) { Log.e(tag, msg?: "") }
