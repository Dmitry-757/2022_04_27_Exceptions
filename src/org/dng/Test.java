package org.dng;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        Matrix matrix1 = null, matrix2 = null, matrix3 = null;

        try {
            matrix1 = new Matrix(0, 3);
            matrix1.fillMatrixRandom();
            System.out.println("Matrix1 is: ");
            matrix1.printMatrix();
            System.out.println();
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }

        try {
            matrix1 = new Matrix(3, 3);
            matrix1.fillMatrixRandom();
            System.out.println();
            System.out.println("Matrix1 is: ");
            matrix1.printMatrix();
            System.out.println();

            matrix2 = new Matrix(3, 3);
            matrix2.fillMatrixRandom();
            System.out.println("Matrix2 is: ");
            matrix2.printMatrix();
            System.out.println();

            matrix3 = new Matrix(3, 4);
            matrix3.fillMatrixRandom();
            System.out.println("Matrix3 is: ");
            matrix3.printMatrix();
            System.out.println();

            System.out.println("Addition matrix1 and matrix2 is:");
            matrix1.matrixAdd(matrix2);
            matrix1.printMatrix();

            System.out.println("subtraction matrix2 from matrix1 is:");
            matrix1.matrixSub(matrix2);
            matrix1.printMatrix();

            System.out.println();
            System.out.println("determinant of matrix is: " + matrix1.getDet());
            System.out.println();

        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }

        try{
            System.out.println("Try Add matrix3 to matrix1 (matrix have different dimensions");
            matrix1.matrixAdd(matrix3);
            matrix1.printMatrix();
            System.out.println();
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            System.out.println();
            System.out.println("let`s calc determinant of non square matrix");
            System.out.println("the determinant is " + matrix3.getDet());
            System.out.println();
        } catch (MatrixException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //** lets try to serialize our instance of Matrix
        try {
            if (matrix1 != null) {
                System.out.println();
                matrix1.serialize();
                System.out.println("Serialization......");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        //** now lets try to deserialize matrix.ser to instance of Matrix
        Matrix matrix4 = null;
        try {
            matrix4 = new Matrix(3,3);
            matrix4.deSerialize();
        } catch (MatrixException | ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
