#include <bits/stdc++.h>
using namespace std;
vector<string> res;

// Hàm liệt kê xâu nhị phân độ dài n
void generateBinaryStrings(int n, vector<int>& binaryString, int index) {
    if (index == n) {
        string binary = "";
        for (int i = 0; i < n; i++) {
            binary = binary + "" + binaryString[i];
        }
        res.push_back(binary);
        return;
    }

    binaryString[index] = 0;
    generateBinaryStrings(n, binaryString, index + 1);

    binaryString[index] = 1;
    generateBinaryStrings(n, binaryString, index + 1);
}

int main() {
    int n, k;
    cin >> n >> k;

    vector<int> binaryString(n);
    generateBinaryStrings(n, binaryString, 0);

    for(int i = 0; i < res.size(); i++) {
        if(i % k == 0) {
            cout << res[i] << endl;
        }
    }
    return 0;
}