package com.example.lottery.service;

import static java.util.stream.Collectors.toList;

import com.example.lottery.model.Line;
import com.example.lottery.model.LineNumber;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class LineGenerationService {

  private static final Integer LOWER_BOUND = 0;
  private static final Integer UPPER_BOUND = 3;
  private static final Integer LINE_SIZE = 3;

  private final Random random = new Random();

  List<Line> generateLines(int numLines) {
    List<Line> lineList = new ArrayList<>();
    for (int i = 0; i < numLines; i++) {
      lineList.add(generateLine());
    }
    return lineList;
  }

  private Line generateLine() {
    return new Line(getRandomLineNumber());
  }

  private List<LineNumber> getRandomLineNumber() {
    List<Integer> randomNumbers = random.ints(LINE_SIZE, LOWER_BOUND, UPPER_BOUND).boxed()
        .collect(toList());
    List<LineNumber> lineNumberList = new ArrayList<>();
    for (Integer randomNumber : randomNumbers) {
      lineNumberList.add(new LineNumber(randomNumber));
    }
    return lineNumberList;
  }

}
