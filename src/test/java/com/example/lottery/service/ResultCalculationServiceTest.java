package com.example.lottery.service;

import com.example.lottery.model.LineNumber;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResultCalculationServiceTest {

  @Autowired
  private ResultCalculationService resultCalculationService;

  @Test
  public void calculateResultIsCorrectWhenSumIsTwo() {
    Assert.assertEquals(10, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(0), new LineNumber(1), new LineNumber(1))));
    Assert.assertEquals(10, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(2), new LineNumber(0), new LineNumber(0))));
  }

  @Test
  public void calculateResultIsCorrectWhenAllNumbersHaveTheSameValue() {
    Assert.assertEquals(5, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(0), new LineNumber(0), new LineNumber(0))));
    Assert.assertEquals(5, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(1), new LineNumber(1), new LineNumber(1))));
    Assert.assertEquals(5, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(2))));
  }

  @Test
  public void calculateResultIsCorrectWhenFirstNumberDifferentFromSecondAndThird() {
    Assert.assertEquals(1, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(1), new LineNumber(2), new LineNumber(0))));
    Assert.assertEquals(1, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(0), new LineNumber(2), new LineNumber(1))));
    Assert.assertEquals(1, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(2), new LineNumber(0), new LineNumber(1))));
  }

  @Test
  public void calculateResultIsCorrectWhenOtherScenariosNotMet() {
    Assert.assertEquals(0, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(2), new LineNumber(0), new LineNumber(2))));
    Assert.assertEquals(0, resultCalculationService.calculateResultForLine(
        Arrays.asList(new LineNumber(2), new LineNumber(2), new LineNumber(1))));
  }

}
