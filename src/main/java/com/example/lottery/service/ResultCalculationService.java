package com.example.lottery.service;

import com.example.lottery.model.LineNumber;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ResultCalculationService {

  int calculateResultForLine(List<LineNumber> lineNumbers) {
    lineNumbers = stripNullValuesFromList(lineNumbers);
    if (numbersSumToTwo(lineNumbers)) {
      return 10;
    }
    if (allNumbersHaveSameValue(lineNumbers)) {
      return 5;
    }
    if (firstNumberDifferentToSecondAndThird(lineNumbers)) {
      return 1;
    }
    return 0;
  }

  private List<LineNumber> stripNullValuesFromList(List<LineNumber> numbers) {
    return numbers.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }

  private boolean numbersSumToTwo(List<LineNumber> lineNumbers) {
    return lineNumbers.stream().map(LineNumber::getNumber).mapToInt(Integer::intValue).sum() == 2;
  }

  private boolean allNumbersHaveSameValue(List<LineNumber> lineNumbers) {
    return lineNumbers.stream().map(LineNumber::getNumber).distinct().count() == 1;
  }

  private boolean firstNumberDifferentToSecondAndThird(List<LineNumber> lineNumbers) {
    return lineNumbers.get(0).getNumber() != lineNumbers.get(1).getNumber() &&
        lineNumbers.get(0).getNumber() != lineNumbers.get(2).getNumber();
  }

}
