package com.example.lottery.service;

import com.example.lottery.model.Line;
import java.util.List;
import org.hamcrest.Matchers;
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
public class LineGenerationServiceTest {

  @Autowired
  private LineGenerationService lineGenerationService;

  @Test
  public void testGenerateLines() {
    List<Line> lineList = lineGenerationService.generateLines(5);
    Assert.assertEquals("List should contain 5 lines", 5, lineList.size());
    Assert
        .assertEquals("Lines should contain 3 numbers", 3, lineList.get(0).getLineNumbers().size());
    Assert.assertTrue("Numbers should be in range {0, 1, 2}",
        Matchers.isOneOf(0, 1, 2).matches(lineList.get(0).getLineNumbers().get(0).getNumber()));
    Assert.assertTrue("Numbers should be in range {0, 1, 2}",
        Matchers.isOneOf(0, 1, 2).matches(lineList.get(0).getLineNumbers().get(1).getNumber()));
    Assert.assertTrue("Numbers should be in range {0, 1, 2}",
        Matchers.isOneOf(0, 1, 2).matches(lineList.get(0).getLineNumbers().get(2).getNumber()));
  }


}
