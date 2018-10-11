package com.wallet.walletapi.service.impl;

import com.wallet.walletapi.dao.TransactionRepository;
import com.wallet.walletapi.dao.WalletRepository;
import com.wallet.walletapi.exception.InsufficientBalanceException;
import com.wallet.walletapi.exception.InvalidAmountException;
import com.wallet.walletapi.exception.UserNotFoundException;
import com.wallet.walletapi.model.Transactions;
import com.wallet.walletapi.model.UserRequest;
import com.wallet.walletapi.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Override
    public boolean createAccount(UserRequest userRequest) {
        try{
            if(userRequest.getBalance() < 0)
                throw new InvalidAmountException("Amount is invalid..");

        } catch (Exception e){
            log.error("[createAccount] Exception occurred while creating data. ", e);
            return false;
        }
        walletRepository.insert(new UserRequest(userRequest.getName(), userRequest.getBalance()));
        insertIntoTransaction("Created Account: ", userRequest);
        return true;
    }

    private void insertIntoTransaction(String reason, UserRequest userRequest) {

       log.info("[insertIntoTransaction] Inserting data into transaction table for user details : {}", userRequest);
       String comments = reason + " account with user name : " +userRequest.getName() + " and balance : " + userRequest.getBalance();
       transactionRepository.insert(new Transactions(comments));
    }

    @Override
    public boolean updateBalance(Integer userId, Integer amount) {
        try {
            UserRequest request = walletRepository.findById(userId);
            if(request == null)
                throw new UserNotFoundException("User doesn't exists.");
            if(amount < 0)
                throw new InvalidAmountException("Amount cannot be in negative.");
            float updatedBalance = request.getBalance() + amount;
            request.setBalance(updatedBalance);
            walletRepository.update(request);
            insertIntoTransaction("Updated Balance: ", request);
        } catch (Exception e) {
            log.info("[updateBalance] Exception occurred while updating balance.", e);
            return false;
        }
        return true;
    }

    @Override
    public Float checkBalance(Integer userId) {
        try {
            UserRequest request = walletRepository.findById(userId);
            if (request != null)
                return request.getBalance();
            throw new UserNotFoundException("User doesn't exists.");
        } catch (Exception e){
            log.info("[checkBalance] Exception occurred while fetching balance.", e);
        }
        return null;
    }

    @Override
    public List<Transactions> getAllTransactions(String date) {
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        List<Transactions> transactions = transactionRepository.findByDate(startTime, endTime);
        return transactions;
    }

    @Override
    public boolean transferFunds(Integer fromUser, Integer toUser, Integer amount) {
        try{
            UserRequest getBalanceFromUser = walletRepository.findById(fromUser);
            if(getBalanceFromUser == null){
                throw new UserNotFoundException("No user found exception.");
            }
            if(amount > getBalanceFromUser.getBalance()) {
                throw new InsufficientBalanceException("No sufficient balance present in account.");
            }
            else {
                UserRequest toUserRequest = walletRepository.findById(toUser);
                if(toUserRequest == null){
                    throw new UserNotFoundException("No user found exception.");
                }
                updateBalance(toUser, amount);
                getBalanceFromUser.setBalance(getBalanceFromUser.getBalance()-amount);
                walletRepository.insert(getBalanceFromUser);
                insertIntoTransaction("Transfer Funds to user id " + toUser + " from ", toUserRequest);
            }
        } catch (Exception e) {
            log.info("[transferFunds] Exception occurred while transferring funds.", e);
            return false;
        }
        return true;
    }

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;
}
