package com.dicoding.submissionjetpackcompose.data.repository

import com.dicoding.submissionjetpackcompose.data.model.DataHero
import com.dicoding.submissionjetpackcompose.data.model.ListHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HeroRepository {
    private val hero = mutableListOf<ListHero>()

    init {
        if (hero.isEmpty()){
            DataHero.heroes.forEach{
                hero.add(ListHero(it, 0))
            }
        }
    }

    fun getAllHero(): Flow<List<ListHero>> {
        return flowOf(hero)
    }

    fun getHeroById(heroId: Long): ListHero {
        return hero.first {
            it.listHero.id == heroId
        }
    }

    fun searchHero(query: String): List<ListHero> {
        return hero.filter {
            it.listHero.name.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: HeroRepository? = null

        fun getInstance(): HeroRepository =
            instance ?: synchronized(this) {
                HeroRepository().apply {
                    instance = this
                }
            }
    }
}