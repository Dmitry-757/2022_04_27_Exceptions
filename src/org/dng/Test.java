package org.dng;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        Matrix matrix1 = null, matrix2 = null;
        try {
            matrix1 = new Matrix(3, 3);
            matrix1.fillMatrixRandom();
            System.out.println("Matrix1 is: ");
            matrix1.printMatrix();
            System.out.println();

            matrix2 = new Matrix(3, 3);
            matrix2.fillMatrixRandom();
            System.out.println("Matrix2 is: ");
            matrix2.printMatrix();
            System.out.println();

            System.out.println("Addition matrix1 and matrix2 is:");
            matrix1.matrixAdd(matrix2);
            matrix1.printMatrix();

            System.out.println("subtraction matrix2 from matrix1 is:");
            matrix1.matrixAdd(matrix2);
            matrix1.printMatrix();

            System.out.println();
            System.out.println("determinant of matrix is: " + matrix1.getDet());
            System.out.println();

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }


        //** lets try to serialize our instance of Matrix
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("d:\\matrix.ser");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            System.out.println("matrix for serialization is");
            if (matrix1 != null) {
                matrix1.printMatrix();
                objectOutputStream.writeObject(matrix1);
            }
            //objectOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //** now lets try to deserialize matrix.ser to instance of Matrix
        try (
                FileInputStream fileInputStream = new FileInputStream("d:\\matrix.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            Matrix matrixDeSer = (Matrix) objectInputStream.readObject();
            //objectOutputStream.close();
            System.out.println("DeSerialized matrix is");
            matrixDeSer.printMatrix();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
