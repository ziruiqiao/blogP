package zirui.blog.admin.config;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: testZ
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/28 12:43
 */
public class testZ {
    public static String convert(String s, int numRows) {
        if(numRows == 1) return s;
        int size=s.length(), t = numRows*2 - 2, carry = size%t;
        int n = size/t + (carry == 0? 0:1), counter = 0, formula;
        char[] result = new char[size];
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<n; j++) {
                if(j != n-1 || i < carry || carry == 0) {
                        result[counter++] = s.charAt(i + t*j);
                    if(i != 0 && i != numRows - 1) {
                        formula = t * (j + 1) - i;
                        if (formula < size)
                            result[counter++] = s.charAt(formula);
                    }
                }
            }
        }

        return new String(result);
    }

    public static void main(String[] args) {
        String result = convert("PAYPALISHIRING", 3);

        System.out.println(result);
    }
}
