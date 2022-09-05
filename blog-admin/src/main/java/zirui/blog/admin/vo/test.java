package zirui.blog.admin.vo;

import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 */

// @lc code=start
class Solution {
    static Map<Integer, Integer> param = new HashMap<>();

    public static int maxArea(int[] height) {
        int w = height.length;
        param.put(1, 0);
        param.put(2, height[0]);
        param.put(3, w - 1);
        param.put(4, height[w - 1]);
        int maxArea = Math.min(param.get(2), param.get(4)) * param.get(3);
        param.put(9, maxArea);

        for (int i = 1; i < w - 1; i++) {
            for (int j = w - 2; j > 0; j--) {
                if (height[j] > param.get(4) && height[i] > param.get(2)) {
                    utool(i, height[i], j, height[j]);
                } else {
                    if (height[j] > param.get(4)) {
                        utool(param.get(1), param.get(2), j, height[j]);
                    }
                    if (height[i] > param.get(2)) {
                        utool(i, height[i], param.get(3), param.get(4));
                    }
                }
            }
        }
        return param.get(9);
    }

    private static void utool(int iLeft, int hLeft, int iRight, int hRight) {
        int t1, t2, t3;
        t1 = Math.min(hLeft, hRight);
        t2 = Math.abs(iLeft - iRight);
        t3 = t1 * t2;
        if (param.get(9) < t3) {
            param.put(9, t3);
            if (iLeft != param.get(1)) {
                param.put(1, iLeft);
                param.put(2, hLeft);
            }
            if (iRight != param.get(3)) {
                param.put(3, iRight);
                param.put(4, hRight);
            }
        }
    }

    public static void main(String[] args) {
        int[] test = new int[] { 1,8,6,2,5,4,8,25,7};
        System.out.println(maxArea(test));
    }
}
// @lc code=end
