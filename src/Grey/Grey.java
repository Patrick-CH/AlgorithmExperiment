package Grey;

import java.io.*;
import java.util.ArrayList;

public class Grey {

    public static int ReadInput() throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("src\\Grey\\in.txt"));
            String s;
            if (in.ready()) {
                s = (new String(in.readLine()));
                in.close();
                return Integer.parseInt(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<StringBuffer> getGrey(int n){
        ArrayList<StringBuffer> list = new ArrayList<StringBuffer>();
        if (n==1){
            list.add(new StringBuffer("0"));
            list.add(new StringBuffer("1"));
            return list;
        }
        ArrayList<StringBuffer> new_list = new ArrayList<StringBuffer>();
        ArrayList<StringBuffer> next_list = getGrey(n-1);
        for(StringBuffer s: next_list){
            new_list.add(new StringBuffer("0").append(s));
        }
        for (int i = next_list.size() - 1; i > -1 ; i--) {
            new_list.add(new StringBuffer("1").append(next_list.get(i)));
        }
        return new_list;
    }

    public static void output(StringBuffer sb) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("src\\Grey\\out.txt"));
        out.write(String.valueOf(sb));
        out.close();
    }

    public static void main(String[] args) {
        int n = 0;
        //读取输入
        try {
            n = ReadInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (n<1){
            System.out.println("Invalid Input!");
            return;
        }
        StringBuffer sb = new StringBuffer();
        for(StringBuffer s: getGrey(n)){
            sb.append(s);
            sb.append("\n");
        }
        //输出
        try {
            output(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
