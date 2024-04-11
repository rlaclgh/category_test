def coin_combination(sum_, coins):
    
    ### dp[i] i 값을 만들 수 있는 경우의 수 
    dp = [0 for _ in range(sum_ + 1)]
    
    dp[0] = 1
    
    for coin in coins:
        for i in range(coin, sum_ + 1):
            dp[i] += dp[i - coin]
            
    print(dp[sum_])



if __name__== "__main__":
    coin_combination(4, [1,2,3])
    coin_combination(10, [2,5,3,6])


