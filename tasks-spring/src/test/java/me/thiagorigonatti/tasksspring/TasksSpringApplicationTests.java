package me.thiagorigonatti.tasksspring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TasksSpringApplicationTests {

  @Test
  void itShoulAddTwoNumbers() {


    Calculator underTest = new Calculator();
    //given
    int numberOne = 20;
    int numberTwo = 30;

    //when
    int result = underTest.add(numberOne, numberTwo);


    //then
    Assertions.assertEquals(50, result);

  }

  static class Calculator {
    int add(int a, int b) {
      return a + b;
    }
  }

}
