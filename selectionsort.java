public class selectionsort {
    public static void main(String[] args) {
        int [] arr = {9,8,87,2,1,3,4,5};
        SelectionSort(arr);
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i] + " ");
        }
    }
    public static void SelectionSort(int [] arr) {
        for(int i=0;i<arr.length-1;i++) {
            int idx = minival(arr,i);
            int temp = arr[i];
            arr[i] = arr[idx];
            arr[idx] = temp;

        }
    }
    public static int minival(int [] arr, int a) {
        int mini = a;
        for(int i = a+1;i<arr.length;i++) {
             if(arr[mini] > arr[i]) {
                mini = i;
             }
        }
        return mini;
    }
}
