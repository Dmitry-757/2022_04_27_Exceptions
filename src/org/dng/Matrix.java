package org.dng;

import java.io.*;


/**
 * 1. Написать класс «Матрица». Данный класс реализует функционал математического объекта «матрица».
 * Обязательные поля класса:
 * элементы матрицы – двумерный массив
 * кол-во строк/кол-во столбцов – целочисленные значения
 * <p>
 * Обязательные методы класса:
 * конструкторы
 * деструктор
 * сеттеры/геттеры
 * метод получения/установки элемента матрицы aij, где i – номер строки, j – номер столбца
 * метод заполнения матрицы случайными значениями
 * сложение/вычитание двух матриц (поэлементно)
 * <p>
 * Бонусные методы (реализация не обязательная, по желанию, продвинутый уровень):
 * умножение матрицы на матрицу
 * вычисление определителя матрицы
 * метод записи матрицы в файл
 * метод чтения матрицы из файла
 * <p>
 * 2. При разработке класса «Матрица» выполнять обработку исключительных ситуаций:
 * при вводе/вывод, выделении памяти, доступах к элементам и т.д.
 * сделать новый класс собственного исключения MatrixException, наследника Exception, и использовать его для обработки
 * исключительных ситуаций при работе с матрицами (получение элемента, создание матрицы, операции с матрицами) –
 * пример исключительных ситуаций, требующих обработки – получение/установка элемента по несуществующему индексу,
 * сложение матриц разной размерности и т.д.
 * <p>
 * 3. Протестировать данный класс используя вызовы методов. Можно написать собственный CLI (command line interface)
 * простого калькулятора матриц.
 */
class MatrixException extends Exception {
    public MatrixException(String message) {
        super(message);
    }
}

public class Matrix implements Serializable {

    private int[][] matrix;
    private int yDimension;
    private int xDimension;

    public Matrix(int yDimension, int xDimension) throws MatrixException {
        // catch exception here - we can`t create instance of Matrix with wrong parameters
        try {
            this.yDimension = yDimension;
            this.xDimension = xDimension;
            this.matrix = createRandomMatrix(yDimension, xDimension, 100);
        } catch (MatrixException e) {
            //System.out.println(e.getMessage());
            throw new MatrixException(e.getMessage()); //and pass exception up
        }
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.yDimension = matrix.length;
        this.xDimension = matrix[0].length;
    }

    /**
     * set value val as element of matrix to position i,j
     *
     * @throws MatrixException - checking the condition for the inclusion of indices in the dimension of the matrix
     */
    public void setMatrixElement(int i, int j, int val) throws MatrixException {
        if ((matrix.length <= i) || (matrix[0].length) <= j)
            //throw new ArrayIndexOutOfBoundsException("dimension of the matrix is violated! Index out of range!");
            throw new MatrixException("dimension of the matrix is violated! Index out of range!");

        this.matrix[i][j] = val;
    }

    public int[][] getMatrixAsArr() {
        return matrix;
    }

    public int getYDimension() {
        return yDimension;
    }

    public int getXDimension() {
        return xDimension;
    }

    public void printMatrix() {
        for (int[] y : matrix) {
            System.out.print("[");
            for (int x : y) {
                System.out.printf("%3d", x);
            }
            System.out.print(" ]");
            System.out.println();
        }
        System.out.println();
    }

    public void fillMatrixRandom() throws MatrixException {
        try {
            //rndRange (value 49) is chosen so that when added, the result must be less than 100 - for good printing
            this.matrix = createRandomMatrix(yDimension, xDimension, 49);
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
            throw new MatrixException(e.getMessage()); //and pass exception up
        }

    }

    public static int[][] createRandomMatrix(int dimY, int dimX, int rndRange) throws MatrixException {

        if ((dimY < 1) || (dimX < 1)) {
//            throw new IllegalArgumentException("Wrong matrix dimension!");
            throw new MatrixException("dimension of the matrix is violated! Index out of range!");
        }
        int[][] matrix = new int[dimY][dimX];
        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                matrix[i][j] = (int) (Math.random() * rndRange);
            }
        }
        return matrix;
    }

    public void matrixAdd(Matrix matrix2) throws MatrixException {
        matrixAddition(ActionType.ADD, matrix2);
    }

    public void matrixSub(Matrix matrix2) throws MatrixException {
        matrixAddition(ActionType.SUB, matrix2);
    }

    public void matrixAddition(ActionType actionType, Matrix matrix2) throws MatrixException {
        int[][] m1 = matrix;
        int[][] m2 = matrix2.getMatrixAsArr();
        if ((m1.length == 0) || (m2.length == 0)) {
            throw new MatrixException("Error! Matrix dimensions must be >0 !");
        }
        //lets compare dimensions of matrix
        if (m1.length != m2.length) {
            throw new MatrixException("Error! Matrix Y-dimensions must be equal!");
        }
        for (int i = 0; i < m1.length; i++) {
            if (m1[i].length != m2[i].length) {
                throw new MatrixException("Error! Matrix X-dimensions must be equal!");
            }
        }


        int y = Math.max(m1.length, m2.length);
        int x = 0;
        for (int[] d : m1) {
            x = Math.max(x, d.length);
        }
        for (int[] d : m2) {
            x = Math.max(x, d.length);
        }

        int[][] arr = new int[y][x];
        for (int i = 0; i < (y); i++) {
            for (int j = 0; j < x; j++) {
                arr[i][j] = m1[i][j] + (actionType == ActionType.ADD ? m2[i][j] : (-m2[i][j]));
            }
        }

        setMatrix(arr);
    }

    //determinant calculation built using regression - so, need to make two methods
    public int getDet() throws MatrixException {
        return det(matrix);
    }

    public static int det(int[][] matrix) throws MatrixException {
        int rez = 0;
        int dimY = matrix.length, dimX = matrix[0].length;
        if (dimY != dimX) {
            throw new MatrixException("Error. The matrix is not square! determinant can`t be found!");
        }

        if ((dimY == 2) && (dimX == 2)) {
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        }


        for (int y = 0; y < dimY; y++) {
            int a = matrix[y][0]; //разложение по 1-му столбцу, оптимизацию писать лень ))
            int[][] minor = new int[dimY - 1][dimX - 1]; //reduce of dimension

            for (int i = 0; i < dimY; i++) {
                if (i == y) continue;
                int z = (i < y) ? i : (i - 1);
                System.arraycopy(matrix[i], 1, minor[z], 0, minor[z].length);
            }
//            printMatrix(minor);
//            System.out.println("a = " + (int)Math.pow((-1),(1+y+1))*a);
            rez += (int) Math.pow((-1), (1 + y + 1)) * a * det(minor);
        }
        return rez;
    }

    public void serialize() throws IOException {
        //** lets try to serialize our instance of Matrix
        FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "\\matrix.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        System.out.println("matrix for serialization is: ");
        printMatrix();
        objectOutputStream.writeObject(this);
    }

    public void deSerialize() throws IOException, ClassNotFoundException {
        //** now lets try to deserialize matrix.ser to instance of Matrix
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\matrix.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Matrix matrixDeSer = (Matrix) objectInputStream.readObject();
        setMatrix(matrixDeSer.getMatrixAsArr());
        System.out.println("DeSerialized matrix is");
        printMatrix();
    }

    enum ActionType {
        ADD, SUB
    }

}
