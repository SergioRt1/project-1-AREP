package edu.escuelaing.arem.project.apps;

class Operation <T, R, U>{
    private T num1;
    private R num2;
    private U answer;

    Operation(T num1, R num2, U answer) {
        this.num1 = num1;
        this.num2 = num2;
        this.answer = answer;
    }
}