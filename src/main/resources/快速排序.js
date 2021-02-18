function main() {
    data = [2,3,1,6,3,78,2,5,10,21];
    result = quickSort(0,data.length-1,data);
    console.log(result);
}


function quickSort(left,right,data) {
    // 退出循环条件
    if(left>right){
        return;
    }
    // 左指针
    i = left;
    // 右指针
    j = right;
    // 基准位置
    temp = data[left];

    while (i < j){
        //
        while (temp <= data[j] && i < j){
            j--;
        }
        while (temp >= data[i] && i < j){
            i++;
        }
        if(i < j){
            tmp = data[j];
            data[j] = data[i];
            data[i] = tmp;
        }
    }
    data[left] = data[i];
    data[i] = temp;

    quickSort(left,right-1,data);
    quickSort(right+1,right,data);

    return data;
}

main()


