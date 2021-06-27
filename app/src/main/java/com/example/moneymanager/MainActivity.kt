package com.example.moneymanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moneymanager.model.user.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_categories, R.id.navigation_records))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Sign in first
        createSignInIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; tis adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            //R.id.action_settings -> {};
            R.id.action_sign_out -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseAuth.getInstance().currentUser?.let {
                updateCurrentUser(it)
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            println("Sign in failed!")
            if (response != null) {
                response.error?.let { println(it.errorCode) }
            }
            createSignInIntent()
        }
    }

    private fun updateCurrentUser(user: FirebaseUser){
        val username = user.displayName
        val userEmail = user.email as String
        val userPhotoUrl = user.photoUrl
        GlobalScope.launch {
            val currentUser = (application as MyApplication).userRepository.getWithInsert(User(username, userEmail, userPhotoUrl.toString()))
            MyApplication.currentUser = currentUser
        }
        // update ui
        /*val homeUsername = findViewById<TextView>(R.id.home_username)
        val homeUserEmail = findViewById<TextView>(R.id.home_user_email)
        val homeHeadPhoto = findViewById<ImageView>(R.id.home_head_photo)
        homeUsername.text = username
        homeUserEmail.text = userEmail
        Glide.with(this)
            .load(userPhotoUrl)
            .into(homeHeadPhoto);*/
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                createSignInIntent()
            }
    }
}
