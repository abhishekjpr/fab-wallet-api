package com.wallet.walletapi.controller;

import com.wallet.walletapi.model.Transactions;
import com.wallet.walletapi.model.UserRequest;
import com.wallet.walletapi.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class WalletController {

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public boolean createAccount(@RequestBody UserRequest userRequest) {
        log.info("[createAccount] Received request to create account : {}", userRequest);
        boolean isSuccess = walletService.createAccount(userRequest);
        return isSuccess;
    }

    @RequestMapping(value = "/addMoney/{userid}/{amount}", method = RequestMethod.GET)
    public boolean addMoney(@PathVariable(name = "amount") Integer amount, @PathVariable(name = "userid") Integer userId){
        log.info("[addMoney] Adding money : {} for user id : {}", amount, userId);
        boolean result = walletService.updateBalance(userId, amount);
        return result;
    }

    @RequestMapping(value = "/checkBalance/{userid}", method = RequestMethod.GET)
    public Float checkBalance(@PathVariable(name = "userid") Integer userId){
        log.info("[checkBalance] Checking balance for user id : {}", userId);
        Float balance = walletService.checkBalance(userId);
        return balance;
    }

    @RequestMapping(value = "/listOfTransaction", method = RequestMethod.POST)
    public List<Transactions> getAllTransaction(@RequestBody String date){
        List<Transactions> transactions = walletService.getAllTransactions(date);
        return transactions;
    }

    @RequestMapping(value = "/transferMoney/{fromuser}/{touser}/{amount}", method = RequestMethod.GET)
    public boolean transferAmount(@PathVariable(name = "fromuser") Integer fromUser,
                                  @PathVariable(name = "touser") Integer toUser,
                                  @PathVariable(name = "amount") Integer amount) {
        log.info("[transferAmount] Received request to transfer balance from user {} to {} and amount is {}", fromUser, toUser, amount);
        boolean result = walletService.transferFunds(fromUser, toUser, amount);
        return result;
    }

    @Autowired
    WalletService walletService;
}
