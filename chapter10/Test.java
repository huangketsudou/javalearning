package chapter10;

public class Test {
    private String k;
    public Test(String f){
        //this.k = "aa";
        // k =f;这个没问题
        String k;
        k = f;
    }
    public void setK(String a){
        k=a;
    }
    public String getK(){
        return k;
    }
    public static void main(String[] args){
        Test f = new Test("dadwdw");
        //f.setK("sdadw");
        System.out.println(f.getK());
    }
}
