package com.example.demo.junit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    static Stream<Arguments> prepareAddPositiveShouldWork() {
        return Stream.of(
            Arguments.of(1, 1, 2),
            Arguments.of(1, 2, 3),
            Arguments.of(-2, -302, -304),
            Arguments.of(-2, -2, -4)
        );
    }

    @AfterEach
    void after() {
        System.out.println("After each test");
    }

    @ParameterizedTest
    @MethodSource("prepareAddPositiveShouldWork")
    void addPositiveShouldWork(int a, int b, int expected) {
        int add = calculator.add(a, b);
        Assertions.assertThat(add).isEqualTo(expected);
    }

    @Test
    void subtract() {
        int subtract = calculator.subtract(1, 1);
        Assertions.assertThat(subtract).isZero();
    }

    @Test
    void multiply() {
        int multiply = calculator.multiply(2, 2);
        Assertions.assertThat(multiply).isEqualTo(4);
    }

    @Test
    void divide() {
        int divide = calculator.divide(4, 2);
        Assertions.assertThat(divide).isEqualTo(2);
    }

    @Test
    @Disabled
    void divideZeroShouldThrowException() {
        Assertions.assertThatThrownBy(() -> calculator.divide(1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Cannot divide by zero");
    }

}
