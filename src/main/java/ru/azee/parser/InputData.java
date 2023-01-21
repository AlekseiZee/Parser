package ru.azee.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.azee.parser.jpa.entity.Angle;
import ru.azee.parser.jpa.entity.Anglepair;
import ru.azee.parser.jpa.entity.Point;

public class InputData {

	List<Point> points = new ArrayList<Point>();

	public List<Point> getPoints() {
		return points;
	}

	public void readInputData(String filePath) throws Exception {
		
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

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
				if (!line.toString().trim().isEmpty()) {
					System.out.println(line);
					Pattern patternPoint = Pattern.compile("(^\\b\\d{2}F\\d{1}\\b)\\s+(\\b[a-zA-Z]{1}\\d?\\b)\\s+"
							+ "(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+");
					Pattern patternAngle = Pattern.compile(
							"(^\\b\\d{2}F\\d{1}\\b)\\s+(\\b\\d+\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s*");
					Matcher matcherPoint = patternPoint.matcher(line);
					Matcher matcherAngle = patternAngle.matcher(line);

					if (isFirstLine(matcherPoint, line.toString())) {
						Point point = createPoint(matcherPoint);
						points.add(point);
					} else {// Предыдущая линия была точкой
						if (isPreviousLinePoint(points.get(points.size()-1), matcherAngle, filePath)) {
							Angle angle = createAngle(matcherAngle);
							Point point = points.get(points.size()-1);
							Anglepair anglepair = createAnglepair(angle);
							point.addAnglepair(anglepair);
						} else { //Предыдущая линия была углом
							if (matcherAngle.find()) {
								Angle angle = createAngle(matcherAngle);
								Point point = points.get(points.size()-1);
								List<Anglepair> anglePairs = point.getAnglepairs();
								Anglepair lastAnglepair = anglePairs.get(anglePairs.size()-1);
								if (lastAnglepair.getAngles().size() == 1) {
									lastAnglepair.addAngle(angle);
								} else {
									Anglepair newAnglepair = createAnglepair(angle);
									point.addAnglepair(newAnglepair);
								}
							} else {
								if (!matcherPoint.find()) {
									throw new Exception("Ожидаем Point \n строка:" + line);
								} else {
									Anglepair lastAnglepair = getLastAnglePair();
									if (lastAnglepair.getAngles().size() == 2) {
										Point pnt = createPoint(matcherPoint);
										points.add(pnt);
									} else {
										throw new Exception("Ожидаем Angle \n строка:" + line);
									}
								}
							}
						}
					}
				}
				
			}
			if (getLastAnglePair().getAngles().size() != 2) {
				throw new Exception ("У последней точки не полная пара");
			}
			points.forEach((p) -> System.out.println(p));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/**
 * Получаем список anglePairs и возвращаем последнюю anglepair из списка
 * @return
 */
	private Anglepair getLastAnglePair() {
		Point point = points.get(points.size()-1);
		List<Anglepair> anglePairs = point.getAnglepairs();
		Anglepair anglepair = anglePairs.get(anglePairs.size()-1);
		return anglepair;
	}
/**
 * Создаем угол-пару и добавляем туда @param angle. Так же добавляем его в список углов-пар @param anglePairs
 * @param angle
 * @param anglePairs
 */
	private Anglepair createAnglepair(Angle angle) {
		Anglepair anglePair = new Anglepair();
		anglePair.addAngle(angle);
		return anglePair;
	}

	private Angle createAngle(Matcher matcherAngle) {
		Angle angle = new Angle();
		angle.setVangle(Double.valueOf(matcherAngle.group(3)));
		angle.setHangle(Double.valueOf(matcherAngle.group(4)));
		return angle;
	}

	private Point createPoint(Matcher matcherPoint) {
		Point point = new Point();
		point.setCode(matcherPoint.group(2));
		point.setDistance(Double.valueOf(matcherPoint.group(3)));
		point.setVangle(Double.valueOf(matcherPoint.group(4)));
		point.setHangle(Double.valueOf(matcherPoint.group(5)));
		return point;
	}

	private boolean isFirstLine(Matcher matcherPoint, String line) throws Exception {
		if (points.isEmpty()) {
			if (!matcherPoint.find()) {
				throw new Exception("Первая строка должна быть Point \n строка:" + line);
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
/**
 * Проверка, что предудущая строка соответствовала Point. Настоящая строка это угол
 * @param point
 * @param matcherAngle
 * @param line
 * @return
 * @throws Exception
 */
	private boolean isPreviousLinePoint(Point point, Matcher matcherAngle, String line) throws Exception {
		if (point.getAnglepairs().isEmpty()) {
			if (!matcherAngle.find()) {
				throw new Exception("Ожидаем Angle \n строка:" + line);
			} else {
				return true;
			}
		} else {
			return false;
		}
	}


	public boolean isAngle(String line) {
		Pattern patternAngle = Pattern
				.compile("(^\\b\\d{2}F\\d{1}\\b)\\s+(\\b\\d+\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s+(\\b\\d+\\.\\d*\\b)\\s*");
		Matcher matcherAngle = patternAngle.matcher(line);
		return matcherAngle.find();
	}

	public static void main(String[] args) throws Exception {
		InputData inputData = new InputData();
		inputData.readInputData("D:\\JOB3.txt");
	}
}