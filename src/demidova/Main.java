package demidova;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
      manageTwoArrs();
      manageOneArr();
    }


    public static void manageOneArr(){
        float[] arr = new float[SIZE];
        fillArr(arr);
        long a = System.currentTimeMillis();
        calc(arr);
        System.out.println(System.currentTimeMillis() - a);
    }

    public synchronized static void manageTwoArrs(){
        float[] arr = new float[SIZE];
        fillArr(arr);
        float[] a = new float[HALF];
        float[] b = new float[HALF];
        long startTimeA = System.currentTimeMillis();
        System.arraycopy(arr, 0, a, 0, HALF);
        System.arraycopy(arr, HALF, b, 0, HALF);

        Thread t1 = new Thread(() -> calc(a));
        Thread t2 = new Thread(() -> calc(b));
        t1.start();
        t2.start();

       /* new Thread(() -> {
            float[] a1 = calc(a);
            System.arraycopy(a1, 0, a, 0, a1.length);
        }).start();

        new Thread(() -> {
            float[] b1 = calc(b);
            System.arraycopy(b1, 0, b, 0, b1.length);
        }).start();
*/
        System.arraycopy(a, 0, arr, 0, HALF);
        System.arraycopy(b, 0, arr, HALF, HALF);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - startTimeA);

    }
    public static void fillArr(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
    }

    public  static void calc(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

}
