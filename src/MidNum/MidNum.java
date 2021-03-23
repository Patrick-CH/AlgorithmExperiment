package MidNum;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MidNum {

    public static ArrayList<Integer> ReadInput() throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("src\\MidNum\\in.txt"));
            String sb;
            ArrayList<Integer> nums = new ArrayList<Integer>();
            while (in.ready()) {
                sb = (new String(in.readLine()));
                String[] s;
                s = sb.split(" ");
                for(String i : s){
                    nums.add(Integer.parseInt(i));
                }
            }
            in.close();
            return nums;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 求单个数组的中位数
    public static double mid(ArrayList<Integer> arrayList){
        int len = arrayList.size();
        int mid = len/2;
        if(mid*2 == len){
            return (arrayList.get(mid) + arrayList.get(mid-1))/2.0;
        }else {
            return (double)arrayList.get(mid);
        }
    }

    // 取数组的一半，front为真则取前一半，反之取后一半
    public static ArrayList<Integer> half(ArrayList<Integer> arrayList, boolean front, boolean safe){
        int len = arrayList.size();
        int mid = len/2;
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(front) {
            if (mid * 2 == len) {
                for (int i = 0; i < mid; i++) {
                    list.add(arrayList.get(i));
                }
                if(!safe){
                    // 不能直接折半则保留中间的数
                    list.add(arrayList.get(mid));
                }
            } else {
                for (int i = 0; i <= mid; i++) {
                    list.add(arrayList.get(i));
                }
            }
            return list;
        } else {
            if (mid * 2 == len) {
                if (!safe){
                    // 不能直接折半则保留中间的数
                    list.add(arrayList.get(mid-1));
                }
                for (int i = mid; i < len; i++) {
                    list.add(arrayList.get(i));
                }
            } else {
                for (int i = mid; i < len; i++) {
                    list.add(arrayList.get(i));
                }
            }
            return list;
        }
    }

    // 判断能否直接折半
    public static boolean safe_to_cut(ArrayList<Integer> X, ArrayList<Integer> Y){
        int len = X.size();
        int mid = len/2;
        if (mid*2 == len){
            int x1 = X.get(mid-1);
            int x2 = X.get(mid);
            int y1 = Y.get(mid-1);
            int y2 = Y.get(mid);
            if (mid(X) > mid(Y)) {
                if (x2 < y2) {
                    return false;
                }
                if (x1 < y1){
                    return false;
                }
            }
            else if (mid(X) < mid(Y)){
                if (y2 < x2){
                    return false;
                }
                if (y1 < x1){
                    return false;
                }
            }
        }
        return true;
    }

    // 计算合并后的中位数
    public static double getMid(ArrayList<Integer> X, ArrayList<Integer> Y){
        double mid_x = mid(X);
        double mid_y = mid(Y);
        ArrayList<Integer> array = null;
        // 如果X Y只有一个数，则返回他们平均数
        if (X.size() == 1 && Y.size() == 1){
            return (mid_x + mid_y)/2.0;
        }
        // 如果X Y 各自剩余2个 考虑到折半安全问题，剩余2个时可能已经不能再次折半
        if (X.size() == 2 && Y.size() == 2){
            array = new ArrayList<Integer>();
            array.addAll(X);
            array.addAll(Y);
            array.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            return (array.get(1) + array.get(2))/2.0;
        }
        if (mid_x > mid_y){
            // 如果X的中位数大于Y的，取X的前半部分，Y的后半部分
            if (safe_to_cut(X, Y)) {
                // 如果可以直接折半
                X = half(X, true, true);
                Y = half(Y, false, true);
            } else {
                X = half(X, true, false);
                Y = half(Y, false, false);
            }
            return getMid(X, Y);
        } else if ( mid_x == mid_y){
            // 如果X Y中位数相等，返回这个值
            return (double) mid_x;
        } else {
            // 如果X的中位数小于Y的，取X的后半部分，Y的前半部分
            if (safe_to_cut(X, Y)) {
                // 如果可以直接折半
                X = half(X, false, true);
                Y = half(Y, true, true);
            } else {
                X = half(X, false, false);
                Y = half(Y, true, false);
            }
            return getMid(X, Y);
        }
    }

    public static void output(double x) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("src\\MidNum\\out.txt"));
        out.write(String.format("%f", x));
        out.close();
    }

    public static void main(String[] args) {
        // 读取输入
        ArrayList<Integer> nums = null;
        try {
            nums = ReadInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将输入写入X Y
        ArrayList<Integer> X = new ArrayList<Integer>();
        ArrayList<Integer> Y = new ArrayList<Integer>();
        for (int i = 1; i <= nums.get(0); i++) {
            X.add(nums.get(i));
            Y.add(nums.get(i + nums.get(0)));
        }
        // 计算中位数
        double result = getMid(X, Y);
        // 输出结果
        try {
            output(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
