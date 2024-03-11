package com.example.demo.spring.support;

import com.example.demo.spring.controller.UserController;
import com.example.demo.spring.initializer.PostgreSqlInitializer;
import com.example.demo.spring.repository.DocRepository;
import com.example.demo.spring.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSqlInitializer.class)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected TransactionTemplate transactionTemplate;
    @Autowired
    protected UserController userController;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected DocRepository docRepository;

    @AfterEach
    void truncateTables() {
        jdbcTemplate.execute("TRUNCATE TABLE " + getTablesToTruncate() + " RESTART IDENTITY CASCADE");
    }

    private String getTablesToTruncate() {
        return getTables()
            .stream()
            .map(this::tableNameWithSchema)
            .collect(Collectors.joining(", "));
    }

    protected String tableNameWithSchema(final String tableName) {
        final var schema = getSchema();
        return tableName.startsWith(schema)
               ? tableName
               : String.format("%s.%s", schema, tableName);
    }

    private Set<String> getTables() {
        return Set.of("users", "doc");
    }

    private String getSchema() {
        return "public";
    }
}
