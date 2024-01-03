package com.example.productmanager.ui.login

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productmanager.domain.LoginUseCase
import com.example.productmanager.domain.LoginWithGoogleUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    @MockK
    private lateinit var loginWithGoogleUseCase: LoginWithGoogleUseCase


    private lateinit var loginViewModel: LoginViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(loginUseCase, loginWithGoogleUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun whenLogingUserWithInvalidCredentials_thenReturnFalse() = runTest {
        val wrong_email = "invalidemail"
        val wrong_password = "1234"

        coEvery { loginUseCase.invoke(wrong_email, wrong_password) } returns false

        loginViewModel.onLoginSelected(wrong_email, wrong_password)

        // Await the coroutine completion (use TestCoroutineScope extension function)
        advanceUntilIdle()

        assertEquals(loginViewModel.navigateToHomeUser.value, false)
    }


    @Test
    fun whenLoginUserWithValidCredentials_thenReturnTrue() = runTest {

        val email = "user@gmail.com"
        val password = "123456"
        coEvery { loginUseCase.invoke(email, password) } returns true

        loginViewModel.onLoginSelected(email, password)

        assertEquals(loginViewModel.navigateToHomeUser.value, true)
    }

    @Test
    fun whenCreateAccountWithCredentials_thenReturnAccountCreatedTrue() = runTest {
        val data: Intent = mock(Intent::class.java)

        coEvery { loginWithGoogleUseCase.invoke(data) } returns "accountCreated"

        loginViewModel.onLoginGoogleSelected(data)

        assertEquals(loginViewModel.navigateToSignInGoogle.value, "accountCreated")
    }


}