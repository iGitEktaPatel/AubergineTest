package com.fitbit.sampleandroidoauth2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fitbit.authentication.AuthenticationHandler
import com.fitbit.authentication.AuthenticationManager
import com.fitbit.authentication.AuthenticationResult
import com.fitbit.sampleandroidoauth2.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity(), AuthenticationHandler {
    private var binding: ActivityRootBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root)
    }

    override fun onResume() {
        super.onResume()
        binding!!.loading = false
        /**
         * (Look in FitbitAuthApplication for Step 1)
         */
        /**
         * 2. If we are logged in, go to next activity
         * Otherwise, display the login screen
         */
        if (AuthenticationManager.isLoggedIn()) {
            onLoggedIn()
        }
    }

    fun onLoggedIn() {
        val intent = UserDataActivity.newIntent(this)
        startActivity(intent)
        binding!!.loading = false
    }

    fun onLoginClick(view: View?) {
        binding!!.loading = true
        /**
         * 3. Call login to show the login UI
         */
        AuthenticationManager.login(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /**
         * 4. When the Login UI finishes, it will invoke the `onActivityResult` of this activity.
         * We call `AuthenticationManager.onActivityResult` and set ourselves as a login listener
         * (via AuthenticationHandler) to check to see if this result was a login result. If the
         * result code matches login, the AuthenticationManager will process the login request,
         * and invoke our `onAuthFinished` method.
         *
         * If the result code was not a login result code, then `onActivityResult` will return
         * false, and we can handle other onActivityResult result codes.
         *
         */
        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
            // Handle other activity results, if needed
        }
    }

    override fun onAuthFinished(authenticationResult: AuthenticationResult) {
        binding!!.loading = false
        /**
         * 5. Now we can parse the auth response! If the auth was successful, we can continue onto
         * the next activity. Otherwise, we display a generic error message here
         */
        if (authenticationResult.isSuccessful) {
            onLoggedIn()
        } else {
            displayAuthError(authenticationResult)
        }
    }

    private fun displayAuthError(authenticationResult: AuthenticationResult) {
        var message: String? = ""
        when (authenticationResult.status) {
            AuthenticationResult.Status.dismissed -> message = getString(R.string.login_dismissed)
            AuthenticationResult.Status.error -> message = authenticationResult.errorMessage
            AuthenticationResult.Status.missing_required_scopes -> {
                val missingScopes = authenticationResult.missingScopes
                val missingScopesText = TextUtils.join(", ", missingScopes)
                message = getString(R.string.missing_scopes_error) + missingScopesText
            }
        }
        AlertDialog.Builder(this)
                .setTitle(R.string.login_title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes) { dialog, id -> }
                .create()
                .show()
    }
}