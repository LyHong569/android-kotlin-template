package com.example.testandroid.cores.models

enum class RemoteConfigKey(val key: String) {
    ANDROID_CURRENT_VERSION("android_current_version"),
    ANDROID_MAINTENANCE("android_maintenance"),
    ANDROID_STORE_URL("android_store_url"),
    ANDROID_UPDATE_REQUIRED("android_update_required")
}