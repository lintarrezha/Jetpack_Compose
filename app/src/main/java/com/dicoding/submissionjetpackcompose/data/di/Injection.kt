package com.dicoding.submissionjetpackcompose.data.di

import com.dicoding.submissionjetpackcompose.data.repository.HeroRepository

object Injection {
    fun provideRepository(): HeroRepository {
        return HeroRepository.getInstance()
    }
}