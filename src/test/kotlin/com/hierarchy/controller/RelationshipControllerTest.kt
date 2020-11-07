package com.hierarchy.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.hierarchy.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

internal class RelationshipControllerTest: BaseTest() {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `returns 400 on hierarchy loop detected`() {
        val input = mapOf(
            "Tom" to "Jerry",
            "Jerry" to "Tom"
        )
        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/relationship")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()
            .response
            .contentAsString

        assertThat(response).contains("hierarchy loop detected")
    }

    @Test
    fun `returns 400 on several roots detected`() {
        val input = mapOf(
            "Tom" to "Jerry",
            "Mikki" to "Mini",
        )
        val response = mockMvc.perform(
            MockMvcRequestBuilders.post("/relationship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()
            .response
            .contentAsString

        assertThat(response).contains("Looking for 1 root, but found 2")
    }

    @Test
    fun `correctly maps the answer`() {
        val input = mapOf(
            "Pete" to "Nick",
             "Barbara" to "Nick",
             "Nick" to "Sophie",
             "Sophie" to "Jonas"
        )

        val expectedResult = """
            {
               "Jonas":{
                  "Sophie":{
                     "Nick":{
                        "Pete":{},
                        "Barbara":{}
                     }
                  }
               }
            }
        """
        val expectedObject = objectMapper.readValue(expectedResult, ObjectNode::class.java)

        val responseString = mockMvc.perform(
            MockMvcRequestBuilders.post("/relationship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
            .response
            .contentAsString

        val responseObject = objectMapper.readValue(responseString, ObjectNode::class.java)

        assertThat(responseObject).isEqualTo(expectedObject)

    }
}
