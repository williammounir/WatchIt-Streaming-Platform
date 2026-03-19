#include <bits/stdc++.h>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    cin >> t;

    while (t--) {
        int n;
        cin >> n;

        vector<int> a(n);
        map<int,int> freq;

        for (int i = 0; i < n; i++) {
            cin >> a[i];
            freq[a[i]]++;
        }

        bool ok = true;
        for (auto &x : freq) {
            if (x.second > 2) {
                ok = false;
                break;
            }
        }

        if (!ok) {
            cout << "NO\n";
            continue;
        }

        vector<int> p(n, 0), q(n, 0);
        set<int> availableP, availableQ;

        for (int i = 1; i <= n; i++) {
            availableP.insert(i);
            availableQ.insert(i);
        }

        vector<int> order(n);
        iota(order.begin(), order.end(), 0);

        sort(order.begin(), order.end(), [&](int i, int j) {
            return a[i] < a[j];
        });

        map<int,int> usedInP;

        for (int idx : order) {

            int val = a[idx];

            if (!usedInP[val]) {
                p[idx] = val;
                availableP.erase(val);

                auto it = availableQ.upper_bound(val);
                if (it == availableQ.begin()) {
                    ok = false;
                    break;
                }
                --it;

                q[idx] = *it;
                availableQ.erase(it);

                usedInP[val] = 1;
            }
            else {
                q[idx] = val;
                availableQ.erase(val);

                auto it = availableP.upper_bound(val);
                if (it == availableP.begin()) {
                    ok = false;
                    break;
                }
                --it;

                p[idx] = *it;
                availableP.erase(it);
            }
        }

        if (!ok) {
            cout << "NO\n";
        }
        else {
            cout << "YES\n";
            for (int x : p) cout << x << " ";
            cout << "\n";
            for (int x : q) cout << x << " ";
            cout << "\n";
        }
    }

    return 0;
}