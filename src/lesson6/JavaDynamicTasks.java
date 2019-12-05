package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //Трудоемкость = O(nm), где n - first, m - second
    //Ресурсоемкость = O(nm)
    public static String longestCommonSubSequence(String first, String second) {
        StringBuilder sb = new StringBuilder();
        int[][] max = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    max[i + 1][j + 1] = 1 + max[i][j];
                }
                else
                    { max[i + 1][j + 1] = Math.max(max[i + 1][j], max[i][j + 1]);
                }
            }
        }
        for (int i = first.length() - 1, j = second.length()- 1; i >= 0 && j >= 0;) {
            if (first.charAt(i) == second.charAt(j)) {
                sb.insert(0,first.charAt(i));
                i--;
                j--;
            }
            else if (max[i + 1][j] > max[i][j + 1])
                j--;
            else
                i--;
        }
        return sb.toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    //Трудоемкость = O(n^2), где n - количество чисел.
    //Ресурсоемкость = O(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        int size = list.size();
        int[] prev = new int[size];
        int[] len_max = new int[size];
        ArrayList<Integer> result = new ArrayList<>();

        if (size < 2)
            return list;
        for (int i = 0; i < size; i++) {
            len_max[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < size; j++)
                if (list.get(j) < list.get(i) && len_max[j] + 1 > len_max[i]) {
                    len_max[i] = len_max[j] + 1;
                    prev[i] = j;
                }
        }
        int pos = 0;
        int length = len_max[0];
        for (int i = 0; i < size; i++) {
            if (len_max[i] > length) {
                pos = i;
                length = len_max[i];
            }
        }
        while (pos != -1) {
            result.add(list.get(pos));
            pos = prev[pos];
        }
        Collections.reverse(result);
        return result;

    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
