package com.example;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MyClass {


    public static void main(String arg[]) {


        int[] n = {20, 30, 10, 5, 50, 10};

        int t = 0;

        for (int i = 1; i < n.length; i++) {

            if (n[i] < n[t]) {
                t = i;
            }
        }


        System.out.println(n[t]);

        for (int i = 0; i < n.length; i++) {


            for (int j = i + 1; j < n.length; j++) {

                if (n[i] < n[j]) {

                    int tt = n[i];
                    n[i] = n[j];
                    n[j] = tt;


                }

            }

        }

        System.out.println(Arrays.toString(n));


    }

}
