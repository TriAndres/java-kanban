import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> list1 =  List.of(1,2,3,4);
        String a1 ="";
        for (Integer i : list1) {
            a1 += Integer.toString(i) + ", ";
        }
        a1 += "1 2";
        //System.out.println(a1 + ",1");

        //System.out.println(String.join("","test,",s));

        String line2 = String.join("","test, ",list1.toString());

        String[] a = a1.split("[, ]");
        for (String string : a) {
            System.out.print(string +" ");
        }
    }
}
//String s = "";
//        int count = 0;
//        for (Integer i : list1) {
//            count++;
//            s += i;
//            if (count == list1.size()) {
//                s += "";
//            } else {
//                s += ",";
//            }
//        }