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

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }

        try {
            matrix2 = new Matrix(3, 3);
            matrix2.fillMatrixRandom();
            System.out.println("Matrix2 is: ");
            matrix2.printMatrix();

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Addition matrix1 and matrix2 is:");
            if (matrix1 != null) {
                matrix1.matrixAddition(matrix2).printMatrix();
            }

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }


        //** lets try to serialize our instance of Matrix
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("d:\\matrix.ser");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
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
                FileInputStream fileInputStream  = new FileInputStream("d:\\matrix.ser");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
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
