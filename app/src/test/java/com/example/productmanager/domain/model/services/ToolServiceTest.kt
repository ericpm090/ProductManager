package com.example.productmanager.domain.model.services

import com.example.productmanager.domain.SearchToolUseCase
import com.example.productmanager.domain.admin_usescases.AddToolUseCase
import com.example.productmanager.domain.model.entities.Tool
import com.example.productmanager.domain.model.entities.ToolStatus
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ToolServiceTest {

    @MockK
    private lateinit var searchToolUsecase: SearchToolUseCase
    @MockK
    private lateinit var addTolCaseUse: AddToolUseCase

    @InjectMockKs
    private lateinit var toolService: ToolService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenSearchExistentTool_thenReturnTool()= runBlocking {
        val tool = Tool(
            barcode = "000000",
            name = "tool_name",
            project = "project",
            location = "location",
            photo = "photo",
            type = "type",
            status = ToolStatus.AVAILABLE.toString()
        )

        coEvery { searchToolUsecase(tool.barcode) } returns tool

        val result = toolService.getToolbyBarcode(tool.barcode)

        coVerify(exactly = 1) {
            searchToolUsecase(tool.barcode)
        }

        Assert.assertEquals(tool, result)
    }

    @Test
    fun whenSearchNonExistentTool_thenReturnNull()= runBlocking {
        val barcode = ""
        coEvery { searchToolUsecase(barcode) } returns null

        val result = toolService.getToolbyBarcode(barcode)

        coVerify(exactly = 1) {
            searchToolUsecase(barcode)
        }

        Assert.assertEquals(null, result)
    }

    @Test
    fun whenSaveTool_thenReturnTrue()= runBlocking {
        val tool = Tool(
            barcode = "000000",
            name = "tool_name",
            project = "project",
            location = "location",
            photo = "photo",
            type = "type",
            status = ToolStatus.AVAILABLE.toString()
        )
        coEvery { addTolCaseUse(tool) } returns tool.barcode

        val result = toolService.saveTool(tool)

        coVerify(exactly = 1) {
            addTolCaseUse(tool)
        }

        Assert.assertEquals(tool.barcode, result)
    }

    @Test
    fun whenSetNonAvailableTool_thenReturnFalse()= runBlocking {
        val tool = Tool(
            barcode = "000000",
            name = "tool_name",
            project = "project",
            location = "location",
            photo = "photo",
            type = "type",
            status = ToolStatus.AVAILABLE.toString()
        )
        //Given
        coEvery { searchToolUsecase(tool.barcode) } returns tool
        coEvery { addTolCaseUse(tool) } returns tool.barcode

        //When
        toolService.changeStatus(tool.barcode)
        val result = toolService.isAvailable(tool)

        coVerify(exactly = 1) {
            searchToolUsecase(tool.barcode)
        }
        coVerify(exactly = 1) {
            addTolCaseUse(tool)
        }
        Assert.assertEquals(false, result)
    }

    @Test
    fun whenSetAvailableTool_thenReturnTrue()= runBlocking {
        val tool = Tool(
            barcode = "000000",
            name = "tool_name",
            project = "project",
            location = "location",
            photo = "photo",
            type = "type",
            status = ToolStatus.NOT_AVAILABLE.toString()
        )
        //Given
        coEvery { searchToolUsecase(tool.barcode) } returns tool
        coEvery { addTolCaseUse(tool) } returns tool.barcode
        //When
        toolService.changeStatus(tool.barcode)
        val result = toolService.isAvailable(tool)

        coVerify(exactly = 1) {
            searchToolUsecase(tool.barcode)
        }
        coVerify(exactly = 1) {
            addTolCaseUse(tool)
        }
        Assert.assertEquals(true, result)
    }


}