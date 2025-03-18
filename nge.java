public class nge {
    public static void main(String[] args) {
        int []arr = {3,7,2,5,8,4,9,1,6};
        nger(arr);
        ngel(arr);
        nser(arr);
        nsel(arr);

        
    }
    public static void nger(int [] arr) {
        int max = 0;
        int [] nger = new int[arr.length];
        for(int i = arr.length-1;i>=0;i--) {
             nger[i] = max;
             max = Math.max(max,arr[i]);
        }
        for(int i=0;i<arr.length;i++) {
            System.out.print(nger[i] + " ");
        }
        System.out.println();
    }
    public static void ngel(int [] arr) {
        int max = 0;
        int [] ngel = new int[arr.length];
        for(int i =0;i<arr.length;i++) {
             ngel[i] = max;
             max = Math.max(max,arr[i]);
        }
        for(int i=0;i<arr.length;i++) {
            System.out.print(ngel[i] + " ");
        }
        System.out.println();
    }
    public static void nser(int [] arr) {
        int min = Integer.MAX_VALUE;
        int [] nser = new int [arr.length];
        for(int i = arr.length-1;i>=0;i--) {
            nser[i] = min;
            if(nser[i] == Integer.MAX_VALUE) {
                nser[i] =0;
            }
            min = Math.min(min, arr[i]);
        }
        for(int i=0;i<arr.length;i++) {
            System.out.print(nser[i] + " ");
        }
        System.out.println();
    }
    public static void nsel(int [] arr) {
        int min = Integer.MAX_VALUE;
        int [] nsel = new int[arr.length];
        for(int i=0;i<arr.length;i++) {
            nsel[i] = min;
            if(nsel[i] == Integer.MAX_VALUE) {
                nsel[i] = 0;
            }
            min = Math.min(min, arr[i]);
        }
        for(int i=0;i<arr.length;i++) {
            System.out.print(nsel[i] + " ");
        }
        System.out.println();
    }
}
