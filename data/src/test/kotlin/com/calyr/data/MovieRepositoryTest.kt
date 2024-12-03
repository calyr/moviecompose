package com.calyr.data

import com.calyr.domain.Movie
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock

class MovieRepositoryTest {


    val remoteDataSource: IRemoteDataSource  = mock()
    val localDataSource: ILocalDataSource = mock()


    @Test
    fun obtainMovies() = runTest {
            //arrange
            val movieRepository = MovieRepository(remoteDataSource, localDataSource)
            val list: List<Movie> = listOf(Movie(1, "", "", ""))
            val localResult : NetworkResult<List<Movie>> = NetworkResult.Success(list)
            Mockito.`when`(localDataSource.getList()).thenReturn(localResult)
            //act
            val finalList = movieRepository.obtainMovies(false)
            //assert
            assertEquals(finalList.size, list.size)

    }

    @Test
    fun findById() {

        //arrange
        val movieRepository = MovieRepository(remoteDataSource, localDataSource)
        val movieInternt = Movie(
            1,
            "",
            "",
            ""
        )
        val localResult: NetworkResult<Movie> = NetworkResult.Success(movieInternt)
        Mockito.`when`(localDataSource.findById(anyString())).thenReturn(localResult)

        //act
        val movie = movieRepository.findById("1")

        //assert
        assertEquals(movieInternt.id, movie?.id )
    }

    fun getRemoteDataSource() {
    }

    fun getLocalDataSource() {
    }
}