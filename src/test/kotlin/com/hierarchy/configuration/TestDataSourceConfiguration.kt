package com.hierarchy.configuration

import com.opentable.db.postgres.embedded.EmbeddedPostgres
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

import org.springframework.context.annotation.Primary
import java.lang.Exception
import javax.sql.DataSource

@TestConfiguration
class TestDataSourceConfiguration {
    @Bean
    @Primary
    @Throws(Exception::class)
    fun inMemoryDS(): DataSource {
        return EmbeddedPostgres
            .builder()
            .start()
            .postgresDatabase
    }
}
