package com.hierarchy

import com.hierarchy.configuration.TestDataSourceConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestDataSourceConfiguration::class)
class BaseTest {
}
