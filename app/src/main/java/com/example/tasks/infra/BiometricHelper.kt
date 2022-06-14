package com.example.tasks.infra

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.tasks.modules.person.ui.login.LoginActivity
import java.util.concurrent.Executor

class BiometricHelper {
    companion object {
        fun isBiometryAvailable(context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return false
            }

            when (BiometricManager.from(context).canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> return true
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> return false
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> return false
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> return false
            }

            return false
        }
    }
}