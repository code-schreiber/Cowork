package com.toolslab.androidstarterkit.view.base


import android.annotation.SuppressLint
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered") // BaseActivity should not go in the manifest
open class BaseActivity : DaggerAppCompatActivity()
