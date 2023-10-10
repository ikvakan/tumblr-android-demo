package com.ikvakan.tumblrdemo.di.network

import com.ikvakan.tumblrdemo.core.network.ConnectivityObserver
import com.ikvakan.tumblrdemo.core.network.NetworkConnectivityObserver
import org.koin.dsl.module

val connectivityModule = module {
    single<ConnectivityObserver> { NetworkConnectivityObserver(context = get()) }
}
