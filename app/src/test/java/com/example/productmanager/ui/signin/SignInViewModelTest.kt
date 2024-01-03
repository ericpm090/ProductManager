package com.example.productmanager.ui.signin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productmanager.domain.SignInUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class SignInViewModelTest {

    @MockK
    private lateinit var signInUseCase: SignInUseCase

    private lateinit var signInViewModel: SignInViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        signInViewModel = SignInViewModel(signInUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenSignInUserInvalidCredentials_thenReturnFalse() = runTest {
        //

        val wrong_email = "invalidemail"
        val wrong_password = "1234"
        val name = "user"

        signInViewModel.onSignInSelected(wrong_email, name, wrong_password)



        assertEquals(false, signInViewModel.navigateToHomeUser.value)
    }


    /*@Test
    fun whenSignInUserWithValidCredentials_thenReturnTrue() = runTest {

        val email = "user@gmail.com"
        val password = "123456"
        val name = "user"

        coEvery { signInUseCase.invoke(any()) } returns true

        signInViewModel.onSignInSelected(email, name, password)
        //Assert.assertEquals(signInViewModel.navigateToHomeUser.value, true)
        advanceUntilIdle()
        val result = signInViewModel.navigateToHomeUser.value
       assertEquals(true, result)

    }*/


}