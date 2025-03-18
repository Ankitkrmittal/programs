public class insertionsort {
    public static void main(String[] args) {
        int [] arr = {7,5,6,2,8,3,1,9,4};
        selectionSort(arr);
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i] + " ");
        }

    }
    public static void selectionSort(int []arr) {
        for(int i=1;i<arr.length;i++) {
            int key = arr[i];
            int j= i-1;
            while(j>=0 && arr[j] > key) {
                  arr[j+1] = arr[j];
                  --j;
            }
            arr[j+1] = key;
        }
    }
}
