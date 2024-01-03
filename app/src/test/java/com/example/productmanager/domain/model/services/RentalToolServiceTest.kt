package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.admin_usescases.GetAllUsersUseCase
import com.example.productmanager.domain.model.entities.Employee
import com.example.productmanager.domain.model.entities.RentalTool
import com.example.productmanager.domain.model.entities.RentalToolStatus
import com.example.productmanager.domain.user_usescases.AddRentalToolUseCase
import com.example.productmanager.domain.user_usescases.GetPendingToolUseCase
import com.example.productmanager.domain.user_usescases.GetRentalHistoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class RentalToolServiceTest {

    @MockK
    private lateinit var addRentalToolUseCase: AddRentalToolUseCase

    @MockK
    private lateinit var getPendingToolUseCase: GetPendingToolUseCase

    @MockK
    private lateinit var getRentalHistoryUseCase: GetRentalHistoryUseCase

    @MockK
    private lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @MockK
    private lateinit var toolService: ToolService

    @InjectMockKs
    private lateinit var rentalToolService: RentalToolService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        rentalToolService =
            RentalToolService(
                addRentalToolUseCase,
                getPendingToolUseCase,
                getRentalHistoryUseCase,
                toolService,
                getAllUsersUseCase
            )


    }

    @Test
    fun whenDeliveryTools_thenGetEmptyListTools() = runBlocking {
        val email = "user@gmail.com"
        val rentalTool1 = RentalTool(
            userMail = email,
            barcode = "000000",
            toolName = "tool_name",
            project = "project",
            location = "location",
            pickUpDate = "21/12/2023",
            dropOffDate = "",
            status = RentalToolStatus.PENDING.toString(),
            photo = "photo"
        )
        val rentalTool2 = RentalTool(
            userMail = email,
            barcode = "000001",
            toolName = "tool_name",
            project = "project",
            location = "location",
            pickUpDate = "21/12/2023",
            dropOffDate = "",
            status = RentalToolStatus.PENDING.toString(),
            photo = "photo"
        )
        val rentalTool3 = RentalTool(
            userMail = email,
            barcode = "000002",
            toolName = "tool_name",
            project = "project",
            location = "location",
            pickUpDate = "21/12/2023",
            dropOffDate = "",
            status = RentalToolStatus.PENDING.toString(),
            photo = "photo"
        )
        val rentalTools = mutableListOf(rentalTool1, rentalTool2, rentalTool3)

        coEvery { addRentalToolUseCase(any()) } returns true
        coEvery { toolService.changeStatus(any()) } coAnswers {}
        coEvery { getRentalHistoryUseCase(email) } returns rentalTools


        rentalToolService.deliveryRentalTools(rentalTools)
        val res = rentalToolService.getAllPendingTools(email)

        rentalTools.forEach {
            coVerify(exactly = 1) {
                addRentalToolUseCase(it)
            }
        }

        rentalTools.forEach {
            coVerify(exactly = 1) {
                toolService.changeStatus(it.barcode)
            }
        }

        assertEquals(0, res.size)
    }

    @Test
    fun whenUserNotHaveRentalHistoryTools_thenGiveNullList() = runBlocking {
        val email = "example@gmail.com"

        coEvery { getRentalHistoryUseCase(email) } returns mutableListOf()

        val result = rentalToolService.getRentalHistory(email)

        coVerify(exactly = 1) { getRentalHistoryUseCase(email) }

        assertEquals(0, result.size)

    }

    @Test
    fun whenSaveTool_thenGetTrue() = runBlocking {
        val email = "example@gmail.com"

        val rentalTool1 = RentalTool(
            userMail = email,
            barcode = "000000",
            toolName = "tool_name",
            project = "project",
            location = "location",
            pickUpDate = "21/12/2023",
            dropOffDate = "",
            status = RentalToolStatus.PENDING.toString(),
            photo = "photo"
        )
        coEvery { addRentalToolUseCase(rentalTool1) } returns true

        val result = rentalToolService.saveRentalTool(rentalTool1)

        coVerify(exactly = 1) { addRentalToolUseCase(rentalTool1) }

        assertEquals(true, result)
    }

    @Test
    fun whenToolisNotUsed_thenGetEmtpyHistoryList() = runBlocking {
        val userList = mutableListOf(
            Employee("User1", "user1@gmail.com", "123456"),
            Employee("User2", "user2@gmail.com", "123456")

        )


        coEvery { getAllUsersUseCase.invoke() } returns userList
        coEvery { getRentalHistoryUseCase(any()) } returns mutableListOf()

        val result = rentalToolService.getRentalToolHistory("10101000000")

        coVerify { getRentalHistoryUseCase(any()) }

        assertEquals(0, result.size)
    }

    @Test
    fun whenToolIsUsed_thenGetHistoryList() = runBlocking {
        val userList = mutableListOf(
            Employee("User1", "user1@gmail.com", "123456"),
            Employee("User2", "user2@gmail.com", "123456")
        )
        val rentalHistoryUser1 = mutableListOf(
            RentalTool(
                userMail = "user1@gmail.com",
                barcode = "10101000000",
                toolName = "tool_name",
                project = "project",
                location = "location",
                pickUpDate = "21/12/2023",
                dropOffDate = "22/12/2023",
                status = RentalToolStatus.DELIVERED.toString(),
                photo = "photo"
            )
        )
        val rentalHistoryUser2 = mutableListOf(
            RentalTool(
                userMail = "user2@gmail.com",
                barcode = "10101000000",
                toolName = "tool_name",
                project = "project",
                location = "location",
                pickUpDate = "21/12/2023",
                dropOffDate = "",
                status = RentalToolStatus.PENDING.toString(),
                photo = "photo"
            )
        )
        coEvery { getAllUsersUseCase.invoke() } returns userList
        coEvery { getRentalHistoryUseCase("user1@gmail.com") } returns rentalHistoryUser1
        coEvery { getRentalHistoryUseCase("user2@gmail.com") } returns rentalHistoryUser2

        val result = rentalToolService.getRentalToolHistory("10101000000")

        coVerify(exactly = 1) { getAllUsersUseCase.invoke() }

        userList.forEach {
            coVerify(exactly = 1) { getRentalHistoryUseCase(it.email) }
        }
        assertEquals(2, result.size)
    }


}