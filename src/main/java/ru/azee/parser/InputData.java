package ru.azee.parser;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.azee.parser.jpa.entity.Angle;
import ru.azee.parser.jpa.entity.Anglepair;
import ru.azee.parser.jpa.entity.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.valueOf;

public class InputData {

    List<Point> points = new ArrayList<Point>();
    List<Angle> angles = new ArrayList<Angle>();

    public void readInputData(String filePath) throws Exception {

        int countAngle = 0;
        List<Integer> sequence = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath)))) {

            for (int i = 0; i < 5; i++) {
                buffer.readLine();
            }
/**
 * 1. Первый заход в цикл - всегда "Point"
 * 2. После "Point" всегда должен идти "Angle". Должны понимать, что было в предыдущем цикле
 * 3. "Angle" всегда парные. Пара должна быть полной.
 * 4. Цикл может кончиться на парном "Angle".
 */
            while (buffer.ready()) {

                StringBuilder line = new StringBuilder(buffer.readLine());
                System.out.println(line);
                Pattern patternPoint = Pattern.compile("(^\\b\\d{2}F\\d{1}\\b)\\s+(\\b[a-zA-Z]{1}\\d?\\b)\\s+" +
                        "(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+");
                Pattern patternAngle = Pattern.compile("(^\\b\\d{2}F\\d{1}\\b)\\s+" +
                        "(\\b\\d+\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+");
                Matcher matcherPoint = patternPoint.matcher(line);
                Matcher matcherAngle = patternAngle.matcher(line);
                if (matcherPoint.find()) {
                    if (points.isEmpty() || (countAngle % 2 == 0 & sequence.get(sequence.size()-1) != 1)) {
                    Point point = new Point();
                    point.setId(matcherPoint.group(2));
                    point.setDistance(Double.valueOf(matcherPoint.group(3)));
                    point.setVangle(Double.valueOf(matcherPoint.group(4)));
                    point.setHangle(Double.valueOf(matcherPoint.group(5)));
                    points.add(point);
                    sequence.add(1);
                    } else {
                        throw new Exception ("Нельзя создать точку");
                    }
                } else {
                    if (matcherAngle.find()) {
                        if (!points.isEmpty()) {
                            Angle angle = new Angle();
                            angle.setVangle(Double.valueOf(matcherAngle.group(3)));
                            angle.setHangle(Double.valueOf(matcherAngle.group(4)));
                            angles.add(angle);
                            countAngle++;
                            sequence.add(0);
                            if (countAngle % 2 == 0){
                            	Point p = points.get(points.size()-1);// извлекли посследнюю точку из списка
                            	
                                List<Anglepair> lap = p.getAnglepairs(); //извлекаем список углов-пар
                            	Anglepair ap = lap.get(lap.size()-1); // один объек угол-пара (последний в списке)
                            	ap.addAngle(angle);
                                // добавляем второй угол в пару
                            } else {
                            	Anglepair angelpair = new Anglepair();
                            	angelpair.getAngles().add(angle);
                            	points.get(points.size()-1).getAnglepairs().add(angelpair);
                                // добавляем первый угол в пару
                            }
                        } else {
                            throw new Exception ("Нельзя создавать угол");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        InputData inputData = new InputData();
        inputData.readInputData("D:\\JOB3.txt");
    }
}