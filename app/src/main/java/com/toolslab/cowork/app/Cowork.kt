package com.toolslab.cowork.app

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.LOLLIPOP
import com.google.android.gms.security.ProviderInstaller
import com.toolslab.cowork.BuildConfig
import com.toolslab.cowork.base_repository.di.DaggerRepositoryComponent
import com.toolslab.cowork.app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import okhttp3.TlsVersion
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.net.ssl.SSLContext

class Cowork : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        installTls12IfNeeded()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val repositoryComponent = DaggerRepositoryComponent.builder().build()
        return DaggerAppComponent
                .builder()
                .create(this)
                .repositoryComponent(repositoryComponent)
                .build()
    }

    /**
     * Enables TLS v1.2 when creating SSLSockets.
     *
     * For some reason, android supports TLS v1.2 from API 16,
     * but enables it by default only from API 21.
     * @link https://developer.android.com/reference/javax/net/ssl/SSLSocket.html
     */
    private fun installTls12IfNeeded() {
        if (SDK_INT in JELLY_BEAN..LOLLIPOP) {
            try {
                ProviderInstaller.installIfNeeded(this)
                SSLContext.getInstance(TlsVersion.TLS_1_2.javaName()).apply {
                    init(null, null, null)
                    createSSLEngine()
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

}
