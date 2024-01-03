package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.ModifyUserUseCase
import com.example.productmanager.domain.admin_usescases.SearchUserUseCase
import com.example.productmanager.domain.model.entities.Employee
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

//https://www.youtube.com/watch?v=xCjIJMydI3s

class UserServiceTest {


    @MockK
    private lateinit var modifyUserUseCase: ModifyUserUseCase

    @MockK
    private lateinit var searchUseUseCase: SearchUserUseCase

    @InjectMockKs
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenCreateUser_thenGetUser() = runBlocking {
        val employee = Employee(
            name = "User",
            email = "user@gmail.com",
            password = "1234"
        )
        //Given
        //coEvery para corutinas
        coEvery {
            //userService.getUser(employee.email)} returns employee
            modifyUserUseCase(employee)
        } returns true

        //When
        // userService.getUser(employee.email)
        val result = userService.saveUser(employee)
        //Then
        //verificamos que se llama una vez
        coVerify(exactly = 1) {
            modifyUserUseCase(employee)
        }
        //verificamos que obtenemos los resultados esperados
        assertEquals(true, result)
    }

    @Test
    fun whenSearchUser_thenGetUser() = runBlocking {

        val employee = Employee(
            name = "User",
            email = "user@gmail.com",
            password = "1234"
        )
        //Given
        coEvery { searchUseUseCase(employee.email) } returns employee

        //When
        val result = userService.getUser(employee.email)
        coVerify(exactly = 1) {
            searchUseUseCase(employee.email)
        }

        assertEquals(employee, result)
    }

    @Test
    fun whenSearchNoExistentUser_thenGetFalse() = runBlocking {
        val email = "noexistentuser@gmail.com"
        //Given
        coEvery { searchUseUseCase(email) } returns null

        //When
        val result = userService.getUser(email)
        coVerify(exactly = 1) {
            searchUseUseCase(email)
        }

        assertEquals(null, result)
    }


}