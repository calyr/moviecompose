package com.calyr.data

import com.calyr.domain.Movie

class MovieRepository(
    val remoteDataSource: IRemoteDataSource,
    val localDataSource: ILocalDataSource
) {

    suspend fun obtainMovies(hasInternet: Boolean): List<Movie> {

        if(hasInternet) {
            //consultar al servicio web
            val moviesRemote = remoteDataSource.fetchData()

            //Verificar el estado final del consumo de API
            when( moviesRemote) {
                is NetworkResult.Success -> {
                    //eliminamos los datos de la base de datos
                    localDataSource.deleteAll()
                    //Actualizar la base de datos
                    localDataSource.insertMovies(
                        moviesRemote.data
                    )
                }
                is NetworkResult.Error -> {
                    //Registrar un log en Sentry
                }
            }
        }


        val moviesLocal = localDataSource.getList()
        when(moviesLocal) {
            is NetworkResult.Success -> {
                return moviesLocal.data
            }
            is NetworkResult.Error -> {
                //Registrar un log en Sentry
                return emptyList()
            }
        }
    }

    fun findById( id: String) : Movie? {
        val movieLocal = localDataSource.findById(id)
        when( movieLocal) {
            is NetworkResult.Success -> {
                return movieLocal.data
            }
            is NetworkResult.Error -> {
                //Registrar un log en Sentry
                return null
            }
        }
    }
}