package MidNum;

import java.io.*;
import java.util.ArrayList;

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
    public static ArrayList<Integer> half(ArrayList<Integer> arrayList, boolean front){
        int len = arrayList.size();
        int mid = len/2;
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(front) {
            if (mid * 2 == len) {
                for (int i = 0; i < mid; i++) {
                    list.add(arrayList.get(i));
                }
            } else {
                for (int i = 0; i < mid; i++) {
                    list.add(arrayList.get(i));
                }
            }
            return list;
        } else {
            if (mid * 2 == len) {
                for (int i = mid; i < len; i++) {
                    list.add(arrayList.get(i));
                }
            } else {
                for (int i = mid + 1; i < len; i++) {
                    list.add(arrayList.get(i));
                }
            }
            return list;
        }
    }

    // 计算合并后的中位数
    public static double getMid(ArrayList<Integer> X, ArrayList<Integer> Y){
        double mid_x = mid(X);
        double mid_y = mid(Y);
        // 如果X Y只有一个数，则返回他们平均数
        if (X.size() == 1 && Y.size()==1){
            return (mid_x + mid_y)/2.0;
        }
        if (mid_x > mid_y){
            // 如果X的中位数大于Y的，取X的前半部分，Y的后半部分
            X = half(X, true);
            Y = half(Y, false);
            return getMid(X, Y);
        } else if ( mid_x == mid_y){
            // 如果X Y中位数相等，返回这个值
            return (double) mid_x;
        } else {
            // 如果X的中位数小于Y的，取X的后半部分，Y的前半部分
            X = half(X, false);
            Y = half(Y, true);
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
