package com.majid.tummocassignment.ui.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.majid.tummocassignment.R
import com.majid.tummocassignment.data.database.DBDEFINITIONS
import com.majid.tummocassignment.data.database.SharedPreferences
import com.majid.tummocassignment.databinding.ActivityMainBinding
import com.majid.tummocassignment.ui.fragments.HomeFragment
import com.majid.tummocassignment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModel: MainViewModel
    lateinit var sharedPref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPreferences.getInstance()
        if (!sharedPref.getBoolean(DBDEFINITIONS.Key_Has_Data_Been_Inserted)) {
            mainViewModel.saveDate()
            sharedPref.setBoolean(DBDEFINITIONS.Key_Has_Data_Been_Inserted, true)
        }

        openFragment(HomeFragment(), "")
        handleBackPress()
    }

    private var doubleBackToExitPressed = false
    fun handleBackPress() {

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity

                    var count = supportFragmentManager.backStackEntryCount
                    if (count <= 1) {
                        // show exit dialog
                        doubleBackToExitPressed = true
                        Toast.makeText(
                            applicationContext,
                            "${resources.getString(R.string.press_back_again_to_exit)}",
                            Toast.LENGTH_SHORT
                        ).show()

                        val handler = Handler(Looper.myLooper()!!)
                        handler.postDelayed({
                            try {
                                // delayed code here
                                doubleBackToExitPressed = false
                            } catch (e: Exception) {
                            }
                        }, 1000)

                    } else {
                        supportFragmentManager.popBackStack()
                    }


                }
            })
    }


    fun openFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        setFragmentTransactionAnimation(transaction, tag)
        transaction.replace(R.id.main_container, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    private fun setFragmentTransactionAnimation(transaction: FragmentTransaction, tag: String) {
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
    }


}