### 使用 AB 工具測試
        - AB 語法 POST
        ```shell
        ab -l -n 1 -c 1 -p pay_test.txt -T application/json http://localhost:8561/wallets/pay
        -l 代表接受變動的長度，加了這行才不會出現 failed length
        -n 代表多少個 request
        -c 同時要發多少個 上限是 20000
        -p 指定 post 內容
        -T 指定 contentType
        後面加上網址
        ab -l -n 100 -c 1 -p pay_test.txt -T application/json http://localhost:8561/wallets/pay
        ab -l -n 1000 -c 1 -p pay_test.txt -T application/json http://localhost:8561/wallets/pay
        ``` 
        - AB 語法 POST
        ```shell
        ab -l -n 100 -c 100 -T application/json http://localhost:8561/wallets/infoPessimisticWrite/3        
        ab -l -n 1000 -c 1000 -T application/json http://localhost:8561/wallets/infoPessimisticWrite/3
        ab -l -n 10000 -c 10000 -T application/json http://localhost:8561/wallets/infoPessimisticWrite/3
        ab -l -n 100 -c 100 -T application/json http://localhost:8561/wallets/infoOptimisticWrite/3
        ab -l -n 1000 -c 1000 -T application/json http://localhost:8561/wallets/infoOptimisticWrite/3
        ab -l -n 10000 -c 10000 -T application/json http://localhost:8561/wallets/infoOptimisticWrite/3          
        ```

## 悲觀鎖測試結果

### 高寫入的情境

- 驗證內容

```sql
select *
from public.wallet;
select count(*)
from public.wallet_log;
```

- -n 100 -c 100

```shell
oncurrency Level:      100
Time taken for tests:   0.076 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      15300 bytes
Total body sent:        18700
HTML transferred:       4800 bytes
Requests per second:    1315.77 [#/sec] (mean)
Time per request:       76.001 [ms] (mean)
Time per request:       0.760 [ms] (mean, across all concurrent requests)
```

- -n 1000 -c 1000

```shell
Concurrency Level:      1000
Time taken for tests:   0.768 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      153000 bytes
Total body sent:        187000
HTML transferred:       48000 bytes
Requests per second:    1301.39 [#/sec] (mean)
Time per request:       768.411 [ms] (mean)
Time per request:       0.768 [ms] (mean, across all concurrent requests)
```

- -n 10000 -c 10000

```shell
Concurrency Level:      10000
Time taken for tests:   7.539 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1530000 bytes
Total body sent:        1870000
HTML transferred:       480000 bytes
Requests per second:    1326.44 [#/sec] (mean)
Time per request:       7539.002 [ms] (mean)
Time per request:       0.754 [ms] (mean, across all concurrent requests)
```

### 高讀取情境

- -n 100 -c 100

```shell
Concurrency Level:      100
Time taken for tests:   0.060 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      16300 bytes
HTML transferred:       5800 bytes
Requests per second:    1666.61 [#/sec] (mean)
Time per request:       60.002 [ms] (mean)
Time per request:       0.600 [ms] (mean, across all concurrent requests)
```

- -n 1000 -c 1000

```shell
Concurrency Level:      1000
Time taken for tests:   0.413 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      163000 bytes
HTML transferred:       58000 bytes
Requests per second:    2421.30 [#/sec] (mean)
Time per request:       413.002 [ms] (mean)
Time per request:       0.413 [ms] (mean, across all concurrent requests)
```

-
- -n 10000 -c 10000

```shell
Concurrency Level:      10000
Time taken for tests:   4.626 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1630000 bytes
HTML transferred:       580000 bytes
Requests per second:    2161.70 [#/sec] (mean)
Time per request:       4625.991 [ms] (mean)
Time per request:       0.463 [ms] (mean, across all concurrent requests)
```

## 樂觀鎖測試結果

### 高寫入的情境

- -n 100 -c 100

```shell
Concurrency Level:      100
Time taken for tests:   1.260 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      16400 bytes
Total body sent:        20200
HTML transferred:       5900 bytes
Requests per second:    79.34 [#/sec] (mean)
Time per request:       1260.375 [ms] (mean)
Time per request:       12.604 [ms] (mean, across all concurrent requests)
```

- -n 1000 -c 1000

```shell
Concurrency Level:      1000
Time taken for tests:   3.152 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      163893 bytes
Total body sent:        202000
HTML transferred:       58893 bytes
Requests per second:    317.23 [#/sec] (mean)
Time per request:       3152.325 [ms] (mean)
Time per request:       3.152 [ms] (mean, across all concurrent requests)
```

- -n 10000 -c 10000

```shell
Concurrency Level:      10000
Time taken for tests:   18.311 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1647784 bytes
Total body sent:        2020000
HTML transferred:       597784 bytes
Requests per second:    546.12 [#/sec] (mean)
Time per request:       18310.838 [ms] (mean)
Time per request:       1.831 [ms] (mean, across all concurrent requests)
```

### 高讀取情境

- -n 100 -c 100

```shell
Concurrency Level:      100
Time taken for tests:   0.028 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      16300 bytes
HTML transferred:       5800 bytes
Requests per second:    3576.15 [#/sec] (mean)
Time per request:       27.963 [ms] (mean)
Time per request:       0.280 [ms] (mean, across all concurrent requests)
```

- -n 1000 -c 1000

```shell
Concurrency Level:      1000
Time taken for tests:   0.282 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      163000 bytes
HTML transferred:       58000 bytes
Requests per second:    3546.09 [#/sec] (mean)
Time per request:       282.001 [ms] (mean)
Time per request:       0.282 [ms] (mean, across all concurrent requests)
```

-
- -n 10000 -c 10000

```shell
Concurrency Level:      10000
Time taken for tests:   3.254 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1630000 bytes
HTML transferred:       580000 bytes
Requests per second:    3073.14 [#/sec] (mean)
Time per request:       3254.000 [ms] (mean)
Time per request:       0.325 [ms] (mean, across all concurrent requests)
```

### 結論
- 高寫入
可以看出在高寫入的情境下，沒辦法發揮樂觀鎖的能力，因為樂觀鎖在更新時會有機會發生更新失敗，這樣會造成大量的重試，所以在高寫入的情境下，悲觀鎖的效能會比較好。
- 高讀取
在高讀取的情境下，樂觀鎖的效能會比較好，因為樂觀鎖在讀取時不會有任何的阻塞，所以在高讀取的情境下，樂觀鎖的效能會比較好。

讀多寫少: 樂觀鎖
讀少寫多: 悲觀鎖

## 分佈式鎖