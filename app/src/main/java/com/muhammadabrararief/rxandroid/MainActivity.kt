package com.muhammadabrararief.rxandroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .map(String::length)
                .subscribe { length: Int -> Log.e("LENGTH", "$length on ${Thread.currentThread().name}") }

        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .subscribeOn(Schedulers.newThread())
                .map(String::length)
                .subscribe { length: Int -> Log.e("LENGTH", "$length on ${Thread.currentThread().name}") }

        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .subscribeOn(Schedulers.computation())
                .map(String::length)
                .subscribeOn(Schedulers.newThread())
                .subscribe { length: Int -> Log.e("LENGTH", "$length on ${Thread.currentThread().name}") }


        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .subscribeOn(Schedulers.computation())
                .map(String::length)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.e("LENGTH", "$it on ${Thread.currentThread().name}") }

        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map {
                    it.length
                    Log.e("MAP", "$it on ${Thread.currentThread().name}")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe { length: Int -> Log.e("LENGTH", "$length on ${Thread.currentThread().name}") }


        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("1", "first doOnNext: processing item on thread " + Thread.currentThread().name) }
                .observeOn(Schedulers.computation())
                .map { obj: String -> obj }
                .doOnNext { Log.e("2", "second doOnNext: processing item on thread " + Thread.currentThread().name) }
                .observeOn(Schedulers.io())
                .map { obj: String -> obj }
                .subscribeOn(Schedulers.newThread())
                .map { obj: String -> obj.length }
                .subscribe { length: Int -> Log.e("3", "received item length " + length + " on thread " + Thread.currentThread().name) }

        Observable.just("abc", "defg", "hijkl")
                .doOnNext { Log.e("TEXT", "$it on ${Thread.currentThread().name}") }
                .flatMap {
                    Observable.just(it.length)
                            .subscribeOn(Schedulers.newThread())
                }
                .subscribeOn(Schedulers.computation())
                .subscribe { Log.e("LENGTH", "$it on ${Thread.currentThread().name}") }


        Observable.just("1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "123456789", "1234567890")
                .flatMap { v: String ->
                    Observable.just(v.length)
                            .doOnNext { Log.e("flatMap", "processing item $v on thread " + Thread.currentThread().name) }
                            .subscribeOn(Schedulers.newThread())
                }.subscribe ({},{},{})

        Observable.just("1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "123456789", "1234567890")
                .concatMap { v: String ->
                    Observable.just(v.length)
                            .doOnNext { Log.e("concatMap", "processing item $v on thread " + Thread.currentThread().name) }
                            .subscribeOn(Schedulers.io())
                }.subscribe {
                    Log.e("concatMap", "received item length " + it + " on thread " + Thread.currentThread().name)
                }

    }
}